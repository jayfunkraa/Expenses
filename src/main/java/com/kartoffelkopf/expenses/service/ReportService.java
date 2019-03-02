package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.model.Expense;
import com.kartoffelkopf.expenses.model.Report;

import java.util.List;

public interface ReportService {
    List<Report> findAll();

    Report findById(long id);

    void save(Report report);

    void delete(Report report);

    void submit(Report report);

    List<Report> findAllOpen();

    List<Expense> getAllExpenses(Report report);

    double getTotal(Report report);

    void calculateAll();
}
