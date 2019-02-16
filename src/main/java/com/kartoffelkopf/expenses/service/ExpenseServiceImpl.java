package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.data.ExpenseDao;
import com.kartoffelkopf.expenses.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseDao expenseDao;

    @Override
    public List<Expense> findAll() {
        return expenseDao.findAll();
    }

    @Override
    public Expense findById(long id) {
        return expenseDao.findById(id);
    }

    @Override
    public void save(Expense expense) {
        expenseDao.save(expense);
    }

    @Override
    public void delete(Expense expense) {
        expenseDao.delete(expense);
    }
}
