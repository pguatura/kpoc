package com.pguatura.kpoc.api

expect fun platformName(): String

fun createApplicationScreenMessage() : String {
    return "Kotlin Rocks bla on ${platformName()}"
}