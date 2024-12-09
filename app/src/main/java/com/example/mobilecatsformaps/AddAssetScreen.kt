package com.example.mobilecatsformaps


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobilecatsformaps.database.Asset
import com.example.mobilecatsformaps.database.AssetDatabase
import com.example.mobilecatsformaps.database.AssetViewModel
import com.example.mobilecatsformaps.database.Category
import com.example.mobilecatsformaps.database.CategoryViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddAssetScreen(
    navController: NavHostController,
    assetId: String?,
    userId: String?,
    assetViewModel: AssetViewModel,
    categoryViewModel: CategoryViewModel

) {
    // Create a scroll state
    val scrollState = rememberScrollState()
    var assetName by remember { mutableStateOf(TextFieldValue()) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var assetDescription by remember { mutableStateOf(TextFieldValue()) }
    var assetContact by remember { mutableStateOf(TextFieldValue()) }
    var hoursOfOperation by remember { mutableStateOf(TextFieldValue()) }
    var assetAddress by remember { mutableStateOf(TextFieldValue()) }
    var isApproved by remember { mutableStateOf(false) } // State for checkbox
    var assetSocialWorkerNotes by remember { mutableStateOf(TextFieldValue()) }
    var assetRelatedServicesLinks by remember { mutableStateOf(TextFieldValue()) }
    var assetTargetFocusPopulation by remember { mutableStateOf(TextFieldValue()) }
    var assetScheduleRecurrence by remember { mutableStateOf(TextFieldValue()) }
    var assetRegistrationInfo by remember { mutableStateOf(TextFieldValue()) }
    var assetAccessibilityTransportation by remember { mutableStateOf(TextFieldValue()) }
    var assetVolunteeringOpportunities by remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current
    // State for the selected location
    var selectedLocation by remember {
        mutableStateOf(
            LatLng(
                45.38377643930254,
                -75.73336901286898
            )
        )
    }
    // Map for selecting location
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(selectedLocation, 10f)
    }

    // Fetch categories when the Composable is first composed
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    val scope = rememberCoroutineScope()
    val database = remember { AssetDatabase.getInstance(context) }

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            categories = database.categoryDao().getAllCategories()
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 40.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Add Asset", style = MaterialTheme.typography.h5)

        OutlinedTextField(
            value = assetAddress,
            onValueChange = { assetAddress = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = assetName,
            onValueChange = { assetName = it },
            label = { Text("Asset Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Dropdown for categories
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedCategory?.name ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Category") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(onClick = {
                        selectedCategory = category
                        expanded = false
                        categoryViewModel.fetchSubcategories(category.id)
                    }) {
                        Text(text = category.name)
                    }
                }
            }
        }

        OutlinedTextField(
            value = assetDescription,
            onValueChange = { assetDescription = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )


        OutlinedTextField(
            value = assetAddress,
            onValueChange = { assetAddress = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = assetContact,
            onValueChange = { assetContact = it },
            label = { Text("Contact Information") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = hoursOfOperation,
            onValueChange = { hoursOfOperation = it },
            label = { Text("Hours of Operation") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = assetSocialWorkerNotes,
            onValueChange = { assetSocialWorkerNotes = it },
            label = { Text("Social Worker Notes") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        OutlinedTextField(
            value = assetRelatedServicesLinks,
            onValueChange = { assetRelatedServicesLinks = it },
            label = { Text("Related Services Links") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        OutlinedTextField(
            value = assetTargetFocusPopulation,
            onValueChange = { assetTargetFocusPopulation = it },
            label = { Text("Target Focus Population") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = assetScheduleRecurrence,
            onValueChange = { assetScheduleRecurrence = it },
            label = { Text("Schedule Recurrence") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = assetRegistrationInfo,
            onValueChange = { assetRegistrationInfo = it },
            label = { Text("Registration Info") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = assetAccessibilityTransportation,
            onValueChange = { assetAccessibilityTransportation = it },
            label = { Text("Accessibility Transportation") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = assetVolunteeringOpportunities,
            onValueChange = { assetVolunteeringOpportunities = it },
            label = { Text("Volunteering Opportunities") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isApproved,
                onCheckedChange = { isApproved = it }
            )
            Text(text = if (isApproved) "Approved" else "Pending")
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                selectedLocation = latLng // Update the selected location
                // Optionally, you can also update the address here if you have a geocoding service
            }
        ) {
            Marker(
                state = MarkerState(position = selectedLocation),
                title = "Selected Location",
                snippet = "Lat: ${selectedLocation.latitude}, Lng: ${selectedLocation.longitude}"
            )
        }

        // Display the selected location coordinates
        Text(
            text = "Selected Location: ${selectedLocation.latitude}, ${selectedLocation.longitude}",
            style = MaterialTheme.typography.body1
        )

        Button(
            onClick = {
                // Handle submit action
                val asset = Asset(
                    name = assetName.text,
                    category = selectedCategory?.name ?: "",
                    latitude = selectedLocation.latitude,
                    longitude = selectedLocation.longitude,
                    address = assetAddress.text,
                    contactInfo = assetContact.text,
                    approvalStatus = isApproved, // Use checkbox state
                    socialWorkerNotes = assetSocialWorkerNotes.text,
                    relatedServicesLinks = assetRelatedServicesLinks.text,
                    description = assetDescription.text,
                    targetFocusPopulation = assetTargetFocusPopulation.text,
                    scheduleRecurrence = assetScheduleRecurrence.text,
                    registrationInfo = assetRegistrationInfo.text,
                    accessibilityTransportation = assetAccessibilityTransportation.text,
                    volunteeringOpportunities = assetVolunteeringOpportunities.text
                )
                // Show a Toast message with the input values
//                Toast.makeText(
//                    context,
//                    "status: ${asset.approvalStatus}\nlong: ${asset.longitude}\nlat: ${asset.latitude}",
//                    Toast.LENGTH_SHORT
//                ).show()
                assetViewModel.submitAsset(asset)
               navController.navigate("searchScreen")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Asset")

        }
        Spacer(modifier = Modifier.height(26.dp))
    }
}

/*fun saveAssetToPreferences(context: Context, asset: Asset) {
    val sharedPreferences = context.getSharedPreferences("city_prefs", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("name", asset.name)
        putString("lat", asset.latitude.toString())
        putString("lon", asset.longitude.toString())
        putString("address", asset.address)
        putString("contact", asset.contactInfo)
        putBoolean("approval_status", asset.approvalStatus)
        putString("social_worker_notes", asset.socialWorkerNotes)
        putString("related_services_links", asset.relatedServicesLinks)
        putString("description", asset.description)
        putString("target_focus_population", asset.targetFocusPopulation)
        putString("schedule_recurrence", asset.scheduleRecurrence)
        putString("registration_info", asset.registrationInfo)
        putString("accessibility_transportation", asset.accessibilityTransportation)
        putString("volunteering_opportunities", asset.volunteeringOpportunities)
        apply()
    }
 */