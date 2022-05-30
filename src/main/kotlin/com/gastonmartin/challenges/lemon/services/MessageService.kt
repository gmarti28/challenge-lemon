package com.gastonmartin.challenges.lemon.services

import com.gastonmartin.challenges.lemon.clients.FOAASClient
import org.springframework.stereotype.Service

@Service
class MessageService(val client: FOAASClient) {

    fun getBecauseMessage() = client.getBecause()

}