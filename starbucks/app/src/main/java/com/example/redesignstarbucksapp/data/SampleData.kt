package com.example.redesignstarbucksapp.data

import com.example.redesignstarbucksapp.model.Category
import com.example.redesignstarbucksapp.model.Product

object SampleData {
    val sampleProducts = listOf(
        Product(
            id = "1",
            name = "Kopi hmd",
            description = "Espresso yang kaya diseduh kemudian ditambahkan air panas, menghasilkan lapisan crema yang ringan di atasnya.",
            price = 4.95,
            imageUrl = "https://i.ibb.co/LD23FG8Z/avjurz1ew.webp",
            category = Category.COFFEE
        ),
        Product(
            id = "2",
            name = "Kopi hfd",
            description = "Espresso kami yang pekat dan kaya dipadukan dengan susu yang dikukus serta lapisan busa yang ringan.\n",
            price = 5.95,
            imageUrl = "https://i.ibb.co/LD23FG8Z/avjurz1ew.webp",
            category = Category.COFFEE
        ),
        Product(
            id = "3",
            name = "kopi ilaa",
            description = "Kopi dari segala bintang dan mawar",
            price = 5.45,
            imageUrl = "https://i.ibb.co/LD23FG8Z/avjurz1ew.webp",
            category = Category.COFFEE
        ),

    )
}
