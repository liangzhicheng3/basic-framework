package com.liangzhicheng.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.utils.SysCacheUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.entity.SysPermEntity;
import com.liangzhicheng.modules.entity.SysRolePermEntity;
import com.liangzhicheng.modules.entity.SysRoleUserEntity;
import com.liangzhicheng.modules.entity.SysUserEntity;
import com.liangzhicheng.modules.service.ISysPermService;
import com.liangzhicheng.modules.service.ISysRolePermService;
import com.liangzhicheng.modules.service.ISysRoleUserService;
import com.liangzhicheng.modules.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class SysRealm extends AuthorizingRealm {

    @Resource
    private ISysUserService sysUserService;

    /**
     * @description realm中使用指定的凭证匹配器完成密码匹配操作
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher(credentialsMatcher);
    }

    /**
     * @description 用户登录认证
     * @param token
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //登录请求头参数携带，通过token获取用户名
        String accountName = token.getPrincipal().toString();
        String password = new String((char[]) token.getCredentials());
        SysUserEntity user = sysUserService.getOne(new QueryWrapper<SysUserEntity>()
                .eq("account_name", accountName).eq(Constants.DEL_FLAG, Constants.ZERO));
        if(SysToolUtil.isNull(user) || !user.getPassword().equals(password)){
            throw new UnknownAccountException("账号或密码错误");
        }
        if(!Constants.ONE.equals(user.getLoginStatus())){
            throw new DisabledAccountException("账号已被禁用");
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }

    /**
     * @description 角色，权限相关设置
     * @param pc
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        SysUserEntity user = (SysUserEntity) pc.getPrimaryPrincipal();
        if(SysToolUtil.isNull(user)){
            return null;
        }
        Map<String, Object> roleMap = SysCacheUtil.getRoleMap();
        Map<String, Object> permMap = SysCacheUtil.getPermMap();
        Set<String> roles = (Set<String>) roleMap.get(user.getId());
        Set<String> perms = (Set<String>) permMap.get(user.getId());
        //创建SimpleAuthorizationInfo，将角色和权限存储
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
        authInfo.setRoles(roles);
        authInfo.setStringPermissions(perms);
        return authInfo;
    }

}


