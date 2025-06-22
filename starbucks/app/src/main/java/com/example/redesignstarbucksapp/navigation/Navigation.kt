package com.example.redesignstarbucksapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.redesignstarbucksapp.data.SampleData
import com.example.redesignstarbucksapp.ui.screens.CartScreen
import com.example.redesignstarbucksapp.ui.screens.HomeScreen
import com.example.redesignstarbucksapp.ui.screens.ProductDetailScreen
import com.example.redesignstarbucksapp.viewmodel.CartViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object Cart : Screen("cart")
}

@Composable
fun StarbucksNavigation(
    navController: NavHostController = rememberNavController(),
    cartViewModel: CartViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onProductClick = { product ->
                    navController.navigate(Screen.ProductDetail.createRoute(product.id))
                },
                onCartClick = {
                    navController.navigate(Screen.Cart.route)
                }
            )
        }
        
        composable(Screen.ProductDetail.route) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            val product = SampleData.sampleProducts.find { it.id == productId }
            
            product?.let {
                ProductDetailScreen(
                    product = it,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onAddToCart = { product, size, quantity ->
                        cartViewModel.addToCart(product, size, quantity)
                        navController.popBackStack()
                    }
                )
            }
        }
        
        composable(Screen.Cart.route) {
            CartScreen(
                cartItems = cartViewModel.cartItems,
                onBackClick = {
                    navController.popBackStack()
                },
                onUpdateQuantity = { cartItem, quantity ->
                    cartViewModel.updateQuantity(cartItem, quantity)
                },
                onRemoveItem = { cartItem ->
                    cartViewModel.removeFromCart(cartItem)
                },
                onCheckout = {
                    // In a real app, this would navigate to payment/checkout screen
                    cartViewModel.clearCart()
                    navController.popBackStack()
                }
            )
        }
    }
}