package br.com.poc.jmsmqseries;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;

@Slf4j
@Component
public class MessageListener {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @JmsListener(destination = "${queue.name}", containerFactory = "myFactory", concurrency = "5-10", id = "ListenerDEV1")
    public void receiveMessage(Email content, MessageHeaders headers, Session session) throws JMSException {
        log.info("Ack mode: {}", session.getAcknowledgeMode());
        log.info("trace-id: {}", headers.get("traceId"));
        log.info("Received <{}>", content);

        jdbcTemplate.update("INSERT INTO emails (destination, message) VALUE (?, ?)", content.getDestination(), content.getMessage());
    }
}
