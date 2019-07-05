package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.data.ExpenseDao;
import com.kartoffelkopf.expenses.data.ReportDao;
import com.kartoffelkopf.expenses.data.ReportViewDao;
import com.kartoffelkopf.expenses.model.Currency;
import com.kartoffelkopf.expenses.model.Expense;
import com.kartoffelkopf.expenses.model.Report;
import com.kartoffelkopf.expenses.model.ReportView;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
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

    @Autowired
    private CurrencyMapService currencyMapService;

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

    @Override
    public ResponseEntity<byte[]> generateExcel(Report report) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(
                new File("/Users/jamie/IdeaProjects/kartoffelkopf/Expenses/src/main/resources/static/excel/template.xlsx")
        );
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.getSheetAt(1);

        Font baseFont = workbook.createFont();
        baseFont.setFontName("Tahoma");
        baseFont.setFontHeightInPoints((short) 12);


        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MMM-yy"));
        dateCellStyle.setFont(baseFont);
        dateCellStyle.setAlignment(HorizontalAlignment.LEFT);
        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dateCellStyle.setBorderBottom(BorderStyle.THIN);
        dateCellStyle.setBorderTop(BorderStyle.THIN);
        dateCellStyle.setBorderLeft(BorderStyle.THIN);
        dateCellStyle.setBorderRight(BorderStyle.THIN);
        dateCellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        dateCellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        dateCellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        dateCellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

        int currentRow = 10;
        for (Expense expense : getAllExpenses(report.getId())) {
            Row row = sheet.getRow(currentRow);

            //Date Of Spend
            Cell cell = row.getCell(2);
            if (cell == null) {
                cell = row.createCell(2);
            }
            cell.setCellValue(Date.valueOf(expense.getDate()));
            cell.setCellStyle(dateCellStyle);

            //Supplier
            cell = row.getCell(3);
            if (cell == null) {
                cell = row.createCell(3);
            }
            cell.setCellValue(expense.getMerchant());

            //Description
            cell = row.getCell(4);
            if (cell == null) {
                cell = row.createCell(4);
            }
            cell.setCellValue(expense.getDescription());

            //Type
            cell = row.getCell(5);
            if (cell == null) {
                cell = row.createCell(5);
            }
            cell.setCellValue(expense.getCategory().getName());

            //Client
            cell = row.getCell(6);
            if (cell == null) {
                cell = row.createCell(6);
            }
            cell.setCellValue(expense.getClient().getName());

            //Charge/Non-Charge
            cell = row.getCell(7);
            if (cell == null) {
                cell = row.createCell(7);
            }
            if (expense.isBillable()) {
                cell.setCellValue("YES");
            } else {
                cell.setCellValue("NO");
            }

            //Curr Code
            cell = row.getCell(8);
            if (cell == null) {
                cell = row.createCell(8);
            }
            cell.setCellValue(expense.getCurrency().getCurrency());

            //Value
            cell = row.getCell(9);
            if (cell == null) {
                cell = row.createCell(9);
            }
            cell.setCellValue(expense.getCost());

            //Exch Rate
            cell = row.getCell(10);
            if (cell == null) {
                cell = row.createCell(10);
            }
            cell.setCellValue(currencyMapService.getRate(expense.getCurrency(), getReportCurrency()));

            //Total
            cell = row.getCell(11);
            if (cell == null) {
                cell = row.createCell(11);
            }
            cell.setCellValue(expense.getCost() * currencyMapService.getRate(expense.getCurrency(), getReportCurrency()));

            currentRow++;
            }

        File file = new File("/Users/jamie/IdeaProjects/kartoffelkopf/Expenses/src/main/resources/static/" + report.getTitle() + ".xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.builder("inline").filename(file.getName()).build());

        return new ResponseEntity<>(Files.readAllBytes(file.toPath()), headers, HttpStatus.OK);

    }


}
