package com.akjaw.sqldelight.testing.presentation

import com.akjaw.sqldelight.testing.domain.AddTimestampItem
import com.akjaw.sqldelight.testing.domain.GetTimestampItems
import com.akjaw.sqldelight.testing.domain.TimestampItem
import com.akjaw.sqldelight.testing.domain.UpdateTimestampItem
import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class CommonItemsScreenViewModel internal constructor(
    private val getTimestampItems: GetTimestampItems,
    private val addTimestampItem: AddTimestampItem,
    private val updateTimestampItem: UpdateTimestampItem,
    @NativeCoroutineScope
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
) {

    val items: StateFlow<List<TimestampItem>> = getTimestampItems
        .execute()
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    suspend fun addItem() = addTimestampItem.execute()

    suspend fun updateItem(id: Long) = updateTimestampItem.execute(id)
}
