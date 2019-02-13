
package com.sh.crm.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;


@Entity
@Table(name = "attachments")
@XmlRootElement
public class Attachments extends BasicModelWithID {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @JsonProperty
    @Column(name = "FileName")
    private String fileName;
    @Size(max = 500)
    @Column(name = "FileType")
    @JsonProperty
    private String fileType;
    @Size(max = 50)
    @Column(name = "Hash")
    private String hash;
    @Lob
    @Column(name = "RAWContent")
    private byte[] rawContent;
    @Size(max = 500)
    @JsonIgnore
    @Column(name = "FilePath")
    private String filePath;
    @Size(max = 1000)
    @JsonIgnore
    @Column(name = "FileDesc")
    private String fileDesc;

    public Attachments() {
    }

    public Attachments(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public byte[] getRAWContent() {
        return rawContent;
    }

    public void setRAWContent(byte[] rAWContent) {
        this.rawContent = rAWContent;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Attachments)) {
            return false;
        }
        Attachments other = (Attachments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Attachments{" +
                "fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", hash='" + hash + '\'' +
                ", rawContent=" + Arrays.toString( rawContent ) +
                ", filePath='" + filePath + '\'' +
                ", fileDesc='" + fileDesc + '\'' +
                ", id=" + id +
                ", createdBy='" + createdBy + '\'' +
                ", modificationDate=" + modificationDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
