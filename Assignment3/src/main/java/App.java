import demo.MyApplicationContext;
import framework.IOCException;
import framework.IOCService;
import service.BookService;
import service.Impl.BookServiceImpl1;

public class App {

    public static void main(String[] args) {
        try
        {
            MyApplicationContext context=new MyApplicationContext("D:\\Giselle\\大三上\\JavaEE\\Assignment3\\src\\main\\resources\\applicationcontext.xml");
            BookService bookService=(BookService) context.getBean("bookService");
            bookService.save();
        }
        catch (IOCException e)
        {
            System.out.println(e.getMessage());
        }


    }
}
