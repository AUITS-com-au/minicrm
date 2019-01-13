package com.sh.crm.general;

public enum Errors {

    SUCCESSFUL( "0", "SUCCESSFUL RESPONSE" ),
    GENERAL_ERROR( "-1", "GENERAL ERROR" ),
    CANNOT_CREATE_USER( "100", "Unable to create user" ),
    USER_CREATED_ROLES_FAILED( "101", "User created but roles failed to be created" ),
    USER_CREATED_GROUPS_FAILED( "102", "User created but groups failed to be created" ),
    USER_ALREADY_EXISTS( "103", "User Already Exists: " ),
    USER_EDIT_FAILED( "104", "Can't modify user" ),
    USER_NOT_EXISTS( "105", "User does not exists" ),
    USER_NOTALLOWED_TO_CHANGE_SUPERUSER( "106", "Super user is mutable " ),
    USER_DELETE_GROUPS_FAILED( "107", "Cannot delete user's groups" ),
    USER_DELETE_ROLES_FAILED( "108", "Cannot delete user's roles" ),
    USER_ID_CHANGED( "109", "Sorry User ID cannot be changed" ),
    GROUP_CREATE_FAILED( "201", "Cannot Create Group" ),
    GROUP_CREATED_OTHER_FAILED( "202", "Group Created but Other Transaction Failed" ),
    GROUP_EDIT_FAILED( "203", "Cannot Edit Group" ),
    GROUP_EDIT_OTHER_FAILED( "204", "Group Edited but Other Transaction Failed" ),
    GROUP_NOT_EXISTS( "205", "GROUP does not exists" ),
    ROLE_CREATE_FAILED( "206", "Cannot Create Role" ),
    ROLE_CREATED_OTHER_FAILED( "207", "ROLE Created but Other Transaction Failed" ),
    ROLE_EDIT_FAILED( "208", "Cannot Edit ROLE" ),
    FILE_NOT_FOUND( "209", "File Not Found!" ),
    INVALID_TICKET_LOCK( "210", "Invalid Ticket Lock" ),
    EXPIRED_TICKET_LOCK( "211", "Expired Ticket Lock" ),
    TICKET_LOCKED( "212", "Ticket Locked" ),
    INVALID_TICKET( "213", "Invalid Ticket ID" ),
    CANNOT_CREATE_OBJECT( "10000", "Request for create object rejected" ),
    CANNOT_EDIT_OBJECT( "10001", "Request for edit object rejected" ),
    UNAUTHORIZED( "10002", "You are not allowed to perform this action please contact your system admin" ),
    CANNOT_FIND_TICKET( "10003", "Ticket ID not valid" ),
    CANNOT_APPLY_ACTION( "10004", "Cannot Apply Action, This might Happen due to missing information in the request body" );

    private String code;
    private String desc;

    Errors(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
