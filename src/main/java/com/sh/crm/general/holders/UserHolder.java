package com.sh.crm.general.holders;

import com.sh.crm.jpa.entities.Users;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserHolder {

    @NotNull
    private Users user;
    private String password;
    private List<Integer> selectedGroups;
    private List<Integer> selectedRoles;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getSelectedGroups() {
        return selectedGroups;
    }

    public void setSelectedGroups(List<Integer> selectedGroups) {
        this.selectedGroups = selectedGroups;
    }

    public List<Integer> getSelectedRoles() {
        return selectedRoles;
    }

    public void setSelectedRoles(List<Integer> selectedRoles) {
        this.selectedRoles = selectedRoles;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                ", password='" + password + '\'' +
                ", selectedGroups=" + selectedGroups +
                ", selectedRoles=" + selectedRoles +
                '}';
    }
}

