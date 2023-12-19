package demo.service.Impl;

import demo.BookDao;
import service.BookService;

public class BookServiceWithoutSetMethod implements BookService {
    private BookDao bookDao;
    @Override
    public void save() {
        System.out.println("book service save ...");
    }
}
