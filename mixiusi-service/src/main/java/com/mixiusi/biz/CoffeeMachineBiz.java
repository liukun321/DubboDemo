package com.mixiusi.biz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mixiusi.repository.read.CoffeeMachineReadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mixiusi.bean.CoffeeMachine;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.vo.CmachineVo;
import com.mixiusi.bean.vo.MachineBaseInfoVo;
import com.mixiusi.bean.vo.MachineStatusVo;
import com.mixiusi.repository.write.CoffeeMachineRepository;

/**
 * 
 * @author liukun
 *
 */
@Service
public class CoffeeMachineBiz{
	private final Logger log = LoggerFactory.getLogger(CoffeeMachineBiz.class);
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired	
	private CoffeeMachineRepository coffeeMachineRepository;
	@Autowired
	private CoffeeMachineReadRepository coffeeMachineReadRepository;
	
	public CoffeeMachine getCoffeeMachineInfo(String machineCode) {
		return coffeeMachineReadRepository.findByMachineCode(machineCode);
	}

	public List<CoffeeMachine> machineForEmployee(String employeeCode) {
		return coffeeMachineReadRepository.findByEmployeeCode(employeeCode);
	}

	public List<CoffeeMachine> getAllCoffeeMachine() {
		return coffeeMachineReadRepository.findAll();
	}

	public CoffeeMachine addCoffeeMachine(CoffeeMachine coffeeMachine) {
		return coffeeMachineRepository.save(coffeeMachine);
	}
	@Transactional
	public List<CoffeeMachine> queryMachinesByCode(List<String> machineCodes) {
		List<CoffeeMachine> list = new ArrayList<>();
		for (String code : machineCodes) {
			CoffeeMachine cm = this.getCoffeeMachineInfo(code);
			if(null != cm) {
				list.add(cm);
			}
		}
		return list;
	}
	
	@Transactional
	public List<CoffeeMachine> queryMachinesById(List<String> machineIds) {
		List<CoffeeMachine> list = new ArrayList<>();
		for (String id : machineIds) {
			CoffeeMachine cm = this.getCoffeeMachineById(id);
			if(null != cm) {
				list.add(cm);
			}
		}
		return list;
	}
	
	@Transactional
	public void addCoffeeMachines(List<CoffeeMachine> list) {
		//批量保存，数量过多会出现问题，这里认为一个运维人员负责的机器数量较小
		//1 循环将CoffeeMachine实例放进entityManager中，注意这里的CoffeeMachine实例的id属性必须为空
		try {
			list.stream().forEach(machine -> entityManager.persist(machine));
		}catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		//2 刷新后一次两所有的实例保存到数据库中 
		entityManager.flush();
		entityManager.clear();
	}
	/**
	 * 查找所有没有绑定运营人员的咖啡机
	 */
	public List<CoffeeMachine> getAllNoEmployee() {
		List<CoffeeMachine> list = coffeeMachineReadRepository.findByEmployeeCodeIsNull();
		return list;
	}

	public CoffeeMachine queryMaxCodeCoffeeMachine() {
		Pageable pageable = new PageRequest(0, 1, Direction.DESC, "machineCode"); 
		return coffeeMachineReadRepository.findAll(pageable).getContent().get(0);
	}

