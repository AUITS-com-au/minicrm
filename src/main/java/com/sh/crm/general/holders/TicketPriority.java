package com.sh.crm.general.holders;

public enum TicketPriority {
    URGENT( 5 ), VERY_HIGH( 4 ), HIGH( 3 ), NORMAL( 2 ), LOW( 1 );
    private final int priority;

    private TicketPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
