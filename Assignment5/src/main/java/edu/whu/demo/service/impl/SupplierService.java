package edu.whu.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.whu.demo.dao.SupplierDao;
import edu.whu.demo.domain.Supplier;
import edu.whu.demo.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService extends ServiceImpl<SupplierDao, Supplier> implements ISupplierService {

    @Autowired
    SupplierDao supplierDao;

    /**
     * 增加供货商
     * @param supplier
     */
    public void AddSupplier(Supplier supplier)
    {
        supplierDao.insert(supplier);
    }

    /**
     * 根据id删除供货商
     * @param id
     */
    public void DeleteSupplier(int id)
    {
        supplierDao.deleteById(id);
    }

    /**
     * 修改供货商信息
     * @param id
     * @param supplier
     */
    public void ModifySupplier(int id,Supplier supplier)
    {
        supplierDao.deleteById(id);
        supplierDao.insert(supplier);
    }

    public Supplier QueryById(int id)
    {
        return supplierDao.selectById(id);
    }

    /**
     * 通过地址查找供货商
     * @param addr
     * @return
     */
    public List<Supplier> QueryByAddr(String addr)
    {
        return supplierDao.QueryByAddr(addr);
    }


    /**
     * 根据名称查找供应商
     * @param name
     * @return
     */
    public List<Supplier> QueryByName(String name)
    {
        return supplierDao.QueryByName(name);
    }

    public List<Supplier> QueryAll()
    {
        return supplierDao.QueryAll();
    }

    public List<Supplier> findSuppliersByProductId(int id)
    {
        return supplierDao.findSuppliersByProductId(id);
    }




}
