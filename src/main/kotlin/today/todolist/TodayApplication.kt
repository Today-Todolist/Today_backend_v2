package today.todolist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodayApplication

fun main(args: Array<String>) {
    runApplication<TodayApplication>(*args)
}
