package demo.Impl;

import demo.BookDao;

public class BookDaoImpl1 implements BookDao {
    @Override
    public void save() {
        System.out.println("book dao save ...(bookdaoimpl1)");
    }
}
