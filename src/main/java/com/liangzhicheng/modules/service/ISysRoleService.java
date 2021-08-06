package com.liangzhicheng.modules.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liangzhicheng.modules.entity.SysRoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzhicheng.modules.entity.dto.SysRoleDTO;
import com.liangzhicheng.modules.entity.vo.SysRoleDescVO;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
public interface ISysRoleService extends IService<SysRoleEntity> {

    /**
     * @description 角色管理
     * @param roleDTO
     * @return IPage
     */
    IPage listRole(SysRoleDTO roleDTO);

    /**
     * @description 获取角色
     * @param roleDTO
     * @return SysRoleVO
     */
    SysRoleDescVO getRole(SysRoleDTO roleDTO);

    /**
     * @description 保存角色
     * @param roleDTO
     */
    void saveRole(SysRoleDTO roleDTO);

    /**
     * @description 删除角色
     * @param roleDTO
     */
    void deleteRole(SysRoleDTO roleDTO);

}
