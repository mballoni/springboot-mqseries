package br.com.poc.jmsmqseries;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class ApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JmsTemplate jmsTemplate;


    @Value("${queue.name}")
    private String queueName;


    @Test
    public void the_published_email_should_be_persisted_as_is() throws Exception {
        jmsTemplate.convertAndSend(queueName, new Email("abacate", "content abacate"));


        Thread.sleep(400);

        List<String> result = jdbcTemplate.query("SELECT * FROM emails", (resultSet, i) -> resultSet.getString("message"));

        assertThat(result)
                .containsExactly("content abacate");
    }

}
