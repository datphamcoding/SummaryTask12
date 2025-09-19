package com.example.summaytask12.catalog

import com.example.summaytask12.book.Book
import com.example.summaytask12.book.BookItem
import java.time.LocalDate

object Catalog: Search {
   val bookTitles: MutableMap<String, MutableList<Book>> = mutableMapOf()
   val bookAuthors: MutableMap<String, MutableList<Book>> = mutableMapOf()
   val bookSubjects: MutableMap<String, MutableList<Book>> = mutableMapOf()
   val bookPublishers: MutableMap<String, MutableList<Book>> = mutableMapOf()

   fun addBookItems(bookItems: List<BookItem>) {
      for (bookItem in bookItems) {
         val book = bookItem.book
         bookTitles.getOrPut(book.title) { mutableListOf() }.add(book)
         book.authors.forEach { author ->
            bookAuthors.getOrPut(author.name) { mutableListOf() }.add(book)
         }
         bookSubjects.getOrPut(book.subject) { mutableListOf() }.add(book)
         bookPublishers.getOrPut(book.publisher) { mutableListOf() }.add(book)
      }

   }

   override fun searchByTitle(title: String):List<Book>? = bookTitles[title]

   override fun searchByAuthor(author: String):List<Book>? = bookAuthors[author]

   override fun searchBySubject(subject: String):List<Book>? = bookSubjects[subject]

   override fun searchByPublisher(publisher: String): List<Book>? = bookPublishers[publisher]

   override fun searchByISBN(isbn: String, bookItems: List<BookItem>): List<Book> {
      return bookItems.filter { it.book.ISBN == isbn }.map { it.book }
   }
}