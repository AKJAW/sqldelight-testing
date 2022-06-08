package com.akjaw.sqldelight.testing.test

import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.mp.KoinPlatformTools

object TestDoubleRetriever : KoinComponent {

    val mockTimestampProvider: MockTimestampProvider
        get() = get()
}
