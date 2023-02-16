package com.example.jetpackinstrumentation

import android.util.Log


/**
 *
 * @author gleb.maliborsky
 */
public class ClickableComposeCallback(private val function: Function0<Unit>) : Function0<Unit> {

    override fun invoke() {
        Log.d("ClickableCompose", "Hello")
        function.invoke()
    }
}
