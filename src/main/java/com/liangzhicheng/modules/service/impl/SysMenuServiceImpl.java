package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.exception.CustomizeException;
import com.liangzhicheng.common.exception.TransactionException;
import com.liangzhicheng.common.utils.SysBeanUtil;
import com.liangzhicheng.common.utils.SysSnowFlakeUtil;
import com.liangzhicheng.common.utils.SysThreadUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.entity.SysMenuEntity;
import com.liangzhicheng.modules.dao.ISysMenuDao;
import com.liangzhicheng.modules.entity.SysPermEntity;
import com.liangzhicheng.modules.entity.SysPermMenuEntity;
import com.liangzhicheng.modules.entity.SysRolePermEntity;
import com.liangzhicheng.modules.entity.dto.SysMenuDTO;
import com.liangzhicheng.modules.entity.vo.*;
import com.liangzhicheng.modules.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangzhicheng.modules.service.ISysPermMenuService;
import com.liangzhicheng.modules.service.ISysPermService;
import com.liangzhicheng.modules.service.ISysRolePermService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单信息表 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<ISysMenuDao, SysMenuEntity> implements ISysMenuService {

    @Resource
    private ISysPermService permService;
    @Resource
    private ISysPermMenuService permMenuService;
    @Resource
    private ISysRolePermService rolePermService;

    /**
     * @description 权限菜单列表
     * @return List<SysMenuVO>
     */
    @Override
    public List<SysMenuVO> listPermMenu() {
        List<SysMenuVO> oneVOList = Lists.newArrayList();
        List<SysMenuEntity> oneList = list("level", Constants.ONE);
        if(SysToolUtil.listSizeGT(oneList)){
            SysMenuVO oneVO = null;
            SysMenuTwoVO twoVO = null;
            SysMenuThreeVO threeVO = null;
            SysMenuFourVO fourVO = null;
            List<SysMenuEntity> twoList = null;
            List<SysMenuTwoVO> twoVOList = null;
            List<SysMenuEntity> threeList = null;
            List<SysMenuThreeVO> threeVOList = null;
            List<SysMenuEntity> fourList = null;
            List<SysMenuFourVO> fourVOList = null;
            for(SysMenuEntity one : oneList) {
                oneVO = SysBeanUtil.copyEntity(one, SysMenuVO.class);
                //获取一级菜单权限标识
                SysPermEntity onePerm = permService.getPerm(oneVO.getId());
                if(SysToolUtil.isNotNull(onePerm)){
                    oneVO.setPermId(onePerm.getId());
                    oneVO.setExpression(onePerm.getExpression());
                }
                twoList = listTwoByOne(oneVO.getId());
                if(SysToolUtil.listSizeGT(twoList)){
                    twoVOList = Lists.newArrayList();
                    for(SysMenuEntity two : twoList){
                        twoVO = SysBeanUtil.copyEntity(two, SysMenuTwoVO.class);
                        //获取二级菜单权限标识
                        SysPermEntity twoPerm = permService.getPerm(twoVO.getId());
                        if(SysToolUtil.isNotNull(twoPerm)){
                            twoVO.setPermId(twoPerm.getId());
                            twoVO.setExpression(twoPerm.getExpression());
                        }
                        threeList = listThreeByTwo(twoVO.getId());
                        if(SysToolUtil.listSizeGT(threeList)){
                            threeVOList = Lists.newArrayList();
                            for(SysMenuEntity three : threeList){
                                threeVO = SysBeanUtil.copyEntity(three, SysMenuThreeVO.class);
                                //获取三级菜单权限标识
                                SysPermEntity threePerm = permService.getPerm(threeVO.getId());
                                if(SysToolUtil.isNotNull(threePerm)){
                                    threeVO.setPermId(threePerm.getId());
                                    threeVO.setExpression(threePerm.getExpression());
                                }
                                fourList = listFourByThree(threeVO.getId());
                                if(SysToolUtil.listSizeGT(fourList)){
                                    fourVOList = Lists.newArrayList();
                                    for(SysMenuEntity four : fourList){
                                        fourVO = SysBeanUtil.copyEntity(four, SysMenuFourVO.class);
                                        //获取四级菜单权限标识
                                        SysPermEntity fourPerm = permService.getPerm(fourVO.getId());
                                        if(SysToolUtil.isNotNull(fourPerm)){
                                            fourVO.setPermId(fourPerm.getId());
                                            fourVO.setExpression(fourPerm.getExpression());
                                        }
                                        fourVOList.add(fourVO);
                                        threeVO.setChildrenList(fourVOList);
                                    }
                                }
                                threeVOList.add(threeVO);
                                twoVO.setChildrenList(threeVOList);
                            }
                        }
                        twoVOList.add(twoVO);
                        oneVO.setChildrenList(twoVOList);
                    }
                }
                oneVOList.add(oneVO);
            }
        }
        return oneVOList;
    }

    /**
     * @description 根据key，value获取菜单列表
     * @param key
     * @param value
     * @return List<SysMenuEntity>
     */
    @Override
    public List<SysMenuEntity> list(String key, String value) {
        QueryWrapper<SysMenuEntity> wrapperMenu = new QueryWrapper<SysMenuEntity>();
        if(SysToolUtil.isNotBlank(key, value)){
            wrapperMenu.eq(key, value);
        }
        return baseMapper.selectList(wrapperMenu.eq(Constants.DEL_FLAG, Constants.ZERO)
                .orderByAsc("rank"));
    }

    /**
     * @description 获取菜单
     * @param menuDTO
     * @return SysMenuDescVO
     */
    @Override
    public SysMenuDescVO getMenu(SysMenuDTO menuDTO) {
        SysMenuEntity menu = baseMapper.selectById(menuDTO.getId());
        if(SysToolUtil.isNull(menu)){
            throw new TransactionException(ApiConstant.LOG_NOT_EXIST);
        }
        SysMenuDescVO menuDescVO = SysBeanUtil.copyEntity(menu, SysMenuDescVO.class);
        SysPermEntity perm = permService.getPerm(menuDescVO.getId());
        if(SysToolUtil.isNotNull(perm)){
            menuDescVO.setPermId(perm.getId());
            menuDescVO.setPermName(perm.getName());
            menuDescVO.setExpression(perm.getExpression());
        }
        return menuDescVO;
    }

    /**
     * @description 获取一级菜单
     * @return List<SysMenuEntity>
     */
    @Override
    public List<SysMenuEntity> listOne() {
        return baseMapper.selectList(new QueryWrapper<SysMenuEntity>()
                .eq("level", Constants.ONE).eq(Constants.DEL_FLAG, Constants.ZERO)
                .orderByAsc("rank"));
    }

    /**
     * @description 根据一级菜单获取二级菜单
     * @param oneId
     * @return List<SysMenuEntity>
     */
    @Override
    public List<SysMenuEntity> listTwoByOne(String oneId) {
        return baseMapper.selectList(new QueryWrapper<SysMenuEntity>()
                .eq("parent_id", oneId).eq("level", Constants.TWO)
                .eq(Constants.DEL_FLAG, Constants.ZERO).orderByAsc("rank"));
    }

    /**
     * @description 根据二级菜单获取三级菜单
     * @param twoId
     * @return List<SysMenuEntity>
     */
    public List<SysMenuEntity> listThreeByTwo(String twoId) {
        return baseMapper.selectList(new QueryWrapper<SysMenuEntity>()
                .eq("parent_id", twoId).eq("level", Constants.THREE)
                .eq(Constants.DEL_FLAG, Constants.ZERO).orderByAsc("rank"));
    }

    /**
     * @description 根据三级菜单获取四级菜单
     * @param threeId
     * @return List<SysMenuEntity>
     */
    public List<SysMenuEntity> listFourByThree(String threeId) {
        return baseMapper.selectList(new QueryWrapper<SysMenuEntity>()
                .eq("parent_id", threeId).eq("level", "4")
                .eq(Constants.DEL_FLAG, Constants.ZERO).orderByAsc("rank"));
    }

    /**
     * @description 新增菜单
     * @param menuDTO
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertMenu(SysMenuDTO menuDTO) {
        String parentId = menuDTO.getParentId();
        String name = menuDTO.getName();
        String component = menuDTO.getComponent();
        String routerPath = menuDTO.getRouterPath();
        String redirect = menuDTO.getRedirect();
        String isHide = menuDTO.getIsHide();
        Integer rank = menuDTO.getRank();
        if(SysToolUtil.isBlank(name)){
            throw new TransactionException(ApiConstant.PARAM_IS_NULL);
        }
        if(SysToolUtil.notIn(isHide, Constants.ZERO, Constants.ONE)){
            throw new TransactionException(ApiConstant.PARAM_ERROR);
        }
        SysMenuEntity menu = null;
        if(SysToolUtil.isBlank(parentId)){
            menu = new SysMenuEntity();
            menu.setLevel(Constants.ONE);
        }else{
            menu = baseMapper.selectById(parentId);
            if(SysToolUtil.isNull(menu)){
                throw new TransactionException(ApiConstant.LOG_NOT_EXIST);
            }
            if(Constants.ONE.equals(menu.getLevel())){
                menu = new SysMenuEntity();
                menu.setLevel(Constants.TWO);
            }else if(Constants.TWO.equals(menu.getLevel())){
                menu = new SysMenuEntity();
                menu.setLevel(Constants.THREE);
            }else if(Constants.THREE.equals(menu.getLevel())){
                menu = new SysMenuEntity();
                menu.setLevel("4");
            }
        }
        menu.setId(SysSnowFlakeUtil.get().nextId() + "");
        menu.setParentId(parentId);
        if(name.length() > 30){
            throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, "标题字数过长");
        }
        menu.setName(name);
        menu.setComponent(component);
        menu.setRouterPath(routerPath);
        menu.setRedirect(redirect);
        menu.setIsHide(isHide);
        menu.setRank(rank);
        baseMapper.insert(menu);
        //开启线程刷新缓存中菜单
        SysThreadUtil.threadListPermMenu();
        /**
         * 新增权限记录
         * 用于接口贴@RequiresPermissions注解拦截
         * 如：@RequiresPermissions("userServerController-listUser或/user/info")
         * name：权限名称(用户列表)
         * expression：表达式(userServerController-listUser或/user/info)
         */
        SysPermEntity perm = permService.insertPerm(menuDTO);
        if(SysToolUtil.isNotNull(perm)){
            //新增权限菜单记录
            permMenuService.insertPermMenu(perm, menu);
        }
    }

    /**
     * @description 更新菜单
     * @param menuDTO
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateMenu(SysMenuDTO menuDTO) {
        String id = menuDTO.getId();
        String name = menuDTO.getName();
        String component = menuDTO.getComponent();
        String routerPath = menuDTO.getRouterPath();
        String redirect = menuDTO.getRedirect();
        String isHide = menuDTO.getIsHide();
        Integer rank = menuDTO.getRank();
        //权限参数
        String permId = menuDTO.getPermId();
        String permName = menuDTO.getPermName();
        String expression = menuDTO.getExpression();
        SysMenuEntity menu = baseMapper.selectById(id);
        if(SysToolUtil.isNull(menu)){
            throw new TransactionException(ApiConstant.LOG_NOT_EXIST);
        }
        if(SysToolUtil.isNotBlank(name)){
            if(name.length() > 30){
                throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, "标题字数过长");
            }
            menu.setName(name);
        }
        if(SysToolUtil.isNotBlank(component)){
            menu.setComponent(component);
        }
        if(SysToolUtil.isNotBlank(routerPath)){
            menu.setRouterPath(routerPath);
        }
        if(SysToolUtil.isNotBlank(redirect)){
            menu.setRedirect(redirect);
        }
        if(SysToolUtil.isNotBlank(isHide)){
            if(SysToolUtil.notIn(isHide, Constants.ZERO, Constants.ONE)){
                throw new TransactionException(ApiConstant.PARAM_ERROR);
            }
            menu.setIsHide(isHide);
        }
        if(SysToolUtil.isNotBlank(String.valueOf(rank))){
            if(rank < 0){
                throw new TransactionException(ApiConstant.PARAM_ERROR);
            }
            menu.setRank(rank);
        }
        baseMapper.updateById(menu);
        //开启线程刷新缓存中菜单
        SysThreadUtil.threadListPermMenu();
        //权限处理
        SysPermEntity perm = permService.getById(permId);
        if(SysToolUtil.isNull(perm)){
            perm = new SysPermEntity();
            perm.setId(SysSnowFlakeUtil.get().nextId() + "");
        }
        perm.setName(permName);
        perm.setExpression(expression);
        permService.saveOrUpdate(perm);
        //权限菜单处理
        SysPermMenuEntity permMenu = permMenuService.getOne("perm_id", perm.getId());
        if(SysToolUtil.isNull(permMenu)){
            permMenu = new SysPermMenuEntity();
            permMenu.setId(SysSnowFlakeUtil.get().nextId() + "");
            permMenu.setPermId(perm.getId());
            permMenu.setMenuId(menu.getId());
        }
        permMenu.setPermName(permName);
        permMenu.setMenuName(menu.getName());
        permMenuService.saveOrUpdate(permMenu);
        //角色权限处理
        List<SysRolePermEntity> rolePermList = rolePermService.list("perm_id", perm.getId());
        if(SysToolUtil.listSizeGT(rolePermList)){
            for(SysRolePermEntity rolePerm: rolePermList) {
                rolePerm.setPermName(permName);
                rolePerm.setExpression(expression);
            }
            rolePermService.updateBatchById(rolePermList);
        }
    }

    /**
     * @description 删除菜单
     * @param menuDTO
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteMenu(SysMenuDTO menuDTO) {
        SysMenuEntity menu = baseMapper.selectById(menuDTO.getId());
        if(SysToolUtil.isNull(menu)){
            throw new TransactionException(ApiConstant.LOG_NOT_EXIST);
        }
        if(Constants.ONE.equals(menu.getLevel())){
            //更新二级菜单平台删除标记
            SysMenuEntity two = updateDelFlag(Constants.TWO, menu.getId());
            if(SysToolUtil.isNotNull(two)){
                //更新三级菜单平台删除标记
                SysMenuEntity three = updateDelFlag(Constants.THREE, two.getId());
                if(SysToolUtil.isNotNull(three)){
                    //更新四级菜单平台删除标记
                    updateDelFlag("4", three.getId());
                }
            }
        }else if(Constants.TWO.equals(menu.getLevel())){
            //更新三级菜单平台删除标记
            SysMenuEntity three = updateDelFlag(Constants.THREE, menu.getId());
            if(SysToolUtil.isNotNull(three)){
                //更新四级菜单平台删除标记
                updateDelFlag("4", three.getId());
            }
        }else if(Constants.THREE.equals(menu.getLevel())){
            //更新四级菜单平台删除标记
            updateDelFlag("4", menu.getId());
        }
        menu.setDelFlag(Constants.ONE);
        baseMapper.updateById(menu);
        //开启线程刷新缓存中菜单
        SysThreadUtil.threadListPermMenu();
        //更新权限菜单平台删除标记
        SysPermMenuEntity permMenu = permMenuService.getOne("menu_id", menu.getId());
        if(SysToolUtil.isNotNull(permMenu)){
            permMenu.setDelFlag(Constants.ONE);
            permMenuService.updateById(permMenu);
        }
    }

    /**
     * @description 更新平台删除标记
     * @param level
     * @param pid
     * @return SysMenuEntity
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SysMenuEntity updateDelFlag(String level, String pid){
        SysMenuEntity menu = baseMapper.selectOne(new QueryWrapper<SysMenuEntity>()
                .eq("level", level).eq("parent_id", pid).eq(Constants.DEL_FLAG, Constants.ZERO));
        if(SysToolUtil.isNotNull(menu)){
            menu.setDelFlag(Constants.ONE);
            baseMapper.updateById(menu);
        }
        return menu;
    }

}
