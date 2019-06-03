package com.tfb.activiti.form;

import org.activiti.engine.form.AbstractFormType;

/**
 * @author :tfb
 */
public class JavascriptFormType extends AbstractFormType {
	public Object convertFormValueToModelValue(String propertyValue) {
		return propertyValue;
	}
	
	public String convertModelValueToFormValue(Object modelValue) {
		return (String) modelValue;
	}
	
	public String getName() {
		return "javascript";
	}
}
