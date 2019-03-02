package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.model.Receipt;

import java.util.List;

public interface ReceiptService {
    List<Receipt> findAll();

    Receipt findById(long id);

    void save(Receipt receipt);

    void delete(Receipt receipt);
}
