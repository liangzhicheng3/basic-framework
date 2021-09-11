package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.exception.CustomizeException;
import com.liangzhicheng.common.exception.TransactionException;
import com.liangzhicheng.common.utils.*;
import com.liangzhicheng.modules.entity.*;
import com.liangzhicheng.modules.dao.ISysUserDao;
import com.liangzhicheng.modules.entity.dto.SysUserDTO;
import com.liangzhicheng.modules.entity.query.page.PageQuery;
import com.liangzhicheng.modules.entity.vo.*;
import com.liangzhicheng.modules.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 账号信息表 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<ISysUserDao, SysUserEntity> implements ISysUserService {

    @Resource
    private ISysDeptService deptService;
    @Resource
    private ISysRoleService roleService;
    @Resource
    private ISysRoleUserService roleUserService;
    @Resource
    private ISysRoleMenuService roleMenuService;

    /**
     * @description 登录
     * @param userDTO
     * @param request
     * @return SysUserLoginVO
     */
    @Override
    public SysUserLoginVO login(SysUserDTO userDTO, HttpServletRequest request) {
        String accountName = userDTO.getAccountName();
        String password = userDTO.getPassword();
        String content = "账号或密码错误";
        if(SysToolUtil.isBlank(accountName, password)){
            throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, content);
        }
        SysUserLoginVO userVO = null;
        try{
            //封装令牌
            UsernamePasswordToken userToken = new UsernamePasswordToken(accountName, SysAESUtil.aesEncrypt(password));
            //调用Shiro的Api登录
            SecurityUtils.getSubject().login(userToken);
            SysToolUtil.info("--- server login result : " + SecurityUtils.getSubject().isAuthenticated());
            //用户信息
            SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
            userVO = SysBeanUtil.copyEntity(user, SysUserLoginVO.class);
            //角色，权限处理
            //若是超级管理员返回所有权限菜单，否则返回对应权限菜单
            if(Constants.ONE.equals(user.getIsAdmin())){
                userVO.setPermMenuList(SysCacheUtil.listPermMenu());
            }else{
                userVO.setPermMenuList(listPermMenu(user.getId()));
            }
            //生成JSON Web Token
            String token = SysTokenUtil.createTokenWEB(user.getId(), SysToolUtil.dateAdd(new Date(), 7));
            SysTokenUtil.updateTokenWEB(user.getId(), token);
            userVO.setToken(token);
            request.getSession().setAttribute(Constants.LOGIN_ACCOUNT_ID, user.getId());
        }catch(UnknownAccountException e){
            throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, content);
        }catch(IncorrectCredentialsException e){
            throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, content);
        }catch(DisabledAccountException e){
            e.printStackTrace();
            throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, e.getMessage());
        }
        return userVO;
    }

    /**
     * @description 退出登录
     * @param request
     */
    @Override
    public void logOut(HttpServletRequest request) {
//        SysUserEntity user = SysUserContext.getCurrentUser(request);
        SysUserEntity user = baseMapper.selectById(request.getHeader("accountId"));
        if(SysToolUtil.isNull(user)){
            throw new TransactionException(ApiConstant.LOG_NOT_EXIST);
        }
        SysTokenUtil.clearTokenWEB(user.getId());
        request.getSession().removeAttribute(Constants.LOGIN_ACCOUNT_ID);
    }

    /**
     * @description 更新当前登录用户头像
     * @param userDTO
     * @return SysPersonInfoVO
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public SysPersonInfoVO updateAvatar(SysUserDTO userDTO, HttpServletRequest request) {
        String accountId = request.getHeader("accountId");
        String avatar = userDTO.getAvatar();
        if(SysToolUtil.isBlank(accountId, avatar)){
            throw new TransactionException(ApiConstant.PARAM_IS_NULL);
        }
//        SysUserEntity user = SysUserContext.getCurrentUser();
        SysUserEntity user = baseMapper.selectById(accountId);
        if(SysToolUtil.isNull(user)){
            throw new TransactionException(ApiConstant.LOG_NOT_EXIST);
        }
        user.setAvatar(avatar);
        baseMapper.updateById(user);
        return SysBeanUtil.copyEntity(user, SysPersonInfoVO.class);
    }

    /**
     *@description 更新当前登录用户密码
     * @param userDTO
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePassword(SysUserDTO userDTO, HttpServletRequest request) {
        String accountId = request.getHeader("accountId");
        String password = userDTO.getPassword();
        String newPassword = userDTO.getNewPassword();
        if(SysToolUtil.isBlank(accountId, password, newPassword)){
            throw new TransactionException(ApiConstant.PARAM_IS_NULL);
        }
//        SysUserEntity user = SysUserContext.getCurrentUser();
        SysUserEntity user = baseMapper.selectById(accountId);
        if(SysToolUtil.isNull(user)){
            throw new TransactionException(ApiConstant.LOG_NOT_EXIST);
        }
        if(!user.getPassword().equals(SysAESUtil.aesEncrypt(password))){
            throw new TransactionException(ApiConstant.PASSWORD_ERROR);
        }
        user.setPassword(SysAESUtil.aesEncrypt(newPassword));
        baseMapper.updateById(user);
    }

    /**
     * @description 账号管理列表
     * @param userDTO
     * @return IPage
     */
    @Override
    public IPage listAccount(SysUserDTO userDTO) {
        String keyword = userDTO.getKeyword();
        String deptId = userDTO.getDeptId();
        String roleId = userDTO.getRoleIds();
        String loginStatus = userDTO.getLoginStatus();
        //1.账号信息查询
        QueryWrapper<SysUserEntity> wrapperUser = new QueryWrapper<SysUserEntity>();
        if(SysToolUtil.isNotBlank(keyword)){
            wrapperUser.and(Wrapper -> Wrapper.like("id", keyword)
                    .or().like("truename", keyword));
        }
        wrapperUser.eq(SysToolUtil.isNotBlank(deptId), "dept_id", deptId);
        wrapperUser.eq(SysToolUtil.isNotBlank(loginStatus)
                && SysToolUtil.in(loginStatus, Constants.ZERO, Constants.ONE), "login_status", loginStatus);
        List<SysUserEntity> userList = baseMapper.selectList(wrapperUser.eq(Constants.DEL_FLAG, Constants.ZERO));
        List<String> accountIdByUser = Lists.newArrayList();
        if(SysToolUtil.listSizeGT(userList)){
            for(SysUserEntity user : userList){
                accountIdByUser.add(user.getId());
            }
        }
        //2.账号角色信息查询
        QueryWrapper<SysRoleUserEntity> wrapperRoleUser = new QueryWrapper<SysRoleUserEntity>();
        if(SysToolUtil.inOneByNotBlank(roleId)){
            wrapperRoleUser.eq(SysToolUtil.isNotBlank(roleId), "role_id", roleId);
            List<SysRoleUserEntity> roleUserList = roleUserService.list(wrapperRoleUser.eq(Constants.DEL_FLAG, Constants.ZERO));
            //账号角色处理
            List<String> accountIdByRoleUser = Lists.newArrayList();
            if(SysToolUtil.listSizeGT(roleUserList)){
                for(SysRoleUserEntity roleUser : roleUserList){
                    String accountId = roleUser.getAccountId();
                    if(!accountIdByRoleUser.contains(accountId)){
                        accountIdByRoleUser.add(accountId);
                    }
                }
            }
            //获取两个List交集
            accountIdByUser = SysToolUtil.getListBoth(accountIdByUser, accountIdByRoleUser);
        }
        IPage resultList = new Page();
        if(SysToolUtil.listSizeGT(accountIdByUser)){
            resultList = baseMapper.selectPage(PageQuery.queryDispose(userDTO),
                    new QueryWrapper<SysUserEntity>().in("id", accountIdByUser)
                            .orderByDesc("login_status").orderByAsc("id"));
            userList = resultList.getRecords();
            List<SysUserVO> userVOList = Lists.newArrayList();
            if(SysToolUtil.listSizeGT(userList)){
                SysUserVO userVO = null;
                List<SysRoleUserEntity> roleUserList = Lists.newArrayList();
                List<String> roleNames = null;
                for(SysUserEntity user : userList){
                    userVO = SysBeanUtil.copyEntity(user, SysUserVO.class);
                    roleUserList = roleUserService.list("account_id", user.getId());
                    if(SysToolUtil.listSizeGT(roleUserList)){
                        roleNames = Lists.newArrayList();
                        for(SysRoleUserEntity roleUser : roleUserList){
                            roleNames.add(roleUser.getRoleName());
                        }
                        userVO.setRoleNames(roleNames);
                    }
                    userVOList.add(userVO);
                }
            }
            return resultList.setRecords(userVOList);
        }
        resultList.setRecords(Lists.newArrayList());
        resultList.setTotal(0L);
        return resultList;
    }

    /**
     * @description 根据key，value获取用户列表
     * @param key
     * @param value
     * @return List<SysUserEntity>
     */
    @Override
    public List<SysUserEntity> list(String key, String value) {
        QueryWrapper<SysUserEntity> wrapperUser = new QueryWrapper<SysUserEntity>();
        if(SysToolUtil.isNotBlank(key, value)){
            wrapperUser.eq(key, value);
        }
        return baseMapper.selectList(
                wrapperUser.eq("login_status", Constants.ONE).eq(Constants.DEL_FLAG, Constants.ZERO));
    }

    /**
     * @description 获取账号
     * @param userDTO
     * @return SysUserDescVO
     */
    @Override
    public SysUserDescVO getAccount(SysUserDTO userDTO) {
        SysUserEntity user = baseMapper.selectById(userDTO.getId());
        if(SysToolUtil.isNull(user)){
            throw new TransactionException(ApiConstant.LOG_NOT_EXIST);
        }
        SysUserDescVO userDescVO = SysBeanUtil.copyEntity(user, SysUserDescVO.class);
        //获取该账号角色
        List<SysRoleUserEntity> roleUserList = roleUserService.list("account_id", user.getId());
        if(SysToolUtil.listSizeGT(roleUserList)){
            List<String> roleIds = Lists.newArrayList();
            List<String> roleNames = Lists.newArrayList();
            for(SysRoleUserEntity roleUser : roleUserList){
                roleIds.add(roleUser.getRoleId());
                roleNames.add(roleUser.getRoleName());
            }
            userDescVO.setRoleIds(roleIds);
            userDescVO.setRoleNames(roleNames);
        }
        return userDescVO;
    }

    /**
     * @description 保存账号
     * @param userDTO
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveAccount(SysUserDTO userDTO) {
        String id = userDTO.getId();
        String deptId = userDTO.getDeptId();
        String roleId = userDTO.getRoleIds();
        String accountName = userDTO.getAccountName();
        String truename = userDTO.getTruename();
        String loginStatus = userDTO.getLoginStatus();
        SysUserEntity user = baseMapper.selectById(id);
        if(SysToolUtil.isNull(user)){
            user = new SysUserEntity();
            user.setId(SysSnowFlakeUtil.get().nextId() + "");
            user.setPassword(SysAESUtil.aesEncrypt(Constants.DEFAULT_PASSWORD));
            user.setAvatar(Constants.DEFAULT_AVATAR);
        }
        if(SysToolUtil.isNotBlank(deptId)){
            SysDeptEntity dept = deptService.getById(deptId);
            if(SysToolUtil.isNull(dept)){
                throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, "部门记录不存在");
            }
            user.setDeptId(deptId);
            user.setDeptName(dept.getName());
        }
        if(SysToolUtil.isNotBlank(accountName)){
            if(accountName.length() > 30){
                throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, "账号名称字数过长");
            }
            if(SysToolUtil.isBlank(id) || !user.getAccountName().equals(accountName)){
                Long count = baseMapper.selectCount(new QueryWrapper<SysUserEntity>()
                        .eq("account_name", accountName).eq(Constants.DEL_FLAG, Constants.ZERO));
                if(count.intValue() > 0){
                    throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, "账号已存在");
                }
            }
            user.setAccountName(accountName);
        }
        if(SysToolUtil.isNotBlank(truename)){
            if(accountName.length() > 30){
                throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, "姓名字数过长");
            }
            user.setTruename(truename);
        }
        if(SysToolUtil.isNotBlank(loginStatus)){
            if(SysToolUtil.notIn(loginStatus, Constants.ZERO, Constants.ONE)){
                throw new TransactionException(ApiConstant.PARAM_ERROR);
            }
            user.setLoginStatus(loginStatus);
        }
        //角色用户处理
        if(SysToolUtil.isNotBlank(roleId)){
            //角色用户
            String[] arrayRoleId = roleId.split(",");
            if(arrayRoleId != null && arrayRoleId.length > 0){
                SysRoleEntity role = null;
                SysRoleUserEntity roleUser = null;
                String isAdmin = Constants.ZERO;
                List<SysRoleUserEntity> roleUserList = Lists.newArrayList();
                for (String primaryId : arrayRoleId) {
                    role = roleService.getById(primaryId);
                    if(SysToolUtil.isNull(role)){
                        throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, "角色记录不存在");
                    }
                    if(Constants.ONE.equals(primaryId)){
                        isAdmin = Constants.ONE;
                    }
                    roleUser = new SysRoleUserEntity(SysSnowFlakeUtil.get().nextId() + "",
                            primaryId, role.getName(), user.getId(), user.getAccountName());
                    roleUserList.add(roleUser);
                }
                user.setIsAdmin(isAdmin);
                //保存前做删除角色用户
                roleUser = new SysRoleUserEntity();
                roleUser.setDelFlag(Constants.ONE);
                roleUserService.update(roleUser, new QueryWrapper<SysRoleUserEntity>()
                        .eq("account_id", user.getId()).eq(Constants.DEL_FLAG, Constants.ZERO));
                roleUserService.saveBatch(roleUserList);
            }
        }
        saveOrUpdate(user);
        //开启线程刷新缓存中权限
        SysThreadUtil.threadRolePerm();
    }

    /**
     * @description 重置密码
     * @param userDTO
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void resetPassword(SysUserDTO userDTO) {
        SysUserEntity user = baseMapper.selectById(userDTO.getId());
        if(SysToolUtil.isNull(user)){
            throw new TransactionException(ApiConstant.LOG_NOT_EXIST);
        }
        user.setPassword(SysAESUtil.aesEncrypt(Constants.DEFAULT_PASSWORD));
        baseMapper.updateById(user);
    }

    /**
     * @description 删除账号
     * @param userDTO
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteAccount(SysUserDTO userDTO) {
        SysUserEntity user = baseMapper.selectById(userDTO.getId());
        if(SysToolUtil.isNull(user)){
            throw new TransactionException(ApiConstant.LOG_NOT_EXIST);
        }
        //删除角色用户记录
        List<SysRoleUserEntity> roleUserList = roleUserService.list("account_id", user.getId());
        if(SysToolUtil.listSizeGT(roleUserList)){
            for(SysRoleUserEntity roleUser : roleUserList){
                roleUser.setDelFlag(Constants.ONE);
            }
            roleUserService.updateBatchById(roleUserList);
        }
        user.setDelFlag(Constants.ONE);
        baseMapper.updateById(user);
    }

    /**
     * @description 根据账号获取权限菜单
     * @param accountId
     * @return List<SysMenuVO>
     */
    private List<SysMenuVO> listPermMenu(String accountId){
        //一级菜单
        List<SysMenuVO> oneVOList = Lists.newArrayList();
        //二级菜单
        List<SysMenuTwoVO> twoVOList = Lists.newArrayList();
        //三级菜单
        List<SysMenuThreeVO> threeVOList = Lists.newArrayList();
        //四级菜单
        List<SysMenuFourVO> fourVOList = Lists.newArrayList();
        //缓存中权限菜单
        List<SysMenuVO> cacheListPermMenu = SysCacheUtil.listPermMenu();
        if(SysToolUtil.listSizeGT(cacheListPermMenu)){
            List<SysRoleUserEntity> roleUserList = roleUserService.list("account_id", accountId);
            if(SysToolUtil.listSizeGT(roleUserList)){
                for(SysRoleUserEntity roleUser : roleUserList){
                    List<SysRoleMenuEntity> roleMenuList = roleMenuService.list("role_id", roleUser.getRoleId());
                    if(SysToolUtil.listSizeGT(roleMenuList)){
                        //缓存中菜单与该账号角色菜单匹配
                        for(SysMenuVO cacheOneVO : cacheListPermMenu){
                            for(SysRoleMenuEntity oneRoleMenu : roleMenuList){
                                if(cacheOneVO.getId().equals(oneRoleMenu.getMenuId())){
                                    SysMenuVO menuOneVO = cacheOneVO;
                                    List<SysMenuTwoVO> cacheTwoList = cacheOneVO.getChildrenList();
                                    if(SysToolUtil.listSizeGT(cacheTwoList)){
                                        twoVOList = Lists.newArrayList();
                                        for(SysMenuTwoVO cacheTwoVO : cacheTwoList){
                                            for(SysRoleMenuEntity twoRoleMenu : roleMenuList){
                                                if(cacheTwoVO.getId().equals(twoRoleMenu.getMenuId())){
                                                    SysMenuTwoVO menuTwoVO = cacheTwoVO;
                                                    List<SysMenuThreeVO> cacheThreeList = cacheTwoVO.getChildrenList();
                                                    if(SysToolUtil.listSizeGT(cacheThreeList)){
                                                        threeVOList = Lists.newArrayList();
                                                        for(SysMenuThreeVO cacheThreeVO : cacheThreeList){
                                                            for(SysRoleMenuEntity threeRoleMenu : roleMenuList){
                                                                if(cacheThreeVO.getId().equals(threeRoleMenu.getMenuId())){
                                                                    SysMenuThreeVO menuThreeVO = cacheThreeVO;
                                                                    List<SysMenuFourVO> cacheFourList = cacheThreeVO.getChildrenList();
                                                                    if(SysToolUtil.listSizeGT(cacheFourList)){
                                                                        fourVOList = Lists.newArrayList();
                                                                        for(SysMenuFourVO cacheFourVO : cacheFourList){
                                                                            for(SysRoleMenuEntity fourRoleMenu : roleMenuList){
                                                                                if(cacheFourVO.getId().equals(fourRoleMenu.getMenuId())){
                                                                                    SysMenuFourVO menuFourVO = cacheFourVO;
                                                                                    fourVOList.add(menuFourVO);
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    menuThreeVO.setChildrenList(fourVOList);
                                                                    threeVOList.add(menuThreeVO);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    menuTwoVO.setChildrenList(threeVOList);
                                                    twoVOList.add(menuTwoVO);
                                                }
                                            }
                                        }
                                    }
                                    menuOneVO.setChildrenList(twoVOList);
                                    oneVOList.add(menuOneVO);
                                }
                            }
                        }
                    }
                }
            }
        }
        return oneVOList;
    }

}
