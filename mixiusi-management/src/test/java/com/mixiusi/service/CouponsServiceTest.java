package com.mixiusi.service;

import com.mixiusi.MixiusiResourceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MixiusiResourceApplication.class})
public class CouponsServiceTest {
	@Autowired
	private CouponsService couponsService;
	@Test
	public void test() {
//		CouponsVo couponsVo = new CouponsVo();
//		couponsVo.setCount(10);
//		couponsVo.setSumMoney(50);
//		couponsVo.setType(2);
//		couponsVo.setEndTime(DateUtil.tomorrow());
//		Boolean flag = couponsService.addCoupons(couponsVo);
//		
//		
//		System.out.println(flag.toString());
	}
}
