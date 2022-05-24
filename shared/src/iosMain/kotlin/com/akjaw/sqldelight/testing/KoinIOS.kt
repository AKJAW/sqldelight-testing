package com.akjaw.sqldelight.testing

import co.touchlab.kermit.Logger
import com.akjaw.sqldelight.testing.db.AppDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.ObjCProtocol
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

fun initKoinIos(): KoinApplication = startKoin {
    modules(*sharedModules)
}

actual val platformModule = module {
    single<SqlDriver> { NativeSqliteDriver(AppDatabase.Schema, "AppDatabase") }
}

fun Koin.get(objCClass: ObjCClass): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return getOrThrow { get(kClazz) }
}

fun Koin.get(objCProtocol: ObjCProtocol): Any {
    val kClazz = getOriginalKotlinClass(objCProtocol)!!
    return getOrThrow { get(kClazz) }
}

fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return getOrThrow { get(kClazz, qualifier) { parametersOf(parameter) } }
}

fun Koin.get(objCClass: ObjCClass, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return getOrThrow { get(kClazz, null) { parametersOf(parameter) } }
}

fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return getOrThrow { get(kClazz, qualifier, null) }
}

private val kermit = Logger.withTag("Koin")

private fun getOrThrow(get: () -> Any): Any {
    try {
        return get()
    } catch (error: Throwable) {
        kermit.i { "error: $error" }
        throw error
    }
}
