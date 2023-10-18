//
//  Copyright © 2023 Dynatrace LLC. All rights reserved.
//

package com.example.jetpackinstrumentation

/**
 *
 */
class SomeClass {
    private val localField: Int = 1
    fun someMethod(methodField: Int): Boolean {
        return methodField == localField
    }
}
