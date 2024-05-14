package com.odeniz.dev.orbit.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.odeniz.dev.orbit.View;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class DateIDBaseEntity implements DatabaseModel<Long>, Serializable {

    @Serial
    private static final long serialVersionUID = -4246656645746032201L;

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonView(View.Public.class)
    Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at" , nullable = false, updatable = false)
    @Getter
    @Setter
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    @Column(name = "updated_at")
    private Date updatedAt;

    @Override
    public Date getCreatedDate() {
        return createdAt;
    }

    @Override
    public Date getUpdateDate() {
        return updatedAt;
    }

    @Override
    public void setCreatedDate(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public void setUpdateDate(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}