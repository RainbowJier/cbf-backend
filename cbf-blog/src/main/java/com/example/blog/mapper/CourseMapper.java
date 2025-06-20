package com.example.blog.mapper;


import com.example.blog.domain.Course;

import java.util.List;


/**
 * 课程信息Mapper接口
 *
 * @author Frank
 * @date 2025-06-18
 */
public interface CourseMapper {
    /**
     * 查询课程信息
     *
     * @param id 课程信息主键
     * @return 课程信息
     */
    Course selectCourseById(Long id);

    /**
     * 查询课程信息列表
     *
     * @param course 课程信息
     * @return 课程信息集合
     */
    List<Course> selectCourseList(Course course);

    /**
     * 新增课程信息
     *
     * @param course 课程信息
     * @return 结果
     */
    int insertCourse(Course course);

    /**
     * 修改课程信息
     *
     * @param course 课程信息
     * @return 结果
     */
    int updateCourse(Course course);

    /**
     * 删除课程信息
     *
     * @param id 课程信息主键
     * @return 结果
     */
    int deleteCourseById(Long id);

    /**
     * 批量删除课程信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteCourseByIds(Long[] ids);
}
