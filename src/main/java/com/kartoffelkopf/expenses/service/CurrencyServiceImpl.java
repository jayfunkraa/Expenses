package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.data.CurrencyDao;
import com.kartoffelkopf.expenses.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
