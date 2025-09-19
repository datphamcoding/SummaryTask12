package com.example.summaytask12.business

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summaytask12.book.BookItem
import com.example.summaytask12.member.Member
import java.time.LocalDate

class Reservation(
   private val member: Member,
   private val bookItem: BookItem,
   private val reservationDate: LocalDate,
   var status: ReservationStatus = ReservationStatus.None
) {

   @RequiresApi(Build.VERSION_CODES.O)
   private val dueDate = LocalDate.now().plusDays(RESERVER_DURATION_DAYS)

   /**
    * Marks the reservation as completed, signifying the book has been picked up and loaned.
    * This will also update the BookItem's status to LOANED.
    *
    * @return True if the book was successfully loaned and reservation status updated, false otherwise.
    */
   fun completeReservation(): Boolean {
      // A reservation can typically be completed if it's pending (or waiting for pickup)
      if (status == ReservationStatus.Pending || status == ReservationStatus.Waiting) {
         if (bookItem.loan()) { // The loan() method in BookItem handles its status
            status = ReservationStatus.Completed
            return true
         }
      }
      return false
   }

   companion object {
      const val RESERVER_DURATION_DAYS = 7L
   }
}
