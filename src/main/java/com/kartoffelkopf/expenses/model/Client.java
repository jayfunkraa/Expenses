package com.kartoffelkopf.expenses.model;

import javax.persistence.*;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String code;
    private String name;
    private boolean defaultClient;

    public Client() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDefaultClient() {
        return defaultClient;
    }

    public void setDefaultClient(boolean defaultClient) {
        this.defaultClient = defaultClient;
    }
}
