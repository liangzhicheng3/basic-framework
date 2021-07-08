package com.liangzhicheng.modules.controller.client;

import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.basic.WebResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.page.PageResult;
import com.liangzhicheng.modules.entity.dto.TestAreaDTO;
import com.liangzhicheng.modules.entity.vo.TestAreaNameVO;
import com.liangzhicheng.modules.service.ITestAreaNameService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description 地区相关控制器
 * @author liangzhicheng
 * @since 2021-03-05
 */
@Api(value="Client-AreaClientController", tags={"【客户端】地区相关控制器"})
@RestController
@RequestMapping("/client/areaClientController")
public class AreaClientController extends BaseController {

    @Resource
    private ITestAreaNameService areaNameService;

    @ApiOperation(value = "地区列表")
    @PostMapping(value = "/listArea")
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestAreaNameVO.class)})
    public WebResult listArea(@RequestBody TestAreaDTO areaDto){
        PageResult resultList = areaNameService.listArea(areaDto);
        return buildSuccessInfo(resultList);
    }

}
