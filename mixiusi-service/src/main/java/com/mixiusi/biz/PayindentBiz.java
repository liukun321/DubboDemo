package com.mixiusi.biz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mixiusi.repository.read.PayindentReadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mixiusi.bean.Payindent;
import com.mixiusi.bean.utils.DateUtils;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.vo.OrderVo;
import com.mixiusi.bean.vo.SaleStatisticVo;
import com.mixiusi.repository.write.PayindentRepository;

@Service
public class PayindentBiz{
	private final Logger log = LoggerFactory.getLogger(PayindentBiz.class);
	@Autowired
	private PayindentRepository payindentRepository;

	@Autowired
	private PayindentReadRepository payindentReadRepository;
	/**
	 * 删除订单-》撤销订单
	 */
	public boolean deletePayindent(String indentId) {
		boolean flag = false;
		try {
			Payindent payindent = payindentReadRepository.findOne(indentId);
			if(null == payindent) {
				log.info("The payindent of " + indentId + " does not exist");
			}else {
				//删除订单-》将订单修改为 支付失败
				payindent.setPayStatus(5);
				payindentRepository.save(payindent);
				flag = true;
			}
		}catch(Exception e) {
			log.info(e.getMessage() , e);
			return flag;
		}
		return flag;
		
	}
	/**
	 * 根据订单ID查询订单
	 */
	public Payindent queryPayindentById(String indentId) {
		return payindentReadRepository.findByIndentId(indentId);
	}

