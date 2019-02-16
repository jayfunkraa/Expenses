package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.data.ClientDao;
import com.kartoffelkopf.expenses.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientDao clientDao;

    @Override
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    @Override
    public Client findById(long id) {
        return clientDao.findById(id);
    }

    @Override
    public void setDefault(long id) {
        List<Client> clients = findAll();
        for (Client cli : clients) {
            if (cli.isDefaultClient()) {
                cli.setDefaultClient(false);
                clientDao.save(cli);
            }
        }
        Client client = findById(id);
        client.setDefaultClient(true);
        clientDao.save(client);
    }
}
