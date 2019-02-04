package com.sh.crm.general.holders;

public class SearchTicketsSorting {

    private String sortBy;
    private int sortType = 1;


    public SearchTicketsSorting() {

    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }
}
