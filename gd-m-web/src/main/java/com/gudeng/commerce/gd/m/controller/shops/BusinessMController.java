package com.gudeng.commerce.gd.m.controller.shops;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.m.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.m.service.ProCategoryService;
import com.gudeng.commerce.gd.m.service.ProductToolService;
import com.gudeng.commerce.gd.m.service.PromChainToolService;
import com.gudeng.commerce.gd.m.service.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.m.service.ReBusinessMarketToolService;
import com.gudeng.commerce.gd.m.util.DictDataUtil;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdResult;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;

/**
 * @Description H5商铺
 * @Project gd-m-web
 * @ClassName BusinessMController.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年4月25日 下午5:14:22
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 * 
 */
@Controller
@RequestMapping("shop")
public class BusinessMController extends MBaseController {
    @Autowired
    public GdProperties gdProperties;
    @Autowired
    public DictDataUtil dictDataUtil;
    /** 记录日志 */
    private static Logger logger = LoggerFactory.getLogger(BusinessMController.class);

    @Autowired
    public BusinessBaseinfoToolService businessBaseinfoToolService;
    @Autowired
    public MemberBaseinfoToolService memberBaseinfoToolService;
    @Autowired
    public ReBusinessMarketToolService reBusinessMarketToolService;
    @Autowired
    public ReBusinessCategoryToolService reBusinessCategoryToolService;
    @Autowired
    public ProCategoryService proCategoryService;
    @Autowired
    public ProductToolService productToolService;
    @Autowired
    public PromChainToolService promChainToolService;

    /**
     * @Description 商铺首页，包含商铺部分信息以及商品列表
     * @param businessId
     * @param mv
     * @param startRow
     * @param endRow
     * @return
     * @CreationDate 2016年4月25日 下午5:25:40
     * @Author lidong(dli@gdeng.cn)
     */
    @RequestMapping(value = "shop{businessId}")
    public ModelAndView shopIndex(@PathVariable("businessId") String businessId, ModelAndView mv) {
        try {
            BusinessBaseinfoDTO dto = businessBaseinfoToolService.getById(businessId);
            mv.addObject("dto", dto);
            mv.addObject("businessId", businessId);
            mv.addObject("imgUrl", gdProperties.getImgHostServiceUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mv.setViewName("H5/shops/store-index");
        return mv;
    }

    @RequestMapping(value = "productList")
    @ResponseBody
    public Map<String, Object> productList() {
        Map<String, Object> query = new HashMap<String, Object>();
        try {
            Long businessId = Long.valueOf(getRequest().getParameter("businessId"));
            query.put("startRow", (Integer.valueOf(getRequest().getParameter("startRow")) - 1) * 10);
            query.put("endRow", 10);
            // 获取对应商铺的产品
            query.put("businessId", businessId);
            query.put("state", 3);
            query.put("sortName", "updateTime");
            query.put("sortOrder", "d");
            List<ProductDto> productList = productToolService.getProductList(query);
            for (ProductDto productDto : productList) {
                //获取产品活动价
                PromProdInfoDTO promProdInfoDTO = promChainToolService.getPromotionProduct(productDto.getProductId());
                if(promProdInfoDTO != null && promProdInfoDTO.getActPrice() > 0){
                	productDto.setPrice(promProdInfoDTO.getActPrice());
                	productDto.setPromotion(1);
                }
                productDto.setFormattedPrice(dictDataUtil.formatNumber(productDto.getPrice()));
                productDto.setUpdateTimeString(String.format("%tm-%td", productDto.getUpdateTime(), productDto.getUpdateTime()));
                String unit = productDto.getUnit();
                if (StringUtils.isNotEmpty(unit)) {
                    productDto.setUnitName(dictDataUtil.showValueByCode("ProductUnit", unit));
                }
            }
            query.put("productList", productList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return query;
    }

    /**
     * @Description 商铺详情
     * @param businessId
     * @param request
     * @return
     * @CreationDate 2016年4月25日 下午5:26:11
     * @Author lidong(dli@gdeng.cn)
     */
    @RequestMapping(value = "getShopByBusinessId/{businessId}")
    public String getShopByBusinessId(@PathVariable("businessId") String businessId, HttpServletRequest request) {
        try {
            BusinessBaseinfoDTO dto = businessBaseinfoToolService.getById(businessId);
            // MemberBaseinfoDTO mbdto= memberBaseinfoToolService.getById(String.valueOf(dto.getUserId()));
            Map map = new HashMap();
            map.put("businessId", dto.getBusinessId());
            map.put("startRow", 0);
            map.put("endRow", 200);
            List<ReBusinessCategoryDTO> listRBC = null;
            List<ProductCategoryDTO> lsitAll = null;
            if (null != dto.getLevel() && dto.getLevel() != 4) {
                // 获取店铺店铺的经营分类
                listRBC = reBusinessCategoryToolService.getBySearch(map);
                // 获取店铺所在市场的所有分类
                lsitAll = proCategoryService.listTopProductCategoryByMarketId(Long.valueOf(dto.getMarketId()));
            } else {
                // 获取店铺店铺的经营分类
                listRBC = reBusinessCategoryToolService.getBySearch(map);
                // 获取店铺所在市场的所有分类
                lsitAll = proCategoryService.listTopProductCategoryByMarketId(3L);
            }
            putModel("listRBC", listRBC);
            putModel("lsitAll", lsitAll);
            putModel("dto", dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "H5/shops/store-detail";
    }
}
