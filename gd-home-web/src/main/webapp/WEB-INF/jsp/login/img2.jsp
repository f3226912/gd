<%@page import="com.gudeng.commerce.gd.home.util.RandUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="image/jpeg" import="java.awt.*,java.awt.image.*,javax.imageio.*"%>
<%!
	//给定范围获得随机颜色 
	Color getRandColor(int fc, int bc) {  
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
%>
<%
	Map<String,String> randMap=RandUtil.randArithmetic(new Random().nextInt(10), new Random().nextInt(10));
	//设置页面不缓存   
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	//在内存中创建图象   
	int width = 150, height = 30;
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	//获取图形上下文   
	Graphics g = image.getGraphics();
	//生成随机类   
	Random random = new Random();
	//设定背景色   
	g.setColor(getRandColor(200, 250));
	g.fillRect(0, 0, width, height);
	//设定字体   
	g.setFont(new Font("仿宋", Font.PLAIN, 22));
	//画边框   
	//g.setColor(new   Color());   
	//g.drawRect(0,0,width-1,height-1);
	//随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
	g.setColor(getRandColor(160, 200));
	for (int i = 0; i < 20; i++) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(12);
		int yl = random.nextInt(12);
		g.drawLine(x, y, x + xl, y + yl);
	}
	//取随机产生的认证码(4位数字)   
	String sRand = "";
	g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
	g.drawString(randMap.get("arithmetic"), 2, 23);
	//将认证码存入SESSION   
	session.setAttribute("rand", randMap.get("result"));
	//图象生效   
	g.dispose();
	//输出图象到页面   
	ImageIO.write(image, "JPEG", response.getOutputStream());
	out.clear();
	out = pageContext.pushBody();
%>