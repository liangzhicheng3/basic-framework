package com.liangzhicheng.modules.service;

import com.liangzhicheng.modules.entity.SysMenuEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzhicheng.modules.entity.dto.SysMenuDTO;
import com.liangzhicheng.modules.entity.vo.SysMenuDescVO;
import com.liangzhicheng.modules.entity.vo.SysMenuVO;

import java.util.List;

/**
 * <p>
 * 菜单信息表 服务类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
public interface ISysMenuService extends IService<SysMenuEntity> {

    /**
     * @description 权限菜单列表
     * @return List<SysMenuVO>
     */
    List<SysMenuVO> listPermMenu();

    /**
     * @description 根据key，value获取菜单列表
     * @param key
     * @param value
     * @return List<SysMenuEntity>
     */
    List<SysMenuEntity> list(String key, String value);

    /**
     * @description 获取菜单
     * @param menuDTO
     * @return SysMenuDescVO
     */
    SysMenuDescVO getMenu(SysMenuDTO menuDTO);

    /**
     * @description 获取一级菜单
     * @return List<SysMenuEntity>
     */
    List<SysMenuEntity> listOne();

    /**
     * @description 根据一级菜单获取二级菜单
     * @param oneId
     * @return List<SysMenuEntity>
     */
    List<SysMenuEntity> listTwoByOne(String oneId);

    /**
     * @description 根据二级菜单获取三级菜单
     * @param twoId
     * @return List<SysMenuEntity>
     */
    List<SysMenuEntity> listThreeByTwo(String twoId);

    /**
     * @description 根据三级菜单获取四级菜单
     * @param threeId
     * @return List<SysMenuEntity>
     */
    List<SysMenuEntity> listFourByThree(String threeId);

    /**
     * @description 新增菜单
     * @param menuDTO
     */
    void insertMenu(SysMenuDTO menuDTO);

    /**
     * @description 更新菜单
     * @param menuDTO
     */
    void updateMenu(SysMenuDTO menuDTO);

    /**
     * @description 删除菜单
     * @param menuDTO
     */
    void deleteMenu(SysMenuDTO menuDTO);

}
