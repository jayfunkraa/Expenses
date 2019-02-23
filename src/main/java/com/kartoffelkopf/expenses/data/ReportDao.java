package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.Report;

import java.util.List;

public interface ReportDao {
    List<Report> findAll();
    Report findById(long id);
    void save(Report report);
    void delete(Report report);
    List<Report> findAllOpen();
}
