package com.example.demo.ui.theme.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.demo.ui.theme.DemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoTheme {
                val navController = rememberNavController()
                var selected by rememberSaveable { mutableIntStateOf(BottomNavDestination.HOME.ordinal) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        CustomBottomNavigationBar(
                            selectedDestination = BottomNavDestination.entries[selected],
                            onItemSelected = {
                                selected = it.ordinal
                                navController.navigate(it.route) {
                                    launchSingleTop = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    restoreState = true
                                }
                            }
                        )
                    },
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

enum class BottomNavDestination(
    val label: String,
    val icon: ImageVector,
    val route: String,
    val isCenter: Boolean = false
) {
    HOME("Home", Icons.Default.Home, "home"),
    COLLECTION("Collection", Icons.Default.AccountCircle, "collection"),
    SCAN("Scan", Icons.Default.Add, "scan", isCenter = true),
    DISCOVERY("Discovery", Icons.Default.LocationOn, "discovery"),
    SETTINGS("Settings", Icons.Default.Settings, "settings")
}

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavDestination.HOME.route,
        modifier = modifier
    ) {
        composable(BottomNavDestination.HOME.route) {
            Greeting("Home Screen")
        }
        composable(BottomNavDestination.COLLECTION.route) {
            Greeting("Collection Screen")
        }
        composable(BottomNavDestination.SCAN.route) {
            Greeting("Scan Screen")
        }
        composable(BottomNavDestination.DISCOVERY.route) {
            Greeting("Discovery Screen")
        }
        composable(BottomNavDestination.SETTINGS.route) {
            Greeting("Settings Screen")
        }
    }
}

@Composable
fun CustomBottomNavigationBar(
    selectedDestination: BottomNavDestination,
    onItemSelected: (BottomNavDestination) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            BottomNavDestination.entries.forEach { destination ->
                if (destination.isCenter) {
                    Spacer(modifier = Modifier.weight(1f, true))
                } else {
                    NavigationBarItem(
                        selected = selectedDestination == destination,
                        onClick = { onItemSelected(destination) },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.label
                            )
                        },
                        label = {
                            Text(destination.label)
                        },
                        alwaysShowLabel = true
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { onItemSelected(BottomNavDestination.SCAN) },
            containerColor = Color(0xFF5A3A1B),
            contentColor = Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(8.dp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-24).dp)
        ) {
            Icon(
                imageVector = BottomNavDestination.SCAN.icon,
                contentDescription = BottomNavDestination.SCAN.label,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    DemoTheme {
        val navController = rememberNavController()
        var selected by remember { mutableIntStateOf(BottomNavDestination.HOME.ordinal) }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                CustomBottomNavigationBar(
                    selectedDestination = BottomNavDestination.entries[selected],
                    onItemSelected = {
                        selected = it.ordinal
                        navController.navigate(it.route)
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Preview Content")
            }
        }
    }
}