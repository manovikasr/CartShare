package com.cmpe275project.service;

import java.io.IOException;
import java.util.Map;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public interface EmailService {
	public void sendEmail(String to, String subject, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;
	
	public void sendVerificationEmail(String to, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;
	
	public void sendMessageEmail(String from, String to, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;
	
	public void sendRefEmail(String to, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;
	
	public void sendEmailForLeaderApproval(String to, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;
	
	public void sendEmailForOrderConfirmation(String to, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;
	
	public void sendEmailOfSelfAndPoolerOrderDetails(String to, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;
	
	public void sendEmailForPickedUpConfirmation(String to, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;
	
	public void sendEmailOfUserOrderDetails(String to, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;
	
	public void sendEmailforOrderDelivered(String to, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;
	
	public void sendEmailforOrderNotDelivered(String to, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;

}
