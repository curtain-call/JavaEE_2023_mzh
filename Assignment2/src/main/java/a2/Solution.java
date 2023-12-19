package a2;



import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import static a2.Validator.CheckAdd;



public class Solution {

    static String file = "";//文件内容
    static Class myclass;
    static Object myclass2;
    public static String GetFile()
    {
        return file;
    }


    //读写文件 设置file
    public static void ReadFile() {
        Properties prop = new Properties();
        try (InputStream input = Solution.class.getResourceAsStream("/myapp.properties")) {
            if (input == null) return;
            prop.load(input);
            file = prop.getProperty("bootstrapClass");
            System.out.println(file);


        } catch (IllegalArgumentException | IOException e) {
            System.out.println(e.getMessage());
        }

    }

    //创建对象 设置myclass
    public static Object Create(String s)throws ClassNotFoundException,NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {

            myclass = Class.forName(s);
            //使用有参构造函数创建对象
            Constructor constructor = myclass.getConstructor(String.class, int.class);
            myclass2 = constructor.newInstance("one", 1);
            //打印name属性
            Field field = myclass.getDeclaredField("name");
            field.setAccessible(true);
            System.out.println(field.get(myclass2));
            return myclass2;




    }

    //初始化
    public static void Init(Class cl,Object myClass)throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        Method method=cl.getMethod("Init",int.class,int.class);
        method.invoke(myClass,20,21);
    }



    public static void main(String[] args) {
        try
        {
            ReadFile();
            Create(file);
            Init(myclass,myclass2);
            //注解
            CheckAdd((MyClass) myclass2);

            Method method=myclass2.getClass().getMethod("GetNum");
            System.out.println(method.invoke(myclass2));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}
