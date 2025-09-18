package com.example.summaytask12.business

import com.example.summaytask12.book.BookItem
import com.example.summaytask12.member.Member
import java.time.LocalDate

data class Loan(
   private val member: Member,
   private val bookItem: BookItem,
   private val checkoutDate: LocalDate,
   private val dueDate: LocalDate,
   private val returnDate: LocalDate?
)