package com.example.jetpackinstrumentation

import android.util.Log

/**
 *
 * @author gleb.maliborsky
 */
public class RangeSliderCallback {
    fun test(a: ClosedFloatingPointRange<*>) {
        Log.d(
            "Ura", "${a.start} ${
                a.endInclusive
            }"
        )
    }

    fun test() {
        Log.d("Ura", "Ura")
    }

    fun test(a: Float, b: Float) {
        Log.d("Ura", "$a  $b")
    }
}
