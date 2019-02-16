package com.kartoffelkopf.expenses.controller;

import com.kartoffelkopf.expenses.model.Category;
import com.kartoffelkopf.expenses.model.Client;
import com.kartoffelkopf.expenses.model.Expense;
import com.kartoffelkopf.expenses.service.CategoryService;
import com.kartoffelkopf.expenses.service.ClientService;
import com.kartoffelkopf.expenses.service.CurrencyService;
import com.kartoffelkopf.expenses.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("expenses", expenseService.findAll());
        return "index";
    }

    @RequestMapping("/expense/add")
    public String expenseAdd(Model model) {
        model.addAttribute("expense", new Expense());
        model.addAttribute("action", "/expense");
        model.addAttribute("heading", "New");
        model.addAttribute("submit", "Add");
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("currencies", currencyService.findAll());
        return "form";
    }

    @RequestMapping(value = "/expense", method = RequestMethod.POST)
    public String expenseAddPost(@RequestParam long categoryId, @RequestParam long clientId, @RequestParam long currencyId, Expense expense) {
        expense.setCategory(categoryService.findById(categoryId));
        expense.setClient(clientService.findById(clientId));
        expense.setCurrency(currencyService.findById(currencyId));
        expenseService.save(expense);
        return "redirect:/";
    }

    @RequestMapping("/expense/{id}/edit")
    public String expenseEdit(@PathVariable long id, Model model) {
        model.addAttribute("expense", expenseService.findById(id));
        model.addAttribute("action", "/expense/edit");
        model.addAttribute("heading", "Edit");
        model.addAttribute("submit", "Save");
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("currencies", currencyService.findAll());
        return "form";
    }

    @RequestMapping(value = "/expense/edit", method = RequestMethod.POST)
    public String expenseEditPost(@RequestParam long categoryId, @RequestParam long clientId, @RequestParam long currencyId, Expense expense) {
        expense.setCategory(categoryService.findById(categoryId));
        expense.setClient(clientService.findById(clientId));
        expense.setCurrency(currencyService.findById(currencyId));
        expenseService.save(expense);
        return "redirect:/";
    }

    @RequestMapping(value = "/expense/{id}/delete", method = RequestMethod.POST)
    public String expenseDelete(@PathVariable long id) {
        expenseService.delete(expenseService.findById(id));
        return "redirect:/";
    }

}
