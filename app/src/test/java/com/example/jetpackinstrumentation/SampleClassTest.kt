package com.example.jetpackinstrumentation

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.be
import io.kotest.matchers.should
import io.mockk.*

/**
 *
 * @author gleb.maliborsky
 */
class SampleClassTest : FunSpec({

    val collaborator1 = mockk<Collaborator1>()
    val collaborator2 = mockk<Collaborator2>()
    val collaborator3 = mockk<Collaborator3>()

    val sut = SampleClass(collaborator1, collaborator2, collaborator3)

    beforeTest {
        every { collaborator3.doSomething(any()) } just Runs
    }

    test("more than 5") {
        every { collaborator2.createInt() } returns 6

        sut.start()

        verify(exactly = 1) { collaborator3.doSomething("more than 5") }
    }

    test("less than 5 and string empty") {
        every { collaborator2.createInt() } returns 4
        every { collaborator1.createString() } returns ""

        sut.start()

        verify(exactly = 1) { collaborator3.doSomething("empty string") }
    }

    test("less than 5 and string NOT empty") {
        every { collaborator2.createInt() } returns 4
        every { collaborator1.createString() } returns "not empty"

        val exception = shouldThrow<IllegalStateException> {
            sut.start()
        }

        exception.message should be("this should never happen")
    }
})