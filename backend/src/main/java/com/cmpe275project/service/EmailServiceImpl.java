package com.cmpe275project.service;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
@Transactional
public class EmailServiceImpl implements EmailService{

	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	private Configuration freeMarkerConfig;
	
	@Override
	public void sendEmail(String to, String subject, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
			
			Template t = freeMarkerConfig.getTemplate("SimpleEmail.ftl");
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
			
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text, true);
			
			emailSender.send(message);
			
		} catch (MessagingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void sendVerificationEmail(String to, String subject, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
			
			Template t = freeMarkerConfig.getTemplate("EmailVerification.ftl");
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
			
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text, true);
			
			emailSender.send(message);
			
		} catch (MessagingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
