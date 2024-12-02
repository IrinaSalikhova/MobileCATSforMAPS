package com.example.mobilecatsformaps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilecatsformaps.database.Asset
import com.example.mobilecatsformaps.database.AssetDatabase
import com.example.mobilecatsformaps.navigation.AppNavigation
import com.example.mobilecatsformaps.ui.theme.TextColor
import com.example.mobilecatsformaps.ui.theme.MobileCATSforMAPSTheme
import com.example.mobilecatsformaps.ui.theme.myFontFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileCATSforMAPSTheme {
                //to check database comment out AppNavigation and uncomment AssetListScreen
                // Asset list screen here is just to test db. It will be completely removed later
                //AssetListScreen()
                AppNavigation(context = this)

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetListScreen() {
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
                AssetItem(asset)
            }
        }
    }
}

@Composable
fun AssetItem(asset: Asset) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${asset.name}", style = MaterialTheme.typography.titleMedium)
            Text(text = "ID: ${asset.id}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Category: ${asset.category}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Address: ${asset.address ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Contact: ${asset.contactInfo}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Approval Status: ${if (asset.approvalStatus) "Approved" else "Pending"}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}