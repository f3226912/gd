package com.gudeng.commerce.info.home.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.info.customer.dto.BaseMenu;
import com.gudeng.commerce.info.customer.dto.BoardDTO;
import com.gudeng.commerce.info.customer.entity.SysUserBoard;
import com.gudeng.commerce.info.customer.util.CommonConstant;
import com.gudeng.commerce.info.customer.util.Constant;
import com.gudeng.commerce.info.home.service.SysUserBoardHomeService;
import com.gudeng.commerce.info.home.util.LoginUserUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("sysUserBoard")
public class SysUserBoardCotroller extends AdminBaseController{
	private static final GdLogger logger = GdLoggerFactory.getLogger(SysUserBoardCotroller.class);

	/** 用户看板Serivce */
	@Autowired
	private SysUserBoardHomeService sysUserBoardHomeService;
	/**
	 * 查询账户信息
	 * 
	 * @author liufan
	 * @param request
	 * @return String
	 */
	@RequestMapping("getUserBoardList")
	public String getUserBoardList(HttpServletRequest request) throws Exception{
		Map<Long,BoardDTO> resultMap = new HashMap<Long,BoardDTO>();
		List<BoardDTO> boardList = null;
		try{
			String boardType = request.getParameter("type");
			putModel("type",boardType);
			//如果传入类型为综合看板
			if(Constant.USERBOARD_TYPE_SYNTHESIZE.equals(boardType)){
				boardList = getBoardList();
			//分类看板
			}else{
				//查询出menuName返回到页面显示
				String menuId = request.getParameter("menuID");
				BaseMenu baseMenu = getBaseMenu(menuId);
				if(null != baseMenu && StringUtils.isNotBlank(baseMenu.getMenuName())){
					putModel("menuName", baseMenu.getMenuName());
				}
				boardList = getBoardListByMenuId(menuId);
			}
			Map<String,Object> paramMap = new HashMap<String,Object>();
			
			//存放所有看板ID集合
			List<Long> boardIDList =new ArrayList<Long>(); 
			if(CollectionUtils.isNotEmpty(boardList)){
				for(BoardDTO board: boardList){
					//综合看板,默认状态为关闭,打开此功能就新增，关闭此功能就删除;分类看板,默认为打开，打开此功能即删除，关闭此功能则新增.
					//(常量名因其它人在很多地方都使用了，故不做修改。)
					if(Constant.USERBOARD_TYPE_SYNTHESIZE.equals(boardType)){
						board.setStatus(Constant.USERBOARD_STATUS_OPEN);
					}else{
						board.setStatus(Constant.USERBOARD_STATUS_CLOSE);
					}
					boardIDList.add(board.getId());
					resultMap.put(board.getId(), board);
				}
				if(CollectionUtils.isNotEmpty(boardIDList)){
					paramMap.put("userID", LoginUserUtil.getLoginUserId(request));
					paramMap.put("list", boardIDList);
					paramMap.put("type", boardType);
					List<SysUserBoard>  sysUserBoardList = sysUserBoardHomeService.getUserBoardList(paramMap);
					if(CollectionUtils.isNotEmpty(sysUserBoardList)){
						for(SysUserBoard userBoard : sysUserBoardList){
							//如果list中的数据也在userboard表中存在,则需要根据是综合看板还是分类看板来设置状态值
							if(resultMap.get(userBoard.getBoardID()) !=null){
								if(Constant.USERBOARD_TYPE_SYNTHESIZE.equals(boardType)){
									resultMap.get(userBoard.getBoardID()).setStatus(Constant.USERBOARD_STATUS_CLOSE);
								}else{
									resultMap.get(userBoard.getBoardID()).setStatus(Constant.USERBOARD_STATUS_OPEN);
								}
							}
						}
					}
				
				}
			}else{
				putModel("result", "此分类下还没有看板数据");
			}
			putModel("resultMap",resultMap);
		}catch(Exception e){
			logger.trace(e.getMessage());
		}
		return "H5/board/shopBoard";
	}
	@RequestMapping("insertUserBoard")
	@ResponseBody
    public String insertUserBoard(HttpServletRequest request) throws Exception{
		SysUserBoard sysUserBoard = new SysUserBoard();
		sysUserBoard.setUserID(LoginUserUtil.getLoginUserId(request));
		sysUserBoard.setBoardID(Long.valueOf(request.getParameter("boardID")));
		sysUserBoard.setCreateUserID(LoginUserUtil.getLoginUserId(request));
		if(Constant.USERBOARD_TYPE_SYNTHESIZE.equals(request.getParameter("type"))){
			sysUserBoard.setType(Constant.USERBOARD_TYPE_SYNTHESIZE);
		}else{
			sysUserBoard.setType(Constant.USERBOARD_TYPE_CLASSIFY);
		}
		return sysUserBoardHomeService.insert(sysUserBoard);
	}

	@RequestMapping("deleteUserBoard")
	@ResponseBody
    public String deleteUserBoard(HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userID", LoginUserUtil.getLoginUserId(request));
		map.put("boardID", request.getParameter("boardID"));
        if(Constant.USERBOARD_TYPE_SYNTHESIZE.equals(request.getParameter("type"))){
        	map.put("type",Constant.USERBOARD_TYPE_SYNTHESIZE);
        }else{
        	map.put("type",Constant.USERBOARD_TYPE_CLASSIFY);
        }
        int result = sysUserBoardHomeService.delete(map);
       return (result>0)?CommonConstant.COMMON_AJAX_SUCCESS:"fail";
        
	}


}
