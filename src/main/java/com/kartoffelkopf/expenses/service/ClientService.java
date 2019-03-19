package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.model.Client;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClientService {

    List<Client> findAll();
    Client findById(long id);
    void setDefault(long id);
    void importFromCsv(MultipartFile file) throws IOException;
}
