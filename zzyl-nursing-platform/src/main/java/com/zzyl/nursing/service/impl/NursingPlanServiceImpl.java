package com.zzyl.nursing.service.impl;

import java.util.List;
import com.zzyl.common.utils.DateUtils;
import com.zzyl.common.utils.bean.BeanUtils;
import com.zzyl.nursing.dto.NursingPlanDto;
import com.zzyl.nursing.mapper.NursingProjectPlanMapper;
import com.zzyl.nursing.vo.NursingLevelVo;
import com.zzyl.nursing.vo.NursingPlanVo;
import com.zzyl.nursing.vo.NursingProjectPlanVo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzyl.nursing.mapper.NursingPlanMapper;
import com.zzyl.nursing.domain.NursingPlan;
import com.zzyl.nursing.service.INursingPlanService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * 护理计划Service业务层处理
 * 
 * @author theo
 * @date 2026-03-20
 */
@Service
public class NursingPlanServiceImpl extends ServiceImpl<NursingPlanMapper,NursingPlan> implements INursingPlanService
{
    @Autowired
    private NursingPlanMapper nursingPlanMapper;

    @Autowired
    private NursingProjectPlanMapper nursingProjectPlanMapper;

    /**
     * 查询护理计划
     * 
     * @param id 护理计划主键
     * @return 护理计划
     */
    @Override
    public NursingPlanVo selectNursingPlanById(Long id)
    {
        //查询基本信息
        NursingPlan nursingPlan = nursingPlanMapper.selectNursingPlanById(id);
        NursingPlanVo nursingPlanVo = new NursingPlanVo();


        //查询关联的护理项目列表
        List<NursingProjectPlanVo> projectIds = nursingProjectPlanMapper.selectByPlanId(id);
        nursingPlanVo.setProjectPlans(projectIds);
        BeanUtils.copyProperties(nursingPlan, nursingPlanVo);

        return nursingPlanVo;
    }

    /**
     * 查询护理计划列表
     * 
     * @param nursingPlan 护理计划
     * @return 护理计划
     */
    @Override
    public List<NursingPlan> selectNursingPlanList(NursingPlan nursingPlan)
    {
        return nursingPlanMapper.selectNursingPlanList(nursingPlan);
    }

    /**
     * 新增护理计划
     * 
     * @param dto 护理计划
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertNursingPlan(NursingPlanDto dto)
    {
        //保存护理计划基本信息
        NursingPlan nursingPlan = new NursingPlan();
        BeanUtils.copyBeanProp(nursingPlan,dto);
        nursingPlan.setCreateTime(DateUtils.getNowDate());
        nursingPlanMapper.insert(nursingPlan);

        //保存护理计划项目信息
        int count = nursingProjectPlanMapper.batchInsert(dto.getProjectPlans(), nursingPlan.getId());

        return count > 0 ? 1 : 0;



    }

    /**
     * 修改护理计划
     *
     * @param dto 护理计划
     * @return 结果
     */
    @Override
    public int updateNursingPlan(NursingPlanDto dto)
    {
        try {
            // 判断dto中的项目列表为空，如果不为空，则先删除护理计划与护理项目的关系，然后重新批量添加
            if (dto.getProjectPlans() != null && !dto.getProjectPlans().isEmpty()) {
                // 删除护理计划对应的护理项目列表
                nursingProjectPlanMapper.deleteByPlanId(dto.getId());

                // 批量保存护理计划对应的护理项目列表
                nursingProjectPlanMapper.batchInsert(dto.getProjectPlans(), dto.getId());
            }

            // 属性拷贝
            NursingPlan nursingPlan = new NursingPlan();
            BeanUtils.copyProperties(dto, nursingPlan);

            // 不管项目列表是否为空，都要修改护理计划
            return nursingPlanMapper.updateById(nursingPlan);
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量删除护理计划
     * 
     * @param ids 需要删除的护理计划主键
     * @return 结果
     */
    @Override
    public int deleteNursingPlanByIds(Long[] ids)
    {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    /**
     * 删除护理计划信息
     * 
     * @param id 护理计划主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteNursingPlanById(Long id)
    {
        //删除护理计划关联的护理项目列表
        nursingProjectPlanMapper.deleteByPlanId(id);
        //删除护理计划
        return removeById(id) ? 1 : 0;
    }

    @Override
    public List<NursingPlanVo> listAll() {

        return nursingPlanMapper.selectAll();
    }
}
