package com.example.summaytask12.book

import com.example.summaytask12.model.Author
import java.time.LocalDate
import java.util.UUID

open class Book(
   private val ISBN:String= UUID.randomUUID().toString(),
   private val title:String,
   private val subject: String,
   private val publisher: String,
   private val publicationDate: LocalDate,
   private val language: String,
   private val numberOfPages:Int,
   private val format: BookFormat,
   private val author:List<Author>,
)

