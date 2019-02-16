package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.Expense;

import java.util.List;

public interface ExpenseDao {
    List<Expense> findAll();
    Expense findById(long id);
    void save(Expense expense);
    void delete(Expense expense);
}
