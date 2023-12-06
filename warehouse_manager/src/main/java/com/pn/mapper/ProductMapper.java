package com.pn.mapper;

import com.pn.entity.Product;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {

    // 查询商品行数的方法
    public Integer findProductRowCount(Product product);

    // 分页查询商品
    public List<Product> findProductPage(@Param("page") Page page, @Param("product") Product product);

    // 添加商品
    public int insertProduct(Product product);

    // 根据型号查询商品
    public Product findProductByNum(String productNum);

    // 根据商品id修改商品上下架
    public int setStateByPid(@Param("productId") Integer productId, @Param("upDownState") String upDownState);

    // 根据商品id删除商品
    public int removeProductByRid(List<Integer> productIdList);

    // 根据商品id修改商品
    public int setProductById(Product product);

    // 根据id修改商品库存的方法
    public int setInventById(@Param("productId") Integer productId, @Param("invent") Integer invent);

    // 根据商品id查出商品库存
    public int findInventById(Integer productId);
}
