package com.mixiusi.service;

import com.mixiusi.MixiusiServiceApplication;
import com.mixiusi.bean.CoffeeMachine;
import com.mixiusi.biz.CoffeeMachineBiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)  
@SpringBootTest(classes = {MixiusiServiceApplication.class})
public class CouponsServiceTest {
	@Autowired
	private CoffeeMachineBiz coffeeMachineBiz;
	@Test
	public void test() {
		List<CoffeeMachine> list = new ArrayList<>();
		CoffeeMachine cm = coffeeMachineBiz.getCoffeeMachineById("10000000112");
		CoffeeMachine cm1 = coffeeMachineBiz.getCoffeeMachineById("10000000113");
		cm.setDownTime(new Date());
		cm1.setEmployeeCode("23efdt5u75r2etr");
		list.add(cm);
		list.add(cm1);
		coffeeMachineBiz.updateCoffeeMachines(list);

	}
}
