package com.zzyl.nursing.mapper;

import java.util.List;

import com.zzyl.nursing.vo.NursingPlanVo;
import com.zzyl.nursing.vo.NursingProjectPlanVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzyl.nursing.domain.NursingProjectPlan;
import org.apache.ibatis.annotations.Param;

/**
 * 护理计划和项目关联Mapper接口
 * 
 * @author theo
 * @date 2026-03-21
 */
@Mapper
public interface NursingProjectPlanMapper extends BaseMapper<NursingProjectPlan>
{
    /**
     * 查询护理计划和项目关联
     * 
     * @param id 护理计划和项目关联主键
     * @return 护理计划和项目关联
     */
    public NursingProjectPlan selectNursingProjectPlanById(Long id);

    /**
     * 查询护理计划和项目关联列表
     * 
     * @param nursingProjectPlan 护理计划和项目关联
     * @return 护理计划和项目关联集合
     */
    public List<NursingProjectPlan> selectNursingProjectPlanList(NursingProjectPlan nursingProjectPlan);

    /**
     * 新增护理计划和项目关联
     * 
     * @param nursingProjectPlan 护理计划和项目关联
     * @return 结果
     */
    public int insertNursingProjectPlan(NursingProjectPlan nursingProjectPlan);

    /**
     * 修改护理计划和项目关联
     * 
     * @param nursingProjectPlan 护理计划和项目关联
     * @return 结果
     */
    public int updateNursingProjectPlan(NursingProjectPlan nursingProjectPlan);

    /**
     * 删除护理计划和项目关联
     * 
     * @param id 护理计划和项目关联主键
     * @return 结果
     */
    public int deleteNursingProjectPlanById(Long id);

    /**
     * 批量删除护理计划和项目关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNursingProjectPlanByIds(Long[] ids);

    int batchInsert(@Param("list") List<NursingProjectPlan> projectPlans,@Param("planId") Long planId);


    /**
     * 根据计划id查询项目计划
     * @param id
     * @return
     */
    List<NursingProjectPlanVo> selectByPlanId(Long id);

    /**
     * 根据计划id删除项目计划
     * @param planId
     */
    @Delete("delete from nursing_project_plan where plan_id = #{planId}")
    void deleteByPlanId(Long planId);
}
