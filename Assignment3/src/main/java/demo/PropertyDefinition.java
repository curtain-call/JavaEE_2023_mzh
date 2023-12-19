package demo;

/**
 * 表示bean中property的属性的类
 */
public class PropertyDefinition {

    private String name;
    private String ref;

    public PropertyDefinition(String name,String ref)
    {
        this.name=name;
        this.ref=ref;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public void setRef(String ref)
    {
        this.ref=ref;
    }

    public String getName()
    {
        return name;

    }

    public String getRef()
    {
        return ref;
    }
}
