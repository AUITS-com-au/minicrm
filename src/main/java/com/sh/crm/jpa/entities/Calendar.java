
package com.sh.crm.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


@Entity
@Table(name = "calendar")
@XmlRootElement
public class Calendar {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "isWeekday")
    private Boolean isWeekday;
    @Column(name = "isHoliday")
    private Boolean isHoliday;
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

    public Calendar(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getIsWeekday() {
        return isWeekday;
    }

    public void setIsWeekday(Boolean isWeekday) {
        this.isWeekday = isWeekday;
    }

    public Boolean getIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(Boolean isHoliday) {
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
        hash += (date != null ? date.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calendar)) {
            return false;
        }
        Calendar other = (Calendar) object;
        if ((this.date == null && other.date != null) || (this.date != null && !this.date.equals( other.date ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "date=" + date +
                ", isWeekday=" + isWeekday +
                ", isHoliday=" + isHoliday +
                '}';
    }
}
