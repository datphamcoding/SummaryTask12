package com.example.summaytask12.business

import com.example.summaytask12.book.Book
import com.example.summaytask12.book.BookItem
import com.example.summaytask12.member.Member
import java.time.LocalDate

class Reservation(
   private val member: Member,
   private val bookItem: BookItem,
   private val reservationDate: LocalDate,
   private val status: ReservationStatus
)

