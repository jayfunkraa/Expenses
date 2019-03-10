package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.Currency;
import com.kartoffelkopf.expenses.model.CurrencyMap;

import java.util.List;

public interface CurrencyDao {

    List<Currency> findAll();
    Currency findById(long id);
    void save(Currency currency);

    List<CurrencyMap> findAllCurrencyMaps();
    CurrencyMap findCurrencyMapByFromTo(Currency from, Currency to);

}
