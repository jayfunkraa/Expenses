package com.kartoffelkopf.expenses.controller;

import com.kartoffelkopf.expenses.model.Report;
import com.kartoffelkopf.expenses.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping("/reports")
    public String index(Model model) {
        model.addAttribute("reports", reportService.getReportView());
        return "report/index";
    }

    @RequestMapping("/report/add")
    public String add(Model model) {
        model.addAttribute("report", new Report());
        model.addAttribute("action", "/report");
        model.addAttribute("heading", "New");
        model.addAttribute("submit", "Add");
        return "report/form";
    }

    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public String addPost(Report report) {
        report.setCurrency(reportService.getReportCurrency());
        reportService.save(report);
        return "redirect:/reports";
    }

    @RequestMapping("/report/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("report", reportService.findById(id));
        model.addAttribute("action", "/report/edit");
        model.addAttribute("heading", "Edit");
        model.addAttribute("submit", "Save");
        return "report/form";
    }

    @RequestMapping(value = "/report/edit", method = RequestMethod.POST)
    public String editPost(Report report) {
        reportService.save(report);
        return "redirect:/reports";
    }

    @RequestMapping(value = "/report/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable long id) {
        reportService.delete(reportService.findById(id));
        return "redirect:/reports";
    }

    @RequestMapping("/report/{id}/submit")
    public String submit(@PathVariable long id) {
        reportService.submit(reportService.findById(id));
        return "redirect:/reports";
    }

    @RequestMapping("/report/{id}/expenses")
    public String reportExpenses(Model model, @PathVariable long id) {
        model.addAttribute("report", reportService.findById(id));
        model.addAttribute("expenses", reportService.getAllExpenses(id));
        return "report/expenses";
    }

    @RequestMapping("/report/{id}/generate")
    public ResponseEntity<byte[]> reportGetExcel(@PathVariable long id) {
        Report report = reportService.findById(id);
        try {
            return reportService.generateExcel(report);
        } catch (IOException e) {
            System.err.println("IOException thrown during excel generation");
            return null;
        }
    }
    }
