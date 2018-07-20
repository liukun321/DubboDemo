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

import com.mixiusi.bean.ErrorRecord;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.vo.ErrorRecordVo;
import com.mixiusi.bean.vo.MachineDownVo;
import com.mixiusi.repository.write.ErrorRecordRepository;
/**
 * 咖啡机错误记录service层
 * @author liukun
 *	
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ErrorRecordBiz{
	private final Logger log = LoggerFactory.getLogger(ErrorRecordBiz.class);
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private ErrorRecordRepository errorRecordRepository;
	@Autowired
	private ErrorRecordReadRepository errorRecordReadRepository;

	/**
	 * 添加错误记录
	 */
	public ErrorRecord addErrorRecord(ErrorRecord errorRecord) {
		return errorRecordRepository.save(errorRecord);
	}

	/**
	 * 查询错误记录， 定位到没有结束的错误记录
	 */
	public List<ErrorRecord> queryErrorRecord(String machineId) {
		return errorRecordReadRepository.findByMachineIdAndEndTimeIsNull(machineId);
	}

	public List<ErrorRecord> queryErrorRecordByCoffeeId(String machineId) {
		List<ErrorRecord> list = errorRecordReadRepository.findByMachineId(machineId);
		if(null == list || list.isEmpty())
			return null;
		return list;
	}
	/**
	 * 同时更新的数量小，所以不做分批处理
	 */
	@Transactional
	public void batchUpdate(List<ErrorRecord> ers) {
		ers.stream().forEach(error -> entityManager.merge(error));
		entityManager.flush();
		entityManager.clear();
	}
	
	/**
	 * 删除运维人员，同时将其负责的咖啡机信息更新
	 */
	public Boolean removeBatch(List<Integer> errorIds) {
		for (Integer id : errorIds) {
			ErrorRecord error = errorRecordRepository.findOne(id);
			if(null != error) {
				entityManager.remove(error);
			}else {
				return false;
			}
		}
		entityManager.flush();
		entityManager.clear();
		
		return true;
	}

	
	public Page<ErrorRecord> findErrorCriteria(ErrorRecordVo erVo) {
		Integer page = erVo.getPage();
		Integer size = erVo.getSize();
		String sort = erVo.getSort();
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info("canshu:" + erVo + "---" + page + "---" + size);
		Pageable pageable = null;
		if(StringUtils.isNull(sort)) {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
		}else {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, sort);
		}
        return errorRecordReadRepository.findAll(new Specification<ErrorRecord>(){
			@Override
			public Predicate toPredicate(Root<ErrorRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(erVo.getMachineId())){  
					 list.add(cb.equal(root.get("machineId").as(String.class), erVo.getMachineId()));
	             }
				 //判断错误的持续时间是否超过给定的值
				 if(!StringUtils.isNull(erVo.getDurationTime())){  
					 list.add(cb.equal(root.get("durationTime").as(Long.class), erVo.getDurationTime()));
	             }
				 if(!StringUtils.isNull(erVo.getWorkerId())){  
					 list.add(cb.equal(root.get("workerId").as(String.class), erVo.getWorkerId()));
	             }
				 if(!StringUtils.isNull(erVo.getType())){  
					 list.add(cb.equal(root.get("type").as(Integer.class), erVo.getType()));
	             }
				 //开始时间在给定时间之后
				 if(!StringUtils.isNull(erVo.getStartTime())) {
					try {
						 list.add(cb.greaterThan(root.get("startTime").as(Date.class), 
								sdfmat.parse(sdfmat.format(erVo.getStartTime()))));
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				 }
				 //结束时间在给定时间之前
				 if(!StringUtils.isNull(erVo.getEndTime())) {
						try {
							 list.add(cb.lessThan(root.get("endTime").as(Date.class), 
									 sdfmat.parse(sdfmat.format(erVo.getEndTime()))));
						} catch (Exception e) {
							log.error(e.getMessage());
						}
					 }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			}  
        }, pageable);  
	}

	public Long findErrorNumber(ErrorRecordVo erVo) {
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return errorRecordReadRepository.count(new Specification<ErrorRecord>(){
				@Override
				public Predicate toPredicate(Root<ErrorRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					 List<Predicate> list = new ArrayList<>();
					 if(!StringUtils.isNull(erVo.getMachineId())){  
						 list.add(cb.equal(root.get("machineId").as(String.class), erVo.getMachineId()));
		             }
					 //判断错误的持续时间是否超过给定的值
					 if(!StringUtils.isNull(erVo.getDurationTime())){  
						 list.add(cb.equal(root.get("durationTime").as(Long.class), erVo.getDurationTime()));
		             }
					 if(!StringUtils.isNull(erVo.getWorkerId())){  
						 list.add(cb.equal(root.get("workerId").as(String.class), erVo.getWorkerId()));
		             }
					 if(!StringUtils.isNull(erVo.getType())){  
						 list.add(cb.equal(root.get("type").as(Integer.class), erVo.getType()));
		             }
					 //开始时间在给定时间之后
					 if(!StringUtils.isNull(erVo.getStartTime())) {
						try {
							 list.add(cb.greaterThan(root.get("startTime").as(Date.class), 
									sdfmat.parse(sdfmat.format(erVo.getStartTime()))));
						} catch (Exception e) {
							log.error(e.getMessage());
						}
					 }
					 //结束时间在给定时间之前
					 if(!StringUtils.isNull(erVo.getEndTime())) {
							try {
								 list.add(cb.lessThan(root.get("endTime").as(Date.class), 
										 sdfmat.parse(sdfmat.format(erVo.getEndTime()))));
							} catch (Exception e) {
								log.error(e.getMessage());
							}
						 }
					 Predicate[] p = new Predicate[list.size()];  
		             return cb.and(list.toArray(p)); 
				}  
	        });  
	}
	
	public List<ErrorRecord> queryAll() {
		return errorRecordReadRepository.findAll();
	}
	
	public List<ErrorRecord> queryErrorRecordByType(String machineId, Integer type) {
		return errorRecordReadRepository.findByMachineIdAndType(machineId, type);
	}

	public Page<ErrorRecord> queryErrorDown(MachineDownVo downInfo) {
		Integer page = downInfo.getPage();
		Integer size = downInfo.getSize();
		String sort = downInfo.getSort();
		Pageable pageable = null;
		if(StringUtils.isNull(sort)) {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
		}else {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, sort);
		}
		log.info("canshu:" + downInfo + "---" + page + "---" + size);
        return errorRecordReadRepository.findAll(new Specification<ErrorRecord>(){
			@Override
			public Predicate toPredicate(Root<ErrorRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(downInfo.getMachineId())){  
					 list.add(cb.equal(root.get("machineId").as(String.class), downInfo.getMachineId()));
	             }
				 if(!StringUtils.isNull(downInfo.getEmployeeCode())){  
					 list.add(cb.equal(root.get("workerId").as(String.class), downInfo.getEmployeeCode()));
	             }
				 if(!StringUtils.isNull(downInfo.getType())){  
					 list.add(cb.equal(root.get("type").as(String.class), downInfo.getType()));//停机
	             }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			}  
        }, pageable);  
	}

	public Long queryErrorDownSum(MachineDownVo downInfo) {
		return errorRecordReadRepository.count(new Specification<ErrorRecord>(){
			@Override
			public Predicate toPredicate(Root<ErrorRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(downInfo.getMachineId())){  
					 list.add(cb.equal(root.get("machineId").as(String.class), downInfo.getMachineId()));
	             }
				 if(!StringUtils.isNull(downInfo.getEmployeeCode())){  
					 list.add(cb.equal(root.get("workerId").as(String.class), downInfo.getEmployeeCode()));
	             }
				 if(!StringUtils.isNull(downInfo.getType())){  
					 list.add(cb.equal(root.get("type").as(String.class), downInfo.getType()));//停机
	             }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			}  
        }); 
	}
}
