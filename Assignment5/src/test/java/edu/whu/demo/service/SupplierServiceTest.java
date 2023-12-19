package edu.whu.demo.service;

import edu.whu.demo.ProductApplication;
import edu.whu.demo.domain.Product;
import edu.whu.demo.domain.Supplier;
import edu.whu.demo.service.impl.ProductService;
import edu.whu.demo.service.impl.SupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ProductApplication.class)
@Transactional
@EnableWebMvc
public class SupplierServiceTest {
    @Autowired
    SupplierService supplierService;
    @Autowired
    ProductService productService;

    @BeforeEach
    void setup()
    {
        Supplier supplier1=new Supplier();
        supplier1.setName("食品供应商");
        supplier1.setAddress("地址1");
        supplierService.AddSupplier(supplier1);
    }

    /**
     * 测试增加供应商
     */
    @Test
    public void testAddSupplier()
    {
        Supplier supplier=new Supplier();
        supplier.setName("供应商2");
        supplier.setAddress("地址2");
        supplierService.AddSupplier(supplier);
        int size=supplierService.QueryAll().size();
        assertEquals(2,size);
    }

    /**
     * 测试删除供应商
     */
    @Test
    public void testDeleteSupplier()
    {
        Supplier supplier1=new Supplier();
        supplier1.setName("供应商2");
        supplier1.setAddress("地址2");
        supplierService.AddSupplier(supplier1);

        Supplier supplier2=new Supplier();
        supplier2.setName("供应商3");
        supplier2.setAddress("地址3");
        supplierService.AddSupplier(supplier2);

        supplierService.DeleteSupplier(supplier1.getId());

        int size=supplierService.QueryAll().size();
        assertEquals(2,size);

    }

    /**
     * 测试修改供应商信息
     */
    @Test
    public void testModifySupplier()
    {
        Supplier oldsupplier=new Supplier();
        oldsupplier.setName("old");
        oldsupplier.setAddress("oldaddr");
        supplierService.AddSupplier(oldsupplier);

        Supplier newsupplier=new Supplier();

        newsupplier.setName("new");
        newsupplier.setAddress("oldaddr");
        supplierService.ModifySupplier(oldsupplier.getId(),newsupplier);

        Supplier testsupplier=supplierService.QueryByAddr("oldaddr").get(0);
        assertEquals(newsupplier.getName(),testsupplier.getName());

    }

    /**
     * 测试根据名称查询供应商
     */
    @Test
    public void testQueryByName()
    {
        Supplier supplier=supplierService.QueryByName("食品供应商").get(0);
        assertEquals("地址1",supplier.getAddress());

    }

    /**
     * 测试根据地址查询供应商
     */

    @Test
    public void testQueryByAddress()
    {
        Supplier supplier=supplierService.QueryByAddr("地址1").get(0);
        assertEquals("食品供应商",supplier.getName());
    }

    /**
     * 测试通过商品id找到供货商
     */
    @Test
    public void testfindSuppliersByProductId()
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
        int size=supplierService.findSuppliersByProductId(testproduct.getId()).size();
        assertEquals(2,size);

    }


}
