package com.sh.crm.misc;

public class TopicConfiguration {
    private Integer topicID;
    private Integer lockTime;

    public TopicConfiguration() {

    }

    public Integer getTopicID() {
        return topicID;
    }

    public void setTopicID(Integer topicID) {
        this.topicID = topicID;
    }

    public Integer getLockTime() {
        return lockTime;
    }

    public void setLockTime(Integer lockTime) {
        this.lockTime = lockTime;
    }
}
