package de.cmanigrasso.chatstorage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChatStorageApplication

fun main(args: Array<String>) {
	runApplication<ChatStorageApplication>(*args)
}
