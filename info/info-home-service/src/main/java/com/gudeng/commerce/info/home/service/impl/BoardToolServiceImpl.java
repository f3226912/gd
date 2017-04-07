package com.gudeng.commerce.info.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.customer.dto.BoardDTO;
import com.gudeng.commerce.info.customer.service.BoardService;
import com.gudeng.commerce.info.home.service.BoardToolService;
import com.gudeng.commerce.info.home.util.GdProperties;

public class BoardToolServiceImpl implements BoardToolService {

    @Autowired
    private GdProperties gdProperties;

    private static BoardService boardService;

    protected BoardService getBoardHessianService() throws MalformedURLException {
        if (boardService == null) {
            String hessianUrl = gdProperties.getBoardServiceUrl();
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            boardService = (BoardService) factory.create(BoardService.class, hessianUrl);
        }
        return boardService;
    }
    /**
     * @Description 根据用户ID查出该用户所拥有的所有看板权限
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年3月7日 下午3:17:50
     * @Author lidong(dli@gdeng.cn)
     */
    @Override
    public List<BoardDTO> getListByUserId(Map<String, Object> map) throws Exception {
        return getBoardHessianService().getListByUserId(map);
    }
    /**
     * @Description 根据用户ID查找用户有效综合看板数据
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年3月20日 上午10:22:10
     * @Author lidong(dli@gdeng.cn)
    */
    @Override
    public List<BoardDTO> getCommonListByUserId(Map<String, Object> map) throws Exception {
        return getBoardHessianService().getCommonListByUserId(map);
    }

}
