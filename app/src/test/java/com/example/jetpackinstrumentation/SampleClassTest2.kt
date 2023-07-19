package com.example.jetpackinstrumentation

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.system.NoSystemErrListener.beforeTest
import io.kotest.matchers.be
import io.kotest.matchers.should
import io.mockk.*

/**
 *
 * @author gleb.maliborsky
 */
class SampleClassTest2 : BehaviorSpec({

    val collaborator1 = mockk<Collaborator1>()
    val collaborator2 = mockk<Collaborator2>()
    val collaborator3 = mockk<Collaborator3>()

    val sut = SampleClass(collaborator1, collaborator2, collaborator3)

    beforeTest {
        every { collaborator3.doSomething(any()) } just Runs
    }

    given("number is more than 5") {
        every { collaborator2.createInt() } returns 6

        `when`("sut starts") {
            sut.start()

            then("it should invoke collaborator3 with empty string") {
                verify(exactly = 1) { collaborator3.doSomething("more than 5") }
            }
        }
    }

    given("number is less than 5 and ") {
        every { collaborator2.createInt() } returns 4

        and("string is empty") {
            every { collaborator1.createString() } returns ""
        }

        `when`("sut starts") {
            sut.start()

            then("it should invoke collaborator3 with empty string") {
                verify(exactly = 1) { collaborator3.doSomething("empty string") }
            }
        }

        and("string is NOT empty") {
            every { collaborator1.createString() } returns "hello"
        }
        `when`("sut starts") {
            val exception = shouldThrow<IllegalStateException> {
                sut.start()
            }

            then("it should invoke collaborator3 with empty string") {
                exception.message should be("this should never happen")
            }
        }
    }
})