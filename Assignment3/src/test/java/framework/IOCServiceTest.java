package framework;


import demo.MyApplicationContext;
import org.junit.jupiter.api.Test;
import service.BookService;

import java.awt.print.Book;

import static org.junit.jupiter.api.Assertions.*;

public class IOCServiceTest {
    String applicontext="D:\\Giselle\\大三上\\JavaEE\\Assignment3\\src\\test\\resources\\applicationcontext.xml";

    String myapp_bean_not_found="D:\\Giselle\\大三上\\JavaEE\\Assignment3\\src\\test\\resources\\myapp_bean_not_found.xml";
    String myapp_class_not_found="D:\\Giselle\\大三上\\JavaEE\\Assignment3\\src\\test\\resources\\myapp_class_not_found.xml";
    String myapp_clazz_not_found="D:\\Giselle\\大三上\\JavaEE\\Assignment3\\src\\test\\resources\\myapp_class_not_found.xml";
    String myapp_id_not_found="D:\\Giselle\\大三上\\JavaEE\\Assignment3\\src\\test\\resources\\myapp_id_not_found.xml";

    String myapp_without_paramless_constructor="D:\\Giselle\\大三上\\JavaEE\\Assignment3\\src\\test\\resources\\myapp_without_paramless_constructor.xml";
    String myapp_abstract_class="D:\\Giselle\\大三上\\JavaEE\\Assignment3\\src\\test\\resources\\myapp_abstract_class.xml";
    String myapp_with_private_constructor="D:\\Giselle\\大三上\\JavaEE\\Assignment3\\src\\test\\resources\\myapp_with_private_constructor.xml";
    String myapp_without_set_method="D:\\Giselle\\大三上\\JavaEE\\Assignment3\\src\\test\\resources\\myapp_without_set_method.xml";
    @Test
    public void testContext() throws IOCException{
        MyApplicationContext myApplicationContext=new MyApplicationContext(applicontext);
        Object object=myApplicationContext.getBean("bookService");
        assertNotNull(object);
    }
    @Test
    public void testLoadxmlFile() throws IOCException {
        //测试不存在的xml文件
        IOCException exception=assertThrows(IOCException.class,()->{
            MyApplicationContext myApplicationContext=new MyApplicationContext("xml_error.xml");
        });
        assertEquals(IOCException.ErrorType.XML_READ_ERROR,exception.getErrorType());
        //测试不合法的xml文件


        //prop的ref指向不存在bean标签
        exception=assertThrows(IOCException.class,()->{
            MyApplicationContext myApplicationContext=new MyApplicationContext(myapp_bean_not_found);
        });
        assertEquals(IOCException.ErrorType.BEAN_NOT_FOUND,exception.getErrorType());
        //bean标签不存在class属性
        exception=assertThrows(IOCException.class,()->{
            MyApplicationContext myApplicationContext=new MyApplicationContext(myapp_class_not_found);
        });
        assertEquals(IOCException.ErrorType.CLASS_NOT_FOUND,exception.getErrorType());
        //bean标签不存在id属性
        exception=assertThrows(IOCException.class,()->{
            MyApplicationContext myApplicationContext=new MyApplicationContext(myapp_id_not_found);
        });
        assertEquals(IOCException.ErrorType.ID_NOT_FOUND,exception.getErrorType());


    }

    @Test
    public void testCreateObject()
    {
        //测试属性中标注的类不存在
        IOCException exception=assertThrows(IOCException.class,()->{
            MyApplicationContext context=new MyApplicationContext(myapp_clazz_not_found);
        });
        assertEquals(IOCException.ErrorType.CLASS_NOT_FOUND,exception.getErrorType());

        //测试创建对象没有无参构造函数
        exception=assertThrows(IOCException.class,()->{
            MyApplicationContext context=new MyApplicationContext(myapp_without_paramless_constructor);
        });
        assertEquals(IOCException.ErrorType.CREATE_OBJECT_ERROR,exception.getErrorType());

        //测试创建抽象类对象
        exception=assertThrows(IOCException.class,()->{
            MyApplicationContext context=new MyApplicationContext(myapp_abstract_class);
        });
        assertEquals(IOCException.ErrorType.CREATE_OBJECT_ERROR,exception.getErrorType());

        //测试创建对象构造函数为私有
        exception=assertThrows(IOCException.class,()->{
            MyApplicationContext context=new MyApplicationContext(myapp_with_private_constructor);
        });
        assertEquals(IOCException.ErrorType.CREATE_OBJECT_ERROR,exception.getErrorType());

        //测试xml文件中不存在需要获取的对象
        exception=assertThrows(IOCException.class,()->
        {
            MyApplicationContext context=new MyApplicationContext(applicontext);
            BookService bookService=(BookService) context.getBean("book");
        });
        assertEquals(IOCException.ErrorType.CREATE_OBJECT_ERROR,exception.getErrorType());
    }

    @Test
    public void testGetMethod()
    {
        //测试未找到set方法
        IOCException exception=assertThrows(IOCException.class,()->{
           MyApplicationContext context=new MyApplicationContext(myapp_without_set_method);
        });
        assertEquals(IOCException.ErrorType.Method_NOT_FOUND,exception.getErrorType());
    }


}
