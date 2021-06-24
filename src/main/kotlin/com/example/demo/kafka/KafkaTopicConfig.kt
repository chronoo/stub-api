package com.example.demo.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaTopicConfig {
    @Bean
    fun topic1(): NewTopic =
        NewTopic("topp", 1, 1)
}
