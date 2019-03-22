package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.CurrencyMap;

import java.util.List;

public interface CurrencyMapDao {
    List<CurrencyMap> findAll();

    CurrencyMap findById(long id);

    void save(CurrencyMap currencyMap);

    void delete(CurrencyMap currencyMap);
}
