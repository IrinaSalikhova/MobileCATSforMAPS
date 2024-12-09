package com.example.mobilecatsformaps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.example.mobilecatsformaps.database.Asset
import com.example.mobilecatsformaps.database.AssetDatabase
import com.example.mobilecatsformaps.database.Category
import com.example.mobilecatsformaps.database.CategorySeeder
import com.example.mobilecatsformaps.database.buildDynamicQuery

import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, userId: String?) {
    val context = LocalContext.current
    val database = AssetDatabase.getInstance(context)
    val assetDao = database.assetDao()
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var assetList by remember { mutableStateOf<List<Asset>>(emptyList()) }
    Log.d("DatabaseCheck2", "Categories in the database: $categories")
    Log.d("DatabaseCheck2", "Assets in the database: $assetList")

    var selectedCategories = remember { mutableStateOf<List<String>>(emptyList()) }
    var searchedWords = remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(Unit) {
        val loadedcategories = withContext(Dispatchers.IO) {
            database.categoryDao().getAllCategories()
        }
        categories = loadedcategories
        Log.d("DatabaseCheck", "Categories in the database: $loadedcategories")
    }

    LaunchedEffect(selectedCategories.value to searchedWords.value) {
            val query = buildDynamicQuery(selectedCategories.value, searchedWords.value)
            try {
                val filteredAssets = withContext(Dispatchers.IO) {
                    assetDao.getAssetsByDynamicQuery(query).firstOrNull() ?: emptyList()
                }
                assetList = filteredAssets
                Log.d("Filtered assets", "Filtered assets: $filteredAssets")
            }
            catch (e: Exception) {
                Log.e("Error", "Error fetching assets by category", e)
            }
    }

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val defaultLocation = LatLng(45.383313,-75.733428) //CCHC
    var userLocation by remember { mutableStateOf(defaultLocation) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 12f)
    }

    var hasPermission by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            hasPermission = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    userLocation = LatLng(location.latitude, location.longitude)
                }
            }
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        // Search Bar
        SearchBar(
            onSearch = { query ->
                val queryWords = query.split(" ").filter { it.isNotBlank() }
                Log.d("Search Query Words", "Words: $queryWords")
                searchedWords.value = queryWords
                       },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        // Filter Dropdown
        FilterDropdown(
            categories = categories,
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            onFilterChanged = { selected ->
                selectedCategories.value = selected
                Log.d("Selected categories:", "Selected categories: $selected") }
        )

        // Map View Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = userLocation),
                    title = "Your Location",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)
                )
                assetList.filter { it.approvalStatus }.forEach { asset ->
                    Marker(
                        state = MarkerState(
                            position = LatLng(asset.latitude, asset.longitude)
                        ),
                        title = asset.name,
                        snippet = asset.description,
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                    )
                }
            }
        }

        // List View
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(assetList.filter { it.approvalStatus }) { asset ->
                AssetItemPlaceholder(asset = asset) { selectedAsset -> navController.navigate("assetDetailsScreen/${selectedAsset.id}?userId=$userId")}
            }
        }
    }
}


// Search Bar Component
@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }

    // Track delayed query updates
    LaunchedEffect(query) {
        snapshotFlow { query }
            .debounce(2000) // Wait for 2 seconds
            .collectLatest { debouncedQuery ->
                onSearch(debouncedQuery) // Pass the updated value after delay
            }
    }

    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        placeholder = {
            Text(
                text ="Search for assets...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )},
        leadingIcon = { Icon(
            Icons.Default.Search,
            contentDescription = "Search Icon",
            tint = MaterialTheme.colorScheme.primary
            ) },
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            focusedBorderColor = MaterialTheme.colorScheme.primary
        )
    )
}

// Filter Dropdown Component
@Composable
fun FilterDropdown(
    categories: List<Category>,
    modifier: Modifier = Modifier,
    onFilterChanged: (List<String>) -> Unit
) {
    if (categories.isEmpty()) {
        Text("Loading categories...", style = MaterialTheme.typography.bodyLarge)
        return
    }

    // States to manage UI
    val expanded = remember { mutableStateOf(false) }
    val selectedFilters = remember { mutableStateListOf<String>() }
    val expandedParentCategory = remember { mutableStateOf<Long?>(null) } // Tracks which parent is expanded

    // Group categories by parentCategoryId to create the hierarchy
    val parentCategories = categories.filter { it.parentCategoryId == null }
    val childCategories = categories.groupBy { it.parentCategoryId }

    Column(modifier = modifier) {
        // Dropdown header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded.value = !expanded.value },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Filters",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        if (expanded.value) {
            // Dropdown content with scrollable LazyColumn
            LazyColumn(modifier = Modifier.fillMaxHeight(0.5f)) { // Adjust height as needed
                items(parentCategories) { parent ->
                    // Parent category row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expandedParentCategory.value =
                                    if (expandedParentCategory.value == parent.id) null else parent.id
                            }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = parent.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = if (expandedParentCategory.value == parent.id) Icons.Default.KeyboardArrowLeft else Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    }

                    // Display child categories if this parent is expanded
                    if (expandedParentCategory.value == parent.id) {
                        childCategories[parent.id]?.forEach { child ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(start = 16.dp) // Indent child categories
                                    .fillMaxWidth()
                            ) {
                                Checkbox(
                                    checked = selectedFilters.contains(child.name),
                                    onCheckedChange = { isChecked ->
                                        if (isChecked) selectedFilters.add(child.name)
                                        else selectedFilters.remove(child.name)
                                        onFilterChanged(selectedFilters.toList())
                                    }
                                )
                                Text(
                                    text = child.name,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

// Asset Item Placeholder
@Composable
fun AssetItemPlaceholder(
    asset: Asset,
    onAssetClick: (Asset) -> Unit
) {
    if (asset.approvalStatus) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onAssetClick(asset) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Open Asset Details",
                tint = Color.Blue
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Asset Name and Address
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = asset.name,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (!asset.address.isNullOrEmpty()) {
                    Text(
                        text = asset.address,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Right Arrow Icon
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Open Asset Details",
                tint = Color.Gray
            )
        }
    }
}



