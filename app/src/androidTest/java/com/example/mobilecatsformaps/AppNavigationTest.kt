package com.example.mobilecatsformaps

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.mobilecatsformaps.navigation.AppNavigation
import com.example.mobilecatsformaps.ui.theme.MobileCATSforMAPSTheme
import org.junit.Rule
import org.junit.Test

class AppNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNavigationFromSearchScreenToAssetDetailsScreen() {

        // Launch the AppNavigation with the starting destination as "searchScreen"
        composeTestRule.setContent {
                MobileCATSforMAPSTheme {
                    AppNavigation(context = LocalContext.current)
            }}
        composeTestRule.onNodeWithText("ALLOW").performClick()
        // Validate that "Assets in the area" is displayed
        composeTestRule.onNodeWithText("Assets in the area").assertExists()

        // Simulate navigation action to go to "Add Asset Screen"
        // Here, you would simulate the click or action that triggers the navigation
        composeTestRule.onNodeWithTag("addNew").performClick() // Replace with the actual trigger

        // Validate that "Asset Details Screen" is displayed
        composeTestRule.onNodeWithText("Add Asset").assertExists()
    }

}