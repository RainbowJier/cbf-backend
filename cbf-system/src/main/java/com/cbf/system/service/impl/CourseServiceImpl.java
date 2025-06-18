package com.cbf.system.service.impl;

import com.cbf.common.utils.DateUtils;
import com.cbf.system.domain.Course;
import com.cbf.system.mapper.CourseMapper;
import com.cbf.system.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程信息Service业务层处理
 *
 * @author Frank
 * @date 2025-06-18
 */
@Service
public class CourseServiceImpl implements ICourseService {
    @Autowired
    private CourseMapper courseMapper;

    /**
     * 查询课程信息
     *
     * @param id 课程信息主键
     * @return 课程信息
     */
    @Override
    public Course selectCourseById(Long id) {
        return courseMapper.selectCourseById(id);
    }

    /**
     * 查询课程信息列表
     *
     * @param course 课程信息
     * @return 课程信息
     */
    @Override
    public List<Course> selectCourseList(Course course) {
        return courseMapper.selectCourseList(course);
    }

    /**
     * 新增课程信息
     *
     * @param course 课程信息
     * @return 结果
     */
    @Override
    public int insertCourse(Course course) {
        course.setCreateTime(DateUtils.getNowDate());
        return courseMapper.insertCourse(course);
    }

    /**
     * 修改课程信息
     *
     * @param course 课程信息
     * @return 结果
     */
    @Override
    public int updateCourse(Course course) {
        course.setUpdateTime(DateUtils.getNowDate());
        return courseMapper.updateCourse(course);
    }

    /**
     * 批量删除课程信息
     *
     * @param ids 需要删除的课程信息主键
     * @return 结果
     */
    @Override
    public int deleteCourseByIds(Long[] ids) {
        return courseMapper.deleteCourseByIds(ids);
    }

    /**
     * 删除课程信息信息
     *
     * @param id 课程信息主键
     * @return 结果
     */
    @Override
    public int deleteCourseById(Long id) {
        return courseMapper.deleteCourseById(id);
    }
}
