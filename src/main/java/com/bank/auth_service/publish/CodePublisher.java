package com.bank.auth_service.publish;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bank.auth_service.dto.ConclusionPaymentDto;
import com.bank.auth_service.dto.ConfirmCodeDto;
import com.bank.auth_service.dto.SendEmailDto;
import com.bank.auth_service.model.Code;

/**
 * Handles the publication of messages to RabbitMQ queues for email notifications
 * and payment confirmations.
 * 
 * <p>This class publishes:</p>
 * <ul>
 *   <li>Security code emails</li>
 *   <li>Unusual access warnings</li>
 *   <li>Payment confirmation messages</li>
 * </ul>
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @since 1.0
 */
@Component
public class CodePublisher {
    
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.email.sender}")
    private String routingEmailKey;

    @Value("${broker.queue.sendPayment}")
    private String routingPaymentKey;

    /**
     * Publishes an email message containing a security code to the user.
     * 
     * @param code the security code to be sent
     */
    public void publishMessageEmailWithCodeSecurity(Code code){
        var emailDto = new SendEmailDto();
        Instant createdInstant = Instant.ofEpochMilli(code.getCreatedAt());
        String formattedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                .withZone(ZoneId.of("America/Sao_Paulo"))
                .format(createdInstant);

        emailDto.setEmailTo(code.getKeyCode());
        emailDto.setSubject("Confirmação com Código de Segurança");
        emailDto.setText("Olá,\n\n"
            + "Você solicitou um código de segurança. Aqui está o seu código:\n\n"
            + code.getCode() + "\n\n"
            + "Este código é válido por 2 minutos a partir do momento em que foi gerado, em " + formattedDate + ".\n"
            + "Se você não solicitou este código, por favor ignore este e-mail.\n\n"
            + "Atenciosamente,\n"
            + "Seu banco de confiança");

        rabbitTemplate.convertAndSend("", routingEmailKey,emailDto);
    }
    /**
     * Publishes a warning email about an unusual access attempt with mismatched IP or device.
     * 
     * @param email the email address to send the warning to
     * @param ip the IP address from which the unusual access was detected
     * @param userAgent the user agent string of the browser or device used for access
     * @param detectedAt the timestamp when the unusual access was detected
     */
    public void publishMessageEmailWithUnusualAccessWarning(String email, String ip, String userAgent, Instant detectedAt) {
        var emailDto = new SendEmailDto();

        String formattedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                .withZone(ZoneId.of("America/Sao_Paulo"))
                .format(detectedAt);

        emailDto.setEmailTo(email);
        emailDto.setSubject("Aviso de Acesso Incomum Detectado");
        emailDto.setText("Olá,\n\n"
            + "Detectamos uma tentativa de acesso com um IP ou navegador diferente do usual associado à sua conta.\n\n"
            + "Informações do acesso:\n"
            + "- IP: " + ip + "\n"
            + "- Navegador (User-Agent): " + userAgent + "\n"
            + "- Data e Hora: " + formattedDate + "\n\n"
            + "Se esta tentativa foi feita por você, nenhuma ação adicional é necessária.\n"
            + "Caso contrário, recomendamos que altere sua senha imediatamente e entre em contato com o suporte.\n\n"
            + "Atenciosamente,\n"
            + "Equipe de Segurança");

        rabbitTemplate.convertAndSend("", routingEmailKey, emailDto);
    }

    /**
     * Publishes a payment confirmation message to the appropriate RabbitMQ queue
     * after successful code validation.
     * @param confirmCodeDto the DTO containing payment confirmation details
     */
    public void publishValidatePayment(ConfirmCodeDto confirmCodeDto){
        var conclusionPaymentDto = new ConclusionPaymentDto(
            confirmCodeDto.idAccount(),
            confirmCodeDto.pixKey(),
            confirmCodeDto.paymentDescription(),
            confirmCodeDto.amountPaid()
        );
        rabbitTemplate.convertAndSend("", routingPaymentKey, conclusionPaymentDto);
    }
}
