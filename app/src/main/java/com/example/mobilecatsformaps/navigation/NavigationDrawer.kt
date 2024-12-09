package com.example.mobilecatsformaps.navigation

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    context: Context,
    startDestination: String = "searchScreen"
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawerContent(navController, drawerState, scope)
        }
    ) {
        Scaffold(
            topBar = {
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                TopAppBar(
                    title = {
                        Text(
                            text = when  {
                                currentRoute?.startsWith("searchScreen") == true -> "Search Assets"
                                currentRoute?.startsWith("assetListScreen") == true -> "Asset List"
                                currentRoute?.startsWith("homeScreen") == true -> "Home"
                                else -> "App Title"
                            }
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Open Drawer")
                        }
                    },
                    actions = {
                        if (currentRoute?.startsWith("searchScreen") == true) {
                            IconButton(onClick = { navController.navigate("addAssetScreen") }) {
                                Icon(Icons.Filled.Add, contentDescription = "Add New Asset")
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                AppNavigation(navController, context = context)
            }
        }
    }
}

@Composable
fun NavigationDrawerContent(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    Box(
        modifier = Modifier
            .width(250.dp) // Adjust the width to define how much space the drawer takes
            .heightIn(min = 0.dp, max = 500.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Icon to fold back the drawer
            IconButton(
                onClick = { scope.launch { drawerState.close() } }
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Close Drawer")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Navigation Drawer", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            DrawerMenuItem("Search", "searchScreen", navController, drawerState, scope)
            DrawerMenuItem("Assets", "assetListScreen", navController, drawerState, scope)
            DrawerMenuItem("Home", "homeScreen/{userId}", navController, drawerState, scope)
        }
    }
}

@Composable
fun DrawerMenuItem(
    title: String,
    route: String,
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    TextButton(
        onClick = {
            scope.launch {
                drawerState.close()
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    ) {
        Text(title)
    }
}