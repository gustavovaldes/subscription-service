package com.guga.lab.subscription.mockexternal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class RabbitConfiguration {

    @Bean
    public Queue emailQueue(@Value("${emailService.queueName}") String queueName) {
        return new Queue(queueName);
    }


    @Bean
    public TopicExchange exchange(@Value("${emailService.exchange}") String exchangeName) {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding accBinding(TopicExchange exchange, Queue emailQueue,
                              @Value("${emailService.routing-key}") String emailRoutingKey) {
        return BindingBuilder.bind(emailQueue).to(exchange).with(emailRoutingKey);
    }


    @Bean
    public MappingJackson2HttpMessageConverter customJsonHttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public MessageConverter messageConverter(MappingJackson2HttpMessageConverter customJsonHttpMessageConverter) {
        return new Jackson2JsonMessageConverter(customJsonHttpMessageConverter.getObjectMapper());
    }
}