package com.taufik.ecommercelist

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.taufik.ecommercelist.ui.navigation.NavigationItem
import com.taufik.ecommercelist.ui.navigation.Screen
import com.taufik.ecommercelist.ui.screen.detail.DetailScreen
import com.taufik.ecommercelist.ui.screen.home.HomeScreen
import com.taufik.ecommercelist.ui.screen.profile.ProfileScreen
import com.taufik.ecommercelist.ui.screen.wishlist.WishlistScreen
import com.taufik.ecommercelist.ui.theme.ECommerceListTheme

@Composable
fun ECommerceApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigationToDetail = { id ->
                        navController.navigate(Screen.DetailProduct.createRoute(id))
                    }
                )
            }
            composable(Screen.Wishlist.route) {
                WishlistScreen(
                    navigationToDetail = { id ->
                        navController.navigate(Screen.DetailProduct.createRoute(id))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailProduct.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                val id = it.arguments?.getInt("id") ?: -1
                DetailScreen(
                    id = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToWishlist = {
                        navController.popBackStack()
                        navController.navigate(Screen.Wishlist.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier
    ) {
        val navigationItem = listOf(
            NavigationItem(
                title = stringResource(id = R.string.menu_home),
                icon = Icons.Rounded.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_wishlist),
                icon = Icons.Rounded.Favorite,
                screen = Screen.Wishlist
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_profile),
                icon = Icons.Rounded.Person,
                screen = Screen.Profile
            ),
        )
        navigationItem.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ECommerceAppPreview() {
    ECommerceListTheme {
        ECommerceApp()
    }
}