package com.example.summaytask12.business

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summaytask12.book.BookItem
import com.example.summaytask12.member.Member
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class Loan(
   val member: Member,
   val bookItem: BookItem,
){

   val checkoutDate= LocalDate.now()

   val dueDate = checkoutDate.plusDays(LOAN_DURATION_DAYS)

   var returnDate: LocalDate?=null
      private set

   fun returnBook(){
      bookItem.returnBook()
      returnDate = LocalDate.now()
   }

   companion object{
      const val LOAN_DURATION_DAYS = 7L
   }
}