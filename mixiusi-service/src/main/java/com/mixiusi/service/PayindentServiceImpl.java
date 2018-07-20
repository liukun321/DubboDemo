package com.mixiusi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.Payindent;
import com.mixiusi.bean.vo.OrderVo;
import com.mixiusi.bean.vo.SaleStatisticVo;
import com.mixiusi.biz.PayindentBiz;

@Service(interfaceClass = PayindentService.class)
public class PayindentServiceImpl implements PayindentService {
	@Autowired
	private PayindentBiz payindentBiz;
	@Override
	public boolean deletePayindent(String indentId) {
		return payindentBiz.deletePayindent(indentId);
	}

	@Override
	public Payindent queryPayindentById(String indentId) {
		return payindentBiz.queryPayindentById(indentId);
	}

	@Override
	public List<Payindent> queryOrder(OrderVo orderVo) {
		return payindentBiz.queryOrder(orderVo).getContent();
	}

	@Override
	public Double querySumprice(Integer status, Integer payMethod) {
		return payindentBiz.querySumprice(status, payMethod);
	}

//	@Override
//	public List<Payindent> getAllOrder(Integer page, Integer size) {
//		return payindentBiz.getAllOrder(page, size).getContent();
//	}

	@Override
	public Long querySumOrder(OrderVo orderVo) {
		return payindentBiz.querySumOrder(orderVo);
	}

	@Override
	public List<Payindent> querySale(SaleStatisticVo ssvo) {
		return payindentBiz.querySale(ssvo).getContent();
	}

	@Override
	public Long querySaleSum(SaleStatisticVo ssvo) {
		return payindentBiz.querySaleSum(ssvo);
	}

	@Override
	public Long queryCoffeeSaleSum(String coffeeId) {
		return payindentBiz.queryCoffeeSaleSum(coffeeId);
	}

	@Override
	public Payindent addPayindent(Payindent payindent) {
		return payindentBiz.addPayindent(payindent);
	}

}
