package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.exception.BusinessException;
import com.liangzhicheng.common.exception.TransactionException;
import com.liangzhicheng.common.utils.*;
import com.liangzhicheng.modules.entity.SysPermEntity;
import com.liangzhicheng.modules.entity.SysRolePermEntity;
import com.liangzhicheng.modules.entity.SysRoleUserEntity;
import com.liangzhicheng.modules.entity.SysUserEntity;
import com.liangzhicheng.modules.dao.ISysUserDao;
import com.liangzhicheng.modules.entity.dto.SysUserDTO;
import com.liangzhicheng.modules.entity.vo.SysUserLoginVO;
import com.liangzhicheng.modules.service.ISysPermService;
import com.liangzhicheng.modules.service.ISysRolePermService;
import com.liangzhicheng.modules.service.ISysRoleUserService;
import com.liangzhicheng.modules.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Service;

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
    private ISysRoleUserService roleUserService;
    @Resource
    private ISysPermService permService;
    @Resource
    private ISysRolePermService rolePermService;

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
        if(SysToolUtil.isBlank(accountName, password)){
            throw new BusinessException(ApiConstant.BASE_FAIL_CODE, "账号或密码错误");
        }
        SysUserLoginVO userVO = null;
        try{
            //封装令牌
            UsernamePasswordToken userToken = new UsernamePasswordToken(accountName, password);
            //调用Shiro的Api登录
            SecurityUtils.getSubject().login(userToken);
            SysToolUtil.info("--- server login result : " + SecurityUtils.getSubject().isAuthenticated());
            //用户信息
            SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
            userVO = SysBeanUtil.copyEntity(user, SysUserLoginVO.class);
            //角色，权限处理
            List<String> permIdList = Lists.newArrayList();
            if(Constants.ONE.equals(user.getIsAdmin())){
                List<SysPermEntity> permList = permService.list(
                        new QueryWrapper<SysPermEntity>().eq(Constants.DEL_FLAG, Constants.ZERO));
                for(SysPermEntity perm : permList){
                    permIdList.add(perm.getId());
                }
            }else{
                SysRoleUserEntity roleUser = roleUserService.getOne(user.getId());
                if(SysToolUtil.isNotNull(roleUser)){
                    String roleId = roleUser.getRoleId();
                    userVO.setRoleId(roleId);
                    List<SysRolePermEntity> rolePermList = rolePermService.list(
                            new QueryWrapper<SysRolePermEntity>().eq("role_id", roleId));
                    if(SysToolUtil.listSizeGT(rolePermList)){
                        for(SysRolePermEntity rolePerm : rolePermList){
                            permIdList.add(rolePerm.getPermId());
                        }
                    }
                }
            }
            userVO.setPermList(permIdList);
            //生成JSON Web Token
            String token = SysTokenUtil.createTokenWEB(user.getId(), SysToolUtil.dateAdd(new Date(), 7));
            SysTokenUtil.updateTokenWEB(user.getId(), token);
            userVO.setToken(token);
            //用户绑定token，用于全局获取当前登录用户
            SysCacheUtil.set(token, user, 3600 * 24 * 7);
            request.getSession().setAttribute(Constants.LOGIN_ACCOUNT_ID, user.getId());
        }catch(UnknownAccountException e){
            throw new BusinessException(ApiConstant.BASE_FAIL_CODE, "账号或密码错误");
        }catch(IncorrectCredentialsException e){
            throw new BusinessException(ApiConstant.BASE_FAIL_CODE, "账号或密码错误");
        }catch(DisabledAccountException e){
            e.printStackTrace();
            throw new BusinessException(ApiConstant.BASE_FAIL_CODE, e.getMessage());
        }
        return userVO;
    }

    /**
     * @description 退出登录
     * @param request
     */
    @Override
    public void logOut(HttpServletRequest request) {
        SysUserEntity user = SysUserContext.getCurrentUser(request);
        if(SysToolUtil.isNull(user)){
            throw new TransactionException(ApiConstant.LOG_NOT_EXIST);
        }
        SysTokenUtil.clearTokenWEB(user.getId());
        SysCacheUtil.del(request.getHeader("tokenWEB"));
        request.getSession().removeAttribute(Constants.LOGIN_ACCOUNT_ID);
    }

}
