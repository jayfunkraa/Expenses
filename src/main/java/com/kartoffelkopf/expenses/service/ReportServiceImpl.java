package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.data.ReportDao;
import com.kartoffelkopf.expenses.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Override
    public List<Report> findAll() {
        return reportDao.findAll();
    }

    @Override
    public Report findById(long id) {
        return reportDao.findById(id);
    }

    @Override
    public void save(Report report) {
        reportDao.save(report);
    }

    @Override
    public void delete(Report report) {
        reportDao.delete(report);
    }

    @Override
    public void submit(Report report) {
        if (!report.isSubmitted()) {
            report.setSubmitted(true);
            reportDao.save(report);
        }
    }

    @Override
    public List<Report> findAllOpen() {
        return reportDao.findAllOpen();
    }
}
