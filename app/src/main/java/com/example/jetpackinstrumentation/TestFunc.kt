package com.example.jetpackinstrumentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 *
 * @author gleb.maliborsky
 */
public class TestFunc(private val onValueChangeFinished: (() -> Unit)? = null) : Function0<Unit> {
    init {
        println("Jopa")
    }

    private var f1: Float = 0f
    public fun setFloat(f2: Float) {
        f1 = f2
    }

    override fun invoke() {
        onValueChangeFinished?.invoke()
        Log.d("Ura", f1.toString())
        val a = 1
    }
}

fun jopa1() {

}

fun jopa2() {

}

@Composable
fun comp1() {

}

@Composable
fun comp2() {
    comp1()
}

@Composable
fun test(value: Float, onValueChangeFinished: (() -> Unit)? = null): TestFunc {
    val a = remember(onValueChangeFinished) {
        TestFunc(onValueChangeFinished)
    }
    a.setFloat(value)
    return a
}
