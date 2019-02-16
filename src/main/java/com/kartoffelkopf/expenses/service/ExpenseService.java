package com.kartoffelkopf.expenses.service;


import com.kartoffelkopf.expenses.model.Expense;

import java.util.List;

public interface ExpenseService {
    List<Expense> findAll();
    Expense findById(long id);
    void save(Expense expense);
    void delete(Expense expense);
}
