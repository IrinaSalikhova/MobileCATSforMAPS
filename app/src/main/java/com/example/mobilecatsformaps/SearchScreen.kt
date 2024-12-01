package com.example.mobilecatsformaps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun SearchScreen(navController: NavHostController, userId: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Screen Content (e.g., search bar, list of assets, etc.)
        Text(
            text = "Search Assets",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Example placeholder text for user ID
        userId?.let {
            Text(text = "Logged in as User ID: $it", style = MaterialTheme.typography.bodyMedium)
        } ?: Text(text = "Not logged in", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(30.dp))

        // Button to add a new asset
        Button(
            onClick = {
                navController.navigate("addAssetScreen") // Navigate to Add Asset screen
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add New Asset")
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}