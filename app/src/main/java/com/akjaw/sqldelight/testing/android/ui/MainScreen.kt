package com.akjaw.sqldelight.testing.android.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akjaw.sqldelight.testing.android.ui.theme.Typography
import com.akjaw.sqldelight.testing.domain.TimestampItem
import com.akjaw.sqldelight.testing.presentation.CommonItemsScreenViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun MainScreen(viewModel: CommonItemsScreenViewModel = get()) {
    val items: List<TimestampItem> = viewModel.items.collectAsState().value
    val scope = rememberCoroutineScope()
    MainScreenContent(
        items = items,
        onAddClick = { scope.launch { viewModel.addItem() } },
        onRefreshClick = { item -> scope.launch { viewModel.updateItem(item.id) } },
    )
}

@Composable
fun MainScreenContent(
    items: List<TimestampItem>,
    onAddClick: () -> Unit,
    onRefreshClick: (TimestampItem) -> Unit,
) {
    LazyColumn {
        items(items = items, key = { it.id }) { item ->
            TimestampCard(
                item = item,
                onRefreshClick = { onRefreshClick(item) }
            )
        }
        item {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onAddClick,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
                ) {
                    Text(text = "Add")
                }
            }
        }
    }
}

@Composable
fun TimestampCard(item: TimestampItem, onRefreshClick: () -> Unit) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = item.name,
                style = Typography.h5.copy(fontSize = 28.sp),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Rounded.Refresh,
                contentDescription = "Refresh",
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onRefreshClick() }
            )
        }
    }
}
