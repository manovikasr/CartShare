package com.cmpe275project.service;

import java.io.IOException;
import java.util.Map;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public interface EmailService {
	public void sendEmail(String to, String subject, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;
	
	public void sendVerificationEmail(String to, String subject, Map<String, Object> map) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;
}
