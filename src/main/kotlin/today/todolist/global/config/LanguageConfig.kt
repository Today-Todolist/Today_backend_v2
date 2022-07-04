package today.todolist.global.config

import org.springframework.context.annotation.Configuration
import java.util.*
import javax.annotation.PostConstruct

@Configuration
class LanguageConfig {
    @PostConstruct
    fun setLanguage() {
        Locale.setDefault(Locale("en", "us"))
    }
}