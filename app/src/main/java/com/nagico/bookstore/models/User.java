package com.nagico.bookstore.models;

import org.greenrobot.greendao.annotation.*;

import java.util.Date;
import java.util.List;

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;

    @Unique
    private String username;

    @NotNull
    private String password;

    @ToMany(referencedJoinProperty = "cardId")
    private transient List<OrderItem> cart;

    @NotNull
    private Date createdAt;

    @NotNull
    private Date updatedAt;

    @Generated(hash = 2018120844)
    public User(Long id, String username, @NotNull String password,
            @NotNull Date createdAt, @NotNull Date updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
