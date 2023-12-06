package com.pn.controller;

import com.pn.entity.*;
import com.pn.page.Page;
import com.pn.service.*;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/product")
@RestController
public class ProductController {

    @Resource
    private TokenUtils tokenUtils;

    /**
     * 将配置文件的file.upload-path属性值注入给控制器的uploadPath属性,
     * 其为图片上传到项目的目录路径(类路径classes即resources下的static/img/upload);
     */
    @Value("${file.upload-path}")
    private String uploadPath;

    @Resource
    private UnitService unitService;

    @Resource
    private PlaceService placeService;

    @Resource
    private SupplyService supplyService;

    @Resource
    private ProductTypeService productTypeService;

    @Resource
    private ProductService productService;

    @Resource
    private StoreService storeService;

    @Resource
    private BrandService brandService;

    // 查询所有仓库
    @RequestMapping("/store-list")
    public Result storeList() {
        // 执行业务
        List<Store> storeList = storeService.queryAllStore();
        // 响应
        return Result.ok(storeList);
    }

    // 查询所有品牌
    @RequestMapping("/brand-list")
    public Result brandList() {
        // 执行业务
        List<Brand> brandList = brandService.queryAllBrand();
        // 响应
        return Result.ok(brandList);
    }

    // 分页查询商品
    @RequestMapping("/product-page-list")
    public Result productListPage(Page page, Product product) {
        // 执行业务
        page = productService.queryProductPage(page, product);
        // 响应
        return Result.ok(page);
    }

    // 查询所有商品分类树
    @RequestMapping("/category-tree")
    public Result loadTypeTree() {
        // 执行业务
        List<ProductType> typeTreeList = productTypeService.productTypeTree();
        // 响应
        return Result.ok(typeTreeList);
    }

    // 查询所有供应商
    @RequestMapping("/supply-list")
    public Result supplyList() {
        // 执行业务
        List<Supply> supplyList = supplyService.queryAllSupply();
        // 响应
        return Result.ok(supplyList);
    }

    // 查询所有产地
    @RequestMapping("/place-list")
    public Result placeList() {
        // 执行业务
        List<Place> placeList = placeService.queryAllPlace();
        // 响应
        return Result.ok(placeList);
    }

    // 查询所有单位
    @RequestMapping("/unit-list")
    public Result unitList() {
        // 执行业务
        List<Unit> unitList = unitService.queryAllUnit();
        // 响应
        return Result.ok(unitList);
    }

    // 上传图片
    /**
     * 参数MultipartFile file对象封装了上传的图片;
     */
    // 支持跨域
    @CrossOrigin
    @RequestMapping("/img-upload")
    public Result uploadImage(MultipartFile file) {
        try {
            // 解析图片上传到的目录，拿到封装了类路径的File对象
            File uploadDirFile = ResourceUtils.getFile(uploadPath);
            // 拿到图片上传到的目录的磁盘路径
            File uploadDirPath = uploadDirFile.getAbsoluteFile();
            // 拿到上传的图片的名称
            String filename = file.getOriginalFilename();
            // 拿到上传的文件要保存到的磁盘路径
            String uploadFilePath = uploadDirPath + "\\" + filename;
            // 上传图片
            file.transferTo(new File(uploadFilePath));

            // 成功响应
            return Result.ok("图片上传成功！");
        } catch (Exception e) {
            return Result.err(Result.CODE_ERR_BUSINESS, "图片上传失败！");
        }
    }

    // 添加商品
    @RequestMapping("/product-add")
    public Result addProduct(@RequestBody Product product, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        // 拿到当前登录的用户的id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int createBy = currentUser.getUserId();

        product.setCreateBy(createBy);

        // 执行业务
        Result result = productService.saveProduct(product);

        // 响应
        return result;
    }

    // 修改商品上下架状态
    @RequestMapping("/state-change")
    public Result changeProductState(@RequestBody Product product) {
        // 执行业务
        Result result = productService.updateStateByPid(product);
        // 响应
        return result;
    }

    // 删除单个商品
    @RequestMapping("/product-delete/{productId}")
    public Result deleteProduct(@PathVariable Integer productId) {
        // 执行业务
        Result result = productService.deleteProductByIds(Arrays.asList(productId));
        // 响应
        return result;
    }

    // 批量删除商品
    @RequestMapping("/product-list-delete")
    public Result deleteProductList(@RequestBody List<Integer> productIdList) {
        // 执行业务
        Result result = productService.deleteProductByIds(productIdList);
        // 响应
        return result;
    }

    // 修改商品
    @RequestMapping("/product-update")
    public Result updateProduct(@RequestBody Product product, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        // 拿到当前登录用户的id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int updateBy = currentUser.getUserId();
        product.setUpdateBy(updateBy);

        // 执行业务
        Result result = productService.setProductById(product);

        // 响应
        return result;
    }
}
