package edu.whu.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.whu.demo.domain.Product;
import edu.whu.demo.domain.ProductDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductDao extends BaseMapper<Product> {


    @Select("SELECT * FROM product WHERE product.name = #{name}")
    List<Product> QueryByName(String name);


    @Select("SELECT * FROM product")
    List<Product> QueryAll();

    @Select("SELECT * from product WHERE product.price<=#{price}")
    List<Product> QueryByPrice(float price);

    @Select("SELECT * from product WHERE product.stock_Quantity>=#{quantity}&&product.price<=#{price}")
    List<Product> QueryByPriceAndQuantity(float quantity,float price);

    /**
     * 设置商品的供应商
     * @param productid
     * @param supplierid
     */
    @Insert("INSERT INTO product_supplier (product_id,supplier_id) VALUES (#{productid},#{supplierid})")
    void setProductSupplier(int productid,int supplierid);

    /**
     * 返回所有商品及其对应供应商
     * @return
     */
    @Select("SELECT * FROM product")
    @Results({@Result(id = true, property = "id", column = "id"),
            @Result(property = "suppliers", column = "id",
                    many = @Many(select = "edu.whu.demo.dao.SupplierDao.findSuppliersByProductId"))
    })
    List<ProductDto> findAllProductVo();
}
