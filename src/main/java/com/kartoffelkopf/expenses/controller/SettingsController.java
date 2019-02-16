package com.kartoffelkopf.expenses.controller;

import com.kartoffelkopf.expenses.service.CategoryService;
import com.kartoffelkopf.expenses.service.ClientService;
import com.kartoffelkopf.expenses.service.CurrencyService;
import com.kartoffelkopf.expenses.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    //TODO: combine all three default field mappings into one request
    @RequestMapping(value = "/categories/set-default", method = RequestMethod.POST)
    public String setDefaultCategory(@RequestParam("selectedId") long id) {
        categoryService.setDefault(id);
        return "redirect:/settings";
    }

    @RequestMapping(value = "/clients/set-default", method = RequestMethod.POST)
    public String setDefaultClient(@RequestParam("selectedId") long id) {
        clientService.setDefault(id);
        return "redirect:/settings";
    }
    @RequestMapping(value = "/currencies/set-default", method = RequestMethod.POST)
    public String setDefaultCurrency(@RequestParam("selectedId") long id) {
        currencyService.setDefault(id);
        return "redirect:/settings";
    }
}
