/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sh.crm.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author achah
 */
@Entity
@Table(name = "calendar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calendar.findAll", query = "SELECT c FROM Calendar c")})
public class Calendar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;
    @Column(name = "isWeekday")
    private Short isWeekday;
    @Column(name = "isHoliday")
    private Short isHoliday;
    @Column(name = "Y")
    private Short y;
    @Column(name = "FY")
    private Short fy;
    @Column(name = "Q")
    private Short q;
    @Column(name = "M")
    private Short m;
    @Column(name = "D")
    private Short d;
    @Column(name = "DW")
    private Short dw;
    @Size(max = 9)
    @Column(name = "monthname")
    private String monthname;
    @Size(max = 9)
    @Column(name = "dayname")
    private String dayname;
    @Column(name = "W")
    private Short w;
    @Size(max = 150)
    @Column(name = "Description")
    private String description;

    public Calendar() {
    }

    public Calendar(Date dt) {
        this.dt = dt;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Short getIsWeekday() {
        return isWeekday;
    }

    public void setIsWeekday(Short isWeekday) {
        this.isWeekday = isWeekday;
    }

    public Short getIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(Short isHoliday) {
        this.isHoliday = isHoliday;
    }

    public Short getY() {
        return y;
    }

    public void setY(Short y) {
        this.y = y;
    }

    public Short getFy() {
        return fy;
    }

    public void setFy(Short fy) {
        this.fy = fy;
    }

    public Short getQ() {
        return q;
    }

    public void setQ(Short q) {
        this.q = q;
    }

    public Short getM() {
        return m;
    }

    public void setM(Short m) {
        this.m = m;
    }

    public Short getD() {
        return d;
    }

    public void setD(Short d) {
        this.d = d;
    }

    public Short getDw() {
        return dw;
    }

    public void setDw(Short dw) {
        this.dw = dw;
    }

    public String getMonthname() {
        return monthname;
    }

    public void setMonthname(String monthname) {
        this.monthname = monthname;
    }

    public String getDayname() {
        return dayname;
    }

    public void setDayname(String dayname) {
        this.dayname = dayname;
    }

    public Short getW() {
        return w;
    }

    public void setW(Short w) {
        this.w = w;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dt != null ? dt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calendar)) {
            return false;
        }
        Calendar other = (Calendar) object;
        if ((this.dt == null && other.dt != null) || (this.dt != null && !this.dt.equals(other.dt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sh.crm.jpa.entities.Calendar[ dt=" + dt + " ]";
    }
    
}
