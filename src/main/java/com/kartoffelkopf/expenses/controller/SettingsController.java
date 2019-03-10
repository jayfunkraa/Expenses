package com.kartoffelkopf.expenses.controller;

import com.kartoffelkopf.expenses.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @RequestMapping("/settings")
    public String settings(Model model) {
        model.addAttribute("categories", settingsService.getCategories());
        model.addAttribute("clients", settingsService.getClients());
        model.addAttribute("currencies", settingsService.getCurrencies());
        model.addAttribute("reportCurrency", settingsService.getReportCurrency());
        return "settings";
    }

    @RequestMapping(value = "/settings/save", method = RequestMethod.POST)
    public String settingsSave(@RequestParam long defaultCategoryId,
                               @RequestParam long defaultClientId,
                               @RequestParam long defaultCurrencyId,
                               @RequestParam long reportCurrencyId) {
        settingsService.save(defaultCategoryId, defaultClientId, defaultCurrencyId, reportCurrencyId);
        return "redirect:/settings";
    }


}
