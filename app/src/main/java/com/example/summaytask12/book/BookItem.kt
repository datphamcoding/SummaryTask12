package com.example.summaytask12.book

import com.example.summaytask12.model.Author
import java.time.LocalDate

class BookItem(
   title: String,
   subject: String,
   publisher: String,
   language: String,
   numberOfPages: Int,
   author: List<Author>,
   val borrowedDate: LocalDate? = null,
   val dueDate: LocalDate? = null,
   val price: Double = 0.0,
   val format: BookFormat = BookFormat.Hardcover,
   val status: BookStatus = BookStatus.Available
):Book(
   title = title,
   subject = subject,
   publisher = publisher,
   language = language,
   numberOfPages = numberOfPages,
   author = author,
   publicationDate = LocalDate.now(),
)