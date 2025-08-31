package com.task.model;

import java.util.*;

public interface TaskDAO_interface {
          public void insert(TaskVO taskVO);
          public void update(TaskVO taskVO);
          public void delete(Integer taskId);
          public TaskVO findByPrimaryKey(Integer taskId);
          public List<TaskVO> getAll();
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<EmpVO> getAll(Map<String, String[]> map); 
}
