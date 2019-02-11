package com.sh.crm.general.holders;

import java.util.List;

public class GetAuthorizedTopics {
    private List<String> permissions;
    private Integer subCat;
    private Integer mainCat;

    private String assigne;

    public GetAuthorizedTopics() {
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public Integer getSubCat() {
        return subCat;
    }

    public void setSubCat(Integer subCat) {
        this.subCat = subCat;
    }

    public Integer getMainCat() {
        return mainCat;
    }

    public void setMainCat(Integer mainCat) {
        this.mainCat = mainCat;
    }

    public String getAssigne() {
        return assigne;
    }

    public void setAssigne(String assigne) {
        this.assigne = assigne;
    }
}
