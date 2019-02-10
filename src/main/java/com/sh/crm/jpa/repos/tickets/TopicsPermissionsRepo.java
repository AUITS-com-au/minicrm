package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.Topic;
import com.sh.crm.jpa.entities.Topicspermissions;
import com.sh.crm.jpa.repos.custom.GetUsersTopicsCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface TopicsPermissionsRepo extends JpaRepository<Topicspermissions, Long>, GetUsersTopicsCustom {

    Set<Topicspermissions> findByTopicId_Id(Integer topic);

    Set<Topicspermissions> findByTopicId_SubCategory_Id(Integer subCat);

    Set<Topicspermissions> findByTopicId_SubCategory_MainCategory_Id(Integer mainCat);

    Set<Topicspermissions> findByAssigneAndTypeAndTopicId(Integer assigne, String type, Topic topicID);

    Set<Topicspermissions> findByAssigneAndType(Integer assigne, String type);

    /*@Query("select tp.id,tp.assigne, t ,tp.admin,tp.canAssign,tp.canChgDpt,tp.canClose,tp.canCreate,tp.canDelete, tp.canRead,tp.canModify, tp.canReopen,tp.canReply,tp.canSubscribe,tp.canResolve,tp.canRunReport from Topic t left outer join Topicspermissions  tp on tp.topicId=t and t in  ?1  and tp.assigne =?2  and tp.type=?3")
    Set<Topicspermissions> findTopicsPermissionJoinTopic(List<Topic> topicList, Integer assigne, String type);
*/
    @Query("select tp from   Topicspermissions  tp where tp.topicId in ?1  and tp.assigne =?2  and tp.type=?3")
    List<Topicspermissions> findTopicsPermissionByTPListAndAssigneAndType(List<Topic> topicList, Integer assigne, String type);
}
