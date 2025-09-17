package com.example.summaytask12.action

import com.example.summaytask12.book.Book
import com.example.summaytask12.action.ReservationStatus
import com.example.summaytask12.member.Member
import java.time.LocalDate

class BookReservation(
   private val member: Member,
   private val book: Book,
   private val reservationDate: LocalDate,
   private val status: ReservationStatus
)

