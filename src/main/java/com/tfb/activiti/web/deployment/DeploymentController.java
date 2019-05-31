package com.tfb.activiti.web.deployment;

import com.tfb.activiti.web.base.AbstractController;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @author :tfb
 */
@Controller
@RequestMapping(value = "deployment")
public class DeploymentController extends AbstractController {
	RepositoryService repositoryService = processEngine.getRepositoryService();
	
	@RequestMapping(value = "/process-list")
	public String processLIst(HttpServletRequest request) {
		List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().list();
		request.setAttribute("processDefinitionList", processDefinitionList);
		return "process-list";
	}
	
	@RequestMapping(value = "deploy")
	public String deploy(MultipartFile file) {
		String filename = file.getOriginalFilename();
		try {
			InputStream fileInputStream = file.getInputStream();
			String extension = FilenameUtils.getExtension(filename);
			DeploymentBuilder deployment = repositoryService.createDeployment();
			if ("zip".equals(extension) || "bar".equals(extension)) {
				ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
				deployment.addZipInputStream(zipInputStream);
			}else{
				deployment.addInputStream(filename, fileInputStream);
			}
			deployment.deploy();
			logger.info("deploy {}", filename);
		} catch (IOException e) {
			logger.error("流程发布失败:{}", e.getMessage());
		}
		return "redirect:process-list";
	}
	
	@RequestMapping("/read-resource")
	public void readResource(@RequestParam("pdid") String processDefinitionId,
							 String resourceName,
							 HttpServletResponse response) throws IOException {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		InputStream resource = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		byte[] buff = new byte[1024];
		int len = -1;
		while ((len = resource.read(buff, 0, 1024)) != -1) {
			response.getOutputStream().write(buff,0,1024);
		}
		logger.info("read-resource {},{}", processDefinitionId,resourceName);
		
	}
	
	@RequestMapping("/delete-deployment")
	public String deleteProcessDefinition(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
		logger.info("delete-deployment:{}",deploymentId);
		return "redirect:process-list";
	}
}
