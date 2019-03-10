package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.ReportView;

import java.util.List;

public interface ReportViewDao {
    List<ReportView> findAll();

    ReportView findById(long id);
}
