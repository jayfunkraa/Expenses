package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.data.CurrencyDao;
import com.kartoffelkopf.expenses.model.Category;
import com.kartoffelkopf.expenses.model.Currency;
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
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyDao currencyDao;

    @Override
    public List<Currency> findAll() {
        return currencyDao.findAll();
    }

    @Override
    public Currency findById(long id) {
        return currencyDao.findById(id);
    }

    @Override
    public void setDefault(long id) {
        List<Currency> currencies = findAll();
        for (Currency cur : currencies) {
            if (cur.isDefaultCurrency()) {
                cur.setDefaultCurrency(false);
                currencyDao.save(cur);
            }
        }
        Currency currency = findById(id);
        currency.setDefaultCurrency(true);
        currencyDao.save(currency);

    }

    @Override
    public double getRate(Currency from, Currency to) {
        return currencyDao.findCurrencyMapByFromTo(from, to).getRate();
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
                Currency currency = new Currency();
                currency.setCurrency(line[0]);
                currency.setName(line[1]);
                currency.setSymbol(line[2]);
                currency.setCountry(line[3]);
                currency.setDefaultCurrency(false);
                currencyDao.save(currency);
            }
    }
}
