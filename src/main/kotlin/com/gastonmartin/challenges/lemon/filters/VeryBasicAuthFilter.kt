package com.gastonmartin.challenges.lemon.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.gastonmartin.challenges.lemon.filters.AuthConstants.USERNAME_HEADER
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.Date
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class VeryBasicAuthFilter : OncePerRequestFilter() {

    private val objectMapper = ObjectMapper()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val headerNames = request.headerNames.toList().map { h -> h.lowercase() }

        val givenUserName = if (USERNAME_HEADER in headerNames) {
            request.getHeader(USERNAME_HEADER)
        } else {
            ""
        }

        if (givenUserName == "Admin") {
            filterChain.doFilter(request, response)
        } else {
            handleInvalidUsername(request.servletPath, response)
        }
    }

    fun handleInvalidUsername(path: String, response: HttpServletResponse) {
        val errorResponse = ErrorResponse(
            timestamp = Date(),
            status = 403,
            error = "Forbidden",
            path = path
        )

        response.contentType = "application/json";
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}

object AuthConstants {
    const val USERNAME_HEADER = "x-username"
}

data class ErrorResponse(
    val timestamp: Date,
    val status: Int,
    val error: String,
    val path: String?
)