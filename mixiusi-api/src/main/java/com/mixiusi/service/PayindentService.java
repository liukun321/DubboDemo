package com.mixiusi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.Payindent;
import com.mixiusi.bean.vo.OrderVo;
import com.mixiusi.bean.vo.SaleStatisticVo;

public interface PayindentService {
	boolean deletePayindent(String indentId);
	
	Payindent queryPayindentById(String indentId);
	/**
	 * 查询订单列表 条件查询
	 * @param orderVo
	 * @return
	 */
	List<Payindent> queryOrder(OrderVo orderVo);
	
	Double querySumprice(Integer status, Integer payMethod);

//	List<Payindent> getAllOrder(Integer page, Integer size);
	
	Long querySumOrder(OrderVo orderVo);
	/**
	 * 获取某时间段内的销售量
	 * @param ssvo
	 * @return
	 */
	List<Payindent> querySale(SaleStatisticVo ssvo);
	/**
	 * 销售数量
	 * @param ssvo
	 * @return
	 */
	Long querySaleSum(SaleStatisticVo ssvo);
	/**
	 * 查询某种咖啡的销量
	 * @param coffeeId
	 * @return
	 */
	Long queryCoffeeSaleSum(String coffeeId);
	/**
	 * 添加订单
	 * @param payindent
	 * @return
	 */
	Payindent addPayindent(Payindent payindent);
}
