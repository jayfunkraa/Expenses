package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.data.ExpenseDao;
import com.kartoffelkopf.expenses.data.ReportDao;
import com.kartoffelkopf.expenses.data.ReportViewDao;
import com.kartoffelkopf.expenses.model.Currency;
import com.kartoffelkopf.expenses.model.Expense;
import com.kartoffelkopf.expenses.model.Report;
import com.kartoffelkopf.expenses.model.ReportView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private ReportViewDao reportViewDao;

    @Autowired
    private ExpenseDao expenseDao;

    @Autowired
    private CurrencyService currencyService;

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
        List<Expense> expenses = expenseDao.findAll();
        for (Expense expense : expenses) {
            if (expense.getReport().getId() == report.getId()) {
                expense.setReport(getUnreportedReport());
                expenseDao.save(expense);
            }
        }
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
        List<Report> reports = findAll();
        reports.removeIf(Report::isSubmitted);
        return reports;
    }

    @Override
    public List<Expense> getAllExpenses(long id) {
        //TODO: find a more efficient way of getting all expenses for a report
        List<Expense> expenses = expenseDao.findAll();
        expenses.removeIf(expense -> expense.getReport().getId() != id);
        return expenses;
    }

    @Override
    public List<ReportView> getReportView() {
        return reportViewDao.findAll();
    }

    @Override
    public void setReportCurrency(Currency currency) {
        List<Report> reports = findAll();
        for (Report report : reports) {
            report.setCurrency(currency);
            save(report);
        }
        currencyService.setReportCurrency(currency.getId());
    }

    @Override
    public Currency getReportCurrency() {
        return currencyService.getReportCurrency();
    }

    @Override
    public Report getUnreportedReport() {
        List<Report> unreported = new ArrayList<>();
        for (Report rep : findAll()) {
            if (rep.getType().equals("Unreported")) {
                unreported.add(rep);
            }
        }
        switch (unreported.size()) {
            case 0:
                Report report = new Report("Unreported", "Unreported", LocalDate.now());
                report.setCurrency(currencyService.getReportCurrency());
                save(report);
                return report;
            case 1:
                return unreported.get(0);
            default:
                return null;
        }
    }

}
