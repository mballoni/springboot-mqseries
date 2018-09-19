package br.com.poc.jmsmqseries;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class Email {
    private String destination;
    private String message;

}
