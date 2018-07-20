package com.mixiusi.biz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mixiusi.repository.read.UserPermissionReadRepository;
import com.mixiusi.repository.read.UserReadRepository;
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

import com.mixiusi.bean.Permission;
import com.mixiusi.bean.User;
import com.mixiusi.bean.UserPermission;
import com.mixiusi.bean.utils.StringUtils;
import com.mixiusi.bean.vo.UserVo;
import com.mixiusi.repository.read.PermissionReadRepository;
import com.mixiusi.repository.write.UserPermissionRepository;
import com.mixiusi.repository.write.UserRepository;

/**
 * 
 * @author liukun
 *
 */
@Transactional
@Service
public class UserServiceBiz{
	private final Logger log = LoggerFactory.getLogger(UserServiceBiz.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserReadRepository userReadRepository;
	@Autowired
	private PermissionReadRepository permissionReadRepository;
	@Autowired
	private UserPermissionRepository userPermissionRepository;
	@Autowired
	private UserPermissionReadRepository userPermissionReadRepository;

	public Page<User> findAll(UserVo uvo) {
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Pageable pageable = new PageRequest(uvo.getPage(), uvo.getSize(), Sort.Direction.DESC, "id");
        return userReadRepository.findAll(new Specification<User>(){
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(uvo.getUserName())){  
					 list.add(cb.equal(root.get("userName").as(String.class), uvo.getUserName()));
	             }
				 //判断错误的持续时间是否超过给定的值
				 if(!StringUtils.isNull(uvo.getEmail())){  
					 list.add(cb.equal(root.get("email").as(Long.class), uvo.getEmail()));
	             }
				 if(!StringUtils.isNull(uvo.getIsAdmin())){  
					 list.add(cb.equal(root.get("isAdmin").as(Boolean.class), uvo.getIsAdmin()));
	             }
				 if(!StringUtils.isNull(uvo.getSex())){  
					 list.add(cb.equal(root.get("sex").as(Boolean.class), uvo.getSex()));
	             }
				 if(!StringUtils.isNull(uvo.getCreateDate())) {
						try {
							 list.add(cb.lessThan(root.get("createDate").as(Date.class), 
									 sdfmat.parse(sdfmat.format(uvo.getCreateDate()))));
						} catch (Exception e) {
							log.error(e.getMessage());
						}
					 }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			}  
        }, pageable);  
	}

	@Transactional
	public User add(User user) {
    	return userRepository.save(user);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean delete(Integer uid) {
		User user = this.load(uid);
		if(null == user)
			return false;
		userRepository.delete(user);
		userPermissionRepository.deleteByUid(uid);
		return true;
		
	}

	public User load(Integer uid) {
		 return userRepository.findOne(uid);
	}

	public User findByUserName(String username) {
		 return userRepository.findByUserName(username);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addUserPermissions(Integer uid, List<Integer> pids) {
		for (Integer pid : pids) {
			this.addUserPermission(uid, pid);
		}
		
	}

	public User findByUserNamePassword(String username, String password) {
		return userReadRepository.findByUserNameAndPassword(username, password);
	}


	public List<Permission> listUserPermissions(Integer uid) {
		return userPermissionReadRepository.findAllPermissionByUid(uid);
	}
	
	private void addUserPermission(Integer uid, Integer pid) {
		UserPermission up = userPermissionReadRepository.findByUidAndPid(uid, pid);
		if (up != null) {
			return;
		}
		up = new UserPermission(userReadRepository.findOne(uid), permissionReadRepository.findOne(pid));
		userPermissionRepository.save(up);
	}


	public Long findUserNumber(UserVo uvo) {
		SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return userReadRepository.count(new Specification<User>(){
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 List<Predicate> list = new ArrayList<>();
				 if(!StringUtils.isNull(uvo.getUserName())){  
					 list.add(cb.equal(root.get("userName").as(String.class), uvo.getUserName()));
	             }
				 //判断错误的持续时间是否超过给定的值
				 if(!StringUtils.isNull(uvo.getEmail())){  
					 list.add(cb.equal(root.get("email").as(Long.class), uvo.getEmail()));
	             }
				 if(!StringUtils.isNull(uvo.getIsAdmin())){  
					 list.add(cb.equal(root.get("isAdmin").as(Boolean.class), uvo.getIsAdmin()));
	             }
				 if(!StringUtils.isNull(uvo.getSex())){  
					 list.add(cb.equal(root.get("sex").as(Boolean.class), uvo.getSex()));
	             }
				 if(!StringUtils.isNull(uvo.getCreateDate())) {
						try {
							 list.add(cb.lessThan(root.get("createDate").as(Date.class), 
									 sdfmat.parse(sdfmat.format(uvo.getCreateDate()))));
						} catch (Exception e) {
							log.error(e.getMessage());
						}
					 }
				 Predicate[] p = new Predicate[list.size()];  
	             return cb.and(list.toArray(p)); 
			}  
        });  
	}
}
