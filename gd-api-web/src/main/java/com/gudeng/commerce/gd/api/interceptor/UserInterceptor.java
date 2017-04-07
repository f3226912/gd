package com.gudeng.commerce.gd.api.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.service.ditui.GrdMemberToolService;
import com.gudeng.commerce.gd.api.util.Des3Request;
import com.gudeng.commerce.gd.api.util.Des3Response;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.PathUtil;
import com.gudeng.commerce.gd.api.util.StringUtils;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;

@Component
public class UserInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory.getLogger(UserInterceptor.class);

	@Autowired
	public MemberToolService memberToolService;
	
	@Autowired
	public GrdMemberToolService grdMemberToolService;
	
	
	 /** 不用检查的checkUrl */
    private List<String> doNotCheckUrl;

    public List<String> getDoNotCheckUrl() {
        return doNotCheckUrl;
    }

    public void setDoNotCheckUrl(List<String> doNotCheckUrl) {
        this.doNotCheckUrl = doNotCheckUrl;
    }



	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

        String url = PathUtil.urlFormat(request.getRequestURL().toString().replace(PathUtil.getBasePath(request), ""));
        // 如果不要进行检查的url，直接跳过;登录，首页等。。。。
        if (doNotCheckUrl != null) {
            String str = "";
            for (int i = 0; i < doNotCheckUrl.size(); i++) {
                str = doNotCheckUrl.get(i);
                if (url.indexOf(str) >= 0) {
                    return super.preHandle(request, response, handler);
                }
            }
        }
		
        String type=request.getParameter("memberapi_type");
        if(type!=null && type.equals("admin")){
        	//农速通的管理后台调用修改memberapi修改用户信息的时候，应该允许，而不应该过滤，故加一个memberapi_type参数，以区分是后台还是API进行的调用
            return super.preHandle(request, response, handler);
        }
		
		
		//判断未加密的userId是否禁用
		String userId=null ,memberId=null;
		List<String> userIds=StringUtils.getStrings("userid");
		List<String> memberIds=StringUtils.getStrings("memberId");
		for(String id:userIds){
			if(!StringUtils.isEmpty(request.getParameter(id))){
				userId=request.getParameter(id);
				break;
			} 
		}
		if(userId==null){
			for(String id:memberIds){
				if(!StringUtils.isEmpty(request.getParameter(id))){
					memberId=request.getParameter(id);
					break;
				} 
			}
		}
		 // 状态（0未启用，1启用，其他状态待补）
		if(userId==null && memberId==null){
//			return super.preHandle(request, response, handler);
		}else if(userId!=null){
			MemberBaseinfoDTO memberBaseinfoDTO = memberToolService.getById(userId);
			if(memberBaseinfoDTO!=null && memberBaseinfoDTO.getStatus()!=null && memberBaseinfoDTO.getStatus().equals("0")){
				retMessage(request, response,false);
				return false;
			}
		}else if(memberId!=null){
			MemberBaseinfoDTO memberBaseinfoDTO = memberToolService.getById(memberId);
			if(memberBaseinfoDTO!=null && memberBaseinfoDTO.getStatus()!=null && memberBaseinfoDTO.getStatus().equals("0")){
				retMessage(request, response,false);
				return false;
			}
		} 		
		
		

		//判断加密的userId是否禁用