	/**
	 * 根据订单的状态查询订单
	 */
	public Page<Payindent> queryOrder(OrderVo orderVo) {
		Integer page = orderVo.getPage();
		Integer size = orderVo.getSize();
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pageable pageable = null;
		if(StringUtils.isNull(orderVo.getSort())) {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, "indentId");
		}else {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, orderVo.getSort());
		}
		return payindentReadRepository.findAll(new Specification<Payindent>() {
			@Override
			public Predicate toPredicate(Root<Payindent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				
				if(!StringUtils.isNull(orderVo.getIndentId())) {
					list.add(cb.equal(root.get("indentId").as(String.class), orderVo.getIndentId()));
				}
//				if(!StringUtils.isNull(orderVo.getCoffeeId())) {
//					list.add(cb.equal(root.get("coffeeId").as(String.class), orderVo.getCoffeeId()));
//				}
//				In<String> in = cb.in(root.get("coffeeId"));
//				in.value(orderVo.getCoffeeId());
				
//				if(!StringUtils.isNull(orderVo.getMachineId())) {
//					list.add(cb.equal(root.get("machineId").as(String.class), orderVo.getMachineId()));
//				}
//				if(!StringUtils.isNull(orderVo.getOrderId())) {
//					list.add(cb.equal(root.get("orderId").as(String.class), orderVo.getOrderId()));
//				}
				if(!StringUtils.isNull(orderVo.getPayMethod())) {
					list.add(cb.equal(root.get("payMethod").as(Integer.class), orderVo.getPayMethod()));
				}
				if(!StringUtils.isNull(orderVo.getPayStatus())) {
					list.add(cb.equal(root.get("payStatus").as(Integer.class), orderVo.getPayStatus()));
				}
//				if(!StringUtils.isNull(orderVo.getSugar())) {
//					list.add(cb.equal(root.get("sugar").as(Integer.class), orderVo.getSugar()));
//				}
//				if(!StringUtils.isNull(orderVo.isHot())) {
//					list.add(cb.equal(root.get("isHot").as(Boolean.class), orderVo.isHot()));
//				}
				Date startTime = orderVo.getStartTime();
				Date endTime = orderVo.getEndTime();
				if(!StringUtils.isNull(startTime) || !StringUtils.isNull(endTime)) {
				 try {
					 if(StringUtils.isNull(startTime)) {
						 list.add(cb.lessThan(root.get("createTime").as(Date.class),
									sdfmat.parse(sdfmat.format(orderVo.getEndTime()))));
					 }else if(StringUtils.isNull(endTime)) {
						 list.add(cb.greaterThan(root.get("createTime").as(Date.class), 
									sdfmat.parse(sdfmat.format(orderVo.getStartTime()))));
					 }else {
						 list.add(cb.between(root.get("createTime").as(Date.class), 
								 sdfmat.parse(sdfmat.format(orderVo.getStartTime())),
								 sdfmat.parse(sdfmat.format(orderVo.getEndTime()))));
					 }
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			 }
			Predicate[] p = new Predicate[list.size()];  
            return cb.and(list.toArray(p)); 
			}
		}, pageable);
	}

	/**
	 * 根据订单的状态和 支付方式查询订单的总金额
	 */
	public Double querySumprice(Integer status, Integer payMethod) {
		return payindentReadRepository.querySumprice(status, payMethod);
	}

	public Page<Payindent> getAllOrder(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "indentId");
		return payindentReadRepository.findAll(pageable);
	}

	public Long querySumOrder(OrderVo orderVo) {
		
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return payindentReadRepository.count(new Specification<Payindent>() {
			@Override
			public Predicate toPredicate(Root<Payindent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				
				if(!StringUtils.isNull(orderVo.getIndentId())) {
					list.add(cb.equal(root.get("indentId").as(String.class), orderVo.getIndentId()));
				}
//				if(!StringUtils.isNull(orderVo.getCoffeeId())) {
//					list.add(cb.equal(root.get("coffeeId").as(String.class), orderVo.getCoffeeId()));
//				}
				
//				if(!StringUtils.isNull(orderVo.getCoffeeId())) {
//					list.add(cb.isMember(root.get("coffeeId").as(String.class), ss));
//				}
				
//				if(!StringUtils.isNull(orderVo.getMachineId())) {
//					list.add(cb.equal(root.get("machineId").as(String.class), orderVo.getMachineId()));
//				}
//				if(!StringUtils.isNull(orderVo.getOrderId())) {
//					list.add(cb.equal(root.get("orderId").as(String.class), orderVo.getOrderId()));
//				}
				if(!StringUtils.isNull(orderVo.getPayMethod())) {
					list.add(cb.equal(root.get("payMethod").as(Integer.class), orderVo.getPayMethod()));
				}
				if(!StringUtils.isNull(orderVo.getPayStatus())) {
					list.add(cb.equal(root.get("payStatus").as(Integer.class), orderVo.getPayStatus()));
				}
//				if(!StringUtils.isNull(orderVo.getSugar())) {
//					list.add(cb.equal(root.get("sugar").as(Integer.class), orderVo.getSugar()));
//				}
//				if(!StringUtils.isNull(orderVo.isHot())) {
//					list.add(cb.equal(root.get("isHot").as(Boolean.class), orderVo.isHot()));
//				}
				Date startTime = orderVo.getStartTime();
				Date endTime = orderVo.getEndTime();
				if(!StringUtils.isNull(startTime) || !StringUtils.isNull(endTime)) {
					 try {
						 if(StringUtils.isNull(startTime)) {
							 list.add(cb.lessThan(root.get("createTime").as(Date.class),
										sdfmat.parse(sdfmat.format(orderVo.getEndTime()))));
						 }else if(StringUtils.isNull(endTime)) {
							 list.add(cb.greaterThan(root.get("createTime").as(Date.class), 
										sdfmat.parse(sdfmat.format(orderVo.getStartTime()))));
						 }else {
							 list.add(cb.between(root.get("createTime").as(Date.class), 
									 sdfmat.parse(sdfmat.format(orderVo.getStartTime())),
									 sdfmat.parse(sdfmat.format(orderVo.getEndTime()))));
						 }
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				 }
			Predicate[] p = new Predicate[list.size()];  
            return cb.and(list.toArray(p)); 
			}
		});
	}

	public Page<Payindent> querySale(SaleStatisticVo ssvo) {
		Integer page = ssvo.getPage();
		Integer size = ssvo.getSize();
		Pageable pageable = null;
		if(StringUtils.isNull(ssvo.getSort())) {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, "indentId");
		}else {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, ssvo.getSort());
		}
		return payindentReadRepository.findAll(new Specification<Payindent>() {
			@Override
			public Predicate toPredicate(Root<Payindent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				
				if(!StringUtils.isNull(ssvo.getCoffeeId())) {
					list.add(cb.equal(root.get("coffeeId").as(String.class), ssvo.getCoffeeId()));
				}
				if(!StringUtils.isNull(ssvo.getIsHot())) {
					list.add(cb.equal(root.get("isHot").as(Boolean.class), ssvo.getIsHot()));
				}
				List<Integer> sugars = ssvo.getSugar();
				if(null !=sugars && !sugars.isEmpty()) {
					for (int i = 0; i < sugars.size(); i++) {
						list.add(cb.equal(root.get("sugar").as(String.class), sugars.get(i)));
					}
				}
				list.add(cb.equal(root.get("payStatus").as(String.class), 1));
				if(!StringUtils.isNull(ssvo.getTimeType())) {
					Integer key = ssvo.getTimeType();
					switch (key) {
					case 1://日
						queryTimeparam(root, cb, list, DateUtils.getStartTimeOfDay(), new Date());
						break;
					case 2://周
						queryTimeparam(root, cb, list, DateUtils.getNowWeek(), new Date());
						break;
					case 3://月
						queryTimeparam(root, cb, list, DateUtils.getFirstDayOfMonth(), new Date());
						break;
					case 4://年
						queryTimeparam(root, cb, list, DateUtils.getDayOfYear(), new Date());
						break;
					default:
						break;
					}
				}
				Date startTime = ssvo.getStartTime();
				Date endTime = ssvo.getEndTime();
				if(!StringUtils.isNull(startTime) || !StringUtils.isNull(endTime)) {
				 queryTimeparam(root, cb, list, startTime, endTime);
			 }
			Predicate[] p = new Predicate[list.size()];  
            return cb.and(list.toArray(p)); 
			}
		}, pageable);
	}
	private void queryTimeparam(Root<Payindent> root, CriteriaBuilder cb, List<Predicate> list, Date startTime, Date endTime) {
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info(sdfmat.format(startTime));
		try {
			 if(StringUtils.isNull(startTime)) {
				 list.add(cb.lessThan(root.get("createTime").as(Date.class),
							sdfmat.parse(sdfmat.format(endTime))));
			 }else if(StringUtils.isNull(endTime)) {
				 list.add(cb.greaterThan(root.get("createTime").as(Date.class), 
							sdfmat.parse(sdfmat.format(startTime))));
			 }else {
				 list.add(cb.between(root.get("createTime").as(Date.class), 
						 sdfmat.parse(sdfmat.format(startTime)),
						 sdfmat.parse(sdfmat.format(endTime))));
			 }
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public Long querySaleSum(SaleStatisticVo ssvo) {
		return payindentReadRepository.count(new Specification<Payindent>() {
			@Override
			public Predicate toPredicate(Root<Payindent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				
				if(!StringUtils.isNull(ssvo.getCoffeeId())) {
					list.add(cb.equal(root.get("coffeeId").as(String.class), ssvo.getCoffeeId()));
				}
				if(!StringUtils.isNull(ssvo.getIsHot())) {
					list.add(cb.equal(root.get("isHot").as(Boolean.class), ssvo.getIsHot()));
				}
				list.add(cb.equal(root.get("payStatus").as(String.class), 1));//销售记录查询只查交易成功的
				List<Integer> sugars = ssvo.getSugar();
				if(null != sugars && !sugars.isEmpty()) {
					for (int i = 0; i < sugars.size(); i++) {
						list.add(cb.equal(root.get("sugar").as(String.class), sugars.get(i)));
					}
				}
				if(!StringUtils.isNull(ssvo.getTimeType())) {
					Integer key = ssvo.getTimeType();
					switch (key) {
					case 1://日
						queryTimeparam(root, cb, list, DateUtils.getStartTimeOfDay(), new Date());
						break;
					case 2://周
						queryTimeparam(root, cb, list, DateUtils.getNowWeek(), new Date());
						break;
					case 3://月
						queryTimeparam(root, cb, list, DateUtils.getFirstDayOfMonth(), new Date());
						break;
					case 4://年
						queryTimeparam(root, cb, list, DateUtils.getDayOfYear(), new Date());
						break;
					default:
						break;
					}
				}
				Date startTime = ssvo.getStartTime();
				Date endTime = ssvo.getEndTime();
				if(!StringUtils.isNull(startTime) || !StringUtils.isNull(endTime)) {
				 queryTimeparam(root, cb, list, startTime, endTime);
			 }
			Predicate[] p = new Predicate[list.size()];  
            return cb.and(list.toArray(p)); 
			}
		});
	}

	public Long queryCoffeeSaleSum(String coffeeId) {
		return payindentReadRepository.count(new Specification<Payindent>() {
			@Override
			public Predicate toPredicate(Root<Payindent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				list.add(cb.equal(root.get("coffeeId").as(String.class), coffeeId));
//				list.add(cb.equal(root.get("payStatus").as(Integer.class), 1));//销售记录查询只查交易成功的
				Predicate[] p = new Predicate[list.size()];  
	            return cb.and(list.toArray(p)); 
			}
		});
	}
	/**
	 * 添加订单
	 * @param payindent
	 * @return
	 */
	public Payindent addPayindent(Payindent payindent) {
		Payindent p = null;
		try {
			p = payindentRepository.save(payindent);
		}catch(Exception e) {
			log.info(e.getMessage() , e);
		}
		return p;
	}
}
