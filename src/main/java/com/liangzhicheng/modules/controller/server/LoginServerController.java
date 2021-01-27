package com.liangzhicheng.modules.controller.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.basic.WebResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.enums.UserTokenServerEnum;
import com.liangzhicheng.common.utils.SysBeanUtil;
import com.liangzhicheng.common.utils.SysCacheUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.config.mvc.interceptor.annotation.LoginServerValidate;
import com.liangzhicheng.modules.entity.dto.TestLoginServerDto;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description 登录相关控制器-服务端
 * @author liangzhicheng
 * @since 2020-08-06
 */
@Api(value="Server-LoginServerController", tags={"【服务端】登录相关控制器"})
@RestController
@RequestMapping("/server/loginServerController")
public class LoginServerController extends BaseController {

    @ApiOperation(value = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult register(@ApiParam(name = "param", value = "需要传的参数说明", required = true) @RequestBody @Valid String param,
                              BindingResult bindingResult){
        JSONObject json = JSON.parseObject(param);
        String username = json.getString("username");
        String password = json.getString("password");
        String address = json.getString("address");
        String vcode = json.getString("vcode");
        if(SysToolUtil.isBlank(username, password, address, vcode)){
            return buildFailedInfo(ApiConstant.PARAM_IS_NULL);
        }
        //判断短信验证码是否存在，是否与传过来的vcode相等
        //判断username是否存在
        //保存用户信息
        return buildSuccessInfo(null);
    }

//    @ApiOperation(value = "登录")
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
//    public WebResult login(@RequestBody TestLoginServerDto dto, HttpServletRequest request){
//        String username = dto.getUsername();
//        String password = dto.getPassword();
//        if(SysToolUtil.isBlank(username, password)){
//            return buildFailedInfo(ApiConstant.PARAM_IS_NULL);
//        }
//        try{
//            //封装令牌
//            UsernamePasswordToken userToken = new UsernamePasswordToken(username, password);
//            //调用shiro的api登录
//            SecurityUtils.getSubject().login(userToken);
//            SysToolUtil.info("--- server login result : " + SecurityUtils.getSubject().isAuthenticated());
//            //用户信息
//            SysUserEntity sysUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
//            SysUserVO userVO = SysBeanUtil.copyEntity(sysUser, SysUserVO.class);
//            Map<String, Object> resultMap = Maps.newHashMap();
//            resultMap.put("user", userVO);
//            //登录token
//            resultMap.put("token", this.createTokenServer(sysUser));
//            //角色，权限处理
//            List<String> permIds = Lists.newArrayList();
//            if(sysUser.isAdmin()){
//                List<SysPermissionEntity> permissionList = permissionService.list(new QueryWrapper<SysPermissionEntity>());
//                for(SysPermissionEntity permissionEntity : permissionList){
//                    permIds.add(permissionEntity.getPermId());
//                }
//            }else{
//                List<SysRolePermissionEntity> rolePermissionEntities = rolePermissionService.list(new QueryWrapper<SysRolePermissionEntity>().eq("role_id", sysUser.getRoleId()));
//                if(rolePermissionEntities != null && rolePermissionEntities.size() > 0){
//                    for(SysRolePermissionEntity rolePermissionEntity : rolePermissionEntities){
//                        permIds.add(rolePermissionEntity.getPermId());
//                    }
//                }
//            }
//            resultMap.put("permIds", permIds);
//            request.getSession().setAttribute(Constants.LOGIN_USER_ID, sysUser.getAccountId());
//            return buildSuccessInfo(resultMap);
//        }catch(UnknownAccountException e){
//            return buildFailedInfo(ApiConstant.USER_NOT_EXIST);
//        }catch(IncorrectCredentialsException e){
//            return buildFailedInfo(ApiConstant.PASSWORD_ERROR);
//        } catch (DisabledAccountException e) {
//            e.printStackTrace();
//            return buildFailedInfo(e.getMessage());
//        }
//    }

    /**
     * @description 【服务端】生成登录token
     * @param sysUser
     * @return String
     */
//    private String createTokenServer(SysUserEntity sysUser){
//        //生成token
//        String token = SysToolUtil.generateId() + SysToolUtil.random();
//        SysCacheUtil.set(UserTokenServerEnum.LOGIN_TOKEN_SERVER.join(token), JSON.toJSONString(sysUser),
//                UserTokenServerEnum.LOGIN_TOKEN_SERVER.getExpireTime(), UserTokenServerEnum.LOGIN_TOKEN_SERVER.getTimeUnit());
//        return token;
//    }

    /**
     * @description 请求头中获取当前用户
     * @param request
     * @return SysUserEntity
     */
//    public static SysUserEntity getCurrentUser(HttpServletRequest request){
//        String token = request.getHeader("token");
//        String userStr = SysCacheUtil.getByKey(UserTokenServerEnum.LOGIN_TOKEN_SERVER.join(token));
//        return JSONObject.parseObject(userStr, SysUserEntity.class);
//    }

    @LoginServerValidate
    @ApiOperation(value = "重置密码")
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult resetPassword(@ApiParam(name = "param", value = "需要传的参数说明", required = true) @RequestBody @Valid String param,
                                   BindingResult bindingResult){
        JSONObject json = JSON.parseObject(param);
        String userId = json.getString("userId");
        String oldPassword = json.getString("oldPassword");
        String newPassword = json.getString("newPassword");
        if(SysToolUtil.isBlank(userId, oldPassword, newPassword)){
            return buildFailedInfo(ApiConstant.PARAM_IS_NULL);
        }
        if(!oldPassword.equals(/*user.getPassword()*/"")){
            return buildFailedInfo(ApiConstant.PASSWORD_ERROR);
        }
        //重置密码保存
        return buildSuccessInfo("设置成功！");
    }

    @LoginServerValidate
    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = String.class)})
    public WebResult logOut(@ApiParam(name = "param", value = "需要传的参数说明", required = true) @RequestBody @Valid String param,
                            BindingResult bindingResult, HttpServletRequest request){
        JSONObject json = JSON.parseObject(param);
        String userId = json.getString("userId");
        if(SysToolUtil.isBlank(userId)){
            return buildFailedInfo(ApiConstant.PARAM_IS_NULL);
        }
        request.getSession().removeAttribute("userId");
        return buildSuccessInfo("退出成功！");
    }

}

