package com.example.summaytask12.book

import java.time.LocalDate
import java.util.UUID

data class BookItem(
   private val id:String= UUID.randomUUID().toString(),
   val book: Book,
   private val placeAt:Rack,
   private val status:BookStatus=BookStatus.NONE
)