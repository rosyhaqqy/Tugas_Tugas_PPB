package com.example.redesignstarbucksapp.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: Category,
    val size: Size = Size.MEDIUM,
    val isAvailable: Boolean = true
)

enum class Category {
    COFFEE, TEA, FRAPPUCCINO, FOOD, MERCHANDISE
}

enum class Size(val displayName: String, val priceMultiplier: Double) {
    SMALL("Small", 0.9),
    MEDIUM("Medium", 1.0),
    LARGE("Large", 1.2)
}

data class CartItem(
    val product: Product,
    val quantity: Int = 1,
    val size: Size = Size.MEDIUM,
    val customizations: List<String> = emptyList()
)
