package com.example.summaytask12

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.summaytask12.book.Book
import com.example.summaytask12.book.BookFormat
import com.example.summaytask12.book.BookItem
import com.example.summaytask12.book.BookStatus
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
      // println("Members loaded: $members") // Quieted for cleaner output
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
      // println("Librarians loaded: $librarians") // Quieted for cleaner output
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
                  placeAt = rack // placeAt is private in BookItem, so its details aren't directly accessible from here
               )
               bookItems.add(bookItem)
            }
      }
      // bookItems.forEach { println(it) } // Quieted for cleaner output during init
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
               "2" -> borrowBook()
               "3" -> logout("Logged out as member successfully")
               "4" -> viewBorrowedBooks()
               "5" -> returnBook()
               else -> {
                  println("Invalid option")
               }
            }
         } else { // currentUser is Librarian
            printOptionLibrarian()
            when (readln()) {
               "1" -> addBookItem()
               "2" -> logout("Logged out as librarian successfully")
               "3" -> viewAllLibraryBooks() // Added case for viewing all books
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
      println("\n***Library Management System***")
      println("1. Find a book")
      println("2. Borrow a book")
      println("3. Log out")
      println("4. View borrowed books")
      println("5. Return a book")
      print("Enter your choice (1-5): ")
   }

   private fun findBook() {
      println("\nSearch by:")
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

   private fun borrowBook() {
      if (currentUser !is Member) {
         println("Only members can borrow books.")
         return
      }
      print("Enter title of the book to borrow: ")
      val title = readln()
      val books = Catalog.searchByTitle(title)
      if (books != null && books.isNotEmpty()) {
         val wantedBook = books[0] // Assuming the first match is the desired one
         // Find an available BookItem corresponding to the Book
         val availableBookItem = bookItems.find {
            it.book.ISBN == wantedBook.ISBN && (it.status == BookStatus.AVAILABLE)
         }

         if (availableBookItem != null) {
            if (availableBookItem.loan()) { // loan() method in BookItem updates its status to LOANED
               bookLoans.add(
                  Loan(
                     currentUser as Member,
                     availableBookItem
                  )
               )
               println("Book '${availableBookItem.book.title}' loaned successfully.")
            } else {
               println("Could not loan the book. Status: ${availableBookItem.status}")
            }
         } else {
            println("No available copies of '${wantedBook.title}' found or all are currently loaned.")
         }
      } else {
         println("No books found with that title.")
      }
   }

   private fun viewBorrowedBooks(): List<Loan>? {
      if (currentUser !is Member) {
         println("Error: Not logged in as a member.")
         return null
      }
      val member = currentUser as Member
      val memberLoans = bookLoans.filter { it.member.id == member.id && it.returnDate == null }

      if (memberLoans.isEmpty()) {
         println("You have not borrowed any books currently.")
         return null
      }

      println("\n*** Your Borrowed Books ***")
      memberLoans.forEachIndexed { index, loan ->
         val bookItem = loan.bookItem
         println("${index + 1}. Title: ${bookItem.book.title}")
         println("   Borrowed on: ${loan.checkoutDate}")
         println("   Due by: ${loan.dueDate}")
         println("   Status: Currently Borrowed")
      }
      println("*************************")
      return memberLoans
   }

   private fun returnBook() {
      if (currentUser !is Member) {
         println("Only members can return books.")
         return
      }
      val borrowedBooks = viewBorrowedBooks() // Display borrowed books and get the list

      if (borrowedBooks == null || borrowedBooks.isEmpty()) {
         // viewBorrowedBooks() already prints a message if no books are borrowed
         return
      }

      print("Enter the number of the book you want to return (or 0 to cancel): ")
      val choice = readln().toIntOrNull()

      if (choice == null || choice == 0) {
         println("Return cancelled.")
         return
      }
      if (choice > 0 && choice <= borrowedBooks.size) {
         val loanToReturn = borrowedBooks[choice - 1]
         loanToReturn.returnBook() // Mark as returned in Loan object
         println("Book '${loanToReturn.bookItem.book.title}' returned successfully.")
      } else {
         println("Invalid choice.")
      }
   }

   private fun findBookBy(str: String, action: (String) -> List<Book>?) {
      val books = action(str)
      if (books == null || books.isEmpty()) {
         println("No books found matching '$str'.")
      } else {
         println("\nSearch results:")
         books.forEach { println("- ${it.title} by ${it.authors.joinToString { author -> author.name }} (ISBN: ${it.ISBN})") }
      }
   }

   private fun addBookItem() {
      if (currentUser !is Librarian) {
         println("Only librarians can add book items")
         return
      }
      println("\n--- Add New Book Item ---")
      print("Enter ISBN: ")
      val isbn = readln()
      // Check if a book with this ISBN already exists in Catalog to reuse info or simplify
      val existingBook = Catalog.searchByISBN(isbn, bookItems).firstOrNull()

      val title: String
      val subject: String
      val publisher: String
      val publicationDate: LocalDate
      val language: String
      val numberOfPages: Int
      val format: BookFormat
      val authorNamesInput: String

      if (existingBook != null) {
         println("Book with ISBN $isbn found in catalog. Using existing details for: Title, Subject, Publisher, etc.")
         title = existingBook.title
         subject = existingBook.subject
         publisher = existingBook.publisher
         publicationDate = existingBook.publicationDate
         language = existingBook.language
         numberOfPages = existingBook.numberOfPages
         format = existingBook.format
         authorNamesInput = existingBook.authors.joinToString("; ") { it.name }
         println("Title: $title, Authors: $authorNamesInput") // Show some details
      } else {
         print("Enter title: ")
         title = readln()
         print("Enter subject: ")
         subject = readln()
         print("Enter publisher: ")
         publisher = readln()
         print("Enter publication date (YYYY-MM-DD): ")
         publicationDate = LocalDate.parse(readln())
         print("Enter language: ")
         language = readln()
         print("Enter number of pages: ")
         numberOfPages = readln().toInt()
         print("Enter format (e.g., HARDCOVER, PAPERBACK): ")
         format = BookFormat.valueOf(readln().uppercase())
         print("Enter author names (separated by semicolon if multiple): ")
         authorNamesInput = readln()
      }

      val authors = authorNamesInput.split(';').map { Author(name = it.trim(), description = "") }

      print("Enter rack name: ")
      val rackName = readln()
      print("Enter rack location: ")
      val rackLocation = readln()

      val newBook = Book(
         ISBN = isbn,
         title = title,
         subject = subject,
         publisher = publisher,
         publicationDate = publicationDate,
         language = language,
         numberOfPages = numberOfPages,
         format = format,
         authors = authors
      )

      val newBookItem = BookItem(
         // id is auto-generated
         book = newBook,
         placeAt = Rack(name = rackName, location = rackLocation),
         status = BookStatus.AVAILABLE // New books are available by default
      )

      bookItems.add(newBookItem)
      Catalog.addBookItems(bookItems) // Assuming Catalog has a method to add single items or it rebuilds
      println("Book item '${newBook.title}' added successfully with ID ${newBookItem.id} and status ${newBookItem.status}.")
   }

   private fun viewAllLibraryBooks() {
      if (currentUser !is Librarian) {
         println("Only librarians can view all books.")
         return
      }

      if (bookItems.isEmpty()) {
         println("\nThere are no books in the library catalog at the moment.")
         return
      }

      println("\n--- All Library Books ---")
      // Define column widths
      val indexWidth = 5
      val titleWidth = 40
      val authorsWidth = 30
      val isbnWidth = 15
      val statusWidth = 12

      // Header format string
      val headerFormat = "%-${indexWidth}s | %-${titleWidth}s | %-${authorsWidth}s | %-${isbnWidth}s | %-${statusWidth}s"
      // Data row format string
      val rowFormat = "%-${indexWidth}d | %-${titleWidth}.${titleWidth}s | %-${authorsWidth}.${authorsWidth}s | %-${isbnWidth}s | %-${statusWidth}s"
      
      // Print table header
      println(String.format(headerFormat, "No.", "Title", "Authors", "ISBN", "Status"))
      println("-".repeat(indexWidth + titleWidth + authorsWidth + isbnWidth + statusWidth + 10)) // Separator line

      bookItems.forEachIndexed { index, bookItem ->
         val book = bookItem.book
         val authorsString = book.authors.joinToString(", ") { it.name }
         println(String.format(rowFormat,
            index + 1,
            book.title,
            authorsString,
            book.ISBN,
            bookItem.status.toString()
         ))
      }
      println("-".repeat(indexWidth + titleWidth + authorsWidth + isbnWidth + statusWidth + 10)) // Footer separator line
      // Note: To display Rack details (name, location), the 'placeAt' property
      // in BookItem.kt needs to be public, or BookItem needs a public getter for Rack details.
   }

   private val printOptionLibrarian = {
      println("\n***Library Management System***")
      println("1. Add a new book")
      println("2. Log out")
      println("3. View all book in library")
      print("Enter your choice (1-3): ")
   }
}
