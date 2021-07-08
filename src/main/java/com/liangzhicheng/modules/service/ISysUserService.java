package com.liangzhicheng.modules.service;

import com.liangzhicheng.modules.entity.SysUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzhicheng.modules.entity.dto.SysUserDTO;
import com.liangzhicheng.modules.entity.vo.SysUserLoginVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 账号信息表 服务类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-07-07
 */
public interface ISysUserService extends IService<SysUserEntity> {

    /**
     * @description 登录
     * @param userDTO
     * @param request
     * @return SysUserLoginVO
     */
    SysUserLoginVO login(SysUserDTO userDTO, HttpServletRequest request);

    /**
     * @description 退出登录
     * @param request
     */
    void logOut(HttpServletRequest request);

}
