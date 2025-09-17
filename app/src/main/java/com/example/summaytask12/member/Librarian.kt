package com.example.summaytask12.member

import com.example.summaytask12.action.BookLending
import com.example.summaytask12.action.BookReservation
import com.example.summaytask12.book.Book
import com.example.summaytask12.book.Catalog
import com.example.summaytask12.member.AccountStatus
import java.util.UUID

data class Librarian(
   override val id: String= UUID.randomUUID().toString(),
   override val password:String,
   override val status: AccountStatus,
   override val person: Person
) :Account{

   fun addBookItem(){
      //TODO("Not yet implemented")
   }

   fun updateBookItem(){
      //TODO("Not yet implemented")
   }

   override fun searchByTitle(title: String) = Catalog.bookTitles[title]

   override fun searchByAuthor(author: String)= Catalog.bookAuthors[author]

   override fun searchBySubject(subject: String) = Catalog.bookSubjects[subject]

   override fun searchByPublisher(publisher: String) = Catalog.bookPublishers[publisher]
   override fun reserveBook(book: Book): BookReservation? {
      TODO("Not yet implemented")
   }

   override fun lendBook(book: Book): BookLending? {
      TODO("Not yet implemented")
   }
}

