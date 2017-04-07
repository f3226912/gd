package com.gudeng.commerce.gd.api.controller.v170209;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.input.NstBaseResponseDTO;
import com.gudeng.commerce.gd.api.dto.input.OrderBatchAddInputDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.enums.NstApiRequestV1Enum;
import com.gudeng.commerce.gd.api.service.ActivityUserintegralToolService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.customer.dto.ActivityUserintegralDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动用户积分
 *
 * @author yanghaoyu
 */
@Controller
@RequestMapping("activityUserintegral")
public class ActivityUserintegralController extends GDAPIBaseController {

	private static final GdLogger logger = GdLoggerFactory.getLogger(ActivityUserintegralController.class);
	@Autowired
	private ActivityUserintegralToolService activityUserintegralService;

	/**
	 * 获取用户积分信息
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("/detail")
	public void payPrePaymenSucc(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			ActivityUserintegralDTO inputDTO = (ActivityUserintegralDTO) GSONUtils.fromJsonToObject(jsonStr, ActivityUserintegralDTO.class);
			Integer memberId = inputDTO.getMemberId();
			if (memberId == null) {
				setEncodeResult(result, ErrorCodeEnum.MEMBER_ADDRESS_ID_IS_NULL, request, response);
				return;
			}
			Map<String, Object> map = new HashMap<>();
			//根据用户id查询
			map.put("memberId", memberId);
			List<ActivityUserintegralDTO> list = activityUserintegralService.getList(map);

			if (list != null && list.size() > 0) {
				result.setObject(list.get(0));
			}
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response, DateUtil.DATE_FORMAT_DATETIME);
		} catch (Exception e) {
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

}

