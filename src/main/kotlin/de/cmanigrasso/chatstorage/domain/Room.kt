package de.cmanigrasso.chatstorage.domain

import de.cmanigrasso.chatstorage.domain.RoomStatus.OPEN
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("ROOM")
data class Room(
    @Id val id: String? = null,
    val name: String,
    val status: RoomStatus = OPEN
)

enum class RoomStatus {
    OPEN,
    ARCHIVED
}