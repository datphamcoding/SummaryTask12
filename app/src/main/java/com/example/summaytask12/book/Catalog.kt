package com.example.summaytask12.book

object Catalog {
   private val totalBooks:Int=0
   val bookTitles:Map<String,List<Book>> = mutableMapOf()
   val bookAuthors:Map<String,List<Book>> = mutableMapOf()
   val bookSubjects:Map<String,List<Book>> = mutableMapOf()
   val bookPublishers:Map<String,List<Book>> = mutableMapOf()
}

