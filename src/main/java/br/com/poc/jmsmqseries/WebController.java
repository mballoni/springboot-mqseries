package br.com.poc.jmsmqseries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

  @Autowired
  private JmsTemplate jmsTemplate;

  @Value("${queue.name}")
  private String queueName;

  @GetMapping("/put/{content}")
  public void put(@PathVariable("content") String content) {

    jmsTemplate.convertAndSend(queueName, new Email(content, content));
  }
}