//		userId=null ;
//		memberId=null;
		if(!StringUtils.isEmpty(request.getParameter("param"))){
			String jsonStr=Des3Request.decode(request.getParameter("param"));
			if(jsonStr.startsWith("{")){
				type = GSONUtils.getJsonValueStr(jsonStr, "memberapi_type");
				
				if(type!=null && type.equals("admin")){
		        	//农速通的管理后台调用修改memberapi修改用户信息的时候，应该允许，而不应该过滤，故加一个memberapi_type参数，以区分是后台还是API进行的调用
		            return super.preHandle(request, response, handler);
		        }
				
				for(String id:userIds){
					if(!StringUtils.isEmpty(GSONUtils.getJsonValueStr(jsonStr, id))){
						userId=GSONUtils.getJsonValueStr(jsonStr, id);
						break;
					} 
				}
				if(userId==null){
					for(String id:memberIds){
						if(!StringUtils.isEmpty(GSONUtils.getJsonValueStr(jsonStr, id))){
							memberId=GSONUtils.getJsonValueStr(jsonStr, id);
							break;
						} 
					}
				}
				if(!StringUtils.isEmpty(GSONUtils.getJsonValueStr(jsonStr, "grdUserId"))){
					String grdUserId=GSONUtils.getJsonValueStr(jsonStr, "grdUserId");
					GrdMemberDTO grdMemberDTO = grdMemberToolService.getById(grdUserId);
					if(grdMemberDTO!=null && grdMemberDTO.getStatus()!=null && grdMemberDTO.getStatus().equals("0")){
						retMessage(request, response,true);
						return false;
					}
					if(grdMemberDTO!=null && grdMemberDTO.getNeedLogin()!=null && grdMemberDTO.getNeedLogin()==1){
						reMessage(request, response,true);
						return false;
					}
					/*if(!StringUtils.isEmpty(GSONUtils.getJsonValueStr(jsonStr, "grdMarketId"))){//市场发生变化
						String grdMarketId=GSONUtils.getJsonValueStr(jsonStr, "grdMarketId");
						if(grdMemberDTO!=null && grdMemberDTO.getMarketId()!=null && grdMemberDTO.getMarketId()!=Long.parseLong(grdMarketId)){
							reMessage(request, response,true);
							return false;
						}
					}*/
				}
			}
		}
		
	    // 状态（0未启用，1启用，其他状态待补）
		if(userId==null && memberId==null){
			return super.preHandle(request, response, handler);
		}else if(userId!=null){
			MemberBaseinfoDTO memberBaseinfoDTO = memberToolService.getById(userId);
			if(memberBaseinfoDTO!=null && memberBaseinfoDTO.getStatus()!=null && memberBaseinfoDTO.getStatus().equals("0")){
				retMessage(request, response,true);
				return false;
			}
		}else if(memberId!=null){
			MemberBaseinfoDTO memberBaseinfoDTO = memberToolService.getById(memberId);
			if(memberBaseinfoDTO!=null && memberBaseinfoDTO.getStatus()!=null && memberBaseinfoDTO.getStatus().equals("0")){
				retMessage(request, response,true);
				return false;
			}
		} 
		return super.preHandle(request, response, handler);
	}
	

	private void retMessage(HttpServletRequest request, HttpServletResponse response,boolean isEncode) throws Exception {
		ObjectResult result = new ObjectResult();
		result.setStatusCode(ErrorCodeEnum.ACCOUNT_IS_DISABLE.getStatusCode());
		result.setMsg("您的账号已被禁用, 如有问题, 请联系客服");
		renderJson(result, request, response,isEncode);
		return;
	}
	private void reMessage(HttpServletRequest request, HttpServletResponse response,boolean isEncode) throws Exception {
		ObjectResult result = new ObjectResult();
		result.setStatusCode(ErrorCodeEnum.ACCOUNT_MARKET_MODIFY.getStatusCode());
//		result.setMsg("您的账号所属市场发生变化，请重新登录");
		result.setMsg("您的账号信息发生变化，请重新登录");
		renderJson(result, request, response,isEncode);
		return;
	}
	
	protected void renderJson(Object result, HttpServletRequest request,HttpServletResponse response,boolean isEncode) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		try {
			String json=JSONUtils.toJSONStringWithDateFormat(result,"yyyy-MM-dd");
			
			String jsoncallback = request.getParameter("jsoncallback");
			if(StringUtils.trimToNull(jsoncallback)!=null){
				json = jsoncallback+"("+json+")";
			}
			
			if(isEncode){
				response.getWriter().write(Des3Response.encode(json));
			}else{
				response.getWriter().write(json);
			}
		} catch (Exception e) {
			logger.error("返回Json结果错误 " + e.getMessage() ,e);
		}
	}
	
	
}
