package com.gastonmartin.challenges.lemon

import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.UnknownHttpStatusCodeException
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI


@Component
class FOAASClient {

    private val rest: RestTemplate = RestTemplate()


    fun getBecause(): String{

        val uriTemplate = "https://www.foaas.com/because/{from}"

        val uri: URI = UriComponentsBuilder
            .fromUriString(uriTemplate)
            .build("Gaston")

        val requestEntity: RequestEntity<Void> = RequestEntity
            .get(uri)
            .header("Accept", "text/plain")
            .build()

        val response: ResponseEntity<String>? = try {
            rest.exchange(requestEntity, String::class.java)
        } catch (e: Exception){
            // todo: Add a log statement
            // todo: Handle org.springframework.web.client.UnknownHttpStatusCodeException i.e 520 : [no body]
            throw e
        }
        return response?.body ?: ""
    }
}