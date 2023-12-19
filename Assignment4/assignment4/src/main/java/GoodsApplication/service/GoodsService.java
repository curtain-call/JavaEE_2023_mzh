package GoodsApplication.service;

import GoodsApplication.entity.Goods;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsService {

    //private Map<String,Goods> goods = new HashMap<String,Goods>();

    //修改
    //采用线程安全的map
    private Map<String, Goods> goods = Collections.synchronizedMap(new HashMap<String, Goods>());

    //总金额
    /*private double sum;

    public GoodsService(){
        sum=0;
    }
    public GoodsService(Map<String,Goods>goods)
    {
        this.goods=goods;
       for(Map.Entry<String, Goods> entry:goods.entrySet())
       {
           sum+=entry.getValue().getPrice();
       }
    }*/

    /**
     * 添加商品
     * @param good
     * @return
     */
    public Goods AddGood(Goods good)
    {
        goods.put(good.getId(),good);
        //sum+=good.getPrice();
        return good;
    }

    /**
     * 删除商品
     * @param id
     */
    public void DeleteGood(String id)
    {
       goods.remove(id);


    }

    /**
     * 删除所有商品
     */
    public void deleteAll(){goods.clear();}

    /**
     * 修改商品信息
     * @param id
     * @param g
     * @return
     */

    public void ModifyGoods(String id,Goods g)
    {
        //Goods good = QueryById(id);
        Goods good=goods.get(id);
        /*if(good==null)
        {
            return null;
        }
        good.setPrice(g.getPrice());
        good.setName(g.getName());
        goods.put(id,good);
        return good;*/
        //修改
        //直接放入覆盖
        if(good!=null){
            goods.put(id,good);
        }


    }

    /**
     * 根据id查找
     * @param id
     * @return
     */
    public Goods QueryById(String id)
    {
        /*for(Map.Entry<String,Goods>entry:goods.entrySet())
        {
            if(entry.getValue().getId().equals(id))
            {
                return entry.getValue();
            }
        }
        return null;*/
        //修改
        return goods.get(id);
    }

    //修改

    /**
     * 根据名称和库存查找
     * @param name
     * @param quantity
     * @return
     */
    public List<Goods> findGoods(String name,Float quantity)
    {
        List<Goods>result=new ArrayList<Goods>();
        for(Goods goodsitem : goods.values())
        {
            if(name!=null &&!goodsitem.getName().contains(name))
            {
                continue;
            }
            if(quantity!=null&&goodsitem.getStockQuantity()>=quantity)
            {
                continue;
            }
            result.add(goodsitem);
        }
        return result;
    }

    public Collection<Goods> QueryAll()
    {
        return goods.values();
    }
}
