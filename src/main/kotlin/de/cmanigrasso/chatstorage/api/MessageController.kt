package de.cmanigrasso.chatstorage.api

import de.cmanigrasso.chatstorage.domain.Message
import de.cmanigrasso.chatstorage.service.ChatStorageService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * This controller is not for user clients, it's there mainly for debugging and admin purposes.
 */
@RestController
@RequestMapping("messages")
class MessageController(
    val service: ChatStorageService
) {

    @GetMapping
    fun getAll(@RequestParam name: String? = null): List<Message> =
        when(name) {
            null -> service.findAllMessages()
            else -> service.findAllMessagesByName(name)
        }

}

