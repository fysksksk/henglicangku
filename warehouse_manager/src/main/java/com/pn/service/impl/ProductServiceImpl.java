package com.pn.service.impl;

import com.pn.entity.Product;
import com.pn.entity.Result;
import com.pn.mapper.ProductMapper;
import com.pn.page.Page;
import com.pn.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${file.access-path}")
    private String fileAccessPath;

    @Resource
    private ProductMapper productMapper;

    // 分页查询商品
    public Page queryProductPage(Page page, Product product) {
        // 查询商品行数
        int count = productMapper.findProductRowCount(product);
        // 分页查询商品
        List<Product> productList = productMapper.findProductPage(page, product);
        // 组装分页信息
        page.setTotalNum(count);
        page.setResultList(productList);
        return page;
    }

    // 添加商品
    @Transactional(rollbackFor = Exception.class)
    public Result saveProduct(Product product) {
        // 判断商品型号是否已存在
        Product prct = productMapper.findProductByNum(product.getProductNum());
        if (prct != null) {
            return Result.err(Result.CODE_ERR_BUSINESS, "该型号商品已存在！");
        }
        // 处理上传的图片的访问路径
        product.setImgs(fileAccessPath + product.getImgs());
        // 添加商品
        int i = productMapper.insertProduct(product);
        if (i > 0) {
            return Result.ok("商品添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "添加商品失败！");
    }

    // 修改商品上下架
    @Transactional(rollbackFor = Exception.class)
    public Result updateStateByPid(Product product) {
        int i = productMapper.setStateByPid(product.getProductId(), product.getUpDownState());
        if (i > 0) {
            return Result.ok("商品的上下架状态设置成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "商品的上下架状态设置失败！");
    }

    // 删除商品
    @Transactional(rollbackFor = Exception.class)
    public Result deleteProductByIds(List<Integer> productIdList) {
        int i = productMapper.removeProductByRid(productIdList);
        if (i > 0) {
            return Result.ok("商品删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "商品删除失败！");
    }


    // 修改商品
    @Transactional(rollbackFor = Exception.class)
    public Result setProductById(Product product) {
        // 判断修改后的型号是否已存在
        Product prod = productMapper.findProductByNum(product.getProductNum());
        if (prod != null && !product.getProductId().equals(prod.getProductId())) {
            return Result.err(Result.CODE_ERR_BUSINESS, "型号已存在！");
        }

        // 判断上传的图片是否被修改
        if (!product.getImgs().contains(fileAccessPath)) {
            product.setImgs(fileAccessPath + product.getImgs());
        }

        // 修改商品
        int i = productMapper.setProductById(product);
        if (i > 0) {
            return Result.ok("商品修改成功！");
        }

        return Result.err(Result.CODE_ERR_BUSINESS, "商品修改失败！");
    }
}
