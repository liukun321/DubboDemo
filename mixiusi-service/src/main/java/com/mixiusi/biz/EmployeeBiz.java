package com.mixiusi.biz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mixiusi.repository.read.EmployeeReadRepository;
import com.mixiusi.repository.read.ErrorRecordReadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mixiusi.bean.CoffeeMachine;
import com.mixiusi.bean.Employee;
import com.mixiusi.bean.EmployeeMachines;
import com.mixiusi.bean.ErrorRecord;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.vo.WorkerVo;
import com.mixiusi.repository.write.EmployeeMachinesRepository;
import com.mixiusi.repository.write.EmployeeRepository;
import com.mixiusi.repository.write.ErrorRecordRepository;
@Transactional
@Service
public class EmployeeBiz {
	private final Logger log = LoggerFactory.getLogger(EmployeeBiz.class);
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeReadRepository employeeReadRepository;
	@Autowired
	private CoffeeMachineBiz coffeeMachineBiz;
	@Autowired
	private EmployeeMachinesBiz employeeMachinesBiz;
	@Autowired
	private ErrorRecordRepository errorRecordRepository;
	@Autowired
	private ErrorRecordReadRepository errorRecordReadRepository;
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * 根据运营人员的登陆用户查询运营人员信息
	 */
	public Employee queryEmployeeByPhone(String phone) {
		return employeeReadRepository.findByPhoneNumber(phone);
	}

	public Employee login4Employee(String tel, String password) {
		return employeeReadRepository.findByPhoneNumberAndPassword(tel, password);
	}

	/**
	 * 添加运营人员
	 */
	@Transactional
	public Employee insertEmployee(Employee employee, Map<String, Integer> maps) {
		//保存运维人员之前更新咖啡机的信息
		String employeeCode = employee.getWorkerId();
		List<String> machineIds = new ArrayList<>();
		List<EmployeeMachines> machines = new ArrayList<>();
		//获取该运营人员负责的咖啡机
		for (Entry<String, Integer> machine : maps.entrySet()) {
			machineIds.add(machine.getKey());
			EmployeeMachines m = new EmployeeMachines();
			machines.add(m);
			m.setMachineId(machine.getKey());
			m.setType(machine.getValue());
			m.setWorkerId(employeeCode);
		}
		try {
			employeeMachinesBiz.batchInsert(machines);

			List<CoffeeMachine> list = coffeeMachineBiz.queryMachinesById(machineIds);
			if (null == list || list.isEmpty()) {
				log.info("不存在咖啡机={}", machineIds);
				return null;
			}
			int size = list.size();
			//将运营人员的编号更新到咖啡机信息中
			for (int i = 0; i < size; i++) {
				list.get(i).setEmployeeCode(employeeCode);
			}
			//更新咖啡机信息
			coffeeMachineBiz.updateCoffeeMachines(list);
			//保存运营人员的信息
			return employeeRepository.save(employee);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
		
	}
	
	//根据运营人员编号查询错误日志
	public List<ErrorRecord> queryWorkerId(String workerId) {
		List<ErrorRecord> list = errorRecordReadRepository.findByWorkerId(workerId);
		if(null ==  list || list.isEmpty())
			return null;
		return list;
	}
	/**
	 * 删除运维人员，同时将其负责的咖啡机信息更新
	 */
	public boolean removeEmployee(String workerId) {
		Employee employee = this.queryEmployeeById(workerId);
		List<EmployeeMachines> machines = employeeMachinesBiz.getMachinesByWorker(workerId);
		if(employee != null) {
//			String employeeCode = employee.getWorkerId();
//			Map<String, Integer> map = employee.getMachines();
			List<String> machineCodes = new ArrayList<>();
			//获取该运营人员负责的咖啡机
			for (EmployeeMachines mid : machines) {
				machineCodes.add(mid.getMachineId());
			}
			List<CoffeeMachine> list = coffeeMachineBiz.queryMachinesById(machineCodes);
			if(null == list || list.isEmpty()) {
				log.info("不存在咖啡机={}", machineCodes);
			}
			int size = list.size();
			//将运营人员的编号更新到咖啡机信息中
			for(int i = 0; i < size; i++) {
				list.get(i).setEmployeeCode("");
			}
			coffeeMachineBiz.updateCoffeeMachines(list);
			employeeRepository.delete(workerId);
		}else {
			return false;
		}
		return true;
	}
	
	public Page<Employee> getAllEmployee(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "workerId");  
		return employeeReadRepository.findAll(pageable);
	}

	public Page<Employee> getAllEmployeeCriteria(WorkerVo evo) {
		Integer page = evo.getPage();
		Integer size = evo.getSize();
		String sort = evo.getSort();
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pageable pageable = null;
		if(StringUtils.isNull(sort)) {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, "workerId");
		}else {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, sort);
		}
        return employeeReadRepository.findAll(new Specification<Employee>(){
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(evo.getNickname())){  
					 list.add(cb.equal(root.get("nickname").as(String.class), evo.getNickname()));
	             }
				 //判断错误的持续时间是否超过给定的值
				 if(!StringUtils.isNull(evo.getPhoneNumber())){  
					 list.add(cb.equal(root.get("phoneNumber").as(String.class), evo.getPhoneNumber()));
	             }
				 if(!StringUtils.isNull(evo.getRealname())){  
					 list.add(cb.equal(root.get("realname").as(String.class), evo.getRealname()));
	             }
				 if(!StringUtils.isNull(evo.getWorkerId())){  
					 list.add(cb.equal(root.get("workerId").as(String.class), evo.getWorkerId()));
	             }
				 //开始时间在给定时间之后
				 if(!StringUtils.isNull(evo.getJoinTime())) {
					try {
						 list.add(cb.greaterThan(root.get("joinTime").as(Date.class), 
								sdfmat.parse(sdfmat.format(evo.getJoinTime()))));
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				 }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			}  
        }, pageable);  
	}
	
	public Long getAllEmployeeNumber(WorkerVo evo) {
		
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return employeeReadRepository.count(new Specification<Employee>(){
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(evo.getNickname())){  
					 list.add(cb.equal(root.get("nickname").as(String.class), evo.getNickname()));
	             }
				 //判断错误的持续时间是否超过给定的值
				 if(!StringUtils.isNull(evo.getPhoneNumber())){  
					 list.add(cb.equal(root.get("phoneNumber").as(String.class), evo.getPhoneNumber()));
	             }
				 if(!StringUtils.isNull(evo.getRealname())){  
					 list.add(cb.equal(root.get("realname").as(String.class), evo.getRealname()));
	             }
				 if(!StringUtils.isNull(evo.getWorkerId())){  
					 list.add(cb.equal(root.get("workerId").as(String.class), evo.getWorkerId()));
	             }
				 //开始时间在给定时间之后
				 if(!StringUtils.isNull(evo.getJoinTime())) {
					try {
						 list.add(cb.greaterThan(root.get("joinTime").as(Date.class), 
								sdfmat.parse(sdfmat.format(evo.getJoinTime()))));
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				 }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			}  
        }); 
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean remiveEmployee(List<String> workerIds) {
		for(String id : workerIds) {
			Employee cm =  employeeReadRepository.findOne(id);
			if(null == cm) {
				return false;
			}else {
				employeeMachinesBiz.batchRemove(id);
				entityManager.remove(cm);
			}
		}
		entityManager.flush();
		entityManager.clear();
		return true;
	}

	public Employee queryEmployeeById(String workerId) {
		return employeeReadRepository.findOne(workerId);
	}

	
}
