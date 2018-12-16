package com.sh.crm.general.holders;

import com.sh.crm.jpa.entities.Ticket;

import java.util.ArrayList;
import java.util.List;

public class SearchTicketsResult {
    private List<Ticket> content;
    private int numberOfElements;
    private long totalElements;
    private int totalPages;
    private int size;
    private int number;
    private boolean last;
    private boolean first;

    public SearchTicketsResult() {
        this.numberOfElements = 0;
        content = new ArrayList<>();
    }

    public List<Ticket> getContent() {
        return content;
    }

    public void setContent(List<Ticket> content) {
        this.content = content;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }
}
