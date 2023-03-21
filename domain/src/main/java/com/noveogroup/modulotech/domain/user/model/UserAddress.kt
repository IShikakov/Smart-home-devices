package com.noveogroup.modulotech.domain.user.model

data class UserAddress(
    val city: String,
    val postalCode: String,
    val street: String,
    val streetCode: String,
    val country: String,
)
