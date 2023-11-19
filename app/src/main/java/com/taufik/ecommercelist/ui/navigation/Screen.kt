package com.taufik.ecommercelist.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Wishlist: Screen("wishlist")
    object Profile: Screen("profile")
    object DetailProduct : Screen("home/{id}") {
        fun createRoute(id: Int) = "home/$id"
    }
}
