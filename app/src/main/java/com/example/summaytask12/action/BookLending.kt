package com.example.summaytask12.action

import com.example.summaytask12.book.Book
import com.example.summaytask12.member.Member
import java.time.LocalDate

class BookLending(
   private val member: Member,
   private val book: Book,
   private val lendingDate: LocalDate,
   private val dueDate: LocalDate
)