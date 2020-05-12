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
	public void sendVerificationEmail(String to, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
			
			Template t = freeMarkerConfig.getTemplate("EmailVerification.ftl");
            String subject = "Cart Share - Verify your email address";
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
	public void sendMessageEmail(String from, String to, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
			
			Template t = freeMarkerConfig.getTemplate("MessageEmail.ftl");
            String subject = "Cart Share - Message from "+from;
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
	public void sendRefEmail(String to, Map<String, Object> map) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
			
			Template t = freeMarkerConfig.getTemplate("ReferenceEmail.ftl");
            String subject = "Cart Share - Request to refer to join your Pool";
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
	public void sendEmailForLeaderApproval(String to, Map<String, Object> map) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
			
			Template t = freeMarkerConfig.getTemplate("ApproveRejectEmail.ftl");
            String subject = "Cart Share - Pool Membership Application Approval pending";
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
	public void sendEmailForOrderConfirmation(String to, Map<String, Object> map) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
			
			Template t = freeMarkerConfig.getTemplate("OrderConfirmation.ftl");
            String subject = "Cart Share - Order placed";
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
	public void sendEmailForSelfOrderConfirmation(String to, Map<String, Object> map) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
			
			Template t = freeMarkerConfig.getTemplate("SelfOrderConfirmation.ftl");
            String subject = "Cart Share - Order ready for pickup";
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
	public void sendEmailPoolerOrderDetails(String to, Map<String, Object> map) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
			
			Template t = freeMarkerConfig.getTemplate("PoolerOrderDetailsEmail.ftl");
            String subject = "Cart Share - Orders to pick up";
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
	public void sendEmailForPickerAssigned(String to, Map<String, Object> map) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
			
			Template t = freeMarkerConfig.getTemplate("PickerAssignedConfirmation.ftl");
            String subject = "Cart Share - Picker assigned to your Order";
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
	public void sendEmailForPickedUpConfirmation(String to, Map<String, Object> map) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
			
			Template t = freeMarkerConfig.getTemplate("PickedUpConfirmation.ftl");
            String subject = "Cart Share - Order picked up";
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
	public void sendEmailOfUserOrderDetails(String to, Map<String, Object> map) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
			
			Template t = freeMarkerConfig.getTemplate("UserOrderDetailsEmail.ftl");
            String subject = "Cart Share - Order Delivery Details";
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
	public void sendEmailforOrderDelivered(String to, Map<String, Object> map) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
			
			Template t = freeMarkerConfig.getTemplate("OrderDeliveredEmail.ftl");
            String subject = "Cart Share - Order Delivered";
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
	public void sendEmailforOrderNotDelivered(String to, Map<String, Object> map) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		// TODO Auto-generated method stub
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			freeMarkerConfig.setClassForTemplateLoading(this.getClass(), "/");
			
			Template t = freeMarkerConfig.getTemplate("OrderNotDeliveredEmail.ftl");
            String subject = "Cart Share - Order not delivered to fellow pooler";
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
