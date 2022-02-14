package com.example.kotlinxserializationexample.model

import kotlinx.serialization.Serializable

@Serializable
data class Dept(
    val no: Int,
    val name: String,
    val location: String = "default"
)

@Serializable
data class Dept2(
    val no: Int,
    val name: String,
    val location: String,
    val employees: List<Employee>
)