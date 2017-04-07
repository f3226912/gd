package com.gudeng.commerce.info.customer.service.impl;

import com.gudeng.commerce.info.customer.dto.BaiDuAuthHeaderDTO;
import com.gudeng.commerce.info.customer.dto.BaiDuDoLoginDTO;
import com.gudeng.commerce.info.customer.dto.BaiDuDoLoginResponseDTO;
import com.gudeng.commerce.info.customer.dto.BaiDuDoLogoutDTO;
import com.gudeng.commerce.info.customer.dto.BaiDuDoLogoutResponseDTO;
import com.gudeng.commerce.info.customer.dto.BaiDuProfileApiRequestDTO;
import com.gudeng.commerce.info.customer.dto.BaiDuProfileRequestDTO;
import com.gudeng.commerce.info.customer.service.BaiDuApiService;
import com.gudeng.commerce.info.customer.util.BaiDuApiUtil;
import com.gudeng.commerce.info.customer.util.LoginConnection;

/**
 * 百度API服务调用
 * @author yangjj
 */
public class BaiDuApiServiceImpl implements BaiDuApiService {

    /**
     * 登录
     * @author yangjj
     */
    @Override
    public BaiDuDoLoginResponseDTO doLogin(String userName, String password, String token) {
        String methodName = BaiDuApiUtil.ApiFunctionNameEnum.DOLOGIN.getValue();
        String url = BaiDuApiUtil.LOGIN_API_URL;
        String uuid = BaiDuApiUtil.getLocalMac();
        String accountType = BaiDuApiUtil.AccountTypeEnum.FENGCHAO.getValue();
        LoginConnection connection = BaiDuApiUtil.makeConnection(url);
        BaiDuDoLoginDTO loginDTO = new BaiDuDoLoginDTO(userName, token, methodName, uuid);
        loginDTO.setPassword(password);
        connection.sendRequest(loginDTO, uuid, accountType);
        BaiDuDoLoginResponseDTO responseDTO = connection.readSecClearResponse(BaiDuDoLoginResponseDTO.class);
        return responseDTO;
    }
    
    /**
     * 登出
     * @author yangjj
     */
    @Override
    public BaiDuDoLogoutResponseDTO doLogout(String userName,String token,long userId,String sessionId) {
        String methodName = BaiDuApiUtil.ApiFunctionNameEnum.DOLOGOUT.getValue();
        String url = BaiDuApiUtil.LOGIN_API_URL;
        String uuid = BaiDuApiUtil.getLocalMac();
        String accountType = BaiDuApiUtil.AccountTypeEnum.FENGCHAO.getValue();
        LoginConnection connection = BaiDuApiUtil.makeConnection(url);
        BaiDuDoLogoutDTO outDTO = new BaiDuDoLogoutDTO(userName, token, methodName, uuid);
        outDTO.setUcid(userId);
        outDTO.setSt(sessionId);
        connection.sendRequest(outDTO, uuid, accountType);
        BaiDuDoLogoutResponseDTO responseDTO = connection.readSecClearResponse(BaiDuDoLogoutResponseDTO.class);
        return responseDTO;
    }

    /**
     * 返回账号下管理的站点、子目录信息
     * @author yangjj
     */
    @Override
    public String getSites(String userName, String token,long userId,String sessionId) {
        String methodName = BaiDuApiUtil.ApiFunctionNameEnum.SITES.getValue();
        String url = BaiDuApiUtil.PRODUCT_API_URL;
        String accountType = BaiDuApiUtil.AccountTypeEnum.FENGCHAO.getValue();
        String serverName = BaiDuApiUtil.ApiServerEnum.PROFILE.getValue();
        String uuid = BaiDuApiUtil.getLocalMac();
        BaiDuAuthHeaderDTO headerDTO = new BaiDuAuthHeaderDTO(userName, sessionId, token, Integer.valueOf(accountType), null, null);
        BaiDuProfileApiRequestDTO bodyDTO = new BaiDuProfileApiRequestDTO(serverName, methodName);
        BaiDuProfileRequestDTO requestDTO = new BaiDuProfileRequestDTO(headerDTO, bodyDTO);
        String json = BaiDuApiUtil.sendPostData(url, requestDTO, uuid, userId, null);
        return json;
    }
}
