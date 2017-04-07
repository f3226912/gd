package com.gudeng.commerce.gd.api.util;

import java.util.List;

/**
 * 邮件发送线程
 * 
 * @author xiaojun
 */
public class MailThread implements Runnable {
	private String subject;
	private Object content;
	private List<String> toMail;

	public MailThread() {
	}

	public MailThread(String subject, Object content, List<String> toMail) {
		this.subject = subject;
		this.content = content;
		this.toMail = toMail;
	}

	@Override
	public void run() {
		MailUtil.sendMail(subject, content.toString(), toMail, null, null,
				null, null);
	}

}
