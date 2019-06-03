package com.tfb.activiti.form;

import org.activiti.engine.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author :tfb
 */

@ContextConfiguration(locations = {"classpath:*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractTest {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	HistoryService historyService;
	@Autowired
	IdentityService identityService;
	@Autowired
	ManagementService managementService;
	@Autowired
	protected FormService formService;
	
	@BeforeClass
	public static void setUpForClass() throws Exception{
		System.out.println("-------------- 开始测试 -----------------");
	}
	@AfterClass
	public static void testOverForClass() throws Exception{
		System.out.println("-------------- 结束测试 -----------------");
	}
}
