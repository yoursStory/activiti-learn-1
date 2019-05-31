package com.tfb.activiti.util;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

/**
 * @author :tfb
 */
public class ActivitiUtils {
	private static ProcessEngine processEngine;
	
	public static ProcessEngine getProcessEngine(){
		if(processEngine==null){
			processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
		}
		return processEngine;
	}
}
