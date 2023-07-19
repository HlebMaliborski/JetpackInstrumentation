package com.example.jetpackinstrumentation

/**
 *
 * @author gleb.maliborsky
 */
interface Collaborator1 {
    fun createString(): String
}

interface Collaborator2 {
    fun createInt(): Int
}

interface Collaborator3 {
    fun doSomething(input: String)
}

class SampleClass(
    val collaborator1: Collaborator1,
    val collaborator2: Collaborator2,
    val collaborator3: Collaborator3,
) {

    fun start() {
        if (collaborator2.createInt() > 5) {
            collaborator3.doSomething("more than 5")

        } else if (collaborator1.createString().isEmpty()) {
            collaborator3.doSomething("empty string")

        } else {
            throw IllegalStateException("this should never happen")
        }
    }

}