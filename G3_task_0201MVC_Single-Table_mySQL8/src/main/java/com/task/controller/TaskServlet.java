package com.task.controller;

import java.io.*;
import java.util.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import com.task.model.*;

public class TaskServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求++++++++++++++++++++++++++++++++

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("taskId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入任務編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/task/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer taskId = null;
			try {
				taskId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("任務編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/task/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			TaskService taskSvc = new TaskService();
			TaskVO taskVO = taskSvc.getOneTask(taskId);
			if (taskVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/task/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("taskVO", taskVO); // 資料庫取出的taskVO物件,存入req
			String url = "/task/listOneTask.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneTask.jsp
			successView.forward(req, res);
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllTask.jsp的請求++++++++++++++++++++++++++++++++++

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer taskId = Integer.valueOf(req.getParameter("taskId"));

			/*************************** 2.開始查詢資料 ****************************************/
			TaskService taskSvc = new TaskService();
			TaskVO taskVO = taskSvc.getOneTask(taskId);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("taskVO", taskVO); // 資料庫取出的taskVO物件,存入req
			String url = "/task/update_task_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_task_input.jsp
			successView.forward(req, res);
		}

		if ("update".equals(action)) { // 來自update_task_input.jsp的請求+++++++++++++++++++++++++++++++++++++++

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer taskId = Integer.valueOf(req.getParameter("taskId").trim());
			// ==================================================================================
			String taskTypeIdStr = req.getParameter("taskTypeId");
			Integer taskTypeId = null;

			if (taskTypeIdStr == null || taskTypeIdStr.isBlank()) { // isBlank() 自動判斷 null / 空白字元
				errorMsgs.add("任務類型ID請勿空白");
			} else {
				taskTypeId = Integer.valueOf(taskTypeIdStr);
			}
			// ==================================================================================
			String taskName = req.getParameter("taskName");
			String taskNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,100}$";
			if (taskName == null || taskName.trim().length() == 0) {
				errorMsgs.add("任務名稱: 請勿空白");
			} else if (!taskName.trim().matches(taskNameReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("任務名稱: 只能是中、英文字母、數字和_ , 且長度必需在1到100之間");
			}
			// ==================================================================================

			String targetValueStr = req.getParameter("targetValue");
			Integer targetValue = null;

			if (targetValueStr == null || targetValueStr.isBlank()) { // isBlank() 自動判斷 null / 空白字元
				errorMsgs.add("任務類型ID請勿空白");
			} else {
				targetValue = Integer.valueOf(targetValueStr);
			}
			// ==================================================================================
			String unit = req.getParameter("unit");
			String unitReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,20}$";
			if (unit == null || unit.trim().length() == 0) {
				errorMsgs.add("單位名稱: 請勿空白");
			} else if (!unit.trim().matches(unitReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("單位名稱: 只能是中、英文字母、數字和_ , 且長度必需在1到20之間");
			}
			// ==================================================================================
			java.sql.Date startTime = null;
			try {
				startTime = java.sql.Date.valueOf(req.getParameter("startTime").trim());
			} catch (IllegalArgumentException e) {
				startTime = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.add("請輸入開始日期!");
			}
			// ==================================================================================
			java.sql.Date endTime = null;
			try {
				endTime = java.sql.Date.valueOf(req.getParameter("endTime").trim());
			} catch (IllegalArgumentException e) {
				endTime = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.add("請輸入結束日期!");
			}
			// ==================================================================================
			String pointsStr = req.getParameter("points");
			Byte points = null;

			if (pointsStr == null || pointsStr.isBlank()) { // isBlank() 自動判斷 null / 空白字元
				errorMsgs.add("任務類型ID請勿空白");
			} else {
				points = Byte.valueOf(pointsStr);
			}
			// ==================================================================================
			String taskIcon = req.getParameter("taskIcon");
			String taskIconReg = "^[\\p{IsHan}a-zA-Z0-9_.-]{1,255}$";
			if (taskIcon == null || taskIcon.trim().length() == 0) {
				errorMsgs.add("單位名稱: 請勿空白");
			} else if (!taskIcon.trim().matches(taskIconReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("圖片位址: 只能是中、英文字母、數字和_ , 且長度必需在1到255之間");
			}
			// ==================================================================================
			String adminIdStr = req.getParameter("adminId");
			Integer adminId = null;

			if (adminIdStr == null || adminIdStr.isBlank()) { // isBlank() 自動判斷 null / 空白字元
				errorMsgs.add("管理人員ID請勿空白");
			} else {
				adminId = Integer.valueOf(adminIdStr);
			}
			// ==================================================================================

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

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("taskVO", taskVO); // 含有輸入格式錯誤的taskVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/task/update_task_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			TaskService taskSvc = new TaskService();
			taskVO = taskSvc.updateTask(taskId, taskTypeId, taskName, targetValue, unit, startTime, endTime, points, taskIcon, adminId);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("taskVO", taskVO); // 資料庫update成功後,正確的的taskVO物件,存入req
			String url = "/task/listOneTask.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneTask.jsp
			successView.forward(req, res);
		}

		if ("insert".equals(action)) { // 來自addTask.jsp的請求++++++++++++++++++++++++++++++++++++++

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			
			// ==================================================================================
			String taskTypeIdStr = req.getParameter("taskTypeId");
			Integer taskTypeId = null;

			if (taskTypeIdStr == null || taskTypeIdStr.isBlank()) { // isBlank() 自動判斷 null / 空白字元
				errorMsgs.add("任務類型ID請勿空白");
			} else {
				taskTypeId = Integer.valueOf(taskTypeIdStr);
			}
			// ==================================================================================
			String taskName = req.getParameter("taskName");
			String taskNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,100}$";
			if (taskName == null || taskName.trim().length() == 0) {
				errorMsgs.add("任務名稱: 請勿空白");
			} else if (!taskName.trim().matches(taskNameReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("任務名稱: 只能是中、英文字母、數字和_ , 且長度必需在1到100之間");
			}
			// ==================================================================================

			String targetValueStr = req.getParameter("targetValue");
			Integer targetValue = null;

			if (targetValueStr == null || targetValueStr.isBlank()) { // isBlank() 自動判斷 null / 空白字元
				errorMsgs.add("任務類型ID請勿空白");
			} else {
				targetValue = Integer.valueOf(targetValueStr);
			}
			// ==================================================================================
			String unit = req.getParameter("unit");
			String unitReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,20}$";
			if (unit == null || unit.trim().length() == 0) {
				errorMsgs.add("單位名稱: 請勿空白");
			} else if (!unit.trim().matches(unitReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("單位名稱: 只能是中、英文字母、數字和_ , 且長度必需在1到20之間");
			}
			// ==================================================================================
			java.sql.Date startTime = null;
			try {
				startTime = java.sql.Date.valueOf(req.getParameter("startTime").trim());
			} catch (IllegalArgumentException e) {
				startTime = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.add("請輸入開始日期!");
			}
			// ==================================================================================
			java.sql.Date endTime = null;
			try {
				endTime = java.sql.Date.valueOf(req.getParameter("endTime").trim());
			} catch (IllegalArgumentException e) {
				endTime = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.add("請輸入結束日期!");
			}
			// ==================================================================================
			String pointsStr = req.getParameter("points");
			Byte points = null;

			if (pointsStr == null || pointsStr.isBlank()) { // isBlank() 自動判斷 null / 空白字元
				errorMsgs.add("任務類型ID請勿空白");
			} else {
				points = Byte.valueOf(pointsStr);
			}
			// ==================================================================================
			String taskIcon = req.getParameter("taskIcon");
			String taskIconReg = "^[\\p{IsHan}a-zA-Z0-9_.-]{1,255}$";
			if (taskIcon == null || taskIcon.trim().length() == 0) {
				errorMsgs.add("單位名稱: 請勿空白");
			} else if (!taskIcon.trim().matches(taskIconReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("圖片位址: 只能是中、英文字母、數字和_ , 且長度必需在1到255之間");
			}
			// ==================================================================================
			String adminIdStr = req.getParameter("adminId");
			Integer adminId = null;

			if (adminIdStr == null || adminIdStr.isBlank()) { // isBlank() 自動判斷 null / 空白字元
				errorMsgs.add("管理人員ID請勿空白");
			} else {
				adminId = Integer.valueOf(adminIdStr);
			}
			// ==================================================================================

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

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("taskVO", taskVO); // 含有輸入格式錯誤的taskVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/task/addTask.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			TaskService taskSvc = new TaskService();
			taskVO = taskSvc.addTask(taskTypeId, taskName, targetValue, unit, startTime, endTime, points, taskIcon, adminId);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/task/listAllTask.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllTask.jsp
			successView.forward(req, res);
		}

		if ("delete".equals(action)) { // 來自listAllTask.jsp++++++++++++++++++++++++++++++++++

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer taskId = Integer.valueOf(req.getParameter("taskId"));

			/*************************** 2.開始刪除資料 ***************************************/
			TaskService taskSvc = new TaskService();
			taskSvc.deleteTask(taskId);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/task/listAllTask.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}
	}
}
