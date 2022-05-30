package com.gastonmartin.challenges.lemon.config
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "ratelimit")
data class RateLimitConfig(
    val maxRequests: Int,
    val windowSizeSeconds: Long
)
