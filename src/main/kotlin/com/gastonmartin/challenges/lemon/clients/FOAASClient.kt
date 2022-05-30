package com.gastonmartin.challenges.lemon.clients

import com.gastonmartin.challenges.lemon.config.FOAASConfig
import com.gastonmartin.challenges.lemon.exceptions.InternalServerErrorException
import com.gastonmartin.challenges.lemon.util.Logging
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.UnknownHttpStatusCodeException
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI


@Component
class FOAASClient(private val config: FOAASConfig) {

    private val rest: RestTemplate = RestTemplate()
    companion object: Logging

    fun getBecause(): String{

        val uriTemplate = "${config.endpoint}/${config.operation}/{param}"

        val uri: URI = UriComponentsBuilder
            .fromUriString(uriTemplate)
            .build("Annonymous")

        val requestEntity: RequestEntity<Void> = RequestEntity
            .get(uri)
            .header("Accept", "text/plain")
            .build()

        val response: ResponseEntity<String>? = try {
            rest.exchange(requestEntity, String::class.java)
        } catch (e: UnknownHttpStatusCodeException) {
            logger.warn("Unknown HTTP Status Code: ${e.message}")
            throw InternalServerErrorException(e)
        } catch (e: Exception){
            logger.error("${e.message} while requesting message from FOAAS service")
            throw e
        }
        return response?.body ?: ""
    }
}