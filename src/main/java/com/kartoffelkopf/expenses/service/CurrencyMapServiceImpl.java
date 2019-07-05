package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.data.CurrencyMapDao;
import com.kartoffelkopf.expenses.model.Currency;
import com.kartoffelkopf.expenses.model.CurrencyMap;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class CurrencyMapServiceImpl implements CurrencyMapService {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyMapDao currencyMapDao;

    @Override
    public List<CurrencyMap> findAll() {
        return currencyMapDao.findAll();
    }

    @Override
    public CurrencyMap findById(long id) {
        return currencyMapDao.findById(id);
    }

    @Override
    public void save(CurrencyMap currencyMap) {
        currencyMapDao.save(currencyMap);
    }

    @Override
    public void delete(CurrencyMap currencyMap) {
        currencyMapDao.delete(currencyMap);
    }

    @Override
    public void importFromCsv(MultipartFile file) throws IOException {
        // convert MultipartFile to File
        File convertedFile = new File(file.getOriginalFilename());
        convertedFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        fileOutputStream.write(file.getBytes());

        // read file and create categories
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(convertedFile)).withSkipLines(1).build();
        String[] line = null;
        while ((line = csvReader.readNext()) != null) {
            CurrencyMap currencyMap = new CurrencyMap();
            Currency currencyFrom = currencyService.findByCurrency(line[0]);
            Currency currencyTo = currencyService.findByCurrency(line[1]);
            if (currencyFrom != null && currencyTo != null) {
                currencyMap.setFrom(currencyFrom);
                currencyMap.setTo(currencyTo);
                currencyMap.setRate(Double.parseDouble(line[2]));
                currencyMap.setDate(LocalDate.parse(line[3]));
                save(currencyMap);
            }
        }
    }

    @Override
    public double getRate(Currency from, Currency to) {

        for (CurrencyMap currencyMap : findAll()) {
            if (currencyMap.getFrom() == from && currencyMap.getTo() == to) {
                return currencyMap.getRate();
            }
        }
        return 0;
    }

}
