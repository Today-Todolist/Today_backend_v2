package today.todolist.domain.search.dao

import org.springframework.data.mongodb.repository.MongoRepository
import today.todolist.domain.search.domain.RecommendSearchWord

interface RecommendSearchWordRepository: MongoRepository<RecommendSearchWord, String>