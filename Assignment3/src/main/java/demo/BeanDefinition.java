package demo;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示Bean配置信息的类
 */
public class BeanDefinition {

    private String id;

    private String clazz;



    private List<PropertyDefinition>properties=new ArrayList<PropertyDefinition>();

    public BeanDefinition(String id,String clazz)
    {
        this.id=id;
        this.clazz=clazz;
    }

    public String getId() {
        return id;
    }

    public String getClazz(){
        return clazz;
    }

    public void addProp(PropertyDefinition prop)
    {
        this.properties.add(prop);
    }

    public List<PropertyDefinition> getProperties(){return this.properties;}
}
