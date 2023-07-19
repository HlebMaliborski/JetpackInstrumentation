package com.example.jetpackinstrumentation

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldHaveLength

/**
 *
 * @author gleb.maliborsky
 */
class MyFirstTestClass : FunSpec({

    beforeEach {
        println("Hello from $it")
    }

    test("sam should be a three letter name") {
        "sam".shouldHaveLength(3)
    }

    test("samу should be a three letter name") {
        "samу".shouldHaveLength(4)
    }

    afterEach {
        println("Goodbye from $it")
    }

})