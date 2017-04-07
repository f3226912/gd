package com.gudeng.commerce.gd.Interceptor;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.MemberToolService;
import com.gudeng.commerce.gd.api.util.Des3Response;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.StringUtils;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;

/**
 * 
 * @author TerryZhang
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	@Autowired
	public MemberToolService memberToolService;
	
	private List<String> excludedUrls;

    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }
    
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object arg2) throws Exception {
		String requestUri = req.getServletPath();
		logger.info("[INFO]Request uri is: " + requestUri);
		for (String url : excludedUrls) {
            if (requestUri.endsWith(url)) {
                return true;
            }
        }
		
		// 从session 里面获取用户名的信息
//		HttpSession session = req.getSession();
//		String mobile = (String) session.getAttribute("mobile");
//		if (StringUtils.isNotBlank(mobile)) {
//			logger.info("[INFO]Mobile is: " + mobile);
//			MemberBaseinfoDTO memberDTO = memberToolService.getByMobile(mobile);
//			if(memberDTO != null){
//				// 判断用户是否禁用
//				String status = memberDTO.getStatus();
//				if(!"1".equals(status)){
//					ObjectResult result = new ObjectResult();
//					try {
//						result.setStatusCode(ErrorCodeEnum.ACCOUNT_IS_DISABLE.getValue());
//						result.setMsg(ErrorCodeEnum.ACCOUNT_IS_DISABLE.getMsg());
//						String json = JSONUtils.toJSONStringWithDateFormat(result,"yyyy-MM-dd");
//						String jsoncallback = req.getParameter("jsoncallback");
//						if(StringUtils.trimToNull(jsoncallback)!=null){
//							json = jsoncallback+"("+json+")";
//						}	
//						
//						res.setCharacterEncoding("UTF-8");
//						res.setContentType("text/html; charset=utf-8");
//						res.getWriter().write(Des3Response.encode(json));
//					} catch (IOException e) {
//						logger.error("[ERROR]PreHandle() throws exception: " + e.getMessage() ,e);
//					}
//					
//					return false;
//				}
//			}
//		}
		return true;
	}

	@SuppressWarnings("unused")
	private static void toWriterHtml(String url,HttpServletResponse response) throws Exception{
    	StringBuilder jsBuilder = new StringBuilder();
		jsBuilder.append("<html>");
		jsBuilder.append("<head>");
		jsBuilder
				.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>");
		jsBuilder.append("</head>");
		jsBuilder.append("<body>");
		jsBuilder.append("<script>");

		// 用于处理模式对话框（js,jsp）
		jsBuilder.append("if(undefined==window.dialogArguments)");
		jsBuilder.append("window.top.location.href='" + url + "';");

		jsBuilder.append("else{");
		jsBuilder.append("alert('请重新登录!');");

		jsBuilder.append("window.close();");
		jsBuilder.append("window.returnValue=\"overtime\"");
		jsBuilder.append("}");

		jsBuilder.append("</script>");
		jsBuilder.append("</body>");
		jsBuilder.append("<html>");

		String js = jsBuilder.toString(); 
		
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(js);
    }
	
	public String getMessage(ServletRequest request,String key,Object[] args){
		 WebApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
		 //RequestContextUtils.getLocale((HttpServletRequest) request)
	        return ac.getMessage(key,args, Locale.CHINA);
	}

}
