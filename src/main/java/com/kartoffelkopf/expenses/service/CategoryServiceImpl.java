package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.data.CategoryDao;
import com.kartoffelkopf.expenses.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(long id) {
        return categoryDao.findById(id);
    }

    @Override
    public void setDefault(long id) {
        List<Category> categories = findAll();
        for (Category cat : categories) {
            if (cat.isDefaultCategory()) {
                cat.setDefaultCategory(false);
                categoryDao.save(cat);
            }
        }
        Category category = findById(id);
        category.setDefaultCategory(true);
        categoryDao.save(category);
    }
}
