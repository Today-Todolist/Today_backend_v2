package today.todolist


import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class TodayApplicationTests extends Specification {

    def "contextLoads" () {
        expect:
        true
    }

}
