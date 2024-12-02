package com.example.mobilecatsformaps

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun AssetDetailsScreen(navController: NavHostController, assetId: String?, userId: String?) {
    Text(
        text = "Hello Mahima!",
        fontSize = 24.sp

    )
}