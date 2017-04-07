package com.gudeng.commerce.gd.home.util;

import java.text.DecimalFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.entity.SystemCode;
import com.gudeng.commerce.gd.home.service.QueryAreaToolService;
import com.gudeng.commerce.gd.home.service.SystemCodeService;

/**
 * @Description 数据字典标签库工具类
 * @Project gd-home-web
 * @ClassName DictDataUtil.java
 * @Author lidong(dli@cnagri-products.com)
 * @CreationDate 2015年10月22日 下午6:04:37
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
@Service
public class DictDataUtil {
    @Autowired
    public SystemCodeService systemCodeService;
    @Autowired
    public QueryAreaToolService queryAreaToolService;

    private static DictDataUtil util;

    /**
     * @Fields systemCodeList 用于存放数据字典
     * @since Ver 1.0
     */
    private static CopyOnWriteArrayList<SystemCode> systemCodeList = new CopyOnWriteArrayList<>();

    /**
     * @Fields areas 用于存放行政区域代码及名称
     * @since Ver 1.0
     */
    private static ConcurrentHashMap<String, String> areas = new ConcurrentHashMap<>();

    public DictDataUtil() {

    }

    @PostConstruct
    public void init() {
        util = this;
        util.systemCodeService = this.systemCodeService;
        util.systemCodeList = this.systemCodeList;
        util.areas = this.areas;
    }

    /**
     * @Description showValueByCode 先从静态集合中获取数据字典值，若不存在则从数据库中获取
     * @param codeType
     * @param codeKey
     * @return
     * @CreationDate 2015年10月22日 下午2:50:17
     * @Author lidong(dli@cnagri-products.com)
     */
    public static String showValueByCode(String codeType, String codeKey) {
        String value = null;
        // 从集合中查找
        value = getValueFromList(codeType, codeKey);
        if (value == null) {
            // 从数据库中查找
            try {
                value = util.systemCodeService.showValueByCode(codeType, codeKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (value != null) {
                SystemCode systemCode = new SystemCode();
                systemCode.setCodeKey(codeKey);
                systemCode.setCodeValue(value);
                systemCode.setType(codeType);
                // 加入集合
                util.systemCodeList.add(systemCode);
            }
        }
        return value;
    }

    /**
     * @Description showDistrictNamebyCode 根据行政区划代码获取行政区划名称
     * @param areaID
     * @return
     * @CreationDate 2015年10月27日 下午5:21:00
     * @Author lidong(dli@cnagri-products.com)
     */
    public static String showDistrictNamebyCode(String areaID) {
        if (areaID == null) {
            return null;
        }
        String value = null;
        // 先从集合中取值
        for (String key : util.areas.keySet()) {
            if (areaID.equals(key)) {
                value = util.areas.get(key);
                break;
            }
        }
        // 若集合中未取到值，则从数据库中查找值，并加入集合中
        if (value == null) {
            try {
                value = util.queryAreaToolService.getArea(areaID).getArea();// 数据库取值
                if (value != null) {
                    util.areas.put(areaID, value);// 加入集合
                }
            } catch (Exception e) {

            }
        }
        return value;
    }

    /**
     * @Description getValueFromList 从静态集合中获取数据字典
     * @param codeType
     * @param codeKey
     * @return
     * @CreationDate 2015年10月23日 下午5:05:40
     * @Author lidong(dli@cnagri-products.com)
     */
    private static String getValueFromList(String codeType, String codeKey) {
        String value = null;
        for (SystemCode systemCode : util.systemCodeList) {
            if (codeType != null && codeType.equals(systemCode.getType())) {
                if (codeKey.equals(systemCode.getCodeKey())) {
                    value = systemCode.getCodeValue();
                }
            }
        }
        return value;
    }

    /**
     * @Description showImgBySize 根据原始图片地址以及需要的图片尺寸获取相应尺寸图片的存放地址，注：该地址不包含服务器IP等，数据形式表现如：2015/04/14/鸽子120_120.jpg，在页面显示需要加上图片服务器地址
     * @param imgPath
     *            原始图片地址
     * @param width
     *            需要显示的图片尺寸
     * @return 相应图片的地址(注：该地址不包含服务器IP等，数据形式表现如：2015/04/14/鸽子120_120.jpg)
     * @CreationDate 2015年10月26日 上午11:06:47
     * @Author lidong(dli@cnagri-products.com)
     */
    public static String showImgBySize(String imgPath, Integer width) {
        if (StringUtils.isEmpty(imgPath)) {
            return null;
        }
        int index = imgPath.lastIndexOf(".");
        String fileExt = imgPath.substring(index);
        String newImgPath = imgPath.substring(0, index) + width + "_" + width + fileExt;
        return newImgPath;
    }

    /**
     * @Description formatNumber 数字格式化，将数值大于10000的值转化为以万为单位，保留两位小数，最后以为小数四舍五入，123456.56=12.35万
     * @param price
     * @return
     * @CreationDate 2015年11月3日 下午12:20:44
     * @Author lidong(dli@cnagri-products.com)
     */
    public static String formatNumber(Double number) {
        String numberString = null;
        if (number == null || number <= 0) {
            return "面议";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        if (number < 10000) {
            numberString = "" + df.format(number);
        } else if (number < 100000000) {
            number = Math.floor((number / 10000.0 * 100)) / 100.0;
            numberString = df.format(number) + "万";
        } else {
            number = Math.floor((number / 100000000.0 * 100)) / 100.0;
            numberString = df.format(number) + "亿";
        }
        // else if (number < 10000000000L) {
        // number = Math.floor((number / 1000000000.0 * 100)) / 100.0;
        // numberString = df.format(number) + "十亿";
        // }
        return numberString;
    }

    /**
     * @Description formatNumber2 数字格式化，将小数末尾的0去掉，1234.450=1234.45，123.00=123
     * @param number
     * @return
     * @CreationDate 2015年11月3日 下午5:09:23
     * @Author lidong(dli@cnagri-products.com)
     */
    public static String formatNumber2(Double number) {
        String numberString = null;
        if (number != null) {
            char[] chs = number.toString().toCharArray();
            for (int i = chs.length - 1; i >= 0; i--) {
                if (chs[i] == '0') {
                    chs[i] = ' ';
                    continue;
                } else if (chs[i] == '.') {
                    chs[i] = ' ';
                    break;
                } else {
                    break;
                }
            }
            numberString = new String(chs).trim();
        }
        return numberString;
    }

    /**
     * @Description formatMarket 根据marketId获取市场拼音名称，用于url转化
     * @param marketId
     * @return
     * @CreationDate 2015年11月30日 下午12:02:24
     * @Author lidong(dli@cnagri-products.com)
     */
    public static String formatMarket(String marketId) {
        String marketName = null;
        if (StringUtils.isEmpty(marketId)) {
            marketName = "baishazhou";
        } else {
            marketName = showValueByCode("MarketENName", marketId);
            if (StringUtils.isEmpty(marketName)) {
                marketName = "baishazhou";
            }
        }
        return marketName;
    }

    /**
     * @Description formatMarket2 根据marketId获取市场中文名称，
     * @param marketId
     * @return
     * @CreationDate 2015年12月2日 下午8:03:02
     * @Author lidong(dli@gdeng.cn)
     */
    public static String formatMarket2(String marketId) {
        String marketName = null;
        if (StringUtils.isEmpty(marketId)) {
            marketName = "武汉白沙洲批发市场";
        } else {
            marketName = showValueByCode("MarketCNName", marketId);
            if (StringUtils.isEmpty(marketName)) {
                marketName = "武汉白沙洲批发市场";
            }
        }
        return marketName;
    }

    /**
     * @Description 对车辆类型进行格式化
     * @param carTypeKey
     * @return
     * @CreationDate 2016年1月25日 上午9:49:03
     * @Author lidong(dli@gdeng.cn)
     */
    public static String formatCarType(String carTypeKey) {
        String carTypeValue = null;
        if (StringUtils.isEmpty(carTypeKey)) {
            carTypeValue = "其他";
        } else {
            carTypeValue = showValueByCode("CarType", carTypeKey);
            if (StringUtils.isEmpty(carTypeKey)) {
                carTypeValue = "其他";
            }
        }
        return carTypeValue;
    }

    public static void main(String[] args) {
        System.out.println(formatNumber(123.5));
        System.out.println(formatNumber(123446.56));
        System.out.println(formatNumber(123456.56));
        System.out.println(formatNumber(9999999.99999));
        System.out.println(formatNumber(0.5600));
        System.out.println(formatNumber(0.0));
        System.out.println(formatNumber(00000d));
        System.out.println(formatNumber(0.00));
        System.out.println(formatNumber(99999999.99999));
        System.out.println(formatNumber(999999999.99999));
        System.out.println(formatNumber(9999999999.99999));
    }

}