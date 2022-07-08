package today.todolist.global.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.filter.OncePerRequestFilter
import today.todolist.global.error.exception.BasicException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ExceptionHandlerFilter (
    private val objectMapper: ObjectMapper
): OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            chain.doFilter(request, response)
        } catch (e:BasicException) {
            val errorResponse = e.toErrorResponse()
            response.apply {
                status = errorResponse.status
                contentType = "application/json"
                writer.write(objectMapper.writeValueAsString(errorResponse))
            }
        }
    }
}