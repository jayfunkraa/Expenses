package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.Client;

import java.util.List;

public interface ClientDao {

    List<Client> findAll();
    Client findById(long id);
    void save(Client client);

}
