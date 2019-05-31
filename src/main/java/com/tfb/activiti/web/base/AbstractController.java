package com.tfb.activiti.web.base;

import com.tfb.activiti.util.ActivitiUtils;
import org.activiti.engine.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Driver;

/**
 * @author :tfb
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected ProcessEngine processEngine = null;
	
	public AbstractController(){
		processEngine= ActivitiUtils.getProcessEngine();
	}
}
