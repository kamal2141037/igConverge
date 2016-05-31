package com.infogain.igconverge.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.infogain.igconverge.model.Mail;

public class Mailer {
	
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void sendMail(final Mail mail) throws MessagingException {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		mailSender = (JavaMailSender) context.getBean("mailSender");
		velocityEngine=(VelocityEngine) context.getBean("velocityEngine");
		//InputStream inputStream = context.getClassLoader().getSystemResourceAsStream("WEB-INF/igConverge-logo.png")
		//ClassLoader classLoader = getClass().getClassLoader();
		//InputStream inputStream = classLoader.getResourceAsStream("igConverge-logo.png");
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			
			public void prepare(MimeMessage message) throws Exception {
				// TODO Auto-generated method stub
				
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setFrom(mail.getMailFrom());
				helper.setTo(mail.getMailTo());
				helper.setSubject(mail.getMailSubject());
				helper.setPriority(mail.getPriority());
				if(mail.getMailCc()!=null){
					helper.setCc(mail.getMailCc());
				}
				Map model = new HashMap();
				model.put("Mail", mail);
				String text = "";
				
				if(mail.getMailImage() != null){
					System.out.println("enter into image convertion");
					InputStream in = new ByteArrayInputStream(mail.getMailImage());
					BufferedImage bImageFromConvert = ImageIO.read(in);
					ImageIO.write(bImageFromConvert, "jpg", new File(
							"D:/igconvergeimages/new-darksouls.jpg"));
					FileSystemResource image = new FileSystemResource(new File("D:/igconvergeimages/new-darksouls.jpg"));
					
					text = VelocityEngineUtils.mergeTemplateIntoString(
					        velocityEngine, "./templates/emailimagetemplate.vm", model);	
					helper.setText(text, true);
					helper.addInline("identifier1238", image);
					
				}else{
					text = VelocityEngineUtils.mergeTemplateIntoString(
				        velocityEngine, "./templates/emailtemplate.vm", model);
					helper.setText(text, true);
				}
				
				
				//InputStream resource = servletContext.getResourceAsStream("WEB-INF/igConverge-logo.png");
				
				
				
				FileSystemResource res = new FileSystemResource(new File(
		                "D:/igconvergeimages/igConverge-logo.png"));
				helper.addInline("identifier1234", res);
				res = new FileSystemResource(new File("D:/igconvergeimages/infogain-icon.png"));
				helper.addInline("identifier1235", res);
				res = new FileSystemResource(new File("D:/igconvergeimages/mail-icon.png"));
				helper.addInline("identifier1236", res);

				
				
				
				
				//				res = new FileSystemResource(new File("/Users/rajat/Desktop/img1.jpeg"));
//				helper.addInline("identifier1237", res);
//				DataSource ds = new 
//				helper.addInline(contentId,d)
				
			}
		};
		
		/*
		 * FileSystemResource file = new FileSystemResource("d:\\amant.txt");
		 * 
		 * helper.addAttachment(file.getFilename(), file);
		 Template template = velocityEngine.getTemplate("./templates/"
				+ mail.getTemplateName());

		VelocityContext velocityContext = new VelocityContext();

		StringWriter stringWriter = new StringWriter();
		velocityContext.put("description", mail.getMailContent());
		template.merge(velocityContext, stringWriter);

		helper.setText(stringWriter.toString());

		mailSender.send(message);*/
		this.mailSender.send(preparator);
	}

}