package com.example.moviedb.ui.more

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class AppExecutors constructor(
        val ioContext: CoroutineContext = Dispatchers.IO,
        val networkContext: CoroutineContext = Dispatchers.IO,
        val uiContext: CoroutineContext = Dispatchers.Main)
