package com.geniusforapp.movies.ui.ktx

inline fun <reified T> Collection<T>.printString() = toTypedArray().contentToString().replace("[", "")
    .replace("]", "")