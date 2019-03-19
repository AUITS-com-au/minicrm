package com.sh.crm.general.holders;

import com.sh.crm.jpa.entities.Groups;
import com.sh.crm.jpa.entities.Roles;
import com.sh.crm.jpa.entities.Users;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by achah on 10/13/2017.
 */
public class GroupHolder {
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    private Integer id;

    private List<Roles> roles;

    private List<Users> users;

    private Groups group;

    public GroupHolder() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "GroupHolder{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", roles=" + roles +
                ", users=" + users +
                ", group=" + group +
                '}';
    }
}
