package edu.whu.demo.service;

import edu.whu.demo.ProductApplication;
import edu.whu.demo.domain.Product;
import edu.whu.demo.domain.ProductDto;
import edu.whu.demo.domain.Supplier;
import edu.whu.demo.service.impl.ProductService;
import edu.whu.demo.service.impl.SupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SpringBootTest注解的作用：可以自动启动SpringBoot，以支持依赖注入
 * Transactional注解作用：每个Test方法执行完成后，自动回滚事务，数据库变空。
 */
@SpringBootTest(classes = ProductApplication.class)
@Transactional
@EnableWebMvc
public class ProductServiceTest {

    @Autowired
    ProductService productService;
    @Autowired
    SupplierService supplierService;

    @BeforeEach
    void setup(){
        Product product1=new Product();
        product1.setName("普罗大可1");
        product1.setProductType("食品");
        product1.setPrice(100);
        product1.setStockQuantity(10.0f);
        product1.setCategory("饮用品");
        productService.addProduct(product1);

        Product product2=new Product();
        product2.setName("商品1");
        product2.setProductType("生活用品");
        product2.setPrice(50);
        product2.setStockQuantity(60f);
        productService.addProduct(product2);

        Product product3=new Product();
        product3.setPrice(30);
        product3.setStockQuantity(100);
        productService.addProduct(product3);


    }

    /**
     * 测试根据名称查找商品
     * @throws Exception
     */
    @Test
    public void testFindProductByName() throws Exception{

        List<Product> products=productService.QueryByName("普罗大可1");
        assertNotEquals(0,products.size());

    }

    /**
     * 测试增加商品
     * @throws Exception
     */

    @Test
    public void testAddProduct() throws Exception{
        Product product=new Product();
        product.setName("addProduct");
        productService.addProduct(product);
        int size=productService.QueryByName("addProduct").size();
        assertNotEquals(0,size);

    }

    /**
     * 测试删除商品
     * @throws Exception
     */
    @Test
    public void testDeleteProduct() throws Exception{
        Product testproduct1=new Product();
        testproduct1.setName("test1");
        Product testproduct2=new Product();
        testproduct2.setName("test2");
        productService.addProduct(testproduct1);
        productService.addProduct(testproduct2);
        productService.deleteProduct(testproduct1.getId());
        int size=productService.QueryAll().size();
        assertEquals(4,size);
    }


    /**
     * 测试修改商品信息
     * @throws Exception
     */
    @Test
    public void testModifyProduct() throws Exception{
        Product oldpro=new Product();
        oldpro.setName("普罗大可2");
        Product newpro=new Product();
        newpro.setName("普罗大可3");
        productService.ModifyProduct(oldpro.getId(),newpro);
        int size=productService.QueryByName("普罗大可2").size();
        assertEquals(0,size);
        size=productService.QueryByName("普罗大可3").size();
        assertEquals(1,size);
    }

    /**
     * 测试根据价格查询
     */
    @Test
    public void testQueryByPrice()
    {
       int size=productService.QueryByPrice(100).size();
       assertEquals(3,size);


    }

    @Test
    public void testQueryByPriceAndQuantity()
    {
        int size=productService.QueryByPriceAndQuantity(50,60).size();//price小于等于五十，quantity大于等于60
        assertEquals(2,size);
    }

    /**
     * 测试找到所有商品及其对应的供应商
     */
    @Test
    public void testSetProductSupplier()
    {
        Product testproduct=new Product();
        testproduct.setName("testpro");
        productService.addProduct(testproduct);
        Supplier supplier1=new Supplier();
        supplier1.setAddress("地址5");
        supplierService.AddSupplier(supplier1);
        Supplier supplier2=new Supplier();
        supplier2.setAddress("地址6");
        supplierService.AddSupplier(supplier2);
        productService.setProductSupplier(testproduct.getId(),supplier1.getId());
        productService.setProductSupplier(testproduct.getId(),supplier2.getId());

        ProductDto productDto =productService.findAllProductVo().get(3);
        assertEquals(2,productDto.getSuppliers().size());
    }
}
