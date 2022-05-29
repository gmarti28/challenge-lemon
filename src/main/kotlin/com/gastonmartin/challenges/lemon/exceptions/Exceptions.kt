package com.gastonmartin.challenges.lemon.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "To show an example of a custom message")
class ForbiddenException : RuntimeException()


@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS, reason = "Too many requests were made in the last seconds")
class TooManyRequestsException : RuntimeException()
