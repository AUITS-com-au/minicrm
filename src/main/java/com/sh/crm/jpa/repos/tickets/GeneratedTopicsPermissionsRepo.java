package com.sh.crm.jpa.repos.tickets;

import com.sh.crm.jpa.entities.GeneratedTopicPermissions;
import com.sh.crm.jpa.entities.Topic;
import com.sh.crm.jpa.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface GeneratedTopicsPermissionsRepo extends JpaRepository<GeneratedTopicPermissions, Long> {
    Set<GeneratedTopicPermissions> getByUserID(Users user);

    Set<GeneratedTopicPermissions> getByUserName(String username);

    @Query("select true from GeneratedTopicPermissions g where g.userName=?1 and g.topic=?2 and g.canRead=true")
    Boolean canRead(String username, Topic topic);

    @Query("select true from GeneratedTopicPermissions g where g.userName=?1 and g.topic=?2 and g.canClose=true")
    Boolean canClose(String username, Topic topic);

    @Query("select true from GeneratedTopicPermissions g where g.userName=?1 and g.topic=?2 and g.canCreate=true")
    Boolean canCreate(String username, Topic topic);

    @Query("select true from GeneratedTopicPermissions g where g.userName=?1 and g.topic=?2 and g.canDelete=true")
    Boolean canDelete(String username, Topic topic);

    @Query("select true from GeneratedTopicPermissions g where g.userName=?1 and g.topic=?2 and g.canModify=true")
    Boolean canModify(String username, Topic topic);

    @Query("select true from GeneratedTopicPermissions g where g.userName=?1 and g.topic=?2 and g.canReopen=true")
    Boolean canReopen(String username, Topic topic);

    @Query("select true from GeneratedTopicPermissions g where g.userName=?1 and g.topic=?2 and g.canReply=true")
    Boolean canReply(String username, Topic topic);

    @Query("select true from GeneratedTopicPermissions g where g.userName=?1 and g.topic=?2 and g.canResolve=true")
    Boolean canResolve(String username, Topic topic);

    @Query("select true from GeneratedTopicPermissions g where g.userName=?1 and g.topic=?2 and g.canRunReport=true")
    Boolean canRunReport(String username, Topic topic);

    @Query("select true from GeneratedTopicPermissions g where g.userName=?1 and g.topic=?2 and g.canSubscribe=true")
    Boolean canSubcribe(String username, Topic topic);
}
