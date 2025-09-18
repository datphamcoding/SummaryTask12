package com.example.summaytask12.member

import com.example.summaytask12.person.Person

abstract class User {
   abstract val id: String
   abstract val username: String
   abstract var password: String
   abstract val status: AccountStatus
   abstract val person: Person

   fun resetPassword(newPassword: String) {
      password = "123default"
   }
}