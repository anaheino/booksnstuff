package com.example.userapp.models;

import com.example.common.models.AbstractBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity(name="page_user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(indexes = {
        @Index(name = "user_name_idx", columnList = "name")
})
public class User extends AbstractBaseEntity {

    private String name;
    private String pwd;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }
}
