package com.mixiusi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mixiusi.bean.ErrorRecord;
import com.mixiusi.bean.vo.ErrorRecordVo;
import com.mixiusi.bean.vo.MachineDownVo;

public interface ErrorRecordService {
	//无条件分页查询
//	Page<ErrorRecord> findErrorNoCriteria(Integer page,Integer size);
	//条件查询，并进行分页显示
	Page<ErrorRecord> findErrorCriteria(ErrorRecordVo errorRecordVo);
	
	List<ErrorRecord> queryAll();
	/**
	 * 查询没有结束时间的错误记录
	 * @param machineId
	 * @return
	 */
	List<ErrorRecord> queryErrorRecord(String machineId);
	/**
	 * 查询某台咖啡机指定类型的错误信息
	 * @param machineId
	 * @param type
	 * @return
	 */
	List<ErrorRecord> queryErrorRecordByType(String machineId, Integer type);
	/**
	 * 根据咖啡机ID查询错误信息
	 * @param machineId
	 * @return
	 */
	List<ErrorRecord> queryErrorRecordByCoffeeId(String machineId);
	/**
	 * 条件查询错误数量
	 * @param errorRecordVo
	 * @return
	 */
	Long findErrorNumber(ErrorRecordVo errorRecordVo);
	
	/**
	 * 查询咖啡机的停机记录
	 * @param downInfo
	 * @return
	 */
	Page<ErrorRecord> queryErrorDown(MachineDownVo downInfo);
	Long queryErrorDownSum(MachineDownVo downInfo);
	
	
}
