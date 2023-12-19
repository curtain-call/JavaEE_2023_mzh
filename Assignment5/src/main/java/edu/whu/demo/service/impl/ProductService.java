package edu.whu.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import edu.whu.demo.dao.ProductDao;
import edu.whu.demo.domain.Product;
import edu.whu.demo.domain.ProductDto;
import edu.whu.demo.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService extends ServiceImpl<ProductDao,Product> implements IProductService {

    @Autowired
    ProductDao productDao;

    /**
     * 增加商品
     * @param product
     */
    public Product addProduct(Product product)
    {
        productDao.insert(product);
        return product;
    }

    /**
     * 删除商品
     * @param Id
     */
    public void deleteProduct(int Id)
    {
        productDao.deleteById(Id);
    }

    public void ModifyProduct(int id,Product product)
    {
        productDao.deleteById(id);
        productDao.insert(product);
    }

    /**
     * 根据名称查找商品
     * @param name
     * @return
     */
    public List<Product> QueryByName(String name)
    {
        return productDao.QueryByName(name);
    }

    /**
     * 查询所有商品
     * @return
     */
    public List<Product> QueryAll()
    {
        return productDao.QueryAll();
    }

    /**
     * 查询价格小于等于price的商品
     * @param price
     * @return
     */
    public List<Product> QueryByPrice(float price)
    {
        return productDao.QueryByPrice(price);
    }

    public List<Product> QueryByPriceAndQuantity(float quantity,float price)
    {
        return productDao.QueryByPriceAndQuantity(quantity,price);
    }

    /**
     * 设置商品的供应商
     * @param pid
     * @param sid
     */

    public void setProductSupplier(int pid,int sid)
    {
        productDao.setProductSupplier(pid,sid);
    }

    public List<ProductDto> findAllProductVo()
    {
        return productDao.findAllProductVo();
    }

}
