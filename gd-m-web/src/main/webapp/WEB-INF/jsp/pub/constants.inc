<%
	String BASE_PATH = "/";
    String path = request.getContextPath();
    String CONTEXT = "https://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/";
	request.setAttribute("CONTEXT",CONTEXT);
%>