package com.akjaw.sqldelight.testing.android

import android.app.Application
import android.content.Context
import com.akjaw.sqldelight.testing.sharedModules
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                module {
                    single<Context> { this@MainApp }
                },
                *sharedModules
            )
        }
    }
}
