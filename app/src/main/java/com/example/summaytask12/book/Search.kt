package com.example.summaytask12.book

interface Search{
   fun searchByTitle(title:String):List<Book>?
   fun searchByAuthor(author:String):List<Book>?
   fun searchBySubject(subject:String):List<Book>?
   fun searchByPublisher(publisher:String):List<Book>?
}