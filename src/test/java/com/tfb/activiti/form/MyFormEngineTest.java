package com.tfb.activiti.form;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * @author :tfb
 */
public class MyFormEngineTest extends AbstractTest {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Test
	@Deployment(resources = {""})
	public void allPass(){
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
		Object renderedStartForm = formService.getRenderedStartForm(processDefinition.getId(), "myformengine");
		Assert.assertNotNull(renderedStartForm);
		Assert.assertTrue(renderedStartForm instanceof JButton);
		JButton button = (JButton) renderedStartForm;
		Assert.assertEquals("My Start Form Button", button.getName());
	}
}
