package com.liangzhicheng.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

@Component
public class SysRealm extends AuthorizingRealm {

//    @Resource
//    private ISysUserService sysUserService;
//    @Resource
//    private ISysRoleService roleService;
//    @Resource
//    private ISysPermissionService permissionService;
//    @Resource
//    private ISysRolePermissionService rolePermissionService;

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
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //通过token获取用户名，登录请求头参数携带
//        String username = (String) token.getPrincipal();
//        SysUserEntity user = sysUserService.getOne(new QueryWrapper<SysUserEntity>().eq("username", username));
//        if(user == null){
//            throw new UnknownAccountException("用户名或密码错误！");
//        }
//        if(user.isLocked()){
//            throw new DisabledAccountException("账号已被禁用！");
//        }
//        if(user != null){
//            //返回用户信息
//            return new SimpleAuthenticationInfo(user, user.getPassword(), "SysRealm");
//        }

//        String username = token.getPrincipal().toString();
//        String password = new String((char[])token.getCredentials());
//        SysUserEntity user = sysUserService.getOne(new QueryWrapper<SysUserEntity>().eq("username", username).eq("password", password));
//        if(user == null){
//            throw new UnknownAccountException("用户名或密码错误！");
//        }
//        if(user.isLocked()){
//            throw new DisabledAccountException("账号已被禁用！");
//        }
//        if(user != null){
//            //返回用户信息
//            return new SimpleAuthenticationInfo(user, password, this.getName());
//        }
        return null;
    }

    /**
     * @description 角色，权限相关设置
     * @param pc
     * @return
     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
//        //创建info，将角色和权限存储
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        /*Subject subject = SecurityUtils.getSubject();
//        SysUserEntity sysUser = (SysUserEntity) subject.getPrincipal();*/
//        SysUserEntity sysUser = (SysUserEntity) pc.getPrimaryPrincipal();
//        info.addRole(sysUser.getRoleName());
//        if(sysUser.isAdmin()){
//            List<SysPermissionEntity> permissionList = permissionService.list(new QueryWrapper<SysPermissionEntity>());
//            for (SysPermissionEntity permissionEntity : permissionList) {
//                info.addStringPermission(permissionEntity.getExpression());
//            }
//        }else{
//            List<SysRolePermissionEntity> rolePermissionEntities = rolePermissionService.list(new QueryWrapper<SysRolePermissionEntity>().eq("role_id", sysUser.getRoleId()));
//            if(rolePermissionEntities != null && rolePermissionEntities.size() > 0){
//                for(SysRolePermissionEntity rolePermissionEntity : rolePermissionEntities){
//                    info.addStringPermission(rolePermissionEntity.getExpression());
//                }
//            }
//        }
//        return info;
//    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

}


