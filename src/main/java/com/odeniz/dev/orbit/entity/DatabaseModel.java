package com.odeniz.dev.orbit.entity;

import java.util.Date;

public interface DatabaseModel<ID extends Long> {

    void setId(ID id);
    ID getId();
    Date getCreatedDate();
    void setCreatedDate(Date date);
    void setUpdateDate(Date date);
    Date getUpdateDate();
}
