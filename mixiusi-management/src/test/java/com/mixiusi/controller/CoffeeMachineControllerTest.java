package com.mixiusi.controller;

import com.mixiusi.MixiusiResourceApplication;
import com.mixiusi.vo.CoffeeInfoVo;
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
public class CoffeeMachineControllerTest {
	
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
    	
        MvcResult result = (MvcResult) mockMvc.perform(MockMvcRequestBuilders.get("/machine/exportExcelForBaseInfo")
//        		.contentType(MediaType.APPLICATION_JSON_VALUE)
//        		.content(mapper.writeValueAsString(errorRecordVo))
        		.param("page", "0")
        		.param("size", "5")
//        		.param("sort", "coffeeName")
//        		.param("type", "2")
        		)
//        		.andExpect(Mock.status().isOk())
        		.andReturn();
        System.out.println(result.getResponse().getContentAsString());    
    }   

    
    @Test
    public void testQ2() throws Exception {

    	CoffeeInfoVo coffee = new CoffeeInfoVo();
    	coffee.setCoffeeNumber("2445tefe54re53");
    	coffee.setCoffeeName("拿铁");
    	coffee.setDiscount(true);
    	coffee.setPrice(12.0);
    	coffee.setDiscount_price(10.0);
    	ObjectMapper mapper = new ObjectMapper();
		String requestJson= mapper.writeValueAsString(coffee);

        MvcResult result = (MvcResult) mockMvc.perform(MockMvcRequestBuilders.post("/coffeeInfo/addCoffee")
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
