package com.gudeng.commerce.customer;

import java.io.IOException;

import junit.framework.TestCase;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.info.customer.dto.BaiDuDoLoginResponseDTO;
import com.gudeng.commerce.info.customer.dto.BaiDuDoLogoutResponseDTO;
import com.gudeng.commerce.info.customer.dto.BaiDuQuestionDTO;
import com.gudeng.commerce.info.customer.service.BaiDuApiService;
import com.gudeng.commerce.info.customer.service.impl.BaiDuApiServiceImpl;
import com.gudeng.commerce.info.customer.util.BaiDuApiUtil;
import com.gudeng.commerce.info.customer.util.JacksonUtil;

public class BaiDuApiTest extends TestCase {
	@Autowired
	public BaiDuApiService apiService;
	
	private static final String USER_NAME = BaiDuApiUtil.USER_NAME;
	
	private static final String PASSWORD = BaiDuApiUtil.PASSWORD;
	
	private static final String TOKEN = BaiDuApiUtil.TOKEN;
	
	/*public void testDoLogin() throws JsonGenerationException, JsonMappingException, IOException {
		apiService = new BaiDuApiServiceImpl();
		BaiDuDoLoginResponseDTO resposeDTO = apiService.doLogin(USER_NAME,PASSWORD,TOKEN);
		String json = JacksonUtil.obj2Str(resposeDTO);
        System.out.println("结果："+json);
	}*/
	
	/*public void testDoLogOut() throws JsonGenerationException, JsonMappingException, IOException{
	    apiService = new BaiDuApiServiceImpl();
	    BaiDuDoLoginResponseDTO loginResposeDTO = apiService.doLogin(USER_NAME,PASSWORD,TOKEN);
	    long ucid = loginResposeDTO.getUcid();
	    String st = loginResposeDTO.getSt();
	    BaiDuDoLogoutResponseDTO logoutResponseDTO = apiService.doLogout(USER_NAME, TOKEN, ucid, st);
	    String json = JacksonUtil.obj2Str(logoutResponseDTO);
        System.out.println("结果："+json);
	}*/
	
	/*public void testGetSites(){
	    apiService = new BaiDuApiServiceImpl();
	    BaiDuDoLoginResponseDTO resposeDTO = apiService.doLogin(USER_NAME,PASSWORD,TOKEN);
	    long userId = resposeDTO.getUcid();
	    String sessionId = resposeDTO.getSt();
        String json = apiService.getSites(USER_NAME, TOKEN, userId, sessionId);
	    System.out.println(json);
	}*/
}
