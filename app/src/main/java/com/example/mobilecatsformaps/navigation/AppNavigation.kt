package com.example.mobilecatsformaps.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobilecatsformaps.HomeScreen
import com.example.mobilecatsformaps.AddAssetScreen
import com.example.mobilecatsformaps.AssetDetailsScreen
import com.example.mobilecatsformaps.LoginScreen
import com.example.mobilecatsformaps.SearchScreen


@Composable
fun AppNavigation(startDestination: String = "searchScreen", context: Context) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("searchScreen?userId={userId}") { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId")
            SearchScreen(navController = navController, userId = userId)
        }
        composable("assetDetailsScreen/{assetId}?userId={userId}") { navBackStackEntry ->
            val assetId = navBackStackEntry.arguments?.getString("assetId")
            val userId = navBackStackEntry.arguments?.getString("userId")
            AssetDetailsScreen(navController = navController, assetId = assetId, userId = userId)
        }
        composable("addAssetScreen?assetId={assetId}&userId={userId}") { navBackStackEntry ->
            val assetId = navBackStackEntry.arguments?.getString("assetId")
            val userId = navBackStackEntry.arguments?.getString("userId")
            AddAssetScreen(navController = navController, assetId = assetId, userId = userId)
        }
        composable("loginScreen") { LoginScreen(navController = navController) }

        composable("homeScreen/{userId}") { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId")
            HomeScreen(navController = navController, userId = userId)
        }
    }
}










