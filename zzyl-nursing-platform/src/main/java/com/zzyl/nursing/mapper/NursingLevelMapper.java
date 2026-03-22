package com.zzyl.nursing.mapper;

import java.util.List;

import com.zzyl.nursing.vo.NursingLevelVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzyl.nursing.domain.NursingLevel;
import org.apache.ibatis.annotations.Select;

/**
 * 护理等级Mapper接口
 * 
 * @author theo
 * @date 2026-03-20
 */
@Mapper
public interface NursingLevelMapper extends BaseMapper<NursingLevel>
{
    /**
     * 查询护理等级
     * 
     * @param id 护理等级主键
     * @return 护理等级
     */
    public NursingLevel selectNursingLevelById(Long id);

    /**
     * 查询护理等级列表
     * 
     * @param nursingLevel 护理等级
     * @return 护理等级集合
     */
    public List<NursingLevelVo> selectNursingLevelList(NursingLevel nursingLevel);

    /**
     * 新增护理等级
     * 
     * @param nursingLevel 护理等级
     * @return 结果
     */
    public int insertNursingLevel(NursingLevel nursingLevel);

    /**
     * 修改护理等级
     * 
     * @param nursingLevel 护理等级
     * @return 结果
     */
    public int updateNursingLevel(NursingLevel nursingLevel);

    /**
     * 删除护理等级
     * 
     * @param id 护理等级主键
     * @return 结果
     */
    public int deleteNursingLevelById(Long id);

    /**
     * 批量删除护理等级
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNursingLevelByIds(Long[] ids);

    /**
     * 查询护理等级列表
     *
     * @return 护理等级集合
     */
    @Select("select * from nursing_level")
    List<NursingLevel> getNursingLevel();
}
