package com.gudeng.commerce.gd.home.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.CarLineDTO;
import com.gudeng.commerce.gd.customer.dto.MemberAddressDTO;
import com.gudeng.commerce.gd.home.dto.PageDTO;
import com.gudeng.commerce.gd.home.dto.district.AraeDTO;
import com.gudeng.commerce.gd.home.dto.district.CityDTO;
import com.gudeng.commerce.gd.home.dto.district.DistrctConfig;
import com.gudeng.commerce.gd.home.dto.district.ProvinceDTO;
import com.gudeng.commerce.gd.home.service.QueryAreaToolService;
import com.gudeng.commerce.gd.home.service.TransportationService;
import com.gudeng.commerce.gd.home.util.AreaUtil;
import com.gudeng.commerce.gd.home.util.DateUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 首頁物流服務
 * 
 * @author xiaodong
 */
@Controller
@RequestMapping("logistics")
public class TransportationServiceController extends HomeBaseController {
    /** 记录日志 */
    private static final GdLogger logger = GdLoggerFactory.getLogger(TransportationServiceController.class);

    @Autowired
    public TransportationService transportationService;

    @Autowired
    public QueryAreaToolService queryAreaService;

    /**
     * 
     * 货主找车
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "index", "index/query/{page}" })
    public ModelAndView queryCarLineList(HttpServletRequest request, PageDTO<String> pageDTO) {
        ModelAndView mv = new ModelAndView("transportation/carLineList");
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            putAreaModel(request, map);
            // 记录数
            int count = transportationService.getCarLineTotal(map);
            map.put("total", count);
            // 设定分页,排序
            setCommParameters(request, map, pageDTO, count);
            // 排序条件

            List<CarLineDTO> list = transportationService.getByCondition(map);
            if (list != null && list.size() > 0) {
                for (CarLineDTO dto : list) {
                    if (StringUtils.isEmpty(dto.getSendGoodsType())) {
                        dto.setSendGoodsTypeString("不限");
                    } else {
                        switch (dto.getSendGoodsType()) {
                        case "0":
                            dto.setSendGoodsTypeString("零担");
                            break;
                        case "1":
                            dto.setSendGoodsTypeString("整车");
                            break;
                        case "2":
                            dto.setSendGoodsTypeString("不限");
                            break;
                        default:
                            break;
                        }
                    }
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(2);
                    if (dto.getPrice() != null && dto.getPrice() > 0 && dto.getUnitType() != null) {
                        switch (dto.getUnitType()) {
                        case 0:
                            dto.setUnitTypeString(nf.format(dto.getPrice()) + "元/吨");
                            break;
                        case 1:
                            dto.setUnitTypeString(nf.format(dto.getPrice()) + "元/公斤");
                            break;
                        case 2:
                            dto.setUnitTypeString(nf.format(dto.getPrice()) + "元/立方");
                            break;
                        case 3:
                            dto.setUnitTypeString(nf.format(dto.getPrice()) + "元");
                            break;
                        default:
                            break;
                        }
                        if ("1".equals(String.valueOf(dto.getSendGoodsType()))) {
                            dto.setUnitTypeString(nf.format(dto.getPrice()) + "元");
                        }
                    } else if (dto.getPrice() != null && dto.getPrice() > 0 && dto.getUnitType() == null) {
                        dto.setUnitTypeString(nf.format(dto.getPrice()) + "元");
                    } else {
                        dto.setUnitTypeString("面议");
                    }
                    dto.setSendDateString(DateUtil.toString(dto.getSentDate(), DateUtil.DATE_FORMAT_DATEONLY));
                    dto.setCreateTimeString(DateUtil.toString(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATEONLY));
                    dto.setEndDateString(DateUtil.toString(dto.getEndDate(), DateUtil.DATE_FORMAT_DATEONLY));

                    // 3个始发地， 5个目的地
                    StringBuffer startPlace = new StringBuffer();
                    // 始发地1
                    if (dto.getS_provinceId() != null && dto.getS_provinceId() > 1) {
                        AreaDTO city = queryAreaService.getArea(String.valueOf(dto.getS_cityId()));
                        if (dto.getS_cityId() != null && dto.getS_cityId() > 0 && AreaUtil.isCity(city)) {
                            startPlace.append(city != null ? city.getArea() : " ");
                        } else {
                            AreaDTO province = queryAreaService.getArea(String.valueOf(dto.getS_provinceId()));
                            startPlace.append(province != null ? province.getArea() : " ");
                        }
                    } else if (dto.getS_provinceId() != null && dto.getS_provinceId() == 0) {
                        startPlace.append("全国");
                    }

                    // 始发地2
                    String start2 = "";
                    if (dto.getS_provinceId2() != null && dto.getS_provinceId2() > 1) {
                        AreaDTO city = queryAreaService.getArea(String.valueOf(dto.getS_cityId2()));
                        if (dto.getS_cityId2() != null && dto.getS_cityId2() > 0 && AreaUtil.isCity(city)) {
                            start2 = city != null ? "，" + city.getArea() : " ";

                        } else {
                            AreaDTO province = queryAreaService.getArea(String.valueOf(dto.getS_provinceId2()));
                            start2 = province != null ? "，" + province.getArea() : " ";
                            // startPlace.append(province != null?"，"+province.getArea():" ");
                        }
                    } else if (dto.getS_provinceId2() != null && dto.getS_provinceId2() == 0) {
                        startPlace.append("全国");
                    }
                    // 去重判断
                    if (!startPlace.toString().contains(start2.replace("，", ""))) {
                        startPlace.append(start2);
                    }

                    // 始发地3
                    String start3 = "";
                    if (dto.getS_provinceId3() != null && dto.getS_provinceId3() > 1) {
                        AreaDTO city = queryAreaService.getArea(String.valueOf(dto.getS_cityId3()));
                        if (dto.getS_cityId3() != null && dto.getS_cityId3() > 0 && AreaUtil.isCity(city)) {
                            start3 = city != null ? "，" + city.getArea() : " ";
                            // startPlace.append(city != null ? "，"+city.getArea() : " ");
                        } else {
                            AreaDTO province = queryAreaService.getArea(String.valueOf(dto.getS_provinceId3()));
                            start3 = province != null ? "，" + province.getArea() : " ";
                            // startPlace.append(province != null?"，"+province.getArea():" ");
                        }
                    } else if (dto.getS_provinceId3() != null && dto.getS_provinceId3() == 0) {
                        startPlace.append("全国");
                    }

                    // 去重判断
                    if (!startPlace.toString().contains(start3.replace("，", ""))) {
                        startPlace.append(start3);
                    }

                    // 目的地1
                    StringBuffer endPlace = new StringBuffer();
                    if (dto.getE_provinceId() != null && dto.getE_provinceId() > 1) {
                        AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto.getE_cityId()));
                        if (dto.getE_cityId() != null && dto.getE_cityId() > 0 && AreaUtil.isCity(e_city)) {
                            endPlace.append(e_city != null ? " " + e_city.getArea() : "");
                        } else {
                            AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto.getE_provinceId()));
                            endPlace.append(e_province != null ? e_province.getArea() : "");
                        }
                        /*
                         * AreaDTO e_area = queryAreaService.getArea(String.valueOf(dto .getE_areaId())); endPlace.append(e_area != null ? "--"+e_area.getArea() : "");
                         */
                    } else if (dto.getE_provinceId() != null && dto.getE_provinceId() == 0) {
                        endPlace.append("全国");
                    }

                    // 目的地2
                    String end2 = "";
                    if (dto.getE_provinceId2() != null && dto.getE_provinceId2() > 1) {
                        AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto.getE_cityId2()));
                        if (dto.getE_cityId2() != null && dto.getE_cityId2() > 0 && AreaUtil.isCity(e_city)) {
                            end2 = e_city != null ? "， " + e_city.getArea() : "";
                            // endPlace.append(e_city != null ? "， "+e_city.getArea() : "");
                        } else {
                            AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto.getE_provinceId2()));
                            end2 = e_province != null ? "， " + e_province.getArea() : "";
                            // endPlace.append(e_province != null ? "， "+e_province.getArea(): "");
                        }
                    } else if (dto.getE_provinceId2() != null && dto.getE_provinceId2() == 0) {
                        endPlace.append("全国");
                    }

                    // 去重判断
                    if (!endPlace.toString().contains(end2.replace("， ", ""))) {
                        endPlace.append(end2);
                    }

                    String end3 = "";
                    // 目的地3
                    if (dto.getE_provinceId3() != null && dto.getE_provinceId3() > 1) {
                        AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto.getE_cityId3()));
                        if (dto.getE_cityId3() != null && dto.getE_cityId3() > 0 && AreaUtil.isCity(e_city)) {
                            end3 = e_city != null ? "， " + e_city.getArea() : "";
                            // endPlace.append(e_city != null ? "， "+e_city.getArea() : "");
                        } else {
                            AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto.getE_provinceId3()));
                            end3 = e_province != null ? "， " + e_province.getArea() : "";
                            // endPlace.append(e_province != null ? "， "+e_province.getArea(): "");
                        }
                    } else if (dto.getE_provinceId3() != null && dto.getE_provinceId3() == 0) {
                        endPlace.append("全国");
                    }

                    // 去重判断
                    if (!endPlace.toString().contains(end3.replace("， ", ""))) {
                        endPlace.append(end3);
                    }

                    String end4 = "";
                    // 目的地4
                    if (dto.getE_provinceId4() != null && dto.getE_provinceId4() > 1) {
                        AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto.getE_cityId4()));
                        if (dto.getE_cityId4() != null && dto.getE_cityId4() > 0 && AreaUtil.isCity(e_city)) {
                            end4 = e_city != null ? "， " + e_city.getArea() : "";
                            // endPlace.append(e_city != null ? "， "+e_city.getArea() : "");
                        } else {
                            AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto.getE_provinceId4()));
                            end4 = e_province != null ? "， " + e_province.getArea() : "";
                            // endPlace.append(e_province != null ? "， "+e_province.getArea(): "");
                        }
                    } else if (dto.getE_provinceId4() != null && dto.getE_provinceId4() == 0) {
                        endPlace.append("全国");
                    }

                    // 去重判断
                    if (!endPlace.toString().contains(end4.replace("， ", ""))) {
                        endPlace.append(end4);
                    }

                    String end5 = "";
                    // 目的地5
                    if (dto.getE_provinceId5() != null && dto.getE_provinceId5() > 1) {
                        AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto.getE_cityId5()));
                        if (dto.getE_cityId5() != null && dto.getE_cityId5() > 0 && AreaUtil.isCity(e_city)) {
                            end5 = e_city != null ? "， " + e_city.getArea() : "";
                            // endPlace.append(e_city != null ? "， "+e_city.getArea() : "");
                        } else {
                            AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto.getE_provinceId5()));
                            end5 = e_province != null ? "， " + e_province.getArea() : "";
                            // endPlace.append(e_province != null ? "， "+e_province.getArea(): "");
                        }
                    } else if (dto.getE_provinceId5() != null && dto.getE_provinceId5() == 0) {
                        endPlace.append("全国");
                    }
                    // 去重判断
                    if (!endPlace.toString().contains(end5.replace("， ", ""))) {
                        endPlace.append(end5);
                    }

                    // 历史数据处理2015-12-03
                    // 始发地
                    if (dto.getS_provinceId2() == null && dto.getS_provinceId3() == null) {
                        if (dto.getS_provinceId() != null && dto.getS_provinceId() > 0)
                            dto.setStartPlace(startPlace.toString().replace("全国", ""));
                        else
                            dto.setStartPlace("全国");
                    } else {
                        dto.setStartPlace(AreaUtil.isAllCity(startPlace.toString()) ? "全国" : startPlace.toString());
                    }

                    // 目的地
                    if (dto.getE_provinceId2() == null && dto.getE_provinceId3() == null) {
                        if (dto.getE_provinceId() != null && dto.getE_provinceId() > 0)
                            dto.setEndPlace(endPlace.toString().replace("全国", ""));
                        else
                            dto.setEndPlace("全国");
                    } else {
                        dto.setEndPlace(AreaUtil.isAllCity(endPlace.toString()) ? "全国" : endPlace.toString());
                    }

                }
            }
            mv.addObject("list", list);
            mv.addObject("isLogin", checkLogin(request));
            putModel("map", map);
        } catch (Exception e) {
            logger.warn("exception is :" + e.getMessage());
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 司机找货
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "queryGoodsList", "queryGoodsList/query/{page}" })
    public ModelAndView queryGoodsList(HttpServletRequest request, PageDTO<String> pageDTO) {
        ModelAndView mv = new ModelAndView("transportation/goodsList");
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            putAreaModel(request, map);
            // map.put("userType", 2);
            // 记录数
            int count = transportationService.getGoodsTotal(map);
            map.put("total", count);
            // 只查询企业发布信息
            // 设定分页,排序
            setCommParameters(request, map, pageDTO, count);

            List<MemberAddressDTO> list = transportationService.getGoodsListCompanyMobile(map);
            convertPageList(list);
            mv.addObject("goodsList", list);
            mv.addObject("isLogin", checkLogin(request));
            mv.addObject("map", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    private void convertPageList(List<MemberAddressDTO> list) throws Exception {
        if (list != null && list.size() > 0) {
            for (MemberAddressDTO dto : list) {

                AreaDTO province = transportationService.getArea(String.valueOf(dto.getS_provinceId()));
                dto.setS_provinceName(getS_provinceName(String.valueOf(dto.getS_provinceId()), province));
                AreaDTO city = transportationService.getArea(String.valueOf(dto.getS_cityId()));
                // 如果二级目录名称为市辖区或县，不展示
                dto.setS_cityName(AreaUtil.isCity(city) ? city.getArea() : "");
                AreaDTO area = transportationService.getArea(String.valueOf(dto.getS_areaId()));
                dto.setS_areaName(area != null ? area.getArea() : "");
                AreaDTO f_province = transportationService.getArea(String.valueOf(dto.getF_provinceId()));
                dto.setF_provinceName(f_province != null ? f_province.getArea() : "全国");
                AreaDTO f_city = transportationService.getArea(String.valueOf(dto.getF_cityId()));
                dto.setF_cityName(AreaUtil.isCity(f_city) ? f_city.getArea() : "");
                AreaDTO f_area = transportationService.getArea(String.valueOf(dto.getF_areaId()));
                dto.setF_areaName(f_area != null ? f_area.getArea() : "");
                if (dto.getSendGoodsType() == null) {
                    dto.setSendGoodsTypeString("其他");
                } else {
                    switch (dto.getSendGoodsType()) {
                    case 0:
                        dto.setSendGoodsTypeString("零担");
                        break;
                    case 1:
                        dto.setSendGoodsTypeString("整车");
                        break;
                    case 2:
                        dto.setSendGoodsTypeString("其他");
                        break;
                    default:
                        break;
                    }
                }
                if (dto.getGoodsType() == null) {
                    dto.setGoodsTypeString("其他");
                } else {
					switch (dto.getGoodsType()) {
					case 0:
						dto.setGoodsTypeString("普货");
						break;
					case 1:
						dto.setGoodsTypeString("冷藏");
						break;
					case 2:
						dto.setGoodsTypeString("鲜活水产");
						break;
					case 3:
						dto.setGoodsTypeString("其他");
						break;
					case 4:
						dto.setGoodsTypeString("重货");
						break;
					case 5:
						dto.setGoodsTypeString("抛货");
						break;
					case 6:
						dto.setGoodsTypeString("蔬菜");
						break;
					case 7:
						dto.setGoodsTypeString("水果");
						break;
					case 8:
						dto.setGoodsTypeString("农副产品");
						break;
					case 9:
						dto.setGoodsTypeString("日用品");
						break;
					case 10:
						dto.setGoodsTypeString("纺织");
						break;
					case 11:
						dto.setGoodsTypeString("木材");
						break;
					default:
						break;
					}
				}
                if (dto.getSendDateType() == null) {
                    dto.setSendDateTypeString("不限");
                } else {
                    switch (dto.getSendDateType()) {
                    case 0:
                        dto.setSendDateTypeString("上午");
                        break;
                    case 1:
                        dto.setSendDateTypeString("中午");
                        break;
                    case 2:
                        dto.setSendDateTypeString("下午");
                        break;
                    case 3:
                        dto.setSendDateTypeString("晚上");
                        break;
                    default:
                        break;
                    }
                }

                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(2);
                if (null != dto.getPrice() && dto.getPrice() > 0 && null != dto.getPriceUnitType()) {
                    switch (dto.getPriceUnitType()) {
                    case 0:
                        dto.setPriceUnitTypeString(nf.format(dto.getPrice()) + "元/吨");
                        break;
                    case 1:
                        dto.setPriceUnitTypeString(nf.format(dto.getPrice()) + "元/公斤");
                        break;
                    case 2:
                        dto.setPriceUnitTypeString(nf.format(dto.getPrice()) + "元/立方");
                        break;
                    case 3:
                        dto.setPriceUnitTypeString(nf.format(dto.getPrice()) + "元");
                        break;
                    default:
                        break;
                    }
                    if ("1".equals(String.valueOf(dto.getSendGoodsType()))) {
                        dto.setPriceUnitTypeString(nf.format(dto.getPrice()) + "元");
                    }
                }

                else if (null != dto.getPrice() && dto.getPrice() > 0) {
                    if (null == dto.getPriceUnitType()) {
                        dto.setPriceUnitTypeString(nf.format(dto.getPrice()) + "元");
                    } else if (0 == dto.getPriceUnitType()) {
                        dto.setPriceUnitTypeString(nf.format(dto.getPrice()) + "元");
                    }
                } else {
                    dto.setPriceString("面议");
                }

                dto.setPriceString(dto.getPrice() != null ? String.valueOf(dto.getPrice()) : "面议");
                dto.setSendDateString(DateUtil.toString(dto.getSendDate(), DateUtil.DATE_FORMAT_DATEONLY));
                dto.setCreateTimeString(DateUtil.toString(dto.getCreateTime(), DateUtil.DATE_FORMAT_DATEONLY));
            }
        }
    }

    /**
     * 根据总记录计算出 分页条件起始页 记录总页数
     * 
     * @param request
     * @param map
     */
    private void setCommParameters(HttpServletRequest request, Map<String, Object> map, PageDTO<String> pageDTO, int count) {

        // 排序字段名称。
        String sort = StringUtils.trimToNull(request.getParameter("sort"));
        // 排序顺序
        String sortOrder = StringUtils.trimToNull(request.getParameter("order"));
        // 当前第几页
        String page = pageDTO.getPage().toString();
        // 每页显示的记录数
        String rows = request.getParameter("rows");
        // 当前页
        int currentPage = Integer.parseInt((StringUtils.isEmpty(page) || page == "0") ? "1" : page);
        // 每页显示条数
        int pageSize = Integer.parseInt((StringUtils.isEmpty(rows) || rows == "0") ? "10" : rows);
        // 每页的开始记录 第一页为1 第二页为number +1
        int startRow = (currentPage - 1) * pageSize;
        request.setAttribute("currPage", currentPage);
        request.setAttribute("totalPage", count % 10 == 0 ? count / 10 : count / 10 + 1);
        map.put("startRow", startRow);
        map.put("endRow", pageSize);
        map.put("sortName", sort);
        map.put("sortOrder", sortOrder);
    }

    /**
     * @Description province 方法开发完成 物流服务路线查询填充省数据。
     * @return
     * @CreationDate 2015年11月2日 上午10:15:52
     * @Author lidong(dli@cnagri-products.com)
     * @UpdateDate 2015年11月18日 下午5:58:28
     * @Modify
     */
    @RequestMapping(value = "province", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String province() {
        ProvinceDTO provinceDTO = new ProvinceDTO();
        List<ProvinceDTO> provinces = new ArrayList<>();
        int index = 0;
        try {
            List<AreaDTO> provinceList = queryAreaService.getAreaParentTree();
            if (provinceList != null && provinceList.size() > 0) {
                for (int i = 0; i < provinceList.size(); i++) {
                    AreaDTO areaDTO = provinceList.get(i);
                    ProvinceDTO baseProvince = new ProvinceDTO();
                    baseProvince.setId(areaDTO.getAreaID());
                    baseProvince.setProvinceName(areaDTO.getArea());
                    /**
                     * 如果该省份在热门省份中，则进行靠前排序
                     */
                    if (DistrctConfig.hotProvince.containsKey(areaDTO.getAreaID())) {
                        baseProvince.setIndexId("" + DistrctConfig.hotProvince.get(areaDTO.getAreaID()));
                        provinces.add(DistrctConfig.hotProvince.get(areaDTO.getAreaID()), baseProvince);
                    } else {
                        baseProvince.setIndexId(String.valueOf(index));
                        provinces.add(index, baseProvince);
                    }
                    index++;
                }
            }
            provinceDTO.setProvinces(provinces);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(provinceDTO, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * @Description city 方法开发完成 物流服务路线查询填充市数据。
     * @return
     * @CreationDate 2015年11月2日 上午10:16:19
     * @Author lidong(dli@cnagri-products.com)
     */
    @RequestMapping(value = "city", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String city() {
        CityDTO cityDTO = new CityDTO();
        List<CityDTO> cities = new ArrayList<>();
        int index = 0;
        try {
            List<AreaDTO> districtList = queryAreaService.getCityTree();
            if (districtList != null && districtList.size() > 0) {
                for (int i = 0; i < districtList.size(); i++) {
                    AreaDTO areaDTO = districtList.get(i);
                    CityDTO baseCity = new CityDTO();
                    baseCity.setId(areaDTO.getAreaID());
                    baseCity.setName(areaDTO.getArea());
                    baseCity.setCityPinyin(areaDTO.getArea());
                    baseCity.setCityShortPY(areaDTO.getArea());
                    baseCity.setProvinceId(areaDTO.getFather());
                    baseCity.setLastModifyTime(null);
                    /**
                     * 如果该省份在热门城市中，则进行标记并靠前排序
                     */
                    if (DistrctConfig.hotCity.containsKey(areaDTO.getAreaID())) {
                        baseCity.setHotCity(true);
                        cities.add(DistrctConfig.hotCity.get(areaDTO.getAreaID()), baseCity);
                    } else {
                        cities.add(index, baseCity);
                    }
                    index++;
                }
            }
            cityDTO.setCities(cities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(cityDTO, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * @Description area 物流服务路线查询填充县区数据。
     * @return
     * @CreationDate 2015年11月19日 上午11:02:09
     * @Author lidong(dli@cnagri-products.com)
     */
    @RequestMapping(value = "area", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String area() {
        AraeDTO araeDTO = new AraeDTO();
        List<AraeDTO> areas = new ArrayList<>();
        try {
            List<AreaDTO> districtList = queryAreaService.getAreaTree();
            if (districtList != null && districtList.size() > 0) {
                for (int i = 0; i < districtList.size(); i++) {
                    AreaDTO areaDTO = districtList.get(i);
                    AraeDTO baseArae = new AraeDTO();
                    baseArae.setId(areaDTO.getAreaID());
                    baseArae.setCityId(areaDTO.getFather());
                    baseArae.setCityName(areaDTO.getParentName());
                    baseArae.setAreaName(areaDTO.getArea());
                    baseArae.setProvinceId(areaDTO.getpParentId());
                    baseArae.setPinYin(areaDTO.getArea());
                    baseArae.setPinYinChar(areaDTO.getArea());
                    areas.add(baseArae);
                }
            }
            araeDTO.setAreas(areas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(araeDTO, SerializerFeature.WriteDateUseDateFormat);
    }

    private List<CarLineDTO> showCarLineList(String areaId) {
        List<CarLineDTO> list = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("s_cityId", areaId);
            map.put("e_cityId", areaId);

            // 设定分页,排序
            map.put("startRow", 0);
            map.put("endRow", 100);
            // 排序条件
            list = transportationService.getListByAreaId(map);
            if (list != null && list.size() > 0) {
                for (CarLineDTO dto : list) {
                    if (StringUtils.isEmpty(dto.getSendGoodsType())) {
                        dto.setSendGoodsTypeString("不限");
                    } else {
                        switch (dto.getSendGoodsType()) {
                        case "0":
                            dto.setSendGoodsTypeString("零担");
                            break;
                        case "1":
                            dto.setSendGoodsTypeString("整车");
                            break;
                        case "2":
                            dto.setSendGoodsTypeString("不限");
                            break;
                        default:
                            break;
                        }
                    }
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(2);
                    if (dto.getPrice() != null && dto.getPrice() > 0 && dto.getUnitType() != null) {
                        switch (dto.getUnitType()) {
                        case 0:
                            dto.setUnitTypeString(nf.format(dto.getPrice()) + "元/吨");
                            break;
                        case 1:
                            dto.setUnitTypeString(nf.format(dto.getPrice()) + "元/公斤");
                            break;
                        case 2:
                            dto.setUnitTypeString(nf.format(dto.getPrice()) + "元/立方");
                            break;
                        case 3:
                            dto.setUnitTypeString(nf.format(dto.getPrice()) + "元");
                            break;
                        default:
                            break;
                        }
                        if ("1".equals(String.valueOf(dto.getSendGoodsType()))) {
                            dto.setUnitTypeString(nf.format(dto.getPrice()) + "元");
                        }
                    } else if (dto.getPrice() != null && dto.getPrice() > 0 && dto.getUnitType() == null) {
                        dto.setUnitTypeString(nf.format(dto.getPrice()) + "元");
                    } else {
                        dto.setUnitTypeString("面议");
                    }

                    dto.setSendDateString(DateUtil.toString(dto.getSentDate(), DateUtil.DATE_FORMAT_DATEONLY));
                    dto.setEndDateString(DateUtil.toString(dto.getEndDate(), DateUtil.DATE_FORMAT_DATEONLY));
                    dto.setCreateTimeString(DateUtil.getTimeBetween(dto.getCreateTime()));

                    // 420100武汉 ，450900玉林
                    if ("420100".equals(areaId))
                        dto.setmCity("武汉");
                    else if ("450900".equals(areaId))
                        dto.setmCity("玉林");
                    else
                        dto.setmCity("--");
                    dto.setIsCertify("1".equals(dto.getIsCertify()) ? "已认证" : "未认证");
                    // 3个始发地， 5个目的地
                    StringBuffer startPlace = new StringBuffer();
                    // 始发地1
                    if (dto.getS_provinceId() != null && dto.getS_provinceId() > 1) {
                        AreaDTO city = queryAreaService.getArea(String.valueOf(dto.getS_cityId()));
                        if (dto.getS_cityId() != null && dto.getS_cityId() > 0 && AreaUtil.isCity(city)) {
                            startPlace.append(city != null ? city.getArea() : " ");
                        } else {
                            AreaDTO province = queryAreaService.getArea(String.valueOf(dto.getS_provinceId()));
                            startPlace.append(province != null ? province.getArea() : " ");
                        }
                    } else if (dto.getS_provinceId() != null && dto.getS_provinceId() == 0) {
                        startPlace.append("全国");
                    }

                    // 始发地2
                    String start2 = "";
                    if (dto.getS_provinceId2() != null && dto.getS_provinceId2() > 1) {
                        AreaDTO city = queryAreaService.getArea(String.valueOf(dto.getS_cityId2()));
                        if (dto.getS_cityId2() != null && dto.getS_cityId2() > 0 && AreaUtil.isCity(city)) {
                            start2 = city != null ? "，" + city.getArea() : " ";

                        } else {
                            AreaDTO province = queryAreaService.getArea(String.valueOf(dto.getS_provinceId2()));
                            start2 = province != null ? "，" + province.getArea() : " ";
                        }
                    } else if (dto.getS_provinceId2() != null && dto.getS_provinceId2() == 0) {
                        startPlace.append("全国");
                    }
                    // 去重判断
                    if (!startPlace.toString().contains(start2.replace("，", ""))) {
                        startPlace.append(start2);
                    }

                    // 始发地3
                    String start3 = "";
                    if (dto.getS_provinceId3() != null && dto.getS_provinceId3() > 1) {
                        AreaDTO city = queryAreaService.getArea(String.valueOf(dto.getS_cityId3()));
                        if (dto.getS_cityId3() != null && dto.getS_cityId3() > 0 && AreaUtil.isCity(city)) {
                            start3 = city != null ? "，" + city.getArea() : " ";
                        } else {
                            AreaDTO province = queryAreaService.getArea(String.valueOf(dto.getS_provinceId3()));
                            start3 = province != null ? "，" + province.getArea() : " ";
                        }
                    } else if (dto.getS_provinceId3() != null && dto.getS_provinceId3() == 0) {
                        startPlace.append("全国");
                    }

                    // 去重判断
                    if (!startPlace.toString().contains(start3.replace("，", ""))) {
                        startPlace.append(start3);
                    }

                    // 目的地1
                    StringBuffer endPlace = new StringBuffer();
                    if (dto.getE_provinceId() != null && dto.getE_provinceId() > 1) {
                        AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto.getE_cityId()));
                        if (dto.getE_cityId() != null && dto.getE_cityId() > 0 && AreaUtil.isCity(e_city)) {
                            endPlace.append(e_city != null ? " " + e_city.getArea() : "");
                        } else {
                            AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto.getE_provinceId()));
                            endPlace.append(e_province != null ? e_province.getArea() : "");
                        }
                    } else if (dto.getE_provinceId() != null && dto.getE_provinceId() == 0) {
                        endPlace.append("全国");
                    }

                    // 目的地2
                    String end2 = "";
                    if (dto.getE_provinceId2() != null && dto.getE_provinceId2() > 1) {
                        AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto.getE_cityId2()));
                        if (dto.getE_cityId2() != null && dto.getE_cityId2() > 0 && AreaUtil.isCity(e_city)) {
                            end2 = e_city != null ? "， " + e_city.getArea() : "";
                        } else {
                            AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto.getE_provinceId2()));
                            end2 = e_province != null ? "， " + e_province.getArea() : "";
                        }
                    } else if (dto.getE_provinceId2() != null && dto.getE_provinceId2() == 0) {
                        endPlace.append("全国");
                    }

                    // 去重判断
                    if (!endPlace.toString().contains(end2.replace("， ", ""))) {
                        endPlace.append(end2);
                    }

                    String end3 = "";
                    // 目的地3
                    if (dto.getE_provinceId3() != null && dto.getE_provinceId3() > 1) {
                        AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto.getE_cityId3()));
                        if (dto.getE_cityId3() != null && dto.getE_cityId3() > 0 && AreaUtil.isCity(e_city)) {
                            end3 = e_city != null ? "， " + e_city.getArea() : "";
                            // endPlace.append(e_city != null ? "， "+e_city.getArea() : "");
                        } else {
                            AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto.getE_provinceId3()));
                            end3 = e_province != null ? "， " + e_province.getArea() : "";
                            // endPlace.append(e_province != null ? "， "+e_province.getArea(): "");
                        }
                    } else if (dto.getE_provinceId3() != null && dto.getE_provinceId3() == 0) {
                        endPlace.append("全国");
                    }
                    // 去重判断
                    if (!endPlace.toString().contains(end3.replace("， ", ""))) {
                        endPlace.append(end3);
                    }

                    String end4 = "";
                    // 目的地4
                    if (dto.getE_provinceId4() != null && dto.getE_provinceId4() > 1) {
                        AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto.getE_cityId4()));
                        if (dto.getE_cityId4() != null && dto.getE_cityId4() > 0 && AreaUtil.isCity(e_city)) {
                            end4 = e_city != null ? "， " + e_city.getArea() : "";
                        } else {
                            AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto.getE_provinceId4()));
                            end4 = e_province != null ? "， " + e_province.getArea() : "";
                        }
                    } else if (dto.getE_provinceId4() != null && dto.getE_provinceId4() == 0) {
                        endPlace.append("全国");
                    }

                    // 去重判断
                    if (!endPlace.toString().contains(end4.replace("， ", ""))) {
                        endPlace.append(end4);
                    }

                    String end5 = "";
                    // 目的地5
                    if (dto.getE_provinceId5() != null && dto.getE_provinceId5() > 1) {
                        AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto.getE_cityId5()));
                        if (dto.getE_cityId5() != null && dto.getE_cityId5() > 0 && AreaUtil.isCity(e_city)) {
                            end5 = e_city != null ? "， " + e_city.getArea() : "";
                        } else {
                            AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto.getE_provinceId5()));
                            end5 = e_province != null ? "， " + e_province.getArea() : "";
                        }
                    } else if (dto.getE_provinceId5() != null && dto.getE_provinceId5() == 0) {
                        endPlace.append("全国");
                    }
                    // 去重判断
                    if (!endPlace.toString().contains(end5.replace("， ", ""))) {
                        endPlace.append(end5);
                    }

                    // 始发地
                    if (startPlace.toString().split("，").length > 2) {
                        String startPlaces[] = startPlace.toString().split("，");
                        startPlace = new StringBuffer().append(startPlaces[0]).append(",").append(startPlaces[1]);

                    }
                    dto.setStartPlace(AreaUtil.isAllCity(startPlace.toString()) ? "全国" : startPlace.toString());
                    // 目的地
                    if (endPlace.toString().split("，").length > 2) {
                        String endPlaces[] = endPlace.toString().split("，");
                        endPlace = new StringBuffer().append(endPlaces[0]).append(",").append(endPlaces[1]);
                    }
                    dto.setEndPlace(AreaUtil.isAllCity(endPlace.toString()) ? "全国" : endPlace.toString());

                }
            }
        } catch (Exception e) {
            logger.warn("exception is :" + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    private List<MemberAddressDTO> showGoodsList(String areaId) {
        List<MemberAddressDTO> list = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("s_cityId", areaId);
            // 设定分页,排序
            map.put("startRow", 0);
            map.put("endRow", 100);
            list = transportationService.getGoodsListByAreaId(map);
            convertPageList(list);
            if (list != null && list.size() > 0) {
                for (MemberAddressDTO dto : list) {
                    dto.setCreateTimeString(DateUtil.getTimeBetween(dto.getCreateTime()));
                    dto.setIsCertify("1".equals(dto.getIsCertify()) ? "已认证" : "未认证");
                    // 只显示城市
                    if (dto.getS_provinceId() != null && dto.getS_provinceId() > 1) {
                        AreaDTO city = queryAreaService.getArea(String.valueOf(dto.getS_cityId()));
                        if (dto.getS_cityId() != null && dto.getS_cityId() > 0 && AreaUtil.isCity(city)) {
                            dto.setStartPlace(city != null ? city.getArea() : " ");
                        } else {
                            AreaDTO province = queryAreaService.getArea(String.valueOf(dto.getS_provinceId()));
                            dto.setStartPlace(province != null ? province.getArea() : " ");
                        }
                    } else if (dto.getS_provinceId() != null && dto.getS_provinceId() == 0) {
                        dto.setStartPlace("全国");
                    }

                    if (dto.getF_provinceId() != null && dto.getF_provinceId() > 1) {
                        AreaDTO e_city = queryAreaService.getArea(String.valueOf(dto.getF_cityId()));
                        if (dto.getF_cityId() != null && dto.getF_cityId() > 0 && AreaUtil.isCity(e_city)) {
                            dto.setEndPlace(e_city != null ? " " + e_city.getArea() : "");
                        } else {
                            AreaDTO e_province = queryAreaService.getArea(String.valueOf(dto.getF_provinceId()));
                            dto.setEndPlace(e_province != null ? e_province.getArea() : "");
                        }
                    } else if (dto.getF_provinceId() != null && dto.getF_provinceId() == 0) {
                        dto.setEndPlace("全国");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 货源和车源列表展示
     * 
     * @param request
     * @return
     */
    @RequestMapping("display")
    public ModelAndView display(HttpServletRequest request) {
        // 420100武汉 ，450900玉林
        String areaId = "420100"; // request.getParameter("areaId");
        ModelAndView mv = new ModelAndView("transportation/listInfo");
        try {
            // 货源list
            mv.addObject("goodsList", showGoodsList(areaId));
            // 车主list
            mv.addObject("carLineList", showCarLineList(areaId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    private String getS_provinceName(String s_provinceId, AreaDTO dto) {
        String name = "";
        if (dto != null) {
            name = dto.getArea();
        } else if ("0".equals(s_provinceId)) {
            name = "全国";
        }
        return name;

    }

    /**
     * @Description 设置路线区域查询参数
     * @param request
     * @param map
     * @CreationDate 2016年1月25日 上午9:26:52
     * @Author lidong(dli@gdeng.cn)
     */
    private void putAreaModel(HttpServletRequest request, Map<String, Object> map) {
        // String searchFlag = request.getParameter("searchFlag");
        // if (StringUtils.isNotEmpty(searchFlag)) {
        // 设置查询参数
        map.put("startCity", request.getParameter("startCity")); // 发货城市
        map.put("endCity", request.getParameter("endCity")); // 收货城市
        map.put("s_provinceId", getRequest().getParameter("s_provinceId")); // 发货地省
        String s_provinceName = getRequest().getParameter("s_provinceName"); // 发货地省
        map.put("s_cityId", getRequest().getParameter("s_cityId")); // 发货地市
        String s_cityName = getRequest().getParameter("s_cityName"); // 发货地市
        map.put("s_areaId", getRequest().getParameter("s_areaId")); // 发货地区/县
        String s_areaName = getRequest().getParameter("s_areaName"); // 发货地区/县
        map.put("e_provinceId", getRequest().getParameter("e_provinceId")); // 收货地省
        String e_provinceName = getRequest().getParameter("e_provinceName"); // 收货地省
        map.put("e_cityId", getRequest().getParameter("e_cityId")); // 收货地市
        String e_cityName = getRequest().getParameter("e_cityName"); // 收货地市
        map.put("e_areaId", getRequest().getParameter("e_areaId")); // 收货地区县
        String e_areaName = getRequest().getParameter("e_areaName"); // 收货地区县

        StringBuilder beginPlace = new StringBuilder();
        if (StringUtils.isNotEmpty(s_provinceName)) {
            map.put("s_provinceName", s_provinceName);
            beginPlace.append(s_provinceName + " ");
        }
        if (StringUtils.isNotEmpty(s_areaName)) {
            map.put("s_cityName", s_cityName);
            beginPlace.append(s_cityName + " ");
            map.put("s_areaName", s_areaName);
            beginPlace.append(s_areaName);
        } else {
            map.put("s_cityName", s_cityName);
            beginPlace.append(s_cityName);
        }
        StringBuilder destPlace = new StringBuilder();
        if (StringUtils.isNotEmpty(e_provinceName)) {
            map.put("e_provinceName", e_provinceName);
            destPlace.append(e_provinceName + " ");
        }
        if (StringUtils.isNotEmpty(e_areaName)) {
            map.put("e_cityName", e_cityName);
            destPlace.append(e_cityName + " ");
            map.put("e_areaName", e_areaName);
            destPlace.append(e_areaName);
        } else {
            map.put("e_cityName", e_cityName);
            destPlace.append(e_cityName);
        }
        map.put("beginPlace", beginPlace.toString());
        map.put("destPlace", destPlace.toString());
        // }
    }

}
