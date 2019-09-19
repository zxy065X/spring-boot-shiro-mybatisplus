
package com.java.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.java.common.springbootcommon.utils.PageUtils;
import com.java.modules.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	PageUtils queryPage(Map<String, Object> params);
	
}
