package de.cmanigrasso.chatstorage.repository

import de.cmanigrasso.chatstorage.domain.Room
import org.springframework.data.repository.CrudRepository

interface RoomRepository : CrudRepository<Room, String>