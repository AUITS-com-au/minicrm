package com.sh.crm.jpa.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BasicModelWithID extends BasicModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonProperty
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
