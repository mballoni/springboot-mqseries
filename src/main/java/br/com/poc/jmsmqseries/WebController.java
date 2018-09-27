package br.com.poc.jmsmqseries;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class WebController {

    private static final MessagePostProcessor ADD_TRACE_ID = message -> {
        message.setStringProperty("traceId", UUID.randomUUID().toString());
        return message;
    };

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${queue.name}")
    private String queueName;

    @Timed("putTimer")
    @GetMapping("/put/{content}")
    public void put(@PathVariable("content") String content) {

        jmsTemplate.convertAndSend(queueName, new Email(content, content), ADD_TRACE_ID);
    }
}




