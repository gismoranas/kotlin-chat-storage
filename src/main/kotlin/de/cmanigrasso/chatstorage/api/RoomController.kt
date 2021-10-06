package de.cmanigrasso.chatstorage.api

import de.cmanigrasso.chatstorage.exception.RoomAlreadyArchivedException
import de.cmanigrasso.chatstorage.exception.RoomNotFoundException
import de.cmanigrasso.chatstorage.service.ChatService
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * This is the main API. The workflow is:
 * - start a new room,
 * - post new messages,
 * - archive the room.
 * Once the room is archived no more messages can be posted and an error will be returned.
 */
@RestController
@RequestMapping("rooms")
class RoomController(
    private val service: ChatService
) {

    @GetMapping
    fun getAll() = service.findAllRooms()

    @PostMapping
    fun saveRoom(@RequestParam name: String) = service.startNewRoom(name)

    @PostMapping("{roomId}/archive")
    fun archive(@PathVariable roomId: String): ResponseEntity<Any> =
        handleErrors(roomId) { service.archiveRoom(it) }

    @GetMapping("{roomId}/members")
    fun getRoomMembers(@PathVariable roomId: String) =
        handleErrors(roomId) { service.findNamesByRoomId(it) }

    @GetMapping("{roomId}/countByName")
    fun countMessagesByName(@PathVariable roomId: String) =
        handleErrors(roomId) { service.countMessagesByName(it) }

    @GetMapping("{roomId}/messages")
    fun getAllByRoomId(@PathVariable roomId: String) =
        handleErrors(roomId) { service.findAllMessages(it) }

    @PostMapping("{roomId}/messages")
    fun saveMessage(@PathVariable roomId: String, @RequestBody message: MessageRequest) =
        handleErrors(roomId) { service.saveNewMessage(it, message) }

    fun handleErrors(roomId: String, operation: (String) -> Any): ResponseEntity<Any> =
        try {
            ResponseEntity(operation(roomId), OK)
        } catch (e: RoomNotFoundException) {
            ResponseEntity("Room not found. id = $roomId", NOT_FOUND)
        } catch (e: RoomAlreadyArchivedException) {
            ResponseEntity("Room is already archived. id = $roomId", NOT_ACCEPTABLE)
        } catch (e: Throwable) {
            e.printStackTrace()
            ResponseEntity("Unexpected error: ${e.message}", INTERNAL_SERVER_ERROR)
        }

}

data class MessageRequest(val name: String, val text: String)