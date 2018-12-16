package com.sh.crm.jpa.repos.custom;

import com.sh.crm.jpa.entities.Maincategory;
import com.sh.crm.jpa.entities.Subcategory;
import com.sh.crm.jpa.entities.Topic;
import com.sh.crm.jpa.entities.Topicspermissions;

import java.util.Set;

public interface GetUsersTopicsCustom {
    Set<Topic> getUserTopics(String userID);

    Set<Topicspermissions> getUserTopicsPermissions(String userID);

    Set<Topicspermissions> getUserTopicsPermissions(String userID, Integer topicID);

    Set<Topic> getUserTopics(String userID, Integer subcat);

    Set<Subcategory> getUserSubCats(String userID, Integer mainCat);

    Set<Maincategory> getUserMainCats(String userID);

    void generateUserTopicsPermission(Integer topicID, String username, Integer userID);

    void generateGroupTopicsPermission(Integer topicID, String groupName, Integer groupID);


}
