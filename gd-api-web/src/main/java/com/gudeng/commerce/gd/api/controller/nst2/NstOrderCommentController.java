package com.gudeng.commerce.gd.api.controller.nst2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.NstOrderBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.NstOrderCommentToolService;
import com.gudeng.commerce.gd.api.service.impl.MemberToolServiceImple;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.NstOrderBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.NstOrderCommentDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderCommentEntity;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("nst2/orderComment")
public class NstOrderCommentController extends GDAPIBaseController{
	
	private static final GdLogger logger = GdLoggerFactory.getLogger(NstOrderCommentController.class);
	
	@Autowired
	private NstOrderBaseinfoToolService nstOrderBaseinfoToolService;
	
	@Autowired
	private NstOrderCommentToolService nstOrderCommentToolService;
	
	@Autowired
	private MemberToolServiceImple memberToolService;
	
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, NstOrderCommentEntity nstOrderCommentEntity){
		ObjectResult result = new ObjectResult();
		try {
			String orderNo = nstOrderCommentEntity.getOrderNo();
			if(StringUtils.isBlank(orderNo)){
				setResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
				return;
			}
			
			Integer type = nstOrderCommentEntity.getType();
			if(type == null){
				setResult(result, ErrorCodeEnum.TYPE_IS_EMPTY, request, response);
				return;
			}
			
			NstOrderBaseinfoDTO nstOrderBaseinfoDTO = nstOrderBaseinfoToolService.getByOrderNo(orderNo);
			if(nstOrderBaseinfoDTO == null){
				setResult(result, ErrorCodeEnum.ORDER_NOT_EXISTED, request, response);
				return;	
			}
			
			Long memberId = null;
			switch (type) {
			case 1:
				memberId = nstOrderBaseinfoDTO.getShipperId();
				break;
				
			case 2:
				memberId = nstOrderBaseinfoDTO.getOwnerMemberId();
				break;

			default:
				break;
			}
			nstOrderCommentEntity.setMemberId(memberId);
			nstOrderCommentEntity.setUpdateUserId(nstOrderCommentEntity.getCreateUserId());
			nstOrderCommentToolService.insert(nstOrderCommentEntity);
			
			// 统计平均评价星级，并修改member_baseinfo中nstEvaluate字段
			Integer avg = nstOrderCommentToolService.getAvgByMemberId(memberId);
			memberToolService.updateNstEvaluate(memberId, avg);
			
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("[Error]订单评论异常：" + e.getMessage());
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}
	
	/**
	 * 获取用户评论信息列表（用户评论信息+用户被评论信息）分页
	 * @param request
	 * @param response
	 * @param userId
	 */
	@RequestMapping("getUserComment")
	public void getUserComment(HttpServletRequest request, HttpServletResponse response, String userId){
		ObjectResult result = new ObjectResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
		
//			int total = nstOrderCommentToolService.getUserCommentCount(map);
//			CommonPageDTO pageDTO = getPageInfo(request, map);
			List<NstOrderCommentDTO> list = nstOrderCommentToolService.getUserCommentPage(map);
			
//			pageDTO.setRecordCount(total);
//			pageDTO.initiatePage(total);
//			pageDTO.setRecordList(list);
//			result.setObject(pageDTO);
			result.setObject(list);
			result.setStatusCode(ErrorCodeEnum.SUCCESS.getStatusCode());
			result.setMsg("success");
		} catch (Exception e) {
			logger.info("[Error]获取评论异常：" + e.getMessage());
			result.setStatusCode(ErrorCodeEnum.FAIL.getStatusCode());
			result.setMsg(ErrorCodeEnum.FAIL.getStatusMsg());
		}
		renderJson(result, request, response);
	}
}
