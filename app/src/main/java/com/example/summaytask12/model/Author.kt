package com.example.summaytask12.model

data class Author(
   private val name: String,
   private val description: String,
)

data class Library(
   private val name: String,
   private val address: Address,
)