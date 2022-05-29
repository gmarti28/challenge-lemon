package com.gastonmartin.challenges.lemon.util

import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Component
class HomemadeRateLimiter {
    private val requests: MutableCollection<LocalDateTime> = ArrayList()

    /*  Under heavy load the list of requests should have at most 5 records
     *  so cleaning it upfront before inserting should not increase response time
     *  in a considerable manner.
     **/

    // Allow up to 5 requests in the last 10 seconds
    fun consume(): Boolean {
        cleanup()
        synchronized(this){
            if (requests.size >= 5) return false
            requests.add(LocalDateTime.now())
        }
        return true
    }

    // Evict or release records of old requests based on time window (10 secs)
    fun cleanup(){
        synchronized(this){
            val firstCount = requests.size
            val evictTime = LocalDateTime.now().minus(10L, ChronoUnit.SECONDS)
            requests.removeIf {
                it.isBefore(evictTime)
            }
            val secondCount = requests.size
            if (secondCount < firstCount) {
                // todo: Convert to log.debug or remove
                System.err.println("Removed ${firstCount-secondCount} elements from ratelimit records")
            }
        }
    }

    fun getAllRecords() = requests

}