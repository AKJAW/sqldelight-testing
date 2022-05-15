package com.akjaw.sqldelight.testing.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.akjaw.sqldelight.testing.android.ui.theme.AppTheme
import com.akjaw.sqldelight.testing.presentation.CommonItemsScreenViewModel
import kotlinx.coroutines.flow.collect
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : ComponentActivity(), KoinComponent {

    private val viewModel: CommonItemsScreenViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            viewModel.items.collect { items ->
                Log.d("Timestamps", items.toString())
            }
        }
        setContent {
            AppTheme {
                Text(text = "Initial", Modifier.clickable {
                    lifecycleScope.launchWhenResumed {
                        viewModel.addItem()
                    }
                })
            }
        }
    }
}
