package com.cbf.web.controller.course;

import com.cbf.common.annotation.Log;
import com.cbf.common.core.controller.BaseController;
import com.cbf.common.core.domain.AjaxResult;
import com.cbf.common.core.page.TableDataInfo;
import com.cbf.common.enums.BusinessType;
import com.cbf.common.utils.easyexcel.ExcelExportUtil;
import com.cbf.common.utils.easyexcel.handler.AdvancedStyleHandler;
import com.example.blog.domain.Course;
import com.example.blog.service.ICourseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 课程信息
 *
 * @author Frank
 * @date 2025-06-18
 */
@RestController
@RequestMapping("/course/course")
public class CourseController extends BaseController {
    @Resource
    private ICourseService courseService;

    /**
     * 查询课程信息列表
     */
    @PreAuthorize("@ss.hasPermi('course:course:list')")
    @GetMapping("/list")
    public TableDataInfo list(Course course) {
        startPage();
        List<Course> list = courseService.selectCourseList(course);
        return getDataTable(list);
    }

    /**
     * 导出课程信息列表
     */
    @PreAuthorize("@ss.hasPermi('course:course:export')")
    @Log(title = "课程信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Course course) {
        List<Course> list = courseService.selectCourseList(course);
        ExcelExportUtil.export(
                response,
                list,
                Course.class,
                "课程信息导出",
                "课程信息",
                List.of(new AdvancedStyleHandler())
        );
    }

    /**
     * 获取课程信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('course:course:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(courseService.selectCourseById(id));
    }

    /**
     * 新增课程信息
     */
    @PreAuthorize("@ss.hasPermi('course:course:add')")
    @Log(title = "课程信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Course course) {
        return toAjax(courseService.insertCourse(course));
    }

    /**
     * 修改课程信息
     */
    @PreAuthorize("@ss.hasPermi('course:course:edit')")
    @Log(title = "课程信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Course course) {
        return toAjax(courseService.updateCourse(course));
    }

    /**
     * 删除课程信息
     */
    @PreAuthorize("@ss.hasPermi('course:course:remove')")
    @Log(title = "课程信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(courseService.deleteCourseByIds(ids));
    }
}
