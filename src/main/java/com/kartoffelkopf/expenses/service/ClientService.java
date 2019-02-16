package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> findAll();
    Client findById(long id);
    void setDefault(long id);
}
