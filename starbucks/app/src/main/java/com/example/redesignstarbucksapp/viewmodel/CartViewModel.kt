package com.example.redesignstarbucksapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.redesignstarbucksapp.model.CartItem
import com.example.redesignstarbucksapp.model.Product
import com.example.redesignstarbucksapp.model.Size

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> = _cartItems
    
    fun addToCart(product: Product, size: Size, quantity: Int) {
        val existingItem = _cartItems.find { 
            it.product.id == product.id && it.size == size 
        }
        
        if (existingItem != null) {
            val index = _cartItems.indexOf(existingItem)
            _cartItems[index] = existingItem.copy(quantity = existingItem.quantity + quantity)
        } else {
            _cartItems.add(CartItem(product, quantity, size))
        }
    }
    
    fun updateQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(cartItem)
        } else {
            val index = _cartItems.indexOf(cartItem)
            if (index != -1) {
                _cartItems[index] = cartItem.copy(quantity = newQuantity)
            }
        }
    }
    
    fun removeFromCart(cartItem: CartItem) {
        _cartItems.remove(cartItem)
    }
    
    fun clearCart() {
        _cartItems.clear()
    }
    
    fun getTotalItems(): Int {
        return _cartItems.sumOf { it.quantity }
    }
    
    fun getTotalPrice(): Double {
        return _cartItems.sumOf { it.product.price * it.size.priceMultiplier * it.quantity }
    }
}
