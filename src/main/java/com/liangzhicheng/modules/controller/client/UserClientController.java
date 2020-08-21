package com.liangzhicheng.modules.controller.client;

import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.constant.Constants;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 用户相关控制器-客户端
 * @author liangzhicheng
 * @since 2020-08-06
 */
@Api(value="Client-UserClientController", description="用户相关控制器-客户端")
@RestController
@RequestMapping("/client/userClientController")
public class UserClientController extends BaseController implements Constants {



}
