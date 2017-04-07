package com.gudeng.commerce.gd.task.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: TODO(邮件工具类)
 * @author mpan
 * @date 2015年12月22日 下午10:56:17
 */
public class EmailUtil {

	private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

	/**
	 * Message对象将存储我们实际发送的电子邮件信息，
	 * Message对象被作为一个MimeMessage对象来创建并且需要知道应当选择哪一个JavaMail session。
	 */
	private static MimeMessage message;

	/**
	 * Session类代表JavaMail中的一个邮件会话。
	 * 每一个基于JavaMail的应用程序至少有一个Session（可以有任意多的Session）。
	 * 
	 * JavaMail需要Properties来创建一个session对象。 寻找"mail.smtp.host" 属性值就是发送邮件的主机
	 * 寻找"mail.smtp.auth" 身份验证，目前免费邮件服务器都需要这一项
	 */
	private static Session session;
	
	private static Object lock = new Object();

	/***
	 * 邮件是既可以被发送也可以被受到。JavaMail使用了两个不同的类来完成这两个功能：Transport 和 Store。 Transport
	 * 是用来发送信息的，而Store用来收信。
	 */
	private static Transport transport;

	private static String mailHost;
	private static String userName;
	private static String password;
	
	static {
		init();
	}

	private static void init() {
		if (session == null) {
			synchronized (lock) {
				if (session == null) {
					GdProperties gdProperties = (GdProperties) SpringContextUtil.getBean("gdProperties");
					Properties properties = gdProperties.getProperties();
					mailHost = properties.getProperty("mail.smtp.host");
					userName = properties.getProperty("mail.sender.username");
					password = properties.getProperty("mail.sender.password");
					session = Session.getInstance(properties);
					message = new MimeMessage(session);
				}
			}
		}
	}
	
	/**
	 * 发送邮件
	 * 
	 * @param subject
	 *            邮件主题
	 * @param sendHtml
	 *            邮件内容
	 * @param receiveUser
	 *            收件人地址
	 */
	public static void doSendHtmlEmail(String subject, String sendHtml, String receiveUser) {
		if (StringUtils.isBlank(receiveUser)) {
			return;
		}
		List<String> receiveUsers = new ArrayList<String>();
		receiveUsers.add(receiveUser);
		doSendHtmlEmail(subject, sendHtml, receiveUsers);
	}

	/**
	 * 发送邮件
	 * 
	 * @param subject
	 *            邮件主题
	 * @param sendHtml
	 *            邮件内容
	 * @param receiveUsers
	 *            多个收件人地址
	 */
	public static void doSendHtmlEmail(String subject, String sendHtml, List<String> receiveUsers) {
		try {
			if (CollectionUtils.isEmpty(receiveUsers)) {
				return;
			}
			// 发件人
			// InternetAddress from = new InternetAddress(sender_username);
			InternetAddress from = new InternetAddress(userName);
			message.setFrom(from);

			// 收件人
			InternetAddress to = null;
			List<InternetAddress> toList = new ArrayList<InternetAddress>();
			for (String receiveUser : receiveUsers) {
				to = new InternetAddress(receiveUser);
				toList.add(to);
			}
			
			message.setRecipients(Message.RecipientType.TO, (InternetAddress[]) toList.toArray(new InternetAddress[0]));// 还可以有CC、BCC

			// 邮件主题
			message.setSubject(subject);

			String content = sendHtml.toString();
			// 邮件内容,也可以使纯文本"text/plain"
			message.setContent(content, "text/html;charset=UTF-8");

			// 保存邮件
			message.saveChanges();

			transport = session.getTransport("smtp");
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			transport.connect(mailHost, userName, password);
			// 发送
			transport.sendMessage(message, message.getAllRecipients());
			logger.debug("邮件发送成功，发件人：" + userName + "，收件人：" + receiveUsers + "，发送时间：" + DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME));
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					logger.error("发送邮件关闭连接失败", e);
				}
			}
		}
	}

}
