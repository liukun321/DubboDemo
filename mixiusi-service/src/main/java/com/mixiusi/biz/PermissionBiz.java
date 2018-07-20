package com.mixiusi.biz;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mixiusi.bean.Permission;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.vo.PermissionVo;
import com.mixiusi.repository.read.PermissionReadRepository;
import com.mixiusi.repository.write.PermissionRepository;
import com.mixiusi.repository.write.UserPermissionRepository;

/**
 * 
 * @author liukun
 *
 */
@Transactional
@Service
public class PermissionBiz{
	private final Logger log = LoggerFactory.getLogger(PermissionBiz.class);
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private PermissionReadRepository permissionReadRepository;
	@Autowired
	private UserPermissionRepository userPermissionRepository;

	public Permission add(Permission permission) {
		return permissionRepository.save(permission);
	}

	public Page<Permission> findAll(PermissionVo pvo) {
		log.info("canshu:" + pvo + "---" + pvo.getPage() + "---" + pvo.getSize());
		Pageable pageable = new PageRequest(pvo.getPage(), pvo.getSize(), Sort.Direction.DESC, "id");
        return permissionReadRepository.findAll(new Specification<Permission>(){  
			@Override
			public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(pvo.getName())){  
					 list.add(cb.equal(root.get("name").as(String.class), pvo.getName()));
	             }
				 //判断错误的持续时间是否超过给定的值
				 if(!StringUtils.isNull(pvo.getDescription())){  
					 list.add(cb.equal(root.get("description").as(Long.class), pvo.getDescription()));
	             }
				 if(!StringUtils.isNull(pvo.getUrl())){  
					 list.add(cb.equal(root.get("url").as(String.class), pvo.getUrl()));
	             }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			}  
        }, pageable);  
	}
	@Transactional
	public void delete(Integer pid) {
		//删除权限信息，在用户权限表中的引用也要删除
		permissionRepository.delete(pid);
		userPermissionRepository.deleteByPid(pid);
	}

	public Permission queryPermission(Integer pid) {
		
		return permissionReadRepository.findOne(pid);
	}

	public Long findPermissionNumber(PermissionVo pvo) {
		return permissionReadRepository.count(new Specification<Permission>(){  
			@Override
			public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(pvo.getName())){  
					 list.add(cb.equal(root.get("name").as(String.class), pvo.getName()));
	             }
				 //判断错误的持续时间是否超过给定的值
				 if(!StringUtils.isNull(pvo.getDescription())){  
					 list.add(cb.equal(root.get("description").as(Long.class), pvo.getDescription()));
	             }
				 if(!StringUtils.isNull(pvo.getUrl())){  
					 list.add(cb.equal(root.get("url").as(String.class), pvo.getUrl()));
	             }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			}  
        });  
	}
	
	
}
