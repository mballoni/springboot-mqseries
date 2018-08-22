package br.com.poc.jmsmqseries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;

@Component
public class MessageListener {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @JmsListener(destination = "${queue.name}", containerFactory = "myFactory", concurrency = "5-10", id = "ListenerDEV1")
    public void receiveMessage(Email content, MessageHeaders headers, Session session) throws JMSException {
        System.out.println(session.getAcknowledgeMode());
        System.out.println("trace-id: " + headers.get("traceId"));
        System.out.println("Received <" + content + ">");

        jdbcTemplate.update("INSERT INTO emails (destionation, message) VALUE (?, ?)", content.getDestination(), content.getMessage());
    }
}
