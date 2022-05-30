package com.gastonmartin.challenges.lemon.config
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "fooas")
data class FOAASConfig(
    val endpoint: String,
    val operation: String
)
