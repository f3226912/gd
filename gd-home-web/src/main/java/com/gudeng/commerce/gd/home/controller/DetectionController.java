package com.gudeng.commerce.gd.home.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.home.dto.PageDTO;
import com.gudeng.commerce.gd.home.service.DetectionToolService;
import com.gudeng.commerce.gd.home.util.PageUtil;
import com.gudeng.commerce.gd.supplier.dto.DetectionDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("test")
public class DetectionController extends HomeBaseController {
    /** 记录日志 */
    private static final GdLogger logger = GdLoggerFactory.getLogger(DetectionController.class);

    @Autowired
    public DetectionToolService detectionToolService;

    /**
     * 列表
     * 
     * @return
     */
    @RequestMapping(value = { "index", "{maketId}/index", "index/page/{page}" })
    public String list(DetectionDTO detectionDTO, PageDTO<String> pageDTO) {
        try {
            PageDTO<DetectionDTO> pageDto = PageUtil.create(DetectionDTO.class, 10);
            // 查询条件
            Map<String, Object> query = new HashMap<String, Object>();
            String marketId = null;
            if (detectionDTO != null) {
                if (detectionDTO.getMaketId() != null) {
                    marketId = detectionDTO.getMaketId().toString();
                } else {
                    marketId = getCookieByMarketId(getRequest());// 市场ID
                }
                putModel("marketId", marketId);
                query.put("maketId", marketId);
            }
            // 获取总数 通过query提供的参数 此处有service层提供方法
            int size = detectionToolService.getTotal(query);
            // 设置总数据
            pageDto.setTotalSize(size);
            // 设置查询分页基本参数 查询前初始化数据
            if (pageDTO != null && pageDTO.getCurrentPage() != null && pageDTO.getCurrentPage() > 0) {
                pageDto.setCurrentPage(pageDTO.getPage());
            }
            setCommParameters(pageDto, query);
            // 获取数据库数据 并设置到pageDTO中
            pageDto.setPageData(detectionToolService.getDetectionList(query));

            // 数据添加到model 前台准备显示
            putPagedata(pageDto);

        } catch (Exception e) {
            logger.trace(e.getMessage());
        }
        return "detect/detectList";
    }

    /**
     * phone
     * 
     * @return
     */
    @RequestMapping("phone")
    public String phone() {

        return "detect/detectPhone";
    }

}
