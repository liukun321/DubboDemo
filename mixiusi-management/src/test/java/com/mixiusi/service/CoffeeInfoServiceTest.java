package com.mixiusi.service;

import com.mixiusi.MixiusiResourceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MixiusiResourceApplication.class})
public class CoffeeInfoServiceTest {
	@Autowired
	private CoffeeInfoService coffeeInfoService;
	@Test
	public void test() {
//		CoffeeInfo coffee = new CoffeeInfo();
//		coffee.setCoffeeId(GenerateUniqueId.generateCoffeeId());
//		coffee.setCoffeeName("abf");
//		coffee.setCoffeeNumber("353r34wfe53");
//		coffee.setDiscount(false);
//		coffee.setDiscount_price(10.0);
//		coffee.setIs_new(false);
//		coffee.setPrice(12.0);
//		CoffeeInfo flag = coffeeInfoService.addCoffeeInfo(coffee);
////		
////		
//		System.out.println(flag.toString());
	}
}
