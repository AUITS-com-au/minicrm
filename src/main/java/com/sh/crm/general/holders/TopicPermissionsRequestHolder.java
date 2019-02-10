package com.sh.crm.general.holders;

import com.sh.crm.jpa.entities.Topic;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TopicPermissionsRequestHolder {
    @NotNull
    @NotEmpty
    private List<Topic> topicList;
    private Integer assigne;
    private String type;

    public TopicPermissionsRequestHolder() {

    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }


    public Integer getAssigne() {
        return assigne;
    }

    public void setAssigne(Integer assigne) {
        this.assigne = assigne;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
