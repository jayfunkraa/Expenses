package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.data.CategoryDao;
import com.kartoffelkopf.expenses.model.Category;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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

    @Override
    public void importFromCsv(MultipartFile file) throws IOException {
        // convert MultipartFile to File
        File convertedFile = new File(file.getOriginalFilename());
        convertedFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        fileOutputStream.write(file.getBytes());

        // read file and create categories
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(convertedFile)).withSkipLines(0).build();
        String[] line = null;
        while ((line = csvReader.readNext()) != null) {
            Category category = new Category();
            category.setCategory(line[0]);
            category.setName(line[1]);
            category.setDefaultCategory(false);
            categoryDao.save(category);
        }
    }
}
