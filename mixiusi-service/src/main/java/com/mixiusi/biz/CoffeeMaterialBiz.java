package com.mixiusi.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mixiusi.repository.read.CoffeeMaterialReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mixiusi.bean.CoffeeMaterial;
import com.mixiusi.bean.utils.DateUtils;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.vo.CoffeeMaterialVo;
import com.mixiusi.repository.write.CoffeeMaterialRepository;

@Transactional
@Service
public class CoffeeMaterialBiz{
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private CoffeeMaterialRepository coffeeMaterialRepository;
	@Autowired
	private CoffeeMaterialReadRepository coffeeMaterialReadRepository;

	public void addMaterial(CoffeeMaterial material) {
		coffeeMaterialRepository.save(material);
	}


	public List<CoffeeMaterial> queryMaterialByMachineId(String machineId) {
		return coffeeMaterialReadRepository.findByMachineId(machineId);
	}


	public CoffeeMaterial queryMaterialByIdAndNumber(String machineId, Integer number) {
		return coffeeMaterialReadRepository.findByMachineIdAndStackNumber(machineId, number);
	}

	@Transactional
	public void batchInsertMaterial(List<CoffeeMaterial> list) {
		//这里list只有11条数据，所以省略一次性插入条数的判断
		list.stream().forEach(cm -> {
			entityManager.persist(cm);
		});
		entityManager.flush();
		entityManager.clear();
	}


	public List<CoffeeMaterial> queryMaterialByStatus(String machineId, Integer status) {
		return coffeeMaterialReadRepository.findByMachineIdAndStatus(machineId, status);
	}


	public List<CoffeeMaterial> queryMaterialByStatusAndTime(String machineId, Integer status) {
		return coffeeMaterialReadRepository.findByMachineIdAndStatusAndDangerTimeBetween(machineId, status, DateUtils.getFirstDayOfMonth(), new Date());
	}
	
	
	public Page<CoffeeMaterial> queryCoffeeMaterial(CoffeeMaterialVo cvo) {
		Integer page = cvo.getPage();
		Integer size = cvo.getSize();
		String sort = cvo.getSort();
		Pageable pageable = null;
		if(StringUtils.isNull(sort)) {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
		}else {
			pageable = new PageRequest(page, size, Sort.Direction.DESC, sort);
		}
        return coffeeMaterialReadRepository.findAll(new Specification<CoffeeMaterial>(){
			@Override
			public Predicate toPredicate(Root<CoffeeMaterial> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(cvo.getMachineId())){  
					 list.add(cb.equal(root.get("machineId").as(String.class), cvo.getMachineId()));
	             }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			}  
        }, pageable); 
	}

	public Long queryCoffeeMaterialSum(CoffeeMaterialVo cvo) {
		 return coffeeMaterialReadRepository.count(new Specification<CoffeeMaterial>(){
				@Override
				public Predicate toPredicate(Root<CoffeeMaterial> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					 List<Predicate> list = new ArrayList<>();
					 if(!StringUtils.isNull(cvo.getMachineId())){  
						 list.add(cb.equal(root.get("machineId").as(String.class), cvo.getMachineId()));
		             }
					 Predicate[] p = new Predicate[list.size()];  
		             return cb.and(list.toArray(p)); 
				}  
	        }); 
	}
}
