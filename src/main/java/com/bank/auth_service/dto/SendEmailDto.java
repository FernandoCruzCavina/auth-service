package com.bank.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for sending email notifications.
 * 
 * <p>
 * Contains the recipient's email address, subject, and body text of the email.
 * </p>
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0.0, 06/23/2025
 * @since 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SendEmailDto {

    String emailTo;
    String subject;
    String text;

}
