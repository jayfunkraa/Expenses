package com.kartoffelkopf.expenses.controller;

import com.kartoffelkopf.expenses.model.Expense;
import com.kartoffelkopf.expenses.model.Receipt;
import com.kartoffelkopf.expenses.model.Report;
import com.kartoffelkopf.expenses.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReceiptService receiptService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("expenses", expenseService.findAll());
        return "expense/index";
    }

    @RequestMapping("/expense/add")
    public String expenseAdd(Model model) {
        model.addAttribute("expense", new Expense());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("currencies", currencyService.findAll());
        List<Report> reports = reportService.findAll();
        if (reports.isEmpty()) {
            Report report = new Report("New Report");
            reportService.save(report);
            reports.add(report);
        }
        model.addAttribute("reports", reports);
        return "expense/addForm";
    }

    @RequestMapping(value = "/expense", method = RequestMethod.POST)
    public String expenseAddPost(@RequestParam long categoryId,
                                 @RequestParam long clientId,
                                 @RequestParam long currencyId,
                                 @RequestParam long reportId,
                                 @RequestParam MultipartFile file,
                                 Expense expense) {
        expense.setCategory(categoryService.findById(categoryId));
        expense.setClient(clientService.findById(clientId));
        expense.setCurrency(currencyService.findById(currencyId));
        expense.setReport(reportService.findById(reportId));
        if (file != null) {
            try {
                Receipt receipt = new Receipt(file.getOriginalFilename(), file.getContentType(), file.getBytes());
                receiptService.save(receipt);
                expense.setReceipt(receipt);
            } catch (IOException e) {
                System.err.println("Unable to get byte array from file");
            }
        } else {
            System.err.println("No file selected");
        }
        expenseService.save(expense);
        return "redirect:/";
    }

    @RequestMapping("/expense/{id}/edit")
    public String expenseEdit(@PathVariable long id, Model model) {
        model.addAttribute("expense", expenseService.findById(id));
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("currencies", currencyService.findAll());
        List<Report> reports = reportService.findAll();
        if (reports.isEmpty()) {
            Report report = new Report("New Report");
            reportService.save(report);
            reports.add(report);
        }
        model.addAttribute("reports", reports);
        return "expense/editForm";
    }

    @RequestMapping(value = "/expense/edit", method = RequestMethod.POST)
    public String expenseEditPost(@RequestParam long categoryId,
                                  @RequestParam long clientId,
                                  @RequestParam long currencyId,
                                  @RequestParam long reportId,
                                  @RequestParam MultipartFile file,
                                  Expense expense) {
        expense.setCategory(categoryService.findById(categoryId));
        expense.setClient(clientService.findById(clientId));
        expense.setCurrency(currencyService.findById(currencyId));
        expense.setReport(reportService.findById(reportId));
        if (file != null) {
            try {
                Receipt receipt = new Receipt(file.getOriginalFilename(), file.getContentType(), file.getBytes());
                receiptService.save(receipt);
                expense.setReceipt(receipt);
            } catch (IOException e) {
                System.err.println("Unable to get byte array from file");
            }
        } else {
            System.err.println("No file selected");
        }
        expenseService.save(expense);
        return "redirect:/";
    }

    @RequestMapping(value = "/expense/{id}/delete", method = RequestMethod.POST)
    public String expenseDelete(@PathVariable long id) {
        expenseService.delete(expenseService.findById(id));
        return "redirect:/";
    }

    @RequestMapping("/expense/{id}/get-receipt")
    public ResponseEntity<byte[]> expenseGetReceipt(@PathVariable long id) {
        Receipt receipt = expenseService.findById(id).getReceipt();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(receipt.getContentType()));
        headers.setContentDisposition(ContentDisposition.builder("inline").filename(receipt.getFilename()).build());
        return new ResponseEntity<>(receipt.getBytes(), headers, HttpStatus.OK);
    }

}
