package com.sh.crm.email.service;


import com.sh.crm.email.enums.EmailStatus;
import com.sh.crm.general.utils.LoggingUtils;
import com.sh.crm.jpa.entities.Attachments;
import com.sh.crm.jpa.repos.tickets.AttachmentsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class EmailServiceImpl implements EmailService {

    private final static Logger log = LoggerFactory.getLogger( EmailServiceImpl.class );

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private AttachmentsRepo attachmentsRepo;

    @Override
    public int sendEmail(String subject, String text, String... to) {
        log.debug( "Sending message to {} with subject {} ", to, subject );
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo( to );
            message.setSubject( subject );
            message.setText( text );
            emailSender.send( message );
            log.debug( "Message sent successfully to {}", to );
            return EmailStatus.SUCCESSFULL;
        } catch (Exception e) {
            log.error( "Error while sending email {}", e );
            LoggingUtils.logStackTrace( log, e, "error" );
            return EmailStatus.FAILED;
        }
    }

    @Override
    public int sendEmailWithAttachments(String subject, String text, List<Long> attachments, String... to) {
        if (attachments == null || attachments.isEmpty())
            return sendEmail( subject, text, to );

        List<Attachments> attachmentsList = attachmentsRepo.findByIdIn( attachments );
        if (attachmentsList == null || attachmentsList.isEmpty())
            return sendEmail( subject, text, to );
        else {
            try {
                log.debug( "Sending Multi-Part message to {} with subject {} ", to, subject );
                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper( message, true );
                helper.setTo( to );
                helper.setSubject( subject );
                helper.setText( text );
                for (Attachments attch : attachmentsList) {
                    helper.addAttachment( attch.getFileName(), new ByteArrayDataSource( attch.getRAWContent(), attch.getFileType() ) );
                }
                emailSender.send( message );
                log.debug( "Multi-Part message sent successfully to {}", to );
                return EmailStatus.SUCCESSFULL;
            } catch (MessagingException e) {
                log.error( "Error while sending email {}", e );
                LoggingUtils.logStackTrace( log, e, "error" );
                return EmailStatus.FAILED;
            }
        }
    }

    @Override
    public int handleEmail(String subject, String text, String attachments, String... to) {
        List<Long> attachmentsList = null;
        if (attachments != null && !attachments.isEmpty()) {
            attachmentsList = Stream.of( attachments.split( "," ) ).map( Long::parseLong ).collect( Collectors.toList() );
        }
        return sendEmailWithAttachments( subject, text, attachmentsList, to );
    }
}
