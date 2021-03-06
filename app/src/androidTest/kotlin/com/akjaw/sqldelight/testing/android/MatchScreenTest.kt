package com.akjaw.sqldelight.testing.android

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.akjaw.sqldelight.testing.data.time.TimestampProvider
import com.akjaw.sqldelight.testing.db.AppDatabase
import com.akjaw.sqldelight.testing.sharedModules
import com.akjaw.sqldelight.testing.test.MockTimestampProvider
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

class MatchScreenTest : KoinComponent {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    val mockTimestampProvider: MockTimestampProvider by inject()

    @Before
    fun setUp() {
        val modules = listOf(
            *sharedModules,
            module {
                single<SqlDriver> {
                    AndroidSqliteDriver(
                        schema = AppDatabase.Schema,
                        context = get(),
                        name = null,
                    )
                }
                single { MockTimestampProvider() }
                single<TimestampProvider> { get<MockTimestampProvider>() }
            }
        )
        unloadKoinModules(modules)
        loadKoinModules(modules)
    }

    @Test
    fun addingAnItemPopulatesTheListWithCurrentTimestamp() {
        mockTimestampProvider.setNextTimestamp(1653823433000)

        composeTestRule.onNodeWithText("Add").performClick()

        composeTestRule.waitUntilCatching(1000) {
            composeTestRule.onNodeWithText("2022-05-29T11:23:53").assertIsDisplayed()
        }
    }

    @Test
    fun refreshingAnItemUpdatesTheNameWithCurrentTimestamp() {
        mockTimestampProvider.setNextTimestamp(0)
        composeTestRule.onNodeWithText("Add").performClick()
        mockTimestampProvider.setNextTimestamp(1653823433000)

        composeTestRule.onNodeWithContentDescription("Refresh").performClick()

        composeTestRule.waitUntilCatching(1000) {
            composeTestRule.onNodeWithText("2022-05-29T11:23:53").assertIsDisplayed()
        }
    }

    private fun ComposeTestRule.waitUntilCatching(
        timeoutMillis: Long, assertion: () -> Unit
    ) =
        waitUntil(timeoutMillis) {
            try {
                assertion()
                true
            } catch (e: Exception) {
                false
            }
        }
}
