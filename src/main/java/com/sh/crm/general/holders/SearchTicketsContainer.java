package com.sh.crm.general.holders;


import java.util.List;

public class SearchTicketsContainer {
    private Integer size = 20;
    private Integer page = 0;
    private String searchUser;
    private boolean skipValidation;
    private Boolean runningReport;
    private List<String> createdBy;
    private Long startDate;
    private Long endDate;
    private List<Integer> topics;
    private List<Integer> originalTopics;
    private List<Integer> subCats;
    private List<Integer> mainCats;
    private List<Integer> originalSubCats;
    private List<Integer> originalMainCats;
    private List<Integer> sourceChannels;
    private List<Integer> types;
    private List<Integer> status;
    private List<Long> customerAccounts;
    private List<String> assignedTo;

    private List<Integer> language;
    private List<Integer> priority;
    private Boolean deleted;
    private Boolean solved;
    private Boolean closed;
    private Integer numberOfCrossedSLA;
    private Boolean crossedAllSLA;
    private Long totalCrossedTime;
    private SearchTicketsSorting sorting;


    public SearchTicketsContainer() {

    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(String searchUser) {
        this.searchUser = searchUser;
    }

    public boolean getSkipValidation() {
        return skipValidation;
    }

    public void setSkipValidation(Boolean skipValidation) {
        this.skipValidation = skipValidation;
    }

    public Boolean getRunningReport() {
        return runningReport;
    }

    public void setRunningReport(Boolean runningReport) {
        this.runningReport = runningReport;
    }

    public List<String> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(List<String> createdBy) {
        this.createdBy = createdBy;
    }


    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public List<Integer> getTopics() {
        return topics;
    }

    public void setTopics(List<Integer> topics) {
        this.topics = topics;
    }

    public List<Integer> getOriginalTopics() {
        return originalTopics;
    }

    public void setOriginalTopics(List<Integer> originalTopics) {
        this.originalTopics = originalTopics;
    }

    public List<Integer> getSubCats() {
        return subCats;
    }

    public void setSubCats(List<Integer> subCats) {
        this.subCats = subCats;
    }

    public List<Integer> getMainCats() {
        return mainCats;
    }

    public void setMainCats(List<Integer> mainCats) {
        this.mainCats = mainCats;
    }

    public List<Integer> getOriginalSubCats() {
        return originalSubCats;
    }

    public void setOriginalSubCats(List<Integer> originalSubCats) {
        this.originalSubCats = originalSubCats;
    }

    public List<Integer> getOriginalMainCats() {
        return originalMainCats;
    }

    public void setOriginalMainCats(List<Integer> originalMainCats) {
        this.originalMainCats = originalMainCats;
    }

    public List<Integer> getSourceChannels() {
        return sourceChannels;
    }

    public void setSourceChannels(List<Integer> sourceChannels) {
        this.sourceChannels = sourceChannels;
    }

    public List<Integer> getTypes() {
        return types;
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public List<Long> getCustomerAccounts() {
        return customerAccounts;
    }

    public void setCustomerAccounts(List<Long> customerAccounts) {
        this.customerAccounts = customerAccounts;
    }

    public List<String> getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(List<String> assignedTo) {
        this.assignedTo = assignedTo;
    }



    public List<Integer> getLanguage() {
        return language;
    }

    public void setLanguage(List<Integer> language) {
        this.language = language;
    }

    public List<Integer> getPriority() {
        return priority;
    }

    public void setPriority(List<Integer> priority) {
        this.priority = priority;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getSolved() {
        return solved;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public SearchTicketsSorting getSorting() {
        return sorting;
    }

    public void setSorting(SearchTicketsSorting sorting) {
        this.sorting = sorting;
    }

    public Integer getNumberOfCrossedSLA() {
        return numberOfCrossedSLA;
    }

    public void setNumberOfCrossedSLA(Integer numberOfCrossedSLA) {
        this.numberOfCrossedSLA = numberOfCrossedSLA;
    }

    public Boolean getCrossedAllSLA() {
        return crossedAllSLA;
    }

    public void setCrossedAllSLA(Boolean crossedAllSLA) {
        this.crossedAllSLA = crossedAllSLA;
    }

    public Long getTotalCrossedTime() {
        return totalCrossedTime;
    }

    public void setTotalCrossedTime(Long totalCrossedTime) {
        this.totalCrossedTime = totalCrossedTime;
    }
}
