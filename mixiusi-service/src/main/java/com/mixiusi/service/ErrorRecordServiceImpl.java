package com.mixiusi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.mixiusi.bean.ErrorRecord;
import com.mixiusi.bean.vo.ErrorRecordVo;
import com.mixiusi.bean.vo.MachineDownVo;
import com.mixiusi.biz.ErrorRecordBiz;

/**
 * 咖啡机错误记录service层
 * @author liukun
 *	
 */
@Service(interfaceClass = ErrorRecordService.class)
public class ErrorRecordServiceImpl implements ErrorRecordService{
	@Autowired
	private ErrorRecordBiz errorRecordBiz;

	/**
	 * 添加错误记录
	 */
	@Override
	public ErrorRecord addErrorRecord(ErrorRecord errorRecord) {
		return errorRecordBiz.addErrorRecord(errorRecord);
	}

	/**
	 * 查询错误记录， 定位到没有结束的错误记录
	 */
	@Override
	public List<ErrorRecord> queryErrorRecord(String machineId) {
		return errorRecordBiz.queryErrorRecord(machineId);
	}

	@Override
	public List<ErrorRecord> queryErrorRecordByCoffeeId(String machineId) {
		return errorRecordBiz.queryErrorRecordByCoffeeId(machineId);
	}
	/**
	 * 同时更新的数量小，所以不做分批处理
	 */
	@Transactional
	@Override
	public void batchUpdate(List<ErrorRecord> ers) {
		errorRecordBiz.batchUpdate(ers);
	}

	@Override
	public List<ErrorRecord> findErrorCriteria(ErrorRecordVo errorRecordVo) {
		return errorRecordBiz.findErrorCriteria(errorRecordVo).getContent();
	}

	@Override
	public List<ErrorRecord> queryAll() {
		return errorRecordBiz.queryAll();
	}

	@Override
	public List<ErrorRecord> queryErrorRecordByType(String machineId, Integer type) {
		return errorRecordBiz.queryErrorRecordByType(machineId, type);
	}

	@Override
	public Long findErrorNumber(ErrorRecordVo errorRecordVo) {
		return errorRecordBiz.findErrorNumber(errorRecordVo);
	}

	@Override
	public List<ErrorRecord> queryErrorDown(MachineDownVo downInfo) {
		return errorRecordBiz.queryErrorDown(downInfo).getContent();
	}

	@Override
	public Long queryErrorDownSum(MachineDownVo downInfo) {
		return errorRecordBiz.queryErrorDownSum(downInfo);
	}

	@Override
	public Boolean removeBatch(List<Integer> errorIds) {
		return errorRecordBiz.removeBatch(errorIds);
	}

}
