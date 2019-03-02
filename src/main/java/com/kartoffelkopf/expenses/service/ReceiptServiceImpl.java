package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.data.ReceiptDao;
import com.kartoffelkopf.expenses.model.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    @Autowired
    private ReceiptDao receiptDao;

    @Override
    public List<Receipt> findAll() {
        return receiptDao.findAll();
    }

    @Override
    public Receipt findById(long id) {
        return receiptDao.findById(id);
    }

    @Override
    public void save(Receipt receipt) {
        receiptDao.save(receipt);
    }

    @Override
    public void delete(Receipt receipt) {
        receiptDao.delete(receipt);
    }

}

