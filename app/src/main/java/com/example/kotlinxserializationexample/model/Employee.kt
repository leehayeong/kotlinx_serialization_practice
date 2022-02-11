package com.example.kotlinxserializationexample.model

import kotlinx.serialization.Serializable

@Serializable
data class Employee(
    val no: Int,
    val name: String
)
