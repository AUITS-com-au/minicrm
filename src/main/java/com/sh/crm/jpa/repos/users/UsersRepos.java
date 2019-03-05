package com.sh.crm.jpa.repos.users;

import com.sh.crm.jpa.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UsersRepos extends JpaRepository<Users, Integer> {
    Users findByUserID(String username);

    Users findByUserIDAndEnabledIsTrue(String username);
    @Override
    @Query("select u from Users u order by u.creationDate desc")
    List<Users> findAll();

    @Modifying
    void deleteByUserID(String username);

    @Modifying
    void deleteById(Integer id);

    @Query("select u from Users u where u.id=?1")
    Optional<Users> findById(Integer id);

    List<Users> findByEnabledAndSystemUser(boolean enabled, boolean systemUser);

    Users findByUserIDAndPassword(String username, String password);

    @Query("select u  from Users  u where u.email is not null and u.enabled=true and u.userID in ?1 ")
    List<Users> getUsersByUserName(Collection<String> userIDs);

    Users findByUserIDAndEnabledAndLDAPUser(String username, boolean enabled, boolean LDAPUser);




}
