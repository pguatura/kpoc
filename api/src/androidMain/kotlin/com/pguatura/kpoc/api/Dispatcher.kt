package com.pguatura.kpoc.api

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


internal actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Default

actual fun encapsulate(function: () -> Unit){
    GlobalScope.apply {
        launch(Dispatchers.Main) {
            function()
        }
    }
}