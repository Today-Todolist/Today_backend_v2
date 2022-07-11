package today.todolist

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldBeBlank
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TodayApplicationTest: FunSpec({
    test("context load") {}
})