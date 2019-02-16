package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.Currency;

import java.util.List;

public interface CurrencyDao {

    List<Currency> findAll();
    Currency findById(long id);
    void save(Currency currency);
}
