package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.model.Currency;

import java.util.List;

public interface CurrencyService {

    List<Currency> findAll();

    Currency findById(long id);

    void setDefault(long id);

    double getRate(Currency from, Currency to);
}
