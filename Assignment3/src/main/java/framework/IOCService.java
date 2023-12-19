package framework;

import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import demo.BeanDefinition;
import demo.BookDao;
import demo.MyApplicationContext;
import demo.PropertyDefinition;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.annotation.Bean;

import javax.print.Doc;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.interfaces.ECKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本类实现：
 * 1.使用Dom4j从xml配置文件中读出bean配置，读入BeanDefinition中
 * 2.根据BeanDefinition的配置信息，使用反射创建相应对象并实现依赖注入
 * 3.将创建对象及其对应的beanid存入map数据结构中
 */
public class IOCService {



    /**
     * 将xml配置文件读到实体对象Document中
     * @param filename 文件地址
     * @return 存放xml文件信息的document对象
     * @throws IOCException 文件读取异常
     */
    public static Document ReadFile(String filename) throws IOCException{

        try
        {
            SAXReader reader = new SAXReader();
            Document document= reader.read(new File(filename));
            return document;
        }

        catch (DocumentException e)
        {
            throw new IOCException(IOCException.ErrorType.XML_READ_ERROR,"xml文件读取失败！");
        }
    }




    /**
     * 将document对象转化为beanDefinition数据结构存储
     * @param document 存放xml文件内容的document对象
     * @return 存放bean信息的List
     * @throws IOCException xml文件内容异常
     */
    public static List<BeanDefinition> getBeans(Document document) throws IOCException
    {

            Element root=document.getRootElement();//获得根标签
            List<Element> beanlist=root.elements();//获得根标签的子标签bean
            List<BeanDefinition> beans=new ArrayList<BeanDefinition>();
            //创建bean对象
            for(Element bean:beanlist)
            {
                //获取bean标签的id属性
                String id=bean.attributeValue("id");
                String clazz=bean.attributeValue("class");
                if(id==null)throw new IOCException(IOCException.ErrorType.ID_NOT_FOUND,"bean标签中不存在id属性！");
                if(clazz==null)throw new IOCException(IOCException.ErrorType.CLASS_NOT_FOUND,"bean标签中不存在class属性");
                //创建beanDefinition对象
                BeanDefinition beanDefinition= new BeanDefinition(id,clazz);
                //获取bean标签的子标签_property
                List<Element>proplist=bean.elements();
                //创建property对象
                for(Element prop:proplist)
                {
                    //获取标签的名字
                    String propName=prop.getName();
                    if("property".equals(propName))
                    {
                        String name =prop.attributeValue("name");
                        String ref=prop.attributeValue("ref");
                        PropertyDefinition propertyDefinition=new PropertyDefinition(name,ref);
                        beanDefinition.addProp(propertyDefinition);
                    }

                }
                beans.add(beanDefinition);
            }
            return beans;


    }



    /**
     * 获得BeanDefinition的对象类型
     * @param beanDefinition 自定义存放bean信息对象
     * @return beanDefinition对象类型
     * @throws IOCException
     */
    private static Class getClass(BeanDefinition beanDefinition) throws IOCException
    {
        try
        {
            return Class.forName(beanDefinition.getClazz());
        }
        catch (ClassNotFoundException e)
        {
            throw new IOCException(IOCException.ErrorType.CLAZZ_NOT_FOUND,"属性中标注的类不存在！");
        }

    }




    /**
     * 创建bean的实体对象
     * @param cl bean的类
     * @return bean对应实体对象
     * @throws IOCException
     */
    private static Object createObject(Class cl) throws IOCException
    {
        try {
            return cl.newInstance();
        } catch (InstantiationException e) {
            throw new IOCException(IOCException.ErrorType.CREATE_OBJECT_ERROR, "创建对象失败：请检查是否有无参构造函数");
        } catch (IllegalAccessException e) {
            throw new IOCException(IOCException.ErrorType.CREATE_OBJECT_ERROR, "创建对象失败：类不能是抽象类，构造函数不能为私有."+e.getMessage());
        }
    }



    /**
     * 根据prop的ref找到bean
     * @param ref prop属性的ref
     * @param beanDefinitions xml中读出的所有bean
     * @return prop对应的beandefinition
     * @throws IOCException
     */
    private static BeanDefinition getPropBean(String ref,List<BeanDefinition>beanDefinitions) throws IOCException
    {
        for(BeanDefinition beanDefinition:beanDefinitions)
        {
            if(ref.equals(beanDefinition.getId())){
                return beanDefinition;
            }
        }
        throw new IOCException(IOCException.ErrorType.BEAN_NOT_FOUND,"未找到Prop指向的bean！");
    }



    /**
     * 获得实体类的set方法
     * @param beanclazz bean的类
     * @param prop bean需要注入的propdefinition
     * @param propob bean需要注入的propdefinition对象
     * @return set方法
     * @throws IOCException
     */
    private static Method GetMethod(Class beanclazz,PropertyDefinition prop,Object propob) throws IOCException
    {
        try
        {
            String param="set"+prop.getName().substring(0, 1).toUpperCase() + prop.getName().substring(1);
            return beanclazz.getMethod(param,propob.getClass().getInterfaces());//很怪
        }catch (NoSuchMethodException e)
        {
            throw new IOCException(IOCException.ErrorType.Method_NOT_FOUND,"未找到可调用方法！");
        }
    }



    /**
     * 启动函数 对BeanList操作
     * @param filename xml文件地址
     * @return
     * @throws IOCException
     */
    public static Map<String,Object> Start(String filename) throws IOCException {
        try{
            Map<String,Object>beans=new HashMap<>();
            //读xml文件
            Document document=ReadFile(filename);
            //将document转化为自定义数据结构
            List<BeanDefinition> beanDefinitions=getBeans(document);
            //遍历beanlist
            //创建对象
            //实现依赖注入
            for(BeanDefinition bean:beanDefinitions)
            {
                //创建实例对象
                Object obBean=createObject(getClass(bean));
                //创建prop对象
                for(PropertyDefinition propertyDefinition:bean.getProperties())
                {
                    //根据ref==id找到prop的bean
                    BeanDefinition propBean=getPropBean(propertyDefinition.getRef(),beanDefinitions);
                    //创建prop对象
                    Object obProp=createObject(getClass(propBean));
                    //实现依赖注入
                    Method method=GetMethod(obBean.getClass(),propertyDefinition,obProp);
                    method.invoke(obBean,obProp);
                }
                //将对象及其id存入Map
                beans.put(bean.getId(),obBean);
            }
            return beans;



        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ExceptionInInitializerError e)
        {
            throw new IOCException(IOCException.ErrorType.METHOD_INVOKE_ERROR,e.getMessage());
        }



    }






}
