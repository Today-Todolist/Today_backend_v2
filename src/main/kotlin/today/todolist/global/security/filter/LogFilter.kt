package today.todolist.global.security.filter

import mu.KLogging
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LogFilter: OncePerRequestFilter() {
    companion object: KLogging()

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val requestToCache = ContentCachingRequestWrapper(request)
        val responseToCache = ContentCachingResponseWrapper(response)
        chain.doFilter(requestToCache, responseToCache)
        when(response.status) {
            in 200..499 -> logger.info("[${response.status}] - ${request.method} | ${request.requestURI} | ${request.getHeader("User-Agent")}")
            in 500..599 -> logger.error("[${response.status}] - ${request.method} | ${request.requestURI} | ${request.getHeader("User-Agent")}")
        }
    }
}