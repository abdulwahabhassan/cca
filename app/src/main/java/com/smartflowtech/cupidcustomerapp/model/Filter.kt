package com.smartflowtech.cupidcustomerapp.model

data class Filter(
    val category: Category,
    val map: Map<String, Boolean>
)
