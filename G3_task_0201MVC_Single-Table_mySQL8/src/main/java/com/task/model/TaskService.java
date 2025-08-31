package com.task.model;

import java.util.List;

public class TaskService {

	private TaskDAO_interface dao;

	public TaskService() {
		dao = new TaskDAO();
	}

	public TaskVO addTask(Integer taskTypeId, String taskName, Integer targetValue, String unit,
			java.sql.Date startTime, java.sql.Date endTime, Byte points, String taskIcon, Integer adminId) {

		TaskVO taskVO = new TaskVO();

		taskVO.setTaskTypeId(taskTypeId);
		taskVO.setTaskName(taskName);
		taskVO.setTargetValue(targetValue);
		taskVO.setUnit(unit);
		taskVO.setStartTime(startTime);
		taskVO.setEndTime(endTime);
		taskVO.setPoints(points);
		taskVO.setTaskIcon(taskIcon);
		taskVO.setAdminId(adminId);

		dao.insert(taskVO);

		return taskVO;
	}

	public TaskVO updateTask(Integer taskId, Integer taskTypeId, String taskName, Integer targetValue, String unit,
			java.sql.Date startTime, java.sql.Date endTime, Byte points, String taskIcon, Integer adminId) {

		TaskVO taskVO = new TaskVO();

		taskVO.setTaskId(taskId);
		taskVO.setTaskTypeId(taskTypeId);
		taskVO.setTaskName(taskName);
		taskVO.setTargetValue(targetValue);
		taskVO.setUnit(unit);
		taskVO.setStartTime(startTime);
		taskVO.setEndTime(endTime);
		taskVO.setPoints(points);
		taskVO.setTaskIcon(taskIcon);
		taskVO.setAdminId(adminId);

		dao.update(taskVO);

		return taskVO;
	}

	public void deleteTask(Integer taskId) {
		dao.delete(taskId);
	}

	public TaskVO getOneTask(Integer taskId) {
		return dao.findByPrimaryKey(taskId);
	}

	public List<TaskVO> getAll() {
		return dao.getAll();
	}
}