	public CoffeeMachine getCoffeeMachineById(String machineId) {
		return coffeeMachineReadRepository.findOne(machineId);
	}
	
	
	@Transactional
	public void updateCoffeeMachines(List<CoffeeMachine> list) {
		try {
			list.stream().forEach(machine -> entityManager.merge(machine));
		}catch(Exception e) {
			log.error(e.getMessage(), e);
		}
		//2 刷新后一次将所有的实例保存到数据库中
		entityManager.flush();
		entityManager.clear();
	}
	
	
	public Page<CoffeeMachine> getAllCoffeeMachine(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "machineId");  
		return coffeeMachineReadRepository.findAll(pageable);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public boolean removeCoffeeMachines(List<String> machineCodes) {
		for(String id : machineCodes) {
			CoffeeMachine cm =  coffeeMachineReadRepository.findOne(id);
			if(null == cm) {
				return false;
			}else {
				entityManager.remove(cm);
			}
		}
		entityManager.flush();
		entityManager.clear();
		return true;
	}
	
	
	public Page<CoffeeMachine> getAllCoffeeMachineCriteria(CmachineVo mvo) {
		Integer page = mvo.getPage();
		Integer size = mvo.getSize();
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "machineId");
        return coffeeMachineReadRepository.findAll(new Specification<CoffeeMachine>(){
			@Override
			public Predicate toPredicate(Root<CoffeeMachine> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(mvo.getMachineId())){  
					 list.add(cb.equal(root.get("machineId").as(String.class), mvo.getMachineId()));
	             }
				 //判断错误的持续时间是否超过给定的值
				 if(!StringUtils.isNull(mvo.getEmployeeCode())){  
					 list.add(cb.equal(root.get("employeeCode").as(String.class), mvo.getEmployeeCode()));
	             }
				 if(!StringUtils.isNull(mvo.getType())){  
					 list.add(cb.equal(root.get("type").as(Integer.class), mvo.getType()));
	             }
				 if(!StringUtils.isNull(mvo.getIs_running())){  
					 list.add(cb.equal(root.get("is_running").as(Boolean.class), mvo.getIs_running()));
	             }
				 if(!StringUtils.isNull(mvo.getStatus())){  
					 list.add(cb.equal(root.get("status").as(Integer.class), mvo.getStatus()));
	             }
				 //开始时间在给定时间之后
				 if(!StringUtils.isNull(mvo.getCreateTime())) {
					try {
						 list.add(cb.greaterThan(root.get("createTime").as(Date.class), 
								sdfmat.parse(sdfmat.format(mvo.getCreateTime()))));
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				 }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			}  
        }, pageable); 
	}

	public Long getCoffeeMachineNumber(CmachineVo mvo) {
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return coffeeMachineReadRepository.count(new Specification<CoffeeMachine>(){
			@Override
			public Predicate toPredicate(Root<CoffeeMachine> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(mvo.getMachineId())){  
					 list.add(cb.equal(root.get("machineId").as(String.class), mvo.getMachineId()));
	             }
				 //判断错误的持续时间是否超过给定的值
				 if(!StringUtils.isNull(mvo.getEmployeeCode())){  
					 list.add(cb.equal(root.get("employeeCode").as(String.class), mvo.getEmployeeCode()));
	             }
				 if(!StringUtils.isNull(mvo.getType())){  
					 list.add(cb.equal(root.get("type").as(Integer.class), mvo.getType()));
	             }
				 if(!StringUtils.isNull(mvo.getIs_running())){  
					 list.add(cb.equal(root.get("is_running").as(Boolean.class), mvo.getIs_running()));
	             }
				 if(!StringUtils.isNull(mvo.getStatus())){  
					 list.add(cb.equal(root.get("status").as(Integer.class), mvo.getStatus()));
	             }
				 //开始时间在给定时间之后
				 if(!StringUtils.isNull(mvo.getCreateTime())) {
					try {
						 list.add(cb.greaterThan(root.get("createTime").as(Date.class), 
								sdfmat.parse(sdfmat.format(mvo.getCreateTime()))));
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				 }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			}  
        });
	}

	public List<CoffeeMachine> queryMachineBaseInfo(MachineBaseInfoVo baseInfo) {
		Integer page = baseInfo.getPage();
		Integer size = baseInfo.getSize();
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "machineId");
        return coffeeMachineReadRepository.findAll(new Specification<CoffeeMachine>(){
			@Override
			public Predicate toPredicate(Root<CoffeeMachine> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 
				 if(!StringUtils.isNull(baseInfo.getMachineId())){  
					 list.add(cb.equal(root.get("machineId").as(String.class), baseInfo.getMachineId()));
	             }
				 if(!StringUtils.isNull(baseInfo.getWorkerId())){  
					 list.add(cb.equal(root.get("employeeCode").as(String.class), baseInfo.getWorkerId()));
	             }
				 if(!StringUtils.isNull(baseInfo.getType())){  
					 list.add(cb.equal(root.get("type").as(Integer.class), baseInfo.getType()));
	             }
				 if(!StringUtils.isNull(baseInfo.getMachineCode())){  
					 list.add(cb.equal(root.get("machineCode").as(Integer.class), baseInfo.getMachineCode()));
				 }
				 Date startTime = baseInfo.getStartTime();
				 Date endTime = baseInfo.getEndTime();
				 if(!StringUtils.isNull(startTime) || !StringUtils.isNull(endTime)) {
					 try {
						 if(StringUtils.isNull(startTime)) {
							 list.add(cb.lessThan(root.get("createTime").as(Date.class),
										sdfmat.parse(sdfmat.format(baseInfo.getEndTime()))));
						 }else if(StringUtils.isNull(endTime)) {
							 list.add(cb.greaterThan(root.get("createTime").as(Date.class), 
										sdfmat.parse(sdfmat.format(baseInfo.getStartTime()))));
						 }else {
							 list.add(cb.between(root.get("createTime").as(Date.class), 
									 sdfmat.parse(sdfmat.format(baseInfo.getStartTime())),
									 sdfmat.parse(sdfmat.format(baseInfo.getEndTime()))));
						 }
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				 }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			}  
        }, pageable).getContent(); 
	}

	public Long queryMachineBaseInfoSum(MachineBaseInfoVo baseInfo) {
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return coffeeMachineReadRepository.count(new Specification<CoffeeMachine>(){
			@Override
			public Predicate toPredicate(Root<CoffeeMachine> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(baseInfo.getMachineId())){  
					 list.add(cb.equal(root.get("machineId").as(String.class), baseInfo.getMachineId()));
	             }
				 //判断错误的持续时间是否超过给定的值
				 if(!StringUtils.isNull(baseInfo.getWorkerId())){  
					 list.add(cb.equal(root.get("employeeCode").as(String.class), baseInfo.getWorkerId()));
	             }
				 if(!StringUtils.isNull(baseInfo.getType())){  
					 list.add(cb.equal(root.get("type").as(Integer.class), baseInfo.getType()));
	             }
				 if(!StringUtils.isNull(baseInfo.getMachineCode())){  
					 list.add(cb.equal(root.get("machineCode").as(Integer.class), baseInfo.getMachineCode()));
	             }
				 Date startTime = baseInfo.getStartTime();
				 Date endTime = baseInfo.getEndTime();
				 if(!StringUtils.isNull(startTime) || !StringUtils.isNull(endTime)) {
					 try {
						 if(StringUtils.isNull(startTime)) {
							 list.add(cb.lessThan(root.get("createTime").as(Date.class),
										baseInfo.getEndTime()));
						 }else if(StringUtils.isNull(endTime)) {
							 list.add(cb.greaterThan(root.get("createTime").as(Date.class), 
										baseInfo.getStartTime()));
						 }else {
							 list.add(cb.between(root.get("createTime").as(Date.class), 
									 sdfmat.parse(sdfmat.format(baseInfo.getStartTime())),
									 sdfmat.parse(sdfmat.format(baseInfo.getEndTime()))));
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

	public Page<CoffeeMachine> queryMachineStatus(MachineStatusVo statusInfo) {
		Integer page = statusInfo.getPage();
		Integer size = statusInfo.getSize();
		String sort = statusInfo.getSort();
		Pageable pageable = null;
		if(StringUtils.isNull(sort)) {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, "machineId");
		}else {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, sort);
		}
        return coffeeMachineReadRepository.findAll(new Specification<CoffeeMachine>(){
			@Override
			public Predicate toPredicate(Root<CoffeeMachine> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 
				 if(!StringUtils.isNull(statusInfo.getMachineId())){  
					 list.add(cb.equal(root.get("machineId").as(String.class), statusInfo.getMachineId()));
	             }
				 if(!StringUtils.isNull(statusInfo.getEmployeeCode())){  
					 list.add(cb.equal(root.get("employeeCode").as(String.class), statusInfo.getEmployeeCode()));
	             }
				 if(!StringUtils.isNull(statusInfo.getIs_running())){  
					 list.add(cb.equal(root.get("is_running").as(Boolean.class), statusInfo.getIs_running()));
	             }
				 if(!StringUtils.isNull(statusInfo.getMachineCode())){  
					 list.add(cb.equal(root.get("machineCode").as(Integer.class), statusInfo.getMachineCode()));
	             }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			}  
        }, pageable); 
	}

	public Long queryMachineStatusSum(MachineStatusVo statusInfo) {
		return coffeeMachineReadRepository.count(new Specification<CoffeeMachine>(){
			@Override
			public Predicate toPredicate(Root<CoffeeMachine> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 
				 if(!StringUtils.isNull(statusInfo.getMachineId())){  
					 list.add(cb.equal(root.get("machineId").as(String.class), statusInfo.getMachineId()));
	             }
				 if(!StringUtils.isNull(statusInfo.getEmployeeCode())){  
					 list.add(cb.equal(root.get("employeeCode").as(String.class), statusInfo.getEmployeeCode()));
	             }
				 if(!StringUtils.isNull(statusInfo.getIs_running())){  
					 list.add(cb.equal(root.get("is_running").as(Boolean.class), statusInfo.getIs_running()));
	             }
				 if(!StringUtils.isNull(statusInfo.getMachineCode())){  
					 list.add(cb.equal(root.get("machineCode").as(Integer.class), statusInfo.getMachineCode()));
	             }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			} 
		});
	}
}
