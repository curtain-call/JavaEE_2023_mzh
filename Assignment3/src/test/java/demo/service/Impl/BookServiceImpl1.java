package demo.service.Impl;

import demo.BookDao;
import demo.service.BookService;

public class BookServiceImpl1 implements BookService {
    private BookDao bookDao;

    @Override
    public void save() {
        System.out.println("book service save ...(impl1)");
        bookDao.save();
    }
    //6.提供对应的set方法
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
