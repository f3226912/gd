package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.dto.sysmgr.TreeNode;
import com.gudeng.commerce.gd.admin.service.AdAdvertiseToolService;
import com.gudeng.commerce.gd.admin.service.AdMenuToolService;
import com.gudeng.commerce.gd.admin.service.AdSpaceToolService;
import com.gudeng.commerce.gd.admin.service.FarmersMarketToolService;
import com.gudeng.commerce.gd.admin.service.FileUploadToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.ProCategoryService;
import com.gudeng.commerce.gd.admin.service.ProductToolService;
import com.gudeng.commerce.gd.admin.service.PushAdInfoToolService;
import com.gudeng.commerce.gd.admin.service.PushNoticeToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.AdAdvertiseDTO;
import com.gudeng.commerce.gd.customer.dto.AdMenuDTO;
import com.gudeng.commerce.gd.customer.dto.AdSpaceDTO;
import com.gudeng.commerce.gd.customer.entity.AdAdvertise;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * @Description 新版广告管理(第二版)
 * @Project gd-admin-web
 * @ClassName PushV2Controller.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年4月12日 下午8:52:00
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
@Controller
@RequestMapping("pushV2")
public class PushV2Controller extends AdminBaseController {
    /** 记录日志 */
    private static final GdLogger logger = GdLoggerFactory.getLogger(PushV2Controller.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    public PushAdInfoToolService pushAdInfoToolService;
    @Autowired
    public PushNoticeToolService pushNoticeToolService;
    @Autowired
    public ProductToolService productToolService;
    @Autowired
    public FarmersMarketToolService farmersMarketToolService;
    @Autowired
    public MarketManageService marketManageService;
    @Autowired
    public FileUploadToolService fileUploadToolService;
    @Autowired
    public MemberBaseinfoToolService memberBaseinfoToolService;
    @Autowired
    public ProCategoryService proCategoryService;
    @Autowired
    private AdAdvertiseToolService advertiseService;
    @Autowired
    private AdMenuToolService adMenuService;
    @Autowired
    private AdSpaceToolService adSpaceService;

    /**
     * @Description 广告列表页面
     * @param request
     * @param map
     * @return
     * @CreationDate 2016年4月13日 下午1:47:10
     * @Author lidong(dli@gdeng.cn)
     */
    @RequestMapping("adInfoList")
    public String adInfoList(HttpServletRequest request, ModelMap map) {
        return "push-v2/adinfo_list";
    }

    /**
     * 广告轮播管理列表数据查询
     * 
     * @param request
     * @return
     * 
     */
    @RequestMapping("adInfoQuery")
    @ResponseBody
    public String adInfoQuery(HttpServletRequest request, AdAdvertiseDTO adAdvertiseDTO) {
        try {
            // 设置查询参数
            Map<String, Object> map = convertDTO2Map(adAdvertiseDTO);
            // 获取条件记录总数
            int total = advertiseService.getTotal(map);
            map.put("total", total);
            // adMenuService.getAdMenuByCondition(map);
            // 设置分页参数
            setCommParameters(request, map);
            List<AdAdvertiseDTO> list = advertiseService.geAdAdvertiseDTOList(map);
            // rows键 存放每页记录 list
            map.put("rows", list);
            return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
        } catch (Exception e) {
            logger.trace(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化添加页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("adInfoAdd/{adSpaceId}")
    public String adInfoAdd(ModelMap map, @PathVariable Long adSpaceId) {
        try {
            AdSpaceDTO adSpaceDTO = adSpaceService.getById(adSpaceId);
            map.put("adSpaceDTO", adSpaceDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("option", "add");
        return "push-v2/adinfo_add";
    }

    /**
     * 初始化 查看详细信息 application/json
     * 
     * @param request
     * @return
     */
    @RequestMapping("adInfoDetailById/{id}")
    public String adInfoDetailById(@PathVariable("id") String id, ModelMap map) {
        try {
            map.put("id", id);
            map.put("startRow", 0);
            map.put("endRow", 10);
            AdAdvertiseDTO adAdvertiseDTO = advertiseService.geAdAdvertiseDTOList(map).get(0);
            if (null != adAdvertiseDTO.getCategoryId()) {
                ProductCategoryDTO pcdto1 = proCategoryService.getProductCategoryById(adAdvertiseDTO.getCategoryId());
                if (null != pcdto1 && null != pcdto1.getCategoryId()) {
                    if (pcdto1.getParentId() > 0) {
                        ProductCategoryDTO pcdto2 = proCategoryService.getProductCategoryById(pcdto1.getParentId());
                        if (null != pcdto2 && null != pcdto2.getCategoryId()) {
                            if (pcdto2.getParentId() > 0) {
                                ProductCategoryDTO pcdto3 = proCategoryService.getProductCategoryById(pcdto2.getParentId());
                                if (null != pcdto3 && null != pcdto3.getCategoryId()) {
                                    adAdvertiseDTO.setCategoryId1(pcdto3.getCategoryId());
                                    adAdvertiseDTO.setCategoryId2(pcdto2.getCategoryId());
                                    adAdvertiseDTO.setCategoryId3(pcdto1.getCategoryId());
                                }
                            } else {
                                adAdvertiseDTO.setCategoryId1(pcdto2.getCategoryId());
                                adAdvertiseDTO.setCategoryId2(pcdto1.getCategoryId());
                            }
                        }
                    } else {
                        adAdvertiseDTO.setCategoryId1(pcdto1.getCategoryId());
                    }
                }
            }
            adAdvertiseDTO.setStartTimeStr(sdf2.format(adAdvertiseDTO.getStartTime()));
            adAdvertiseDTO.setEndTimeStr(sdf2.format(adAdvertiseDTO.getEndTime()));
            map.put("dto", adAdvertiseDTO);
        } catch (Exception e) {
            logger.trace(e.getMessage());
            e.printStackTrace();
        }
        map.put("option", "view");
        return "push-v2/adinfo_add";
    }

    /**
     * @Description adInfoSaveAdd 新增广告
     * @param adAdvertiseDTO
     * @param adAdvertise
     * @param request
     * @return
     * @CreationDate 2016年4月15日 下午2:13:38
     * @Author lidong(dli@gdeng.cn)
     */
    @RequestMapping(value = "adInfoSaveAdd")
    @ResponseBody
    public Map<String, Object> adInfoSaveAdd(AdAdvertiseDTO adAdvertiseDTO, AdAdvertise adAdvertise, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("option", "add");
        try {
            Long adSpaceId = adAdvertiseDTO.getAdSpaceId();
            map.put("startRow", 0);
            map.put("endRow", 9999999);
            map.put("adSpaceId", adSpaceId);
            map.put("stateList", Arrays.asList(new String[] { "1", "2" }));// 启用和等待状态
            List<AdAdvertiseDTO> advertiseList = advertiseService.geAdAdvertiseDTOList(map);// 该广告位下的广告
            // 所在广告位
            AdSpaceDTO adSpaceDTO = adSpaceService.getById(adSpaceId);
            Map<String, Object> result = validateSave(adSpaceDTO, adAdvertiseDTO, adAdvertise);
            String msg = (String) result.get("msg");
            if (StringUtils.isNotEmpty(msg)) {
                return result;
            }
            if (advertiseList != null) {
                adAdvertiseDTO.setStartTime(sdf.parse(adAdvertiseDTO.getStartTimeStr() + " 00:00:00"));
                adAdvertiseDTO.setEndTime(sdf.parse(adAdvertiseDTO.getEndTimeStr() + " 23:59:59"));
                if (validateAdTimeAll(advertiseList, adAdvertiseDTO)) {
                    map.put("msg", "新广告与已存在的广告的展示时间区间有交叉，请重新指定新广告的时间");
                    return map;
                }
                if (new Date().getTime() > adAdvertiseDTO.getEndTime().getTime()) {
                    map.put("msg", "新广告结束时间必须大于当前时间，请重新指定新广告的时间");
                    return map;
                }
            }
            adAdvertise = (AdAdvertise) result.get("adAdvertise");
            SysRegisterUser user = this.getUser(request);
            adAdvertise.setCreateTime(new Date());
            adAdvertise.setCreateUserId(user.getUserID());
            adAdvertise.setCreateUserName(user.getUserName());
            adAdvertise.setState("2");// 默认等待状态
            Long i = advertiseService.persit(adAdvertise);
            if (i > 0) {
                adAdvertise.setId(i);
                map.put("adAdvertise", adAdvertise);
                map.put("msg", "success");
                return map;
            } else {
                map.put("msg", "操作失败");
                return map;
            }
        } catch (Exception e) {
            logger.trace(e.getMessage());
            e.printStackTrace();
        }
        map.put("msg", "操作失败");
        return map;
    }

    /**
     * 初始化编辑页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("adInfoEditById/{id}")
    public String adInfoEditById(@PathVariable("id") String id, ModelMap map) {
        try {
            map.put("id", id);
            map.put("startRow", 0);
            map.put("endRow", 10);
            AdAdvertiseDTO adAdvertiseDTO = advertiseService.geAdAdvertiseDTOList(map).get(0);
            if (null != adAdvertiseDTO.getCategoryId()) {
                ProductCategoryDTO pcdto1 = proCategoryService.getProductCategoryById(adAdvertiseDTO.getCategoryId());
                if (null != pcdto1 && null != pcdto1.getCategoryId()) {
                    if (pcdto1.getParentId() > 0) {
                        ProductCategoryDTO pcdto2 = proCategoryService.getProductCategoryById(pcdto1.getParentId());
                        if (null != pcdto2 && null != pcdto2.getCategoryId()) {
                            if (pcdto2.getParentId() > 0) {
                                ProductCategoryDTO pcdto3 = proCategoryService.getProductCategoryById(pcdto2.getParentId());
                                if (null != pcdto3 && null != pcdto3.getCategoryId()) {
                                    adAdvertiseDTO.setCategoryId1(pcdto3.getCategoryId());
                                    adAdvertiseDTO.setCategoryId2(pcdto2.getCategoryId());
                                    adAdvertiseDTO.setCategoryId3(pcdto1.getCategoryId());
                                }
                            } else {
                                adAdvertiseDTO.setCategoryId1(pcdto2.getCategoryId());
                                adAdvertiseDTO.setCategoryId2(pcdto1.getCategoryId());
                            }
                        }
                    } else {
                        adAdvertiseDTO.setCategoryId1(pcdto1.getCategoryId());
                    }
                }
            }
            adAdvertiseDTO.setStartTimeStr(sdf2.format(adAdvertiseDTO.getStartTime()));
            adAdvertiseDTO.setEndTimeStr(sdf2.format(adAdvertiseDTO.getEndTime()));
            map.put("dto", adAdvertiseDTO);
        } catch (Exception e) {
            logger.trace(e.getMessage());
            e.printStackTrace();
        }
        map.put("option", "update");
        return "push-v2/adinfo_add";
    }

    /**
     * 提交更新数据
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "adInfoSaveEdit")
    @ResponseBody
    public Map<String, Object> adInfoSaveEdit(AdAdvertiseDTO adAdvertiseDTO, AdAdvertise adAdvertise, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("option", "update");
        try {
            Long adSpaceId = adAdvertiseDTO.getAdSpaceId();
            map.put("startRow", 0);
            map.put("endRow", 9999999);
            map.put("adSpaceId", adSpaceId);
            // 所在广告位字段验证
            AdSpaceDTO adSpaceDTO = adSpaceService.getById(adSpaceId);
            Map<String, Object> result = validateSave(adSpaceDTO, adAdvertiseDTO, adAdvertise);
            String msg = (String) result.get("msg");
            if (StringUtils.isNotEmpty(msg)) {
                return result;
            }
            // 启用和等待状态的广告修改需要验证时间段
            if (!adAdvertiseDTO.getState().equals("4")) {
                map.put("stateList", Arrays.asList(new String[] { "1", "2" }));// 启用和等待状态
                List<AdAdvertiseDTO> advertiseList = advertiseService.geAdAdvertiseDTOList(map);// 该广告位下的广告
                if (advertiseList != null) {
                    List<AdAdvertiseDTO> list = new ArrayList<>();
                    for (AdAdvertiseDTO adAdvertiseDTO2 : advertiseList) {
                        if (adAdvertiseDTO2.getId() != adAdvertiseDTO.getId()) {
                            list.add(adAdvertiseDTO2);
                        }
                    }
                    adAdvertiseDTO.setStartTimeStr(adAdvertiseDTO.getStartTimeStr() + " 00:00:00");
                    adAdvertiseDTO.setEndTimeStr(adAdvertiseDTO.getEndTimeStr() + " 23:59:59");
                    adAdvertiseDTO.setStartTime(sdf.parse(adAdvertiseDTO.getStartTimeStr()));
                    adAdvertiseDTO.setEndTime(sdf.parse(adAdvertiseDTO.getEndTimeStr()));
                    if (validateAdTimeAll(list, adAdvertiseDTO)) {
                        map.put("msg", "新广告与已存在的广告的展示时间区间有交叉，请重新指定新广告的时间");
                        return map;
                    }
                    if (new Date().getTime() > adAdvertiseDTO.getEndTime().getTime()) {
                        map.put("msg", "新广告结束时间必须大于当前时间，请重新指定新广告的时间");
                        return map;
                    }
                }
            } else {
                adAdvertiseDTO.setStartTimeStr(adAdvertiseDTO.getStartTimeStr() + " 00:00:00");
                adAdvertiseDTO.setEndTimeStr(adAdvertiseDTO.getEndTimeStr() + " 23:59:59");
            }
            SysRegisterUser user = this.getUser(request);
            adAdvertiseDTO.setUpdateUserId(user.getUserID());
            adAdvertiseDTO.setUpdateUserName(user.getUserName());
            int i = advertiseService.update(adAdvertiseDTO);
            if (i > 0) {
                map.put("adAdvertise", adAdvertiseDTO);
                map.put("msg", "success");
                return map;
            } else {
                map.put("msg", "操作失败");
                return map;
            }
        } catch (Exception e) {
            logger.trace(e.getMessage());
            e.printStackTrace();
        }
        map.put("msg", "操作失败");
        return map;
    }

    /**
     * 根据ID进行广告状态修改操作
     * 
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "adInfoDeleteById/{id}")
    @ResponseBody
    public Map<String, Object> adInfoDeleteById(@PathVariable Long id, HttpServletRequest request) {
        try {
            String state = request.getParameter("state");
            String adSpaceId = request.getParameter("adSpaceId");
            Map<String, Object> map = new HashMap<>();
            map.put("startRow", 0);
            map.put("endRow", 9999999);
            List<AdAdvertiseDTO> advertiseList = null;
            if ("1".equals(state)) {
                // 启用操作
                // 如果是对非上架状态广告执行上架
                map.put("state", 1);
                map.put("adSpaceId", adSpaceId);
                advertiseList = advertiseService.geAdAdvertiseDTOList(map);
                if (advertiseList != null && advertiseList.size() > 0) {
                    // 已存在上架广告
                    map.put("msg", "该广告位已有广告正在展示，不可以启用");
                    return map;
                }
            }
            map.remove("state");
            map.remove("adSpaceId");
            map.put("id", id);
            AdAdvertiseDTO adAdvertiseDTO = advertiseService.geAdAdvertiseDTOList(map).get(0);// 该广告位下的广告
            if ("1".equals(state)) {
                Long nowTime = new Date().getTime();
                if (!(nowTime >= adAdvertiseDTO.getStartTime().getTime() && nowTime <= adAdvertiseDTO.getEndTime().getTime())) {
                    // 已存在上架广告
                    map.put("msg", "当前时间不在该广告展示时间区间段内，不可以启用");
                    return map;
                }
                map.remove("id");
                map.put("stateList", Arrays.asList(new String[] { "1", "2" }));// 启用和等待状态
                map.put("adSpaceId", adSpaceId);
                advertiseList = advertiseService.geAdAdvertiseDTOList(map);
                List<AdAdvertiseDTO> list = new ArrayList<>();
                for (AdAdvertiseDTO adAdvertiseDTO2 : advertiseList) {
                    if (adAdvertiseDTO2.getId() != adAdvertiseDTO.getId()) {
                        list.add(adAdvertiseDTO2);
                    }
                }
                if (validateAdTimeAll(list, adAdvertiseDTO)) {
                    map.put("msg", "新广告与已存在的广告的展示时间区间有交叉，请重新指定新广告的时间");
                    return map;
                }
            }
            SysRegisterUser user = this.getUser(request);
            adAdvertiseDTO.setState(state);
            adAdvertiseDTO.setUpdateUserId(user.getUserID());
            adAdvertiseDTO.setUpdateUserName(user.getUserName());
            int i = advertiseService.updateState(adAdvertiseDTO);
            if (i > 0) {
                map.put("msg", "success");
                map.put("adAdvertise", adAdvertiseDTO);
                return map;
            } else {
                map.put("message", "操作失败");
                return map;
            }
        } catch (Exception e) {
            logger.trace(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 跳转商品列表页
     * 
     * @return
     */
    @RequestMapping("proInitList")
    public String proInitList(HttpServletRequest request, ModelMap map) {
        String categoryId = request.getParameter("categoryId");
        map.put("categoryId", categoryId);
        String selectType = request.getParameter("selectType");
        map.put("selectType", selectType);
        return "push-v2/productSelectList";
    }

    /**
     * 上传图片
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("uploadProductPic")
    public String uploadProductPic(HttpServletRequest request, @RequestParam(value = "productPicture", required = false) MultipartFile file) {
        String masterPicPath = "";
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!file.isEmpty()) {
                String fileName = CommonUtil.generateSimpleFileName(file.getOriginalFilename());
                masterPicPath = fileUploadToolService.uploadImgfile(file.getBytes(), fileName);
            } else {
                map.put("status", 0);
                map.put("message", "upload file failed!!");
                return JSONObject.toJSONString(map);
            }
        } catch (IllegalStateException e1) {
            map.put("status", 0);
            map.put("message", "upload file failed!!");
            return JSONObject.toJSONString(map);
        } catch (IOException e1) {
            map.put("status", 0);
            map.put("message", "upload file failed!!");
            return JSONObject.toJSONString(map);
        } catch (Exception e) {
            map.put("status", 0);
            map.put("message", "upload file failed!!");
            return JSONObject.toJSONString(map);
        }
        map.put("status", 1);
        map.put("message", masterPicPath);
        map.put("url", masterPicPath);
        return JSONObject.toJSONString(map);
    }

    @RequestMapping("getTreeMenu")
    @ResponseBody
    public String getFirstMenu(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fatherId", 0);
        map.put("startRow", 0);
        map.put("endRow", 9999999);
        AdMenuDTO adMenuDTO = adMenuService.getAdMenuByCondition(map).get(0);// 根节点
        /** =======节点类型 0：根节点，无意义 1：菜单 9：按钮============ **/
        /** =======1 添加根节点============ **/
        TreeNode treeLevel_0_Node = new TreeNode();// 根节点
        Map<String, Object> treeLevel_0_Attributes = new HashMap<>();// 根节点自定义属性
        treeLevel_0_Node.setId(adMenuDTO.getId().toString());
        treeLevel_0_Node.setText(adMenuDTO.getMenuName());
        treeLevel_0_Attributes.put("type", 0);// 节点类型,根节点
        treeLevel_0_Node.setAttributes(treeLevel_0_Attributes);
        // treeLevel_0_Node = getAdMenuTreeNode(treeLevel_0_Node, 1);
        treeLevel_0_Node.setChildren(getTreeNodeChildren(treeLevel_0_Node));
        treeLevel_0_Node.setState("open");// 根节点展开
        /** =======1 添加根节点结束============ **/
        /*** 构建easyUI树 ***/
        List<TreeNode> jsonList = new ArrayList<>();
        jsonList.add(treeLevel_0_Node);
        return JSONObject.toJSONString(jsonList, SerializerFeature.WriteDateUseDateFormat);
    }

    private List<TreeNode> getTreeNodeChildren(TreeNode node) throws Exception {
        String id = node.getId();
        List<TreeNode> children = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        // 菜单下的菜单
        map.clear();
        map.put("fatherId", id);
        map.put("state", 1);
        map.put("startRow", 0);
        map.put("endRow", 9999999);
        List<AdMenuDTO> adMenuDTOList = adMenuService.getAdMenuByCondition(map);
        for (AdMenuDTO adMenuDTO : adMenuDTOList) {
            TreeNode treeLevel_1_Node = new TreeNode();
            Map<String, Object> treeLevel_1_Attributes = new HashMap<>();//
            treeLevel_1_Node.setId(adMenuDTO.getId().toString());
            treeLevel_1_Node.setText(adMenuDTO.getMenuName() + "(" + adMenuDTO.getMenuSign() + ")");
            treeLevel_1_Node.setState("open");// 菜单展开
            treeLevel_1_Node.setChildren(getTreeNodeChildren(treeLevel_1_Node));
            if (treeLevel_1_Node.getChildren().size() > 0) {
                treeLevel_1_Node.setState("closed");// 菜单关闭
            }
            treeLevel_1_Attributes.put("menuSign", adMenuDTO.getMenuSign());// 菜单标识
            treeLevel_1_Attributes.put("fatherId", adMenuDTO.getFatherId());// 父类菜单ID(顶级菜单为0)
            treeLevel_1_Attributes.put("type", 1);// 节点类型 1 菜单
            treeLevel_1_Node.setAttributes(treeLevel_1_Attributes);
            children.add(treeLevel_1_Node);
        }
        // 菜单下的广告位
        map.clear();
        map.put("menuId", id);
        map.put("state", 1);
        List<AdSpaceDTO> adSpaceDTOList = adSpaceService.queryByCondition(map);
        for (AdSpaceDTO adSpaceDTO : adSpaceDTOList) {
            TreeNode treeLevel_1_Node = new TreeNode();
            Map<String, Object> treeLevel_1_Attributes = new HashMap<>();//
            treeLevel_1_Node.setId(adSpaceDTO.getId().toString());
            treeLevel_1_Node.setText(adSpaceDTO.getAdName() + "(" + adSpaceDTO.getSpaceSign() + ")");
            treeLevel_1_Node.setState("open");// 菜单展开
            treeLevel_1_Node.setIconCls("icon-adSpace");
            treeLevel_1_Attributes.put("spaceSign", adSpaceDTO.getSpaceSign());// 菜单标识
            treeLevel_1_Attributes.put("menuId", adSpaceDTO.getMenuId());// 父类菜单ID(顶级菜单为0)
            treeLevel_1_Attributes.put("type", 2);// 节点类型 2 广告位
            treeLevel_1_Node.setAttributes(treeLevel_1_Attributes);
            children.add(treeLevel_1_Node);

            // 广告位下的广告
            /*
             * map.clear(); map.put("adSpaceId", treeLevel_1_Node.getId()); map.put("startRow", 0); map.put("endRow", 9999999); List<AdAdvertiseDTO> advertiseDTOList =
             * advertiseService.geAdAdvertiseDTOList(map); for (AdAdvertiseDTO adAdvertiseDTO : advertiseDTOList) { TreeNode treeLevel_2_Node = new TreeNode(); Map<String, Object>
             * treeLevel_2_Attributes = new HashMap<>();// treeLevel_2_Node.setId(adAdvertiseDTO.getId().toString()); treeLevel_2_Node.setText(adAdvertiseDTO.getAdName());
             * treeLevel_2_Node.setIconCls("icon-adVertise"); treeLevel_2_Node.setState("open");// 菜单展开 treeLevel_2_Attributes.put("type", 3);// 节点类型 3 广告
             * treeLevel_2_Attributes.put("adSpaceId", adAdvertiseDTO.getAdSpaceId());// 广告所属广告位 treeLevel_2_Node.setAttributes(treeLevel_2_Attributes);
             * treeLevel_1_Node.getChildren().add(treeLevel_2_Node); } if (treeLevel_1_Node.getChildren().size() > 0) { treeLevel_1_Node.setState("closed");// 菜单关闭 }
             */
        }
        return children;
    }

    private List<AdAdvertiseDTO> geAdAdvertiseListByMenuId(String menuId) {
        List<AdAdvertiseDTO> list = new ArrayList<>();

        return list;
    }

    private Map<String, Object> validateSave(AdSpaceDTO adSpaceDTO, AdAdvertiseDTO adAdvertiseDTO, AdAdvertise adAdvertise) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String adType = adSpaceDTO.getAdType();// 广告位类型
        String jumpType = adAdvertise.getJumpType();// 广告跳转类型
        String adUrl = adAdvertise.getAdUrl();// 广告图片地址
        String adWord = adAdvertise.getAdWord();// 广告文字
        String jumpUrl = adAdvertise.getJumpUrl();// 跳转地址
        if ("3".equals(adType)) {
            // 广告位为app启动页
            if (StringUtils.isEmpty(adUrl)) {
                map.put("msg", "请上传图片");
                return map;
            }
        } else {
            if ("2".equals(adType)) {
                if (StringUtils.isEmpty(adWord)) {
                    map.put("msg", "请输入广告文字");
                    return map;
                }
            }
            if ("1".equals(adType)) {
                if (StringUtils.isEmpty(adUrl)) {
                    map.put("msg", "请上传图片");
                    return map;
                }
            }
//            if ("3".equals(jumpType)) {
//                if (StringUtils.isEmpty(jumpUrl)) {
//                    map.put("msg", "请填写跳转地址");
//                    return map;
//                }
//            }
        }
        if (StringUtils.isEmpty(adAdvertiseDTO.getAdName())) {
            map.put("msg", "请填写广告名称");
            return map;
        }
        if (StringUtils.isEmpty(adAdvertiseDTO.getStartTimeStr()) || StringUtils.isEmpty(adAdvertiseDTO.getEndTimeStr())) {
            map.put("msg", "请选择广告时间");
            return map;
        }
        adAdvertise.setStartTime(sdf.parse(adAdvertiseDTO.getStartTimeStr() + " 00:00:00"));
        adAdvertise.setEndTime(sdf.parse(adAdvertiseDTO.getEndTimeStr() + " 23:59:59"));
        map.put("adAdvertise", adAdvertise);
        return map;
    }

    /**
     * @Description validateAdTime 验证新增或修改后的广告与已存在的广告的时间关系，两者时间区间不可以交叉
     * @param oldAd
     * @param newAd
     * @return false无交叉，true有交叉
     * @throws Exception
     * @CreationDate 2016年4月16日 上午9:57:01
     * @Author lidong(dli@gdeng.cn)
     */
    private boolean validateAdTime(AdAdvertiseDTO oldAd, AdAdvertiseDTO newAd) throws Exception {
        long oldStart = oldAd.getStartTime().getTime();
        long oldEnd = oldAd.getEndTime().getTime();
        long newStart = newAd.getStartTime().getTime();
        long newEnd = newAd.getEndTime().getTime();
        if (newStart > oldEnd) {
            return false;
        }
        if (oldStart > newEnd) {
            return false;
        }
        return true;
    }

    /**
     * @Description validateAdTimeAll 验证新增广告时间与所有广告的时间是否有交叉
     * @param oldAdList
     * @param newAd
     * @return false无交叉，true有交叉
     * @throws Exception
     * @CreationDate 2016年4月18日 下午5:28:46
     * @Author lidong(dli@gdeng.cn)
     */
    private boolean validateAdTimeAll(List<AdAdvertiseDTO> oldAdList, AdAdvertiseDTO newAd) throws Exception {
        for (AdAdvertiseDTO adAdvertiseDTO : oldAdList) {
            if (validateAdTime(adAdvertiseDTO, newAd)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Description 将DTO组装为查询map条件
     * @param adAdvertiseDTO
     * @return
     * @CreationDate 2016年4月13日 下午1:51:56
     * @Author lidong(dli@gdeng.cn)
     */
    private Map<String, Object> convertDTO2Map(AdAdvertiseDTO adAdvertiseDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", adAdvertiseDTO.getId());
        map.put("state", adAdvertiseDTO.getState());
        map.put("categoryId", adAdvertiseDTO.getCategoryId());
        map.put("createUserId", adAdvertiseDTO.getCreateUserId());
        map.put("createUserName", adAdvertiseDTO.getCreateUserName());
        map.put("createTime", adAdvertiseDTO.getCreateTime());
        map.put("updateUserId", adAdvertiseDTO.getUpdateUserId());
        map.put("updateUserName", adAdvertiseDTO.getUpdateUserName());
        map.put("updateTime", adAdvertiseDTO.getUpdateTime());
        map.put("adSpaceId", adAdvertiseDTO.getAdSpaceId());
        map.put("adspaceName", adAdvertiseDTO.getAdspaceName());
        map.put("adName", adAdvertiseDTO.getAdName());
        map.put("jumpType", adAdvertiseDTO.getJumpType());
        map.put("jumpUrl", adAdvertiseDTO.getJumpUrl());
        map.put("adWord", adAdvertiseDTO.getAdWord());
        map.put("startTime", adAdvertiseDTO.getStartTimeStr());
        map.put("endTime", adAdvertiseDTO.getEndTimeStr());
        map.put("productId", adAdvertiseDTO.getProductId());
        map.put("marketId", adAdvertiseDTO.getMarketId());
        map.put("adCanal", adAdvertiseDTO.getAdCanal());
        map.put("adType", adAdvertiseDTO.getAdType());
        map.put("menuId", adAdvertiseDTO.getMenuId());
        map.put("spaceSign", adAdvertiseDTO.getSpaceSign());
        return map;
    }

}
