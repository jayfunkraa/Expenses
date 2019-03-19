package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.model.Currency;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CurrencyService {

    List<Currency> findAll();

    Currency findById(long id);

    void setDefault(long id);

    void setReportCurrency(long id);

    double getRate(Currency from, Currency to);

    void importFromCsv(MultipartFile file) throws IOException;
}
