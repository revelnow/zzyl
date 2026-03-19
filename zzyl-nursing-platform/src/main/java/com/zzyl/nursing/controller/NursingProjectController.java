package com.zzyl.nursing.controller;

import com.zzyl.common.annotation.Log;
import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.common.core.domain.R;
import com.zzyl.common.core.page.TableDataInfo;
import com.zzyl.common.enums.BusinessType;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.nursing.domain.NursingProject;
import com.zzyl.nursing.service.INursingProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 护理项目Controller
 *
 * @author alexis
 * @date 2025-05-20
 */
@RestController
@RequestMapping("/nursing/project")
@Api(value = "护理项目管理接口")
public class NursingProjectController extends BaseController
{
    @Autowired
    private INursingProjectService nursingProjectService;

    /**
     * 查询护理项目列表
     *
     * @param nursingProject 查询条件对象
     * @return 分页后的护理项目列表数据
     */
    @PreAuthorize("@ss.hasPermi('nursing:project:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询护理项目列表")
    public TableDataInfo<List<NursingProject>> list(
            @ApiParam(value = "查询条件对象") NursingProject nursingProject
    )
    {
        startPage();
        List<NursingProject> list = nursingProjectService.selectNursingProjectList(nursingProject);
        return getDataTable(list);
    }

    /**
     * 导出护理项目列表
     *
     * @param response HTTP响应对象，用于导出Excel文件
     * @param nursingProject 查询条件对象
     */
    @PreAuthorize("@ss.hasPermi('nursing:project:export')")
    @Log(title = "护理项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation(value = "导出护理项目列表")
    public void export(
            @ApiParam(value = "HTTP响应对象") HttpServletResponse response,
            @ApiParam(value = "查询条件对象") NursingProject nursingProject
    )
    {
        List<NursingProject> list = nursingProjectService.selectNursingProjectList(nursingProject);
        ExcelUtil<NursingProject> util = new ExcelUtil<>(NursingProject.class);
        util.exportExcel(response, list, "护理项目数据");
    }

    /**
     * 获取护理项目详细信息
     *
     * @param id 护理项目ID
     * @return 包含护理项目详细信息的AjaxResult对象
     */
    @PreAuthorize("@ss.hasPermi('nursing:project:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取护理项目详细信息")
    public R<NursingProject> getInfo(
            @ApiParam(value = "护理项目ID") @PathVariable("id") Long id
    )
    {
        return R.ok(nursingProjectService.selectNursingProjectById(id));
    }

    /**
     * 新增护理项目
     *
     * @param nursingProject 新增的护理项目对象
     * @return 操作结果的AjaxResult对象
     */
    @PreAuthorize("@ss.hasPermi('nursing:project:add')")
    @Log(title = "护理项目", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增护理项目")
    public AjaxResult add(
            @ApiParam(value = "新增的护理项目对象") @RequestBody NursingProject nursingProject
    )
    {
        return toAjax(nursingProjectService.insertNursingProject(nursingProject));
    }

    /**
     * 修改护理项目
     *
     * @param nursingProject 修改后的护理项目对象
     * @return 操作结果的AjaxResult对象
     */
    @PreAuthorize("@ss.hasPermi('nursing:project:edit')")
    @Log(title = "护理项目", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改护理项目")
    public AjaxResult edit(
            @ApiParam(value = "修改后的护理项目对象") @RequestBody NursingProject nursingProject
    )
    {
        return toAjax(nursingProjectService.updateNursingProject(nursingProject));
    }

    /**
     * 删除护理项目
     *
     * @param ids 要删除的护理项目ID数组
     * @return 操作结果的AjaxResult对象
     */
    @PreAuthorize("@ss.hasPermi('nursing:project:remove')")
    @Log(title = "护理项目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除护理项目")
    public AjaxResult remove(
            @ApiParam(value = "要删除的护理项目ID数组") @PathVariable Long[] ids
    )
    {
        return toAjax(nursingProjectService.deleteNursingProjectByIds(ids));
    }
}