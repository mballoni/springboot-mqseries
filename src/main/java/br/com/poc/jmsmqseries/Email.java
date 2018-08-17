package br.com.poc.jmsmqseries;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Email {
    private String destination;
    private String message;

}
