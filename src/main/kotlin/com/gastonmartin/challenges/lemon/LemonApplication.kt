package com.gastonmartin.challenges.lemon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan
@ConfigurationPropertiesScan
class LemonApplication

fun main(args: Array<String>) {
	runApplication<LemonApplication>(*args)
}
