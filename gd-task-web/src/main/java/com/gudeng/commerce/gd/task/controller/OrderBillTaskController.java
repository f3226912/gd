package com.gudeng.commerce.gd.task.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.order.dto.OrderBillImportLlogDTO;
import com.gudeng.commerce.gd.task.service.OrderBillTaskService;

/**
 * @Description 交易账单服务
 * @Project gd-admin-service
 * @ClassName OrderBillTaskController.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月14日 上午11:59:22
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
@Controller
public class OrderBillTaskController {
	@Autowired
	private OrderBillTaskService orderBillTaskService;

	@RequestMapping("orderBill/import")
	public synchronized void pushImportData(HttpServletRequest request, HttpServletResponse response) {
		String file = request.getParameter("file");
		PrintWriter pw = null;
		response.setCharacterEncoding("UTF-8");
		try {
			pw = response.getWriter();
			if (StringUtils.isEmpty(file)) {
				pw.println("Parameter:file");
			} else {
				if (file.startsWith("gdkj") && file.endsWith(".txt")) {
					OrderBillImportLlogDTO dto = new OrderBillImportLlogDTO();
					dto.setCreateUserId(request.getRemoteAddr());
					String sucess = orderBillTaskService.pushImportData(file, dto);
					switch (sucess) {
					case "304":
						pw.println(file + " has been imported!");
						break;
					case "500":
						pw.println(file + " import failed!");
						break;
					case "200":
						pw.println(file + " import successfully");
						break;
					default:
						pw.println("unknow error");
						break;
					}
				} else {
					pw.println(file + " is not a correct file, please check the file name carefully");
				}

			}
		} catch (Exception e) {
			pw.println("Parameter:file");
			pw.println(e.getMessage());
		} finally {
			pw.flush();
			pw.close();
		}
	}

}
