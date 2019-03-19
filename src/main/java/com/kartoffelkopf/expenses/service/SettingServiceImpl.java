package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.model.Category;
import com.kartoffelkopf.expenses.model.Client;
import com.kartoffelkopf.expenses.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class SettingServiceImpl implements SettingsService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ReportService reportService;

    @Override
    public void save(long defaultCategoryId, long defaultClientId, long defaultCurrencyId, long reportCurrencyId) {
        categoryService.setDefault(defaultCategoryId);
        clientService.setDefault(defaultClientId);
        currencyService.setDefault(defaultCurrencyId);
        reportService.setReportCurrency(currencyService.findById(reportCurrencyId));
    }

    @Override
    public List<Category> getCategories() {
        return categoryService.findAll();
    }

    @Override
    public List<Client> getClients() {
        return clientService.findAll();
    }

    @Override
    public List<Currency> getCurrencies() {
        return currencyService.findAll();
    }

    @Override
    public void importCategories(MultipartFile file) {
        try {
            categoryService.importFromCsv(file);
        } catch (IOException e) {
            System.err.println("Exception thrown whilst importing file");
        }
    }

    @Override
    public void importClients(MultipartFile file) {
        try {
            clientService.importFromCsv(file);
        } catch (IOException e) {
            System.err.println("Exception thrown whilst importing file");
        }
    }

    @Override
    public void importCurrencies(MultipartFile file) {
        try {
            currencyService.importFromCsv(file);
        } catch (IOException e) {
            System.err.println("Exception thrown whilst importing file");
        }
    }
}
