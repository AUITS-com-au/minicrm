package com.sh.crm.jpa.repos.notifications;

import com.sh.crm.jpa.entities.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepo extends JpaRepository<Subscriptions, Long> {


    List<Subscriptions> getByTopicAndSubStatusIsTrueAndOnCloseIsTrue(Integer topic);

    List<Subscriptions> getByTopicAndSubStatusIsTrueAndOnUpdateIsTrue(Integer topic);

    List<Subscriptions> getByTopicAndSubStatusIsTrueAndOnCreateIsTrue(Integer topic);

    List<Subscriptions> getByTicketIDAndSubStatusTrueAndOnCloseIsTrue(Long ticketID);

    List<Subscriptions> getByTicketIDAndSubStatusTrueAndOnUpdateIsTrue(Long ticketID);

    List<Subscriptions> getByTicketIDAndSubStatusTrueAndOnCreateIsTrue(Long ticketID);
}
