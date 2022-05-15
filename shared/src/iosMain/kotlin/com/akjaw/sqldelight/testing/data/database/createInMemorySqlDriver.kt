package com.akjaw.sqldelight.testing.data.database

import co.touchlab.sqliter.DatabaseConfiguration
import com.akjaw.sqldelight.testing.db.AppDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.squareup.sqldelight.drivers.native.wrapConnection

private var index = 0

actual fun createInMemorySqlDriver(): SqlDriver {
    index++
    val schema = AppDatabase.Schema
    return NativeSqliteDriver(
        DatabaseConfiguration(
            name = "test-$index.db",
            version = schema.version,
            create = { connection ->
                wrapConnection(connection) { schema.create(it) }
            },
            upgrade = { connection, oldVersion, newVersion ->
                wrapConnection(connection) { schema.migrate(it, oldVersion, newVersion) }
            },
            inMemory = true
        )
    )
}