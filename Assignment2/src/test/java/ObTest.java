import a2.MyClass;
import a2.Solution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * 测试是否正常创建对象
 * 1.成功创建
 * 2.类不存在
 */

public class ObTest {


    //测试创建成功
    @Test
    public void TestCreate() throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Solution.ReadFile();
        assertEquals(MyClass.class,Solution.Create(Solution.GetFile()).getClass());

    }

    //测试 类不存在
    @Test
    public void TestException() {
        assertThrows(ClassNotFoundException.class,()->{
            Solution.Create("a2.MyClass2");
    });
    }



}
