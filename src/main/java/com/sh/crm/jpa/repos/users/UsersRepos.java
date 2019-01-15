package com.sh.crm.jpa.repos.users;

import com.sh.crm.jpa.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsersRepos extends JpaRepository<Users, Integer> {
    Users findByUserID(String username);

    @Override
    @Query("select u from Users u where u.systemUser=false")
    List<Users> findAll();

    @Modifying
    void deleteByUserID(String username);

    @Modifying
    void deleteById(Integer id);

    @Query("select u from Users u where u.id=?1 and u.systemUser=false")
    Optional<Users> findById(Integer id);

    List<Users> findByEnabledAndSystemUser(boolean enabled, boolean systemUser);

    Users findByUserIDAndPassword(String username, String password);

    Users findByUserIDAndEnabledAndLDAPUser(String username, boolean enabled, boolean LDAPUser);

    /*    @Query(nativeQuery = true, value = "SELECT * FROM [USER] WHERE username=?1 and password=?2 and [enabled]=1 and LDAPuser=0  ")
        User validateCredentials(String username,String password);*/
   /* @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "insert into USER_AUTHORITY Values(?1,?2)")
    void addUserRoles(Long userID, Long authority);*/

/**
 @Modifying
 @Transactional
 @Query(nativeQuery = true, value = "insert into USER_GROUP Values(?1,?2)")
 void addUserGroups(Long userID, Long groupID);

 @Modifying
 @Transactional
 @Query(nativeQuery = true, value = "delete from USER_AUTHORITY where USER_ID=?1")
 void deleteUserRoles(Long userID);

 @Modifying
 @Transactional
 @Query(nativeQuery = true, value = "delete from USER_GROUP where USER_ID=?")
 void deleteUserGroups(Long userID);
 */

}
