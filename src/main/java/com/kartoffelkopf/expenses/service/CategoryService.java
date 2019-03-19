package com.kartoffelkopf.expenses.service;

import com.kartoffelkopf.expenses.model.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category findById(long id);

    void setDefault(long id);
    void importFromCsv(MultipartFile file) throws IOException;

}
