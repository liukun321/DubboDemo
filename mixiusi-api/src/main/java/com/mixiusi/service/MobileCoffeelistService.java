package com.mixiusi.service;

import java.util.List;

import com.mixiusi.bean.MobileCoffeeList;
import com.mixiusi.bean.vo.MobilePayDetailVo;

public interface MobileCoffeelistService {
	List<MobileCoffeeList> queryCoffeeAll(String indentId);

	List<MobileCoffeeList> queryMobilePayDetail(MobilePayDetailVo mpdvo);

	Long queryDetailSumNumber(MobilePayDetailVo mpdvo);
}
