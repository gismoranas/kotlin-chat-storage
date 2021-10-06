package de.cmanigrasso.chatstorage.repository

import de.cmanigrasso.chatstorage.domain.CountResult
import de.cmanigrasso.chatstorage.domain.Message
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface MessageRepository : CrudRepository<Message, String> {

    fun findByRoomIdOrderByDateAsc(roomId: String): List<Message>

    @Query("SELECT DISTINCT name FROM message WHERE room_id=:roomId ORDER BY name ASC")
    fun findNamesByRoomId(roomId: String): List<String>

    @Query("SELECT name, count(*) AS count FROM message WHERE room_id=:roomId GROUP BY name ORDER BY count DESC")
    fun countMessagesGroupingByName(roomId: String): List<CountResult>

    fun findAllByName(name: String): List<Message>

}