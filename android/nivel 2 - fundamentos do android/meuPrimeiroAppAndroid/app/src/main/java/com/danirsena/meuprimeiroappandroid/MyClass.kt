package com.danirsena.meuprimeiroappandroid

import android.content.Context
import java.lang.ref.WeakReference

class MyClass(val context: Context) {

    private val weakReference = WeakReference(context)

    fun doSomething() {
        weakReference.get()
    }
}