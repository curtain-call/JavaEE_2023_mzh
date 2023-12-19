package demo;

import framework.IOCException;
import framework.IOCService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 存放根据bean配置信息创建的对象及其对应的类名
 */

public class MyApplicationContext {
    //修改 将Object改为impl对象
    private Map<String, Object>beans=new HashMap<String,Object>() ;

    private String fileName;

    public MyApplicationContext(String fileName) throws IOCException {
        this.fileName=fileName;
        //调用IOCService接口
        this.beans=IOCService.Start(fileName);
    }


    /**
     * 根据key获取对象
     * @param param key值
     * @return 根据bean构造的对象
     * @throws IOCException
     */
   public Object getBean(String param) throws IOCException
   {
       for (Map.Entry<String, Object> entry : beans.entrySet()) {

          if(entry.getKey().equals(param))
          {
              return entry.getValue();
          }

       }
       throw new IOCException(IOCException.ErrorType.CREATE_OBJECT_ERROR,"对象获取失败！");
   }








}
