package com.example.summaytask12.member

import com.example.summaytask12.action.BookLending
import com.example.summaytask12.action.BookReservation
import com.example.summaytask12.book.Book
import com.example.summaytask12.book.Search
import com.example.summaytask12.member.AccountStatus

interface Account: Search {
   val id: String
   val password: String
   val status: AccountStatus
   val person: Person

   fun reserveBook(book: Book): BookReservation?
   fun lendBook(book: Book): BookLending?
}