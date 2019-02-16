package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> findAll();
    Category findById(long id);
    void save(Category category);
}
