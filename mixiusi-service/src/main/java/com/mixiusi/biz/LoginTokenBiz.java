package com.mixiusi.biz;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mixiusi.repository.read.LoginTokenReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixiusi.bean.LoginToken;
import com.mixiusi.repository.write.LoginTokenRepository;
@Service
public class LoginTokenBiz {
	@Autowired
	private LoginTokenRepository loginTokenRepository;
	@Autowired
	private LoginTokenReadRepository loginTokenReadRepository;
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	public LoginToken addLoginToken(LoginToken loginToken) {
		this.setAllByStatus();
		return loginTokenRepository.save(loginToken);
	}

	public LoginToken query(String workerId) {
		return loginTokenReadRepository.findByWorkerIdAndFailureAndExpireTimeAfter(workerId, false, new Date());
	}

	public void setAllByStatus() {
		List<LoginToken> list = loginTokenReadRepository.findByFailure(false);
		if(!list.isEmpty()) {
			for (LoginToken lt : list) {
				lt.setFailure(true);
				loginTokenRepository.save(lt);
			}
		}
	}

}
