package com.akjaw.sqldelight.testing.domain

import akjaw.com.sqldelight.testing.db.TableQueries
import akjaw.com.sqldelight.testing.db.TimestampItemEntity
import com.akjaw.sqldelight.testing.data.time.TimestampProvider
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTimestampItems(
    private val tableQueries: TableQueries,
) {

    fun execute(): Flow<List<TimestampItem>> =
        tableQueries.selectAll()
            .asFlow()
            .mapToList()
            .map { items ->
                items.map { TimestampItem(it.id, it.name, it.version.toInt()) }
            }
}
