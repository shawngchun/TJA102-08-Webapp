package com.task.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TaskDAO implements TaskDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO task (task_type_id, task_name, target_value, unit, "
			+ "start_time, end_time, points, task_icon, admin_id) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT task_id, task_type_id, task_name, target_value,unit, start_time, end_time, points, task_icon, admin_id FROM task ORDER BY task_id";
	private static final String GET_ONE_STMT = "SELECT task_id, task_type_id, task_name, target_value, unit, start_time, end_time, points, task_icon, admin_id FROM task WHERE task_id = ?";
	private static final String DELETE = "DELETE FROM task where task_id = ?";
	private static final String UPDATE = "UPDATE task SET task_type_id=?, task_name=?, target_value=?, unit=?, "
			+ " start_time=?, end_time=?, points=?, task_icon=?, admin_id=? WHERE task_id=?";

	@Override
	public void insert(TaskVO taskVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, taskVO.getTaskTypeId());
			pstmt.setString(2, taskVO.getTaskName());
			pstmt.setInt(3, taskVO.getTargetValue());
			pstmt.setString(4, taskVO.getUnit());
			pstmt.setDate(5, taskVO.getStartTime());
			pstmt.setDate(6, taskVO.getEndTime());
			pstmt.setByte(7, taskVO.getPoints());
			pstmt.setString(8, taskVO.getTaskIcon());
			pstmt.setInt(9, taskVO.getAdminId());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(TaskVO taskVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, taskVO.getTaskTypeId());
			pstmt.setString(2, taskVO.getTaskName());
			pstmt.setInt(3, taskVO.getTargetValue());
			pstmt.setString(4, taskVO.getUnit());
			pstmt.setDate(5, taskVO.getStartTime());
			pstmt.setDate(6, taskVO.getEndTime());
			pstmt.setByte(7, taskVO.getPoints());
			pstmt.setString(8, taskVO.getTaskIcon());
			pstmt.setInt(9, taskVO.getAdminId());
			pstmt.setInt(10, taskVO.getTaskId()); // WHERE 條件

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(Integer taskId) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, taskId);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public TaskVO findByPrimaryKey(Integer taskId) {

		TaskVO taskVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, taskId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// taskVo 也稱為 Domain objects
				taskVO = new TaskVO();
			    taskVO.setTaskId(rs.getInt("task_id"));
			    taskVO.setTaskTypeId(rs.getInt("task_type_id"));
			    taskVO.setTaskName(rs.getString("task_name"));
			    taskVO.setTargetValue(rs.getInt("target_value"));
			    taskVO.setUnit(rs.getString("unit"));
			    taskVO.setStartTime(rs.getDate("start_time"));
			    taskVO.setEndTime(rs.getDate("end_time"));
			    taskVO.setPoints(rs.getByte("points"));
			    taskVO.setTaskIcon(rs.getString("task_icon"));
			    taskVO.setAdminId(rs.getInt("admin_id"));

			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return taskVO;
	}

	@Override
	public List<TaskVO> getAll() {
		List<TaskVO> list = new ArrayList<TaskVO>();
		TaskVO taskVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// taskVO 也稱為 Domain objects
				taskVO = new TaskVO();
	            taskVO.setTaskId(rs.getInt("task_id"));
	            taskVO.setTaskTypeId(rs.getInt("task_type_id"));
	            taskVO.setTaskName(rs.getString("task_name"));
	            taskVO.setTargetValue(rs.getInt("target_value"));
	            taskVO.setUnit(rs.getString("unit"));
	            taskVO.setStartTime(rs.getDate("start_time"));
	            taskVO.setEndTime(rs.getDate("end_time"));
	            taskVO.setPoints(rs.getByte("points"));
	            taskVO.setTaskIcon(rs.getString("task_icon"));
	            taskVO.setAdminId(rs.getInt("admin_id"));

	            list.add(taskVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
}