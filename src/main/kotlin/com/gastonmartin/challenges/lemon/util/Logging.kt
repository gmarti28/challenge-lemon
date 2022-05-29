package com.gastonmartin.challenges.lemon.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface Logging {
    val logger: Logger get() = LoggerFactory.getLogger(javaClass)
}
