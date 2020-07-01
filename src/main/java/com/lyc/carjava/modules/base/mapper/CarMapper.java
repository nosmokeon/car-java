package com.lyc.carjava.modules.base.mapper;

import com.lyc.carjava.modules.base.entity.Car;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.carjava.modules.system.vo.DamageCarVo;
import com.lyc.carjava.modules.system.vo.UsefulCarVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 电动车 Mapper 接口
 * </p>
 *
 * @author lyc
 * @since 2020-06-20
 */
public interface CarMapper extends BaseMapper<Car> {

    public List<UsefulCarVo> usefulcars(@Param("current") int current, @Param("pageSize") int pageSize);

    public List<DamageCarVo> damagecars(@Param("current") int current, @Param("pageSize") int pageSize);
}
