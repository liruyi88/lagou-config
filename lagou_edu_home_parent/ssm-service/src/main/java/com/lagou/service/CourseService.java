package com.lagou.service;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;
import com.lagou.domain.Teacher;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CourseService {
    public List<Course> findCourseByCondition(CourseVO courseVO);
    /**
     * 保存课程及讲师信息
     */
    public void saveCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;
    /**
     * 课程信息回显
     */
    public CourseVO findCourseById(int id);
    /**
     * 修改课程信息
     */
    public void updateCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;
    /**
     * 课程状态管理
     */
    public void updateCourseStatus(int courseid,int status);

}
