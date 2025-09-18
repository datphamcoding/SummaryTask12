package com.example.summaytask12.person

import com.example.summaytask12.person.Address

data class Person(
   val name:String,
   private val email: String,
   private val phone: String,
   private val address: Address
)