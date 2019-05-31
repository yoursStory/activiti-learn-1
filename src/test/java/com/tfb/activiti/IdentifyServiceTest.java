package com.tfb.activiti;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

/**
 * @author :tfb
 */
public class IdentifyServiceTest {
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();
	
	@Test
	public void testUser() throws Exception{
		IdentityService identityService = activitiRule.getIdentityService();
		
		User user = identityService.newUser("theAngel");
		user.setFirstName("henry");
		user.setLastName("Yan");
		
		user.setEmail("yanhonglei@gmail.com");
		
		identityService.saveUser(user);
		
		User theAngel = identityService.createUserQuery().userId("theAngel").singleResult();
		Assert.assertNotNull(theAngel);
		
		identityService.deleteUser("theAngel");
		theAngel = identityService.createUserQuery().userId("theAngel").singleResult();
		Assert.assertNull(theAngel);
	}
	
	@Test
	public void testGroup() throws Exception{
		IdentityService identityService = activitiRule.getIdentityService();
		Group deptLeader = identityService.newGroup("deptLeader");
		deptLeader.setName("部门领导");
		deptLeader.setType("assignment");
		
		identityService.saveGroup(deptLeader);
		List<Group> groups = identityService.createGroupQuery().groupId("deptLeader").list();
		Assert.assertEquals(1,groups.size());
		
		identityService.deleteGroup("deptLeader");
		groups = identityService.createGroupQuery().groupId("deptLeader").list();
		Assert.assertEquals(0,groups.size());
	}
}
