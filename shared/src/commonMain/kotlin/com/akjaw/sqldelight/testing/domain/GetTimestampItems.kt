package com.akjaw.sqldelight.testing.domain

import akjaw.com.sqldelight.testing.db.TableQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class GetTimestampItems(
    private val tableQueries: TableQueries,
) {

    fun execute(): Flow<List<TimestampItem>> =
        tableQueries.selectAll()
            .asFlow()
            .mapToList()
            .map { items ->
                items.map { TimestampItem(it.id, formatTime(it.time), it.version) }
            }

    private fun formatTime(milliseconds: Long): String {
        val instant = Instant.fromEpochMilliseconds(milliseconds)
        return instant.toLocalDateTime(TimeZone.UTC).toString()
    }
}
