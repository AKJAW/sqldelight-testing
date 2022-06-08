package com.akjaw.sqldelight.testing.test

import com.akjaw.sqldelight.testing.data.time.TimestampProvider
import com.akjaw.sqldelight.testing.db.AppDatabase
import com.akjaw.sqldelight.testing.sharedModules
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

fun setUpKoinForTesting() {
    val modules = listOf(
        *sharedModules,
        module {
            single<SqlDriver> { createInMemorySqlDriver() }
            single { MockTimestampProvider() }
            single<TimestampProvider> { get<MockTimestampProvider>() }
        }
    )
    unloadKoinModules(modules)
    loadKoinModules(modules)
}
