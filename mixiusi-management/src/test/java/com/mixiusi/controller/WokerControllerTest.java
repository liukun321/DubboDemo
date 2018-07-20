package com.mixiusi.controller;

import java.util.HashMap;
import java.util.Map;

import com.mixiusi.MixiusiResourceApplication;
import com.mixiusi.vo.EmployeeVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)  
@SpringBootTest(classes = {MixiusiResourceApplication.class})
public class WokerControllerTest {
	
    @Test  
    public void contextLoads() {  
    }  
  
    private MockMvc mockMvc; // 模拟MVC对象，通过MockMvcBuilders.webAppContextSetup(this.wac).build()初始化。    
        
    @Autowired    
    private WebApplicationContext wac; // 注入WebApplicationContext    
    
    
    @Before // 在测试开始前初始化工作    
    public void setup() {    
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();    
    }    
    
    @Test    
    public void testQ1() throws Exception {   
    	
        MvcResult result = (MvcResult) mockMvc.perform(MockMvcRequestBuilders.get("/coffeeInfo/getAll")
//        		.contentType(MediaType.APPLICATION_JSON_VALUE)
//        		.content(mapper.writeValueAsString(errorRecordVo))
        		.param("page", "0")
        		.param("size", "5")
        		.param("sort", "coffeeName")
//        		.param("type", "2")
        		)
//        		.andExpect(Mock.status().isOk())
        		.andReturn();
        System.out.println(result.getResponse().getContentAsString());    
    }   

    
    @Test    
    public void testQ2() throws Exception {   
    	EmployeeVo worker = new EmployeeVo();
    	worker.setMaintainerNumber("343fr3t4g343");
    	worker.setPhoneNumber("13412546453");
    	worker.setRealname("王五");
    	Map<String, Integer> maps = new HashMap<>();
    	maps.put("10000000112", 0);
    	maps.put("10000000113", 1);
    	worker.setMachines(maps);
    	ObjectMapper mapper = new ObjectMapper();
		String requestJson= mapper.writeValueAsString(worker);
    	
        MvcResult result = (MvcResult) mockMvc.perform(MockMvcRequestBuilders.post("/worker/addEmployee")
        		.accept(MediaType.APPLICATION_JSON_UTF8_VALUE).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(requestJson))
//        		.param("page", "0")
//        		.param("size", "5")
//        		.param("sort", "coffeeName")
//        		.param("type", "2")
//        		.andExpect(Mock.status().isOk())
        		.andReturn();
        System.out.println(result.getResponse().getContentAsString());    
    }   
}
