package today.todolist.domain.todolist.dao

import org.springframework.data.mongodb.repository.MongoRepository
import today.todolist.domain.todolist.domain.Todolist

interface TodolistRepository: MongoRepository<Todolist, String>