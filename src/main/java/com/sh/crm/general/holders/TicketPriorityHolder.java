package com.sh.crm.general.holders;

public class TicketPriorityHolder {
    private TicketPriority priority;
    private Integer priorityValue;

    public TicketPriorityHolder(TicketPriority priority) {
        this.priority = priority;
        this.priorityValue = this.priority.getPriority();
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public Integer getPriorityValue() {
        return priorityValue;
    }

    public void setPriorityValue(Integer priorityValue) {
        this.priorityValue = priorityValue;
    }
}
