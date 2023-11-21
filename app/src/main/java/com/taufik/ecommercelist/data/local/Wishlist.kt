package com.taufik.ecommercelist.data.local

import com.taufik.ecommercelist.data.remote.response.ProductsItem

data class Wishlist(
    val product: ProductsItem,
    val isWishlist: Boolean
)
