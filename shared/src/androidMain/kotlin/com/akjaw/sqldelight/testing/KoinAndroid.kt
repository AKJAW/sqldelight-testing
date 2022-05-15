package com.akjaw.sqldelight.testing

import com.akjaw.sqldelight.testing.db.AppDatabase
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            AppDatabase.Schema,
            get(),
            "AppDatabase"
        )
    }

    single<Settings> {
        AndroidSettings(get())
    }

    single {
        OkHttp.create()
    }
}
