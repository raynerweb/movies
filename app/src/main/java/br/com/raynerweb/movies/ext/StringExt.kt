package br.com.raynerweb.movies.ext

fun String?.urlImage(): String =
    this?.let { "https://image.tmdb.org/t/p/w500$it" } ?:
    kotlin.run { "" }
