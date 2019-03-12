package com.sh.crm.email.service;

import java.util.List;

public interface EmailService {
    int handleEmail(String subject, String text, String attachments, String... to);

    int sendEmail(String subject, String text, String... to);

    int sendEmailWithAttachments(String subject, String text, List<Long> attachments, String... to);
}
