package com.example.summaytask12.catalog

import com.example.summaytask12.book.Book
import com.example.summaytask12.book.BookItem
import java.time.LocalDate

interface Search{
   fun searchByTitle(title:String):List<Book>?
   fun searchByAuthor(author:String):List<Book>?
   fun searchBySubject(subject:String):List<Book>?
   fun searchByPublisher(publisher:String):List<Book>?
   fun searchByISBN(isbn:String, bookItems:List<BookItem>):List<Book>
}