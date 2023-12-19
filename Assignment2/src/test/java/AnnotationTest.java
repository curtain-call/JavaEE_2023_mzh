import a2.MyClass;
import a2.Validator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试注解加在不同的方法上能否调用成功
 */

public class AnnotationTest {
    MyClass myClass;
    @BeforeEach
    public void setUp(){
        this.myClass=new MyClass("one",0);

    }

    @AfterEach
    public void teardown(){
        this.myClass=null;
    }

    @Test
    public void testAdd() throws InvocationTargetException, IllegalAccessException {
        myClass.Init(20,20);
        Validator.CheckAdd(myClass);
        assertEquals(40,myClass.GetNum());
    }

    @Test
    public void testSub() throws InvocationTargetException, IllegalAccessException {
        myClass.Init(20,10);
        Validator.CheckSub(myClass);
        assertEquals(10,myClass.GetNum());
    }





}
