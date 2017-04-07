<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.detail-box .cont-div .cont-lef{
	min-width : 8.5rem;
	text-align:right;
}
</style>
<div class="main-con main-bot-mar detail-box">

	<div class="cont-div clearfix">
		<div class="cont-lef fl">
			联系人：
		</div>
		<div class="cont-rig fl">
			${mem.contactName }
		</div>
	</div>

  <div class="cont-div clearfix">
		<div class="cont-lef fl">
			发货地：
		</div>
		<div class="cont-rig fl">
		    <c:if test ="${mem.source == 1}">
          		${mem.s_detail }${mem.s_detailed_address }
	        </c:if>
	
	        <c:if test ="${mem.source == 0}">
	          		${mem.s_provinceName }${mem.s_cityName }${mem.s_areaName }
	        </c:if>
		</div>
	</div>	
	
  <c:if test ="${mem.source == 0 && !empty mem.s_detailed_address}">
    <div class="cont-div clearfix">
  		<div class="cont-lef fl">
  		&nbsp;
  		</div>
  		<div class="cont-rig fl">
          ${mem.s_detailed_address}
  		</div>
  	</div>
  </c:if>

	<div class="cont-div clearfix">
		<div class="cont-lef fl">
			收货地：
		</div>
		<div class="cont-rig fl">
	      <c:if test ="${mem.source == 1}">
	        ${mem.f_detail }${mem.f_detailed_address }
	      </c:if>

        <c:if test ="${mem.source == 0}">
          		${mem.f_provinceName }${mem.f_cityName }${mem.f_areaName }
        </c:if>
		</div>
	</div>
  <c:if test ="${mem.source == 0 && !empty mem.f_detailed_address}">
    <div class="cont-div clearfix">
  		<div class="cont-lef fl">
  		&nbsp;
  		</div>
  		<div class="cont-rig fl">
          ${mem.f_detailed_address}
  		</div>
  	</div>
  </c:if>

  <div class="cont-div clearfix">
		<div class="cont-lef fl">
			货物分类：
		</div>
		<div class="cont-rig fl">
	      ${mem.goodsTypeString}
		</div>
	</div>

	<%-- <c:if test="${not empty mem.totalWeight && mem.totalWeight != 0 }"> --%>
		<div class="cont-div clearfix">
			<div class="cont-lef fl">
				总重量：
			</div>
			<div class="cont-rig fr">
				${mem.totalWeight } <span class="weig">吨</span>
			</div>
		</div>
	<%-- </c:if> --%>

	<%-- <c:if test="${not empty mem.totalSize && mem.totalSize != 0 }"> --%>
		<div class="cont-div clearfix">
			<div class="cont-lef fl">
				总体积：
			</div>
			<div class="cont-rig fr">
				${mem.totalSize > 0 ? mem.totalSize : '-' } 
				<c:if test="mem.totalSize > 0">
					<span class="weig weig2">&#109;<span class="lif">3</span></span>
				</c:if>
			</div>
		</div>
	<%-- </c:if> --%>

  <div class="cont-div clearfix">
    <div class="cont-lef fl">
        装车时间：
    </div>
    <div class="cont-rig fl">
        <fmt:formatDate value="${mem.sendDate}" pattern="yyyy-MM-dd"/>
    </div>
  </div>

  <div class="cont-div clearfix">
    <div class="cont-lef fl">
        所需车型：
    </div>
    <div class="cont-rig fl">
      ${mem.carTypeString}
    </div>
  </div>

  <div class="cont-div clearfix">
    <div class="cont-lef fl">
        意向价格：
    </div>
    <div class="cont-rig fl">
        ${mem.price > 0 ? mem.price : '面议'}
    </div>
  </div>

  <div class="cont-div clearfix bor-non">
    <div class="cont-lef fl">
        备注：
    </div>
    <div class="cont-rig fl">
        ${mem.remark}
    </div>
  </div>

</div>
