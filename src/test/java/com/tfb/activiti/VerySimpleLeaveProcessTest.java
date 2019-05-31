package com.tfb.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Driver;

/**
 * @author :tfb
 */
public class VerySimpleLeaveProcessTest {
	@Test
	public void testStartProcess() throws Exception{
		ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration().buildProcessEngine();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment().addClasspathResource("activiti-1.bpmn").deploy();
		
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
		Assert.assertEquals("myProcess_1",processDefinition.getKey());
		
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance myProcess = runtimeService.startProcessInstanceByKey("myProcess_1");
		Assert.assertNotNull(myProcess);
		
		System.out.println("pid="+myProcess.getId()+",pdid="+myProcess.getProcessDefinitionId());
		
	}
}
