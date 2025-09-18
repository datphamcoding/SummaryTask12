package com.example.summaytask12

import android.os.Build
import android.os.Message
import androidx.annotation.RequiresApi
import com.example.summaytask12.book.Book
import com.example.summaytask12.book.BookFormat
import com.example.summaytask12.book.BookItem
import com.example.summaytask12.book.Rack
import com.example.summaytask12.business.Loan
import com.example.summaytask12.business.Reservation
import com.example.summaytask12.catalog.Catalog
import com.example.summaytask12.member.AccountStatus
import com.example.summaytask12.member.Librarian
import com.example.summaytask12.member.Member
import com.example.summaytask12.member.User
import com.example.summaytask12.person.Address
import com.example.summaytask12.person.Author
import com.example.summaytask12.person.Person
import java.io.File
import java.io.InputStream
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class LibraryManager {
   private val members: MutableList<Member> = mutableListOf()
   private val librarians: MutableList<Librarian> = mutableListOf()
   private val bookItems: MutableList<BookItem> = mutableListOf()
   private val bookReserves: MutableList<Reservation> = mutableListOf()
   private val bookLoans: MutableList<Loan> = mutableListOf()

   private var currentUser: User? = null

   private fun loadMembersFromCSV(inputStream: InputStream) {
      inputStream.bufferedReader().useLines { lines ->
         lines.drop(1) // Skip header row
            .forEach { line ->
               val tokens = line.split(",")
               val address = Address(
                  street = tokens[6],
                  city = tokens[7],
                  state = tokens[8],
                  zip = tokens[9],
                  country = tokens[10]
               )
               val person = Person(
                  name = tokens[3],
                  email = tokens[4],
                  phone = tokens[5],
                  address = address
               )
               val member = Member(
                  id = tokens[0],
                  password = tokens[1],
                  status = AccountStatus.valueOf(tokens[2]),
                  person = person,
                  username = tokens[11]
               )
               members.add(member)
            }
      }
      println("Members loaded: $members")
   }

   private fun loadLibrariansFromCSV(inputStream: InputStream) {
      inputStream.bufferedReader().useLines { lines ->
         lines.drop(1) // Skip header row
            .forEach { line ->
               val tokens = line.split(",")
               val address = Address(
                  street = tokens[6],
                  city = tokens[7],
                  state = tokens[8],
                  zip = tokens[9],
                  country = tokens[10]
               )
               val person = Person(
                  name = tokens[3],
                  email = tokens[4],
                  phone = tokens[5],
                  address = address
               )
               val librarian = Librarian(
                  id = tokens[0],
                  password = tokens[1],
                  status = AccountStatus.valueOf(tokens[2]),
                  person = person,
                  username = tokens[11]
               )
               librarians.add(librarian)
            }
      }
      println("Librarians loaded: $librarians")
   }

   @RequiresApi(Build.VERSION_CODES.O)
   private fun loadBookItemsFromCSV(inputStream: InputStream) {
      inputStream.bufferedReader().useLines { lines ->
         lines.drop(1) // Skip header row
            .forEach { line ->
               val tokens = line.split(",")
               val authorNames = tokens[9].split(";")
               val authors = authorNames.map { Author(name = it.trim(), description = "") }
               val book = Book(
                  ISBN = tokens[1],
                  title = tokens[2],
                  subject = tokens[3],
                  publisher = tokens[4],
                  publicationDate = LocalDate.parse(tokens[5]),
                  language = tokens[6],
                  numberOfPages = tokens[7].toInt(),
                  format = BookFormat.valueOf(tokens[8]),
                  authors = authors
               )
               val rack = Rack(
                  name = tokens[10],
                  location = tokens[11]
               )
               val bookItem = BookItem(
                  id = tokens[0],
                  book = book,
                  placeAt = rack
               )
               //println(bookItem)
               bookItems.add(bookItem)
            }
      }
      bookItems.forEach { println(it) }
   }

   init {
      File("app/src/main/java/com/example/summaytask12/data/members.csv").inputStream()
         .use(::loadMembersFromCSV)
      File("app/src/main/java/com/example/summaytask12/data/librarians.csv").inputStream()
         .use(::loadLibrariansFromCSV)
      File("app/src/main/java/com/example/summaytask12/data/book_items.csv").inputStream()
         .use(::loadBookItemsFromCSV)
      Catalog.addBookItems(bookItems)

   }

   fun run() {
      while (currentUser == null) {
         println("***Login screen***")
         login()
      }
      while (currentUser != null) {
         if (currentUser is Member) {
            printOptionMember()
            when (readln()) {
               "1" -> findBook()
               "8" -> logout("Logged out as member successfully")
               else -> {
                  println("Invalid option")
               }
            }
         } else {
            printOptionLibrarian()
            when (readln()) {
               "8" -> logout("Logged out as librarian successfully")
               else -> {
                  println("Invalid option")
               }
            }
         }
      }
      println("Thank you for using the library management system. Goodbye!")
   }

   private fun login() {
      print("Enter username: ")
      val username = readln()
      print("Enter password: ")
      val password = readln()
      var greeting: String? = null

      val user = members.find { it.username == username && it.password == password }
         ?: librarians.find { it.username == username && it.password == password }

      if (user != null) {
         greeting =
            if (user is Member) "Login as member successful. Welcome ${user.username}" else "Login as librarian successful. Welcome ${user.username}"
         currentUser = user
      } else println("Login failed")

      greeting?.let(::println)
   }

   private fun logout(message: String) {
      currentUser = null
      println(message)
   }

   private val printOptionMember = {
      println("***Library Management System***")
      println("1. Find a book")
      println("2. Borrow a book")
      println("8. Log out")
      print("Enter your choice (1-11): ")
   }

   private fun findBook() {
      println("Search by:")
      println("1. Title")
      println("2. Author")
      println("3. Subject")
      println("4. Publisher")
      print("Enter your choice: ")
      when (readln()) {
         "1" -> {
            print("Enter title: ")
            val title = readln()
            findBookBy(title, Catalog::searchByTitle)
         }

         "2" -> {
            print("Enter author: ")
            val author = readln()
            findBookBy(author, Catalog::searchByAuthor)
         }

         "3" -> {
            print("Enter subject: ")
            val subject = readln()
            findBookBy(subject, Catalog::searchBySubject)
         }

         "4" -> {
            print("Enter publisher: ")
            val publisher = readln()
            findBookBy(publisher, Catalog::searchByPublisher)
         }

         else -> println("Invalid option")
      }
   }

   private fun reserveBook(){
      print("Enter title: ")
      val title = readln()
      val books= findBookBy(title, Catalog::searchByTitle)
   }

   private fun findBookBy(str:String, action:(String)-> List<Book>?){
      val books = action(str)
      if (books == null) println("No books found") else {
         println("Search results:")
         books.forEach { println(it) }
      }
   }

   private val printOptionLibrarian = {
      println("***Library Management System***")
      println("1. Add a new book")
      println("8. Log out")
      print("Enter your choice (1-11): ")
   }
}