package de.cmanigrasso.chatstorage.service

import de.cmanigrasso.chatstorage.api.MessageRequest
import de.cmanigrasso.chatstorage.domain.CountResult
import de.cmanigrasso.chatstorage.domain.Message
import de.cmanigrasso.chatstorage.domain.Room
import de.cmanigrasso.chatstorage.domain.RoomStatus.ARCHIVED
import de.cmanigrasso.chatstorage.exception.RoomAlreadyArchivedException
import de.cmanigrasso.chatstorage.exception.RoomNotFoundException
import de.cmanigrasso.chatstorage.repository.MessageRepository
import de.cmanigrasso.chatstorage.repository.RoomRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class ChatStorageService(
    private val messageRepository: MessageRepository,
    private val roomRepository: RoomRepository
) {

    private val logger = KotlinLogging.logger {}

    fun findAllMessages() = messageRepository.findAll().toList()

    fun findAllMessagesByName(name: String) = messageRepository.findAllByName(name)

    fun findAllRooms() = roomRepository.findAll().toList()

    fun findAllMessages(roomId: String) =
        findRoom(roomId).let {
            messageRepository.findByRoomIdOrderByDateAsc(roomId)
        }

    fun createRoom(name: String): Room =
        roomRepository.save(Room(name = name))
            .apply { logger.info { "Created new room. name=$name, id=$id" } }

    private fun findRoom(id: String, checkIfArchived: Boolean = false) =
        roomRepository.findById(id).let {
            when {
                it.isEmpty -> throw RoomNotFoundException()
                it.get().status == ARCHIVED && checkIfArchived -> throw RoomAlreadyArchivedException()
                else -> it.get()
            }
        }

    fun archiveRoom(id: String): Room =
        findRoom(id, checkIfArchived = true).let {
            roomRepository.save(it.copy(status = ARCHIVED))
                .apply { logger.info { "Archived room. name=$name, id=$id" } }
        }

    fun saveNewMessage(roomId: String, request: MessageRequest) =
        findRoom(roomId, checkIfArchived = true).let {
            messageRepository.save(
                Message(
                    name = request.name,
                    text = request.text,
                    roomId = roomId
                )
            ).apply { logger.info { "Saved message. name=$name, roomId=$roomId, text=${text.take(10)}(...)" } }
        }

    fun findNamesByRoomId(roomId: String): List<String> =
        findRoom(roomId).let {
            messageRepository.findNamesByRoomId(roomId)
        }

    fun countMessagesByName(roomId: String): List<CountResult> =
        findRoom(roomId).let {
            messageRepository.countMessagesGroupingByName(roomId)
        }

}