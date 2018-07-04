package com.mixiusi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.MobileCoffeeList;
import com.mixiusi.bean.vo.MobilePayDetailVo;

public interface MobileCoffeelistService {
	List<MobileCoffeeList> queryCoffeeAll(String indentId);

	Page<MobileCoffeeList> queryMobilePayDetail(MobilePayDetailVo mpdvo);

	Long queryDetailSumNumber(MobilePayDetailVo mpdvo);
}
