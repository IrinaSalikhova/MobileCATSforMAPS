package com.example.mobilecatsformaps

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobilecatsformaps.database.AssetDatabase
import com.example.mobilecatsformaps.database.CategorySeeder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun SearchScreen(navController: NavHostController, userId: String?) {
    val context = LocalContext.current
    val database = AssetDatabase.getInstance(context)

    CoroutineScope(Dispatchers.IO).launch {
        val categories = database.categoryDao().getAllCategories()
        val assets = database.assetDao().getAllAssets()
        Log.d("DatabaseCheck", "Categories in the database: $categories")
        Log.d("DatabaseCheck", "Assets in the database: $assets")
    }
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