package com.sh.crm.general.holders;

import com.sh.crm.jpa.entities.Topicsla;

import java.util.List;

public class TopicSlaHolder {
    private List<String> usersList;
    private Topicsla topicsla;

    public TopicSlaHolder() {

    }

    public List<String> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<String> usersList) {
        this.usersList = usersList;
    }

    public Topicsla getTopicsla() {
        return topicsla;
    }

    public void setTopicsla(Topicsla topicsla) {
        this.topicsla = topicsla;
    }
}
