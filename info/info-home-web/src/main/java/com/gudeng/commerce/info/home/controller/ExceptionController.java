package com.gudeng.commerce.info.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**   
 * @Description 错误处理页面
 * @Project info-home-web
 * @ClassName ExceptionController.java
 * @Author lidong(dli@gdeng.cn)    
 * @CreationDate 2016年3月11日 下午2:56:09
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
@Controller
@RequestMapping("H5/error")
public class ExceptionController extends AdminBaseController {

    /**
     * @Description 404 500 450
     * @return
     * @CreationDate 2016年3月11日 下午2:56:24
     * @Author lidong(dli@gdeng.cn)
    */
    @RequestMapping("nopage")
    public String noPage() {
        return "404";
    }
}
