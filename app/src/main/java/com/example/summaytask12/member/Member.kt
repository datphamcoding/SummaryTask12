package com.example.summaytask12.member

import com.example.summaytask12.person.Person
import java.util.UUID

data class Member(
   override val username: String,
   override val id: String= UUID.randomUUID().toString(),
   override var password: String,
   override val status: AccountStatus,
   override val person: Person
): User()