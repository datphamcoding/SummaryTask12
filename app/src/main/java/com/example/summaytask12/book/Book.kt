package com.example.summaytask12.book

import com.example.summaytask12.person.Author
import java.time.LocalDate
import java.util.UUID

class Book(
   val ISBN:String= UUID.randomUUID().toString(),
   val title:String,
   val subject: String,
   val publisher: String,
   val publicationDate: LocalDate,
   val language: String,
   val numberOfPages:Int,
   val format: BookFormat,
   val authors:List<Author>,
){
   override fun toString(): String {
      return "Book(ISBN='$ISBN', title='$title', subject='$subject', publisher='$publisher', publicationDate=$publicationDate, language='$language', numberOfPages=$numberOfPages, format=$format, authors=$authors)"
   }
}

