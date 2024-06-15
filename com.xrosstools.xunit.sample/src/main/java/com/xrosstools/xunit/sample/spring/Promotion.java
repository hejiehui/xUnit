package com.xrosstools.xunit.sample.spring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class Promotion {
	private boolean qualified = true;
	private String type = "best sell";

	@Value("classpath:/resource.txt")
	private Resource resource;
	
	public boolean isQualified() {
		return qualified;
	}
	public void setQualified(boolean qualified) {
		this.qualified = qualified;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getText() {
		try {
			BufferedReader reader = new BufferedReader(
	                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
			return reader.readLine();
		}catch (Exception e) {
			return e.toString();
		}
	}
}
