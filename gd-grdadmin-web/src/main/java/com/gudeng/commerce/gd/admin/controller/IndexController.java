package com.gudeng.commerce.gd.admin.controller;


public class IndexController extends AdminBaseController{

	/*@Autowired
	private GdProperties property;
	@Autowired
	private SysMenuManager sysMenuManager;
	@Autowired
	private SysRegisterUserManager sysRegisterUserManager;
	
	*//**
	 * 运营后台首页
	 * @author jww
	 * @param request
	 * @return
	 *
	 *//*
	@RequestMapping("common/index")
	public String toIndex(HttpServletRequest request){
		
		return "main/index";
	}
	
	*//**
	 * 初始化菜单数据
	 * @author wwj
	 * @param request
	 * @return
	 *
	 *//*
	
	@RequestMapping("common/getMenuList")
	@ResponseBody
	public String getMenuList(HttpServletRequest request){
	   if(SecurityContextHolder.getContext().getAuthentication() != null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String userId=userDetails.getUsername();
			if(userId!=null){
				//系统编码
				String systemCode=property.getCasSystemCode();
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("userId", userId);
				map.put("systemCode", systemCode);
				List<SysmenuDTO> menuList=sysMenuManager.getSysMenuByMap(map);
				this.getRequest().getSession(true).setAttribute("menuList", menuList);
				SysregisteruserEntity registeruser= sysRegisterUserManager.getUserByUserId(userId);
				UserSummary u = new UserSummary();
				u.setUserid(registeruser.getId());
				u.setName(registeruser.getUserName());
				this.getRequest().getSession(true).setAttribute(UserSummary.SESSION_USER_KEY, u);
				return JSONObject.toJSONString(menuList);
			}
		}
	   return null;
	}*/
}
