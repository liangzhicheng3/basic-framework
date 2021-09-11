package com.liangzhicheng.modules.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liangzhicheng.modules.entity.SysDeptEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzhicheng.modules.entity.dto.SysDeptDTO;
import com.liangzhicheng.modules.entity.vo.SysDeptDescVO;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * <p>
 * 部门信息表 服务类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-08-06
 */
public interface ISysDeptService extends IBaseService<SysDeptEntity> {

    /**
     * @description 部门列表
     * @param deptDTO
     * @return Map<String, Object>
     */
    Map<String, Object> listDept(SysDeptDTO deptDTO, Pageable pageable);

    /**
     * @description 获取部门
     * @param deptDTO
     * @return SysDeptVO
     */
    SysDeptDescVO getDept(SysDeptDTO deptDTO);

    /**
     * @description 保存部门
     * @param deptDTO
     */
    void saveDept(SysDeptDTO deptDTO);

    /**
     * @description 删除部门
     * @param deptDTO
     */
    void deleteDept(SysDeptDTO deptDTO);

}
