package today.todolist.global.error.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import today.todolist.global.error.ErrorCode
import java.time.LocalDate

@JsonPropertyOrder("timestamp", "status", "code", "message")
open class BasicErrorResponse (
    errorCode: ErrorCode
) {
    @JsonProperty("timestamp")
    val timestamp: LocalDate = LocalDate.now()

    @JsonProperty("status")
    val status: Int = errorCode.status

    @JsonProperty("code")
    val code: String = errorCode.code

    @JsonProperty("message")
    val message: String = errorCode.message
}