package a2;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class Validator {
    public static void CheckAdd(MyClass myClass) throws InvocationTargetException, IllegalAccessException {
        try{
            Method[] methods=MyClass.class.getDeclaredMethods();

            for(Method method:methods){
                if(method.isAnnotationPresent(AddMethod.class)){
                    method.invoke(myClass);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
    public static void CheckSub(MyClass myClass) throws InvocationTargetException, IllegalAccessException {
        try{
            Method[] methods=MyClass.class.getDeclaredMethods();

            for(Method method:methods){
                if(method.isAnnotationPresent(SubMethod.class)){
                    method.invoke(myClass);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }


}

