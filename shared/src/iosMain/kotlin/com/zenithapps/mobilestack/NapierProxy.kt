package com.zenithapps.mobilestack

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun initializeDebug() {
    Napier.base(DebugAntilog())
}

fun initializeRelease(antilog: Antilog) {
    Napier.base(antilog)
}
