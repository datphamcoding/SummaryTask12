package com.example.summaytask12.book

import java.time.LocalDate
import java.util.UUID

data class BookItem(
    val id: String = UUID.randomUUID().toString(),
    val book: Book,
    val placeAt: Rack, // Changed from private val to val
    var status: BookStatus = BookStatus.AVAILABLE,
) {

    fun reserve(): Boolean {
        // A book can only be reserved if it is currently loaned (as a way to queue for it)
        // Or, if the library decides on a different reservation logic (e.g. reserve an available book)
        // For now, let's assume a simple case: reservation is possible if not already reserved.
        // This logic might need to align with how reservations are handled in LibraryManager.
        if (status == BookStatus.AVAILABLE) { // Simplistic: reserve if available
            status = BookStatus.RESERVED
            return true
        }
        if (status == BookStatus.LOANED) { // Example: allow reservation if loaned
            // This implies a queue system which is not fully implemented here.
            // For now, let's keep it simple: can only reserve if AVAILABLE.
        }
        return false // Cannot reserve if already reserved, loaned (unless queue), or other states.
    }

    fun loan(): Boolean {
        if (status == BookStatus.AVAILABLE || status == BookStatus.RESERVED) {
            status = BookStatus.LOANED
            return true
        }
        return false
    }

    fun returnBook() {
        status = BookStatus.AVAILABLE
    }
}
