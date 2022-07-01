package today.todolist.domain.search.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class RecommendSearchWord (
    @Id val word: String
) {
    var wordList: MutableList<String> = mutableListOf()
        private set
}