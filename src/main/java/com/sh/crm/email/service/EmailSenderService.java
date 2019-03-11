package com.sh.crm.email.service;

import com.sh.crm.email.enums.EmailStatus;
import com.sh.crm.general.utils.LoggingUtils;
import com.sh.crm.jpa.entities.Emailhistory;
import com.sh.crm.jpa.repos.global.CalendarRepo;
import com.sh.crm.jpa.repos.notifications.EmailHistoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class EmailSenderService {
    private static final Logger logger = LoggerFactory.getLogger( EmailSenderService.class );

    private final static long emailPeriod = 5000l;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailHistoryRepo emailHistoryRepo;
    @Autowired
    private CalendarRepo calendarRepo;
    @Value("${email.sendingTime}")
    private String sendingTime;


    @Scheduled(fixedRate = emailPeriod)
    void emailSchedule() throws Exception {

        if (calendarRepo.findByIsHolidayFalseAndIsWeekdayTrueAndDate( Date.from( LocalDate.now().atStartOfDay( ZoneId.systemDefault() ).toInstant() ) ) == null) {
            logger.info( "Today Is NOT a working day, EMAIL service will not run" );
            return;
        }

        if (sendingTime != null && !sendingTime.isEmpty()) {
            Integer start = Integer.parseInt( sendingTime.substring( 0, sendingTime.indexOf( "," ) ) );
            Integer end = Integer.parseInt( sendingTime.substring( sendingTime.indexOf( "," ) + 1 ) );
            Calendar calendar = Calendar.getInstance();
            logger.debug( "Hour of day now is {}", calendar.get( Calendar.HOUR_OF_DAY ) );
            if (calendar.get( Calendar.HOUR_OF_DAY ) < start || calendar.get( Calendar.HOUR_OF_DAY ) > end) {
                logger.info( "Today is working but time is not yet, working hours start {} ,end {}", start, end );
                return;
            }
        }

        ReentrantLock lock = new ReentrantLock();

        if (lock.tryLock( emailPeriod, TimeUnit.MILLISECONDS )) {
            logger.debug( "................Locking EMAIL Service for {} Milli-Second................", emailPeriod );
            try {
                List<Emailhistory> emailhistoryList = emailHistoryRepo.findByStatusIsNullOrStatusNotInAndSendDateIsNullAndTriesLessThan( Arrays.asList( EmailStatus.DONTSEND, EmailStatus.MAXTRIES ), 4 );
                if (emailhistoryList == null || emailhistoryList.isEmpty()) {
                    logger.info( "---------------No EMAILS TO SEND----------------" );
                } else {
                    for (Emailhistory emailhistory : emailhistoryList) {
                        Integer emailStatus = null;
                        try {
                            emailStatus = emailService.handleEmail( emailhistory.getEmailMessage().getEmailTitle(), emailhistory.getEmailMessage().getEmailMessage(), emailhistory.getEmailMessage().getAttachmentsID(), emailhistory.getEmailID() );
                        } catch (Exception e) {
                            LoggingUtils.logStackTrace( logger, e, "error" );
                            logger.error( "error sending email ! {}", e );
                            emailStatus = EmailStatus.FAILED;
                        }
                        if (emailStatus == EmailStatus.SUCCESSFULL) {
                            emailhistory.setSendDate( Calendar.getInstance().getTime() );
                        }
                        emailhistory.setStatus( emailStatus );
                        Integer tries = emailhistory.getTries();
                        if (tries == null)
                            tries = 0;
                        ++tries;
                        emailhistory.setTries( tries );
                        if (tries == 4 && emailStatus != EmailStatus.SUCCESSFULL)
                            emailhistory.setStatus( EmailStatus.MAXTRIES );
                        emailHistoryRepo.save( emailhistory );
                    }
                }
            } catch (Exception e) {
                LoggingUtils.logStackTrace( logger, e, "error" );

            } finally {
                lock.unlock();
                logger.debug( "................EMAIL Service Unlock................" );
            }
        }
    }


}
