package de.cmanigrasso.chatstorage.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("MESSAGE")
data class Message(
    @Id val id: String? = null,
    val roomId: String,
    val name: String,
    val text: String,
    val date: LocalDateTime = LocalDateTime.now()
)