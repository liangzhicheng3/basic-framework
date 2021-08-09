package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.exception.CustomizeException;
import com.liangzhicheng.common.exception.TransactionException;
import com.liangzhicheng.common.utils.SysBeanUtil;
import com.liangzhicheng.common.utils.SysSnowFlakeUtil;
import com.liangzhicheng.common.utils.SysThreadUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.entity.*;
import com.liangzhicheng.modules.dao.ISysRoleDao;
import com.liangzhicheng.modules.entity.dto.SysRoleDTO;
import com.liangzhicheng.modules.entity.query.page.PageQuery;
import com.liangzhicheng.modules.entity.vo.SysRoleDescVO;
import com.liangzhicheng.modules.entity.vo.SysRoleVO;
import com.liangzhicheng.modules.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<ISysRoleDao, SysRoleEntity> implements ISysRoleService {

    @Resource
    private ISysMenuService menuService;
    @Resource
    private ISysRoleMenuService roleMenuService;
    @Resource
    private ISysPermService permService;
    @Resource
    private ISysRolePermService rolePermService;
    @Resource
    private ISysRoleUserService roleUserService;

    /**
     * @description 角色管理
     * @param roleDTO
     * @return IPage
     */
    @Override
    public IPage listRole(SysRoleDTO roleDTO) {
        String keyword = roleDTO.getKeyword();
        String dateStartStr = roleDTO.getDateStart();
        String dateEndStr = roleDTO.getDateEnd();
        QueryWrapper<SysRoleEntity> wrapperRole = new QueryWrapper<SysRoleEntity>();
        if(SysToolUtil.isNotBlank(keyword)){
            wrapperRole.and(Wrapper -> Wrapper.like("id", keyword)
                    .or().like("name", keyword));
        }
        if(SysToolUtil.isNotBlank(dateStartStr, dateEndStr)){
            LocalDateTime dateStart = SysToolUtil.stringToLocalDateTime(dateStartStr, null);
            LocalDateTime dateEnd = SysToolUtil.stringToLocalDateTime(dateEndStr, null);
            if(dateStart.isAfter(dateEnd)){
                throw new TransactionException(ApiConstant.PARAM_DATE_ERROR);
            }
            wrapperRole.between("create_date", dateStart, dateEnd);
        }
        IPage resultList = baseMapper.selectPage(PageQuery.queryDispose(roleDTO),
                wrapperRole.eq(Constants.DEL_FLAG, Constants.ZERO).orderByAsc("id"));
        List<SysRoleEntity> roleList = resultList.getRecords();
        List<SysRoleVO> roleVOList = Lists.newArrayList();
        if(SysToolUtil.listSizeGT(roleList)){
            roleVOList = SysBeanUtil.copyList(roleList, SysRoleVO.class);
        }
        return resultList.setRecords(roleVOList);
    }

    /**
     * @description 获取角色
     * @param roleDTO
     * @return SysRoleVO
     */
    @Override
    public SysRoleDescVO getRole(SysRoleDTO roleDTO) {
        SysRoleEntity role = baseMapper.selectById(roleDTO.getId());
        if(SysToolUtil.isNull(role)){
            throw new TransactionException(ApiConstant.LOG_NOT_EXIST);
        }
        SysRoleDescVO roleDescVO = SysBeanUtil.copyEntity(role, SysRoleDescVO.class);
        List<String> permIds = Lists.newArrayList();
        List<String> menuIds = Lists.newArrayList();
        List<SysRoleMenuEntity> roleMenuList = roleMenuService.list("role_id", roleDescVO.getId());
        if(SysToolUtil.listSizeGT(roleMenuList)){
            for(SysRoleMenuEntity roleMenu : roleMenuList){
                menuIds.add(roleMenu.getMenuId());
            }
            roleDescVO.setMenuIds(menuIds);
        }
        List<SysRolePermEntity> rolePermList = rolePermService.list("role_id", roleDescVO.getId());
        if(SysToolUtil.listSizeGT(rolePermList)){
            for(SysRolePermEntity rolePerm : rolePermList){
                permIds.add(rolePerm.getPermId());
            }
            roleDescVO.setPermIds(permIds);
        }
        return roleDescVO;
    }

    /**
     * @description 保存角色
     * @param roleDTO
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveRole(SysRoleDTO roleDTO) {
        String id = roleDTO.getId();
        String name = roleDTO.getName();
        String description = roleDTO.getDescription();
        String menuIds = roleDTO.getMenuIds();
        String permIds = roleDTO.getPermIds();
        SysRoleEntity role = baseMapper.selectById(id);
        if(SysToolUtil.isNull(role)){
            role = new SysRoleEntity();
            role.setId(SysSnowFlakeUtil.get().nextId() + "");
        }
        if(SysToolUtil.isNotBlank(name)){
            if(name.length() > 30){
                throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, "角色名称字数过长");
            }
            role.setName(name);
        }
        if(SysToolUtil.isNotBlank(description)){
            if(description.length() > 255){
                throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, "角色描述字数过长");
            }
            role.setDescription(description);
        }
        //角色菜单，角色权限处理
        if(SysToolUtil.isNotBlank(menuIds, permIds)){
            //角色菜单
            List<SysRoleMenuEntity> roleMenuList = null;
            //角色权限
            List<SysRolePermEntity> rolePermList = null;
            String[] arrayMenuId = menuIds.split(",");
            String[] arrayPermId = permIds.split(",");
            if(arrayMenuId != null && arrayMenuId.length > 0){
                SysMenuEntity menu = null;
                SysRoleMenuEntity roleMenu = null;
                roleMenuList = Lists.newArrayList();
                for(String menuId : arrayMenuId){
                    menu = menuService.getById(menuId);
                    if(SysToolUtil.isNull(menu)){
                        throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, "菜单记录不存在");
                    }
                    roleMenu = new SysRoleMenuEntity(SysSnowFlakeUtil.get().nextId() + "",
                            role.getId(), role.getName(), menuId, menu.getName());
                    roleMenuList.add(roleMenu);
                }
            }
            if(arrayPermId != null && arrayPermId.length > 0){
                SysPermEntity perm = null;
                SysRolePermEntity rolePerm = null;
                rolePermList = Lists.newArrayList();
                for(String permId : arrayPermId){
                    perm = permService.getById(permId);
                    if(SysToolUtil.isNull(perm)){
                        throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, "权限记录不存在");
                    }
                    rolePerm = new SysRolePermEntity(SysSnowFlakeUtil.get().nextId() + "",
                            role.getId(), permId, perm.getName(), perm.getExpression());
                    rolePermList.add(rolePerm);
                }
            }
            //保存前做删除操作
            deleteRolePermMenu(role.getId());
            roleMenuService.saveBatch(roleMenuList);
            rolePermService.saveBatch(rolePermList);
        }else{
            //去除勾选做删除操作
            deleteRolePermMenu(role.getId());
        }
        saveOrUpdate(role);
        //开启线程刷新缓存中权限
        SysThreadUtil.threadRolePerm();
    }

    /**
     * @description 删除角色
     * @param roleDTO
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteRole(SysRoleDTO roleDTO) {
        SysRoleEntity role = baseMapper.selectById(roleDTO.getId());
        if(SysToolUtil.isNull(role)){
            throw new TransactionException(ApiConstant.LOG_NOT_EXIST);
        }
        String id = role.getId();
        //删除角色权限，角色菜单
        deleteRolePermMenu(id);
        //删除角色用户记录
        List<SysRoleUserEntity> roleUserList = roleUserService.list("role_id", id);
        if(SysToolUtil.listSizeGT(roleUserList)){
            for(SysRoleUserEntity roleUser : roleUserList){
                roleUser.setDelFlag(Constants.ONE);
            }
            roleUserService.updateBatchById(roleUserList);
        }
        role.setDelFlag(Constants.ONE);
        baseMapper.updateById(role);
        //开启线程刷新缓存中权限
        SysThreadUtil.threadRolePerm();
    }

    /**
     * @description 删除角色权限，角色菜单
     * @param roleId
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteRolePermMenu(String roleId){
        SysRolePermEntity rolePerm = new SysRolePermEntity();
        rolePerm.setDelFlag(Constants.ONE);
        rolePermService.update(rolePerm, new QueryWrapper<SysRolePermEntity>()
                .eq("role_id", roleId).eq(Constants.DEL_FLAG, Constants.ZERO));
        SysRoleMenuEntity roleMenu = new SysRoleMenuEntity();
        roleMenu.setDelFlag(Constants.ONE);
        roleMenuService.update(roleMenu, new QueryWrapper<SysRoleMenuEntity>()
                .eq("role_id", roleId).eq(Constants.DEL_FLAG, Constants.ZERO));
    }

}
