package com.tfb.activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author :tfb
 */
public class SayHelloToLeaveTest {
	@Test
	public void testStartProcess() throws Exception{
		ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration().buildProcessEngine();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment().addInputStream("SayHelloToLeave.bpmn", this.getClass().getClassLoader().getResourceAsStream("activiti-1.bpmn")).deploy();
		
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
		
		Assert.assertEquals("SayHelloToLeave",processDefinition.getKey());
		
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		HashMap<String, Object> variables = new HashMap<String, Object>();
		
		variables.put("applyUser", "employee1");
		variables.put("days", 3);
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("SayHelloToLeave", variables);
		Assert.assertNotNull(processInstance);
		
		TaskService taskService = processEngine.getTaskService();
		Task taskOfDeptLeader = taskService.createTaskQuery().taskCandidateOrAssigned("deptLeader").singleResult();
		
		Assert.assertNotNull(taskOfDeptLeader);
		Assert.assertEquals("领导审批",taskOfDeptLeader.getName());
		
		taskService.claim(taskOfDeptLeader.getId(),"leaderUser");
		
		variables = new HashMap<String, Object>();
		variables.put("approved", true);
		taskService.complete(taskOfDeptLeader.getId(),variables);
		
		taskOfDeptLeader = taskService.createTaskQuery().taskCandidateOrAssigned("deptLeader").singleResult();
		Assert.assertNull(taskOfDeptLeader);
		
		HistoryService historyService = processEngine.getHistoryService();
		long count = historyService.createHistoricProcessInstanceQuery().finished().count();
		Assert.assertEquals(1,count);
		
	}
}
