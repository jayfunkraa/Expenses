package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.model.Category;
import com.kartoffelkopf.expenses.model.Client;
import com.kartoffelkopf.expenses.model.Currency;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SettingsService {
    void save(long defaultCategoryId,
              long defaultClientId,
              long defaultCurrencyId,
              long reportCurrencyId);

    List<Category> getCategories();
    List<Client> getClients();
    List<Currency> getCurrencies();
    void importCategories(MultipartFile file);
    void importClients(MultipartFile file);
    void importCurrencies(MultipartFile file);
    void importCurrencyMap(MultipartFile file);
}
