package com.akjaw.sqldelight.testing

import com.akjaw.sqldelight.testing.data.database.createInMemorySqlDriver
import com.akjaw.sqldelight.testing.data.time.TimestampProvider
import com.squareup.sqldelight.db.SqlDriver
import org.koin.dsl.module

val testModule = module {
    single { MockTimestampProvider() }
    single<TimestampProvider> { get<MockTimestampProvider>() }
    single<SqlDriver> { createInMemorySqlDriver() }
}