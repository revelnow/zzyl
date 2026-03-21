package com.zzyl.nursing.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zzyl.common.core.domain.R;
import com.zzyl.nursing.dto.NursingPlanDto;
import com.zzyl.nursing.vo.NursingPlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zzyl.common.annotation.Log;
import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.common.enums.BusinessType;
import com.zzyl.nursing.domain.NursingPlan;
import com.zzyl.nursing.service.INursingPlanService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;

/**
 * 护理计划Controller
 * 
 * @author theo
 * @date 2026-03-20
 */
@Api("护理计划管理")
@RestController
@RequestMapping("/nursing/plan")
public class NursingPlanController extends BaseController
{
    @Autowired
    private INursingPlanService nursingPlanService;

    /**
     * 查询护理计划列表
     */
    @PreAuthorize("@ss.hasPermi('nursing:plan:list')")
    @GetMapping("/list")
    @ApiOperation("查询护理计划列表")
    public TableDataInfo<List<NursingPlan>> list(@ApiParam("查询条件对象") NursingPlan nursingPlan)
    {
        startPage();
        List<NursingPlan> list = nursingPlanService.selectNursingPlanList(nursingPlan);
        return getDataTable(list);
    }

    /**
     * 导出护理计划列表
     */
    @ApiOperation("导出护理计划详细信息")
    @PreAuthorize("@ss.hasPermi('nursing:plan:export')")
    @Log(title = "护理计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@ApiParam("导出的查询条件") HttpServletResponse response, NursingPlan nursingPlan)
    {
        List<NursingPlan> list = nursingPlanService.selectNursingPlanList(nursingPlan);
        ExcelUtil<NursingPlan> util = new ExcelUtil<NursingPlan>(NursingPlan.class);
        util.exportExcel(response, list, "护理计划数据");
    }

    /**
     * 获取护理计划详细信息
     */
    @ApiOperation("获取护理计划详细信息")
    @PreAuthorize("@ss.hasPermi('nursing:plan:query')")
    @GetMapping(value = "/{id}")
    public R<NursingPlanVo> getInfo(@ApiParam(value = "护理计划ID", required = true) @PathVariable( "id") Long id)
    {
        return R.ok(nursingPlanService.selectNursingPlanById(id));
    }

    /**
     * 新增护理计划
     */
    @ApiOperation("新增护理计划")
    @PreAuthorize("@ss.hasPermi('nursing:plan:add')")
    @Log(title = "护理计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@ApiParam(value = "护理计划实体", required = true) @RequestBody NursingPlanDto dto)
    {
        return toAjax(nursingPlanService.insertNursingPlan(dto));
    }

    /**
     * 修改护理计划
     */
    @ApiOperation("修改护理计划")
    @PreAuthorize("@ss.hasPermi('nursing:plan:edit')")
    @Log(title = "护理计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@ApiParam(value = "护理计划实体", required = true) @RequestBody  NursingPlanDto dto)
    {
        return toAjax(nursingPlanService.updateNursingPlan(dto));
    }

    /**
     * 删除护理计划
     */
    @PreAuthorize("@ss.hasPermi('nursing:plan:remove')")
    @Log(title = "护理计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    @ApiOperation("删除护理计划")
    public AjaxResult remove(@ApiParam(value = "护理计划ID数组", required = true) @PathVariable Long id)
    {
        return toAjax(nursingPlanService.deleteNursingPlanById(id));
    }
}
