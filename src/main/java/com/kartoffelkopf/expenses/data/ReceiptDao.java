package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.Receipt;

import java.util.List;

public interface ReceiptDao {
    List<Receipt> findAll();

    Receipt findById(long id);

    void save(Receipt receipt);

    void delete(Receipt receipt);
}
