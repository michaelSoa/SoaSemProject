package org.activiti.designer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class TestRechnungEmpfang {
	
	private String filename = "src\\main\\java\\RechnungEmpfang.bpmn";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		repositoryService.createDeployment().addInputStream("myProcess.bpmn20.xml",new FileInputStream(filename)).deploy();
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		
		variableMap.put("name", "Activiti");
		variableMap.put("rechnungsempfänger", "Bert");
		variableMap.put("rechnungsprüfer", "Krümelmonster");
		variableMap.put("rechnungsNr", 1000);
		variableMap.put("buchhalter", "Ernie");
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", variableMap);
		
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "+ processInstance.getProcessDefinitionId());
		
		//test Task:
		Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
//		List<Task> taskList = activitiRule.getTaskService().createTaskQuery().taskCandidateUser("Ernie").list();
		
		//gibt nur 1 Task
		List<Task> taskList = activitiRule.getTaskService().createTaskQuery().list();
		
		//funktioniert nicht!
		//Task task2 = activitiRule.getTaskService().createTaskQuery().taskId("usertask1").singleResult();
		//Task task2 = activitiRule.getTaskService().createTaskQuery().taskName("Rechnung Anlegen").singleResult();

		
		//assertEquals("Rechnung Anlegen",task.getName());
		System.out.println(task.getAssignee());
		System.out.println(task.getDescription());
		
		for(int i=0;i<taskList.size();i++) {
			System.out.println(i+": "+taskList.get(i).getName());
		}
		
		System.out.println("name:");
		System.out.println(task.getName());
//		System.out.println(task2.getName());
//		System.out.println(task3.getName());
//		System.out.println(task4.getName());
//		System.out.println(task5.getName());
		System.out.println("assignee:");
		System.out.println(task.getAssignee());
//		System.out.println(task2.getAssignee());
//		System.out.println(task3.getAssignee());
//		System.out.println(task4.getAssignee());
//		System.out.println(task5.getAssignee());
		
		//ProcessEngine?
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		
		System.out.println(processEngine.getName());
		

	}
}