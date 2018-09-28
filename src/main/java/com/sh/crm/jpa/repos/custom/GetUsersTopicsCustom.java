package com.sh.crm.jpa.repos.custom;

import com.sh.crm.jpa.entities.Maincategory;
import com.sh.crm.jpa.entities.Subcategory;
import com.sh.crm.jpa.entities.Topic;
import com.sh.crm.jpa.entities.Topicspermissions;

import java.util.Set;

public interface GetUsersTopicsCustom {
    Set<Topic> getUserTopics(Integer userID);

    Set<Topicspermissions> getUserTopicsPermissions(Integer userID);

    Set<Topicspermissions> getUserTopicsPermissions(Integer userID, Integer topicID);

    Set<Topic> getUserTopics(Integer userID, Integer subcat);

    Set<Subcategory> getUserSubCats(Integer userID, Integer mainCat);

    Set<Maincategory> getUserMainCats(Integer userID);


}
