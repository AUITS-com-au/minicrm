package com.sh.crm.rest.general;

import com.sh.crm.general.exceptions.GeneralException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;

public abstract class BasicController<T> {
    public final Logger log = LoggerFactory.getLogger( getClass() );

    @GetMapping("all")
    protected Iterable<?> all() {
        return null;
    }

    @GetMapping("authorized")
    protected Iterable<?> authorizedList() {
        return null;
    }

    @GetMapping("active")
    protected Iterable<?> active() {
        return null;
    }

    @PostMapping("create")
    public abstract ResponseEntity<?> create(T object) throws GeneralException;

    @PostMapping("edit")
    @Transactional
    public abstract ResponseEntity<?> edit(T object) throws GeneralException;

    @PostMapping("delete")
    @Transactional
    public abstract ResponseEntity<?> delete(T object) throws GeneralException;
}
