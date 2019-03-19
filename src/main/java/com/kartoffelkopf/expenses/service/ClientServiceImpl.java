package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.data.ClientDao;
import com.kartoffelkopf.expenses.model.Category;
import com.kartoffelkopf.expenses.model.Client;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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

    @Override
    public void importFromCsv(MultipartFile file) throws IOException {
            // convert MultipartFile to File
            File convertedFile = new File(file.getOriginalFilename());
            convertedFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
            fileOutputStream.write(file.getBytes());

            // read file and create categories
            CSVReader csvReader = new CSVReaderBuilder(new FileReader(convertedFile)).withSkipLines(0).build();
            String[] line = null;
            while ((line = csvReader.readNext()) != null) {
                Client client = new Client();
                client.setCode(line[0]);
                client.setName(line[1]);
                client.setDefaultClient(false);
                clientDao.save(client);
            }
    }
}
