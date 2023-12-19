package edu.whu.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.whu.demo.domain.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SupplierDao extends BaseMapper<Supplier> {
    @Select("SELECT * FROM supplier WHERE supplier.address = #{address}")
    List<Supplier> QueryByAddr(String address);


    @Select("SELECT * FROM supplier WHERE supplier.name=#{name}")
    List<Supplier> QueryByName(String name);

    @Select("SELECT * FROM supplier")
    List<Supplier> QueryAll();

    /**
     * 返回相应商品id的供应商
     * @param productid
     * @return
     */
    @Select("SELECT supplier.* FROM supplier,product_supplier WHERE product_supplier.product_id=#{productid}&&product_supplier.supplier_id=supplier.id")
    List<Supplier> findSuppliersByProductId(int productid);
}
