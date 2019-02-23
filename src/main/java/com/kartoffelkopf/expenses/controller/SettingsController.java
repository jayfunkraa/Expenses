package com.kartoffelkopf.expenses.controller;

import com.kartoffelkopf.expenses.service.CategoryService;
import com.kartoffelkopf.expenses.service.ClientService;
import com.kartoffelkopf.expenses.service.CurrencyService;
import com.kartoffelkopf.expenses.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SettingsController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CurrencyService currencyService;

    @RequestMapping("/settings")
    public String settings(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("currencies", currencyService.findAll());
        return "settings";
    }

    @RequestMapping("/settings/set-defaults")
    public String setDefaults(@RequestParam("categoryId") long categoryId,
                              @RequestParam("clientId") long clientId,
                              @RequestParam("currencyId") long currencyId) {
        categoryService.setDefault(categoryId);
        clientService.setDefault(clientId);
        currencyService.setDefault(currencyId);
        return "redirect:/settings";
    }
}
