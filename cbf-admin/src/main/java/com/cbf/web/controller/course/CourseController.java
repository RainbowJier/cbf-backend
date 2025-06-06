package com.cbf.web.controller.course;

import com.cbf.common.annotation.Log;
import com.cbf.common.core.controller.BaseController;
import com.cbf.common.core.domain.AjaxResult;
import com.cbf.common.core.page.TableDataInfo;
import com.cbf.common.enums.BusinessType;
import com.cbf.common.utils.poi.ExcelUtil;
import com.cbf.system.domain.Course;
import com.cbf.system.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 课程管理Controller
 *
 * @author Frank
 * @date 2025-05-31
 */
@RestController
@RequestMapping("/course/course")
public class CourseController extends BaseController {
    @Autowired
    private ICourseService courseService;

    /**
     * 查询课程管理列表
     */
    @PreAuthorize("@ss.hasPermi('course:course:list')")
    @GetMapping("/list")
    public TableDataInfo list(Course course) {
        startPage();
        List<Course> list = courseService.selectCourseList(course);
        return getDataTable(list);
    }

    /**
     * 导出课程管理列表
     */
    @PreAuthorize("@ss.hasPermi('course:course:export')")
    @Log(title = "课程管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Course course) {
        List<Course> list = courseService.selectCourseList(course);
        ExcelUtil<Course> util = new ExcelUtil<>(Course.class);
        util.exportExcel(response, list, "课程管理数据");
    }

    /**
     * 获取课程管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('course:course:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(courseService.selectCourseById(id));
    }

    /**
     * 新增课程管理
     */
    @PreAuthorize("@ss.hasPermi('course:course:add')")
    @Log(title = "课程管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Course course) {
        return toAjax(courseService.insertCourse(course));
    }

    /**
     * 修改课程管理
     */
    @PreAuthorize("@ss.hasPermi('course:course:edit')")
    @Log(title = "课程管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Course course) {
        return toAjax(courseService.updateCourse(course));
    }

    /**
     * 删除课程管理
     */
    @PreAuthorize("@ss.hasPermi('course:course:remove')")
    @Log(title = "课程管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(courseService.deleteCourseByIds(ids));
    }
}
