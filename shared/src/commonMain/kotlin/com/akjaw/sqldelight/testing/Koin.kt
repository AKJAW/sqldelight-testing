package com.akjaw.sqldelight.testing

import akjaw.com.sqldelight.testing.db.TableQueries
import com.akjaw.sqldelight.testing.data.time.DateTimeTimestampProvider
import com.akjaw.sqldelight.testing.data.time.TimestampProvider
import com.akjaw.sqldelight.testing.db.AppDatabase
import com.akjaw.sqldelight.testing.domain.AddTimestampItem
import com.akjaw.sqldelight.testing.domain.GetTimestampItems
import com.akjaw.sqldelight.testing.domain.UpdateTimestampItem
import com.akjaw.sqldelight.testing.presentation.CommonItemsScreenViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            platformModule,
            coreModule
        )
    }

    return koinApplication
}

val coreModule = module {
    factory<TimestampProvider> { DateTimeTimestampProvider() }
    factory { AddTimestampItem(get(), get()) }
    factory { UpdateTimestampItem(get(), get()) }
    factory { GetTimestampItems(get()) }
    factory { CommonItemsScreenViewModel(get(), get(), get()) }
    single<AppDatabase> {
        AppDatabase(get())
    }
    single<TableQueries> { get<AppDatabase>().tableQueries }
}

expect val platformModule: Module
