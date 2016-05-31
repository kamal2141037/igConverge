package com.infogain.igconverge.model;

import java.util.Arrays;
import java.util.Date;

public class Mail {

	private String mailFrom;

	private String[] mailTo;

	private String[] mailCc;

	private String[] mailBcc;

	private String mailSubject;

	private String mailContent;
	
	private byte[] mailImage;

	private String templateName;

	private String contentType;

	private int priority;
	

	public Mail() {
		contentType = "text/html";
		this.priority = 3;
	}

	public String getContentType() {
		return contentType;
	}

	public String[] getMailBcc() {
		return mailBcc;
	}

	public String[] getMailCc() {
		return mailCc;
	}

	public String getMailContent() {
		return mailContent;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public byte[] getMailImage() {
		return mailImage;
	}

	public Date getMailSendDate() {
		return new Date();
	}

	public String getMailSubject() {
		return mailSubject;
	}



	public String[] getMailTo() {
		return mailTo;
	}

	public int getPriority() {
		return priority;
	}


	

	public String getTemplateName() {
		return templateName;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setMailBcc(String[] mailBcc) {
		this.mailBcc = mailBcc;
	}

	public void setMailCc(String[] mailCc) {
		this.mailCc = mailCc;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public void setMailImage(byte[] mailImage) {
		this.mailImage = mailImage;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public void setMailTo(String[] mailTo) {
		this.mailTo = mailTo;
	}

	
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Mail [mailFrom=");
		builder.append(mailFrom);
		builder.append(", mailTo=");
		builder.append(Arrays.toString(mailTo));
		builder.append(", mailCc=");
		builder.append(Arrays.toString(mailCc));
		builder.append(", mailBcc=");
		builder.append(Arrays.toString(mailBcc));
		builder.append(", mailSubject=");
		builder.append(mailSubject);
		builder.append(", mailContent=");
		builder.append(mailContent);
		builder.append(", mailImage=");
		builder.append(mailImage);
		builder.append(", templateName=");
		builder.append(templateName);
		builder.append(", contentType=");
		builder.append(contentType);
		builder.append("]");
		return builder.toString();
	}

	

}