package com.akjaw.sqldelight.testing.test

import com.akjaw.sqldelight.testing.coreModule
import com.akjaw.sqldelight.testing.data.time.TimestampProvider
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

fun startTestKoin() {
    startKoin {
        modules(
            coreModule,
            testModule,
        )
    }
}

fun stopTestKoin() {
    stopKoin()
}

val testModule = module {
    single { MockTimestampProvider() }
    single<TimestampProvider> { get<MockTimestampProvider>() }
    single<SqlDriver> { createInMemorySqlDriver() }
}
