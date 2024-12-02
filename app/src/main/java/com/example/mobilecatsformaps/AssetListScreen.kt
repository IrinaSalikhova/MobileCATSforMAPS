package com.example.mobilecatsformaps

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobilecatsformaps.database.Asset
import com.example.mobilecatsformaps.database.AssetDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetListScreen(navController: NavHostController) {
    val context = LocalContext.current
    val database = remember { AssetDatabase.getInstance(context) }
    val scope = rememberCoroutineScope()
    var assetList by remember { mutableStateOf<List<Asset>>(emptyList()) }

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            assetList = database.assetDao().getAllAssets()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asset Database") },
                colors = TopAppBarDefaults.mediumTopAppBarColors()
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(assetList) { asset ->
                AssetItem(asset, navController)
            }
        }
    }
}

@Composable
fun AssetItem(asset: Asset, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                // Navigate to AssetDetailsScreen with asset ID only
                navController.navigate("assetDetailsScreen/${asset.id}")
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${asset.name}", style = MaterialTheme.typography.titleMedium)
            Text(text = "ID: ${asset.id}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Category: ${asset.category}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Address: ${asset.address ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Contact: ${asset.contactInfo}", style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "Approval Status: ${if (asset.approvalStatus) "Approved" else "Pending"}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}