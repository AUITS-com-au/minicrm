package com.sh.crm.rest.controllers.ticket;

import com.sh.crm.config.general.ResponseCode;
import com.sh.crm.general.Errors;
import com.sh.crm.general.exceptions.GeneralException;
import com.sh.crm.general.holders.TopicSlaHolder;
import com.sh.crm.jpa.entities.Slausers;
import com.sh.crm.jpa.entities.Topic;
import com.sh.crm.jpa.entities.Topicsla;
import com.sh.crm.jpa.repos.tickets.SlaRepo;
import com.sh.crm.jpa.repos.tickets.SlaUsersRepo;
import com.sh.crm.rest.general.BasicController;
import com.sh.crm.security.annotation.TicketsAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "topicSLA", produces = MediaType.APPLICATION_JSON_VALUE)
@TicketsAdmin
public class TopicSlaController extends BasicController<TopicSlaHolder> {

    @Autowired
    private SlaRepo slaRepo;
    @Autowired
    private SlaUsersRepo slaUsersRepo;

    @Override
    protected Iterable<?> all() {
        return topicSlaRepo.findAllOrdered();
    }

    @GetMapping("topic/{topicId}")
    public ResponseEntity<?> getByTopicID(@PathVariable("topicId") Integer topicID) {
        return ResponseEntity.ok( topicSlaRepo.findDistinctByTopicIDOrderBySlaLevelAsc( new Topic( topicID ) ) );
    }

    @Override
    public ResponseEntity<?> create(@RequestBody TopicSlaHolder topicSlaHolder, Principal principal) throws GeneralException {
        if (topicSlaHolder != null) {
            Topicsla topicsla = topicSlaHolder.getTopicsla();
            topicsla.setId( null );
            topicsla = topicSlaRepo.save( topicsla );
            createSlaUsers( topicsla.getId(), topicSlaHolder.getUsersList() );
            return ResponseEntity.ok( topicSlaRepo.findDistinctByTopicIDOrderBySlaLevelAsc( topicsla.getTopicID() ) );
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_CREATE_OBJECT ) );
    }

    @Override
    public ResponseEntity<?> edit(@RequestBody TopicSlaHolder topicSlaHolder, Principal principal) throws GeneralException {
        if (topicSlaHolder.getTopicsla() != null && topicSlaHolder.getTopicsla().getId() != null) {
            Topicsla topicsla = topicSlaRepo.save( topicSlaHolder.getTopicsla() );
            deleteSlaUsers( topicsla.getId() );
            createSlaUsers( topicsla.getId(), topicSlaHolder.getUsersList() );
            return ResponseEntity.ok( topicSlaRepo.findDistinctByTopicIDOrderBySlaLevelAsc( topicsla.getTopicID() ) );
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_EDIT_OBJECT ) );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> delete(@RequestBody TopicSlaHolder topicSlaHolder, Principal principal) throws GeneralException {
        if (topicSlaHolder.getTopicsla() != null && topicSlaHolder.getTopicsla().getId() != null) {
            Topic topic = topicSlaHolder.getTopicsla().getTopicID();
            topicSlaRepo.delete( topicSlaHolder.getTopicsla() );
            return ResponseEntity.ok( topicSlaRepo.findDistinctByTopicIDOrderBySlaLevelAsc( topic ) );
        }
        return ResponseEntity.badRequest().body( new ResponseCode( Errors.CANNOT_EDIT_OBJECT ) );
    }

    @Transactional(rollbackFor = Exception.class)
    public void createSlaUsers(Integer topicSla, List<String> usersList) {
        List<Slausers> slausersList = new ArrayList<>();
        if (usersList != null && !usersList.isEmpty()) {
            for (String userID : usersList) {
                Slausers slausers = new Slausers();
                slausers.setTopicSLA( topicSla );
                slausers.setUserId( userID );
                slausersList.add( slausers );
            }
            slaUsersRepo.saveAll( slausersList );
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteSlaUsers(Integer topicSla) {
        slaUsersRepo.deleteAllByTopicSLA( topicSla );
    }
}
