package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category findById(long id);

    void setDefault(long id);

}
