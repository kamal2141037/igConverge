package com.infogain.igconverge.service;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.infogain.igconverge.model.Mail;

public class RunMeJob extends QuartzJobBean {

	private Mailer mailer;

	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("this is run me job");

		mailer = new Mailer();
		Mail mail = new Mail();
		mail.setMailFrom("igConverge<igconverge@infogain.com>");// igConverge@infogain.com
		String[] to={"admindept@infogain.com"};
		mail.setMailTo(to);// admindept@infogain.com
		String[] cc={"igconverge@infogain.com"};
		mail.setMailCc(cc);
		mail.setMailSubject("igConverge Reminder!");// igconverge Remainder

		mail.setTemplateName("emailtemplate.vm");
		mail.setMailContent("This is gentle reminder for updation of igConverge data for today. Please ignore if already done.");
		ApplicationContext cont = new ClassPathXmlApplicationContext(
				"spring-beans.xml");

		try {
			mailer.sendMail(mail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param mailer
	 *            the mailer to set
	 */
	public void setMailer(Mailer mailer) {
		this.mailer = mailer;
	}
}