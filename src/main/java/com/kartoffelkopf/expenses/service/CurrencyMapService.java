package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.model.Currency;
import com.kartoffelkopf.expenses.model.CurrencyMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CurrencyMapService {
    List<CurrencyMap> findAll();

    CurrencyMap findById(long id);

    void save(CurrencyMap currencyMap);

    void delete(CurrencyMap currencyMap);

    void importFromCsv(MultipartFile file) throws IOException;

    double getRate(Currency from, Currency to);
}
