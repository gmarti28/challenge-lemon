package com.gastonmartin.challenges.lemon.controllers

import com.gastonmartin.challenges.lemon.exceptions.TooManyRequestsException
import com.gastonmartin.challenges.lemon.services.MessageService
import com.gastonmartin.challenges.lemon.util.HomemadeRateLimiter
import com.gastonmartin.challenges.lemon.util.Logging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(val service: MessageService, val limiter: HomemadeRateLimiter) {

    companion object: Logging

    @GetMapping("/message")
    fun getBecause(): FOAASResponse {
        /* As per the challenge requirements, a custom-made rate limiter is used instead of a production-ready such as Bucket4j, Resilience4j or Guava */
        return if ( limiter.consume()){
            // todo: Add further exception handling
            FOAASResponse(message = service.getBecauseMessage())
        } else {
            logger.error("Found that ${limiter.getAllRecords().size} requests were made in the last 10 seconds")
            throw TooManyRequestsException()
        }
    }
}

data class FOAASResponse(
    val message: String
)