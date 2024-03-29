package com.liangzhicheng.modules.controller;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.basic.ResponseResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.page.PageResult;
import com.liangzhicheng.common.utils.SysBeanUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.entity.TestPersonEntity;
import com.liangzhicheng.modules.entity.dto.TestPersonDTO;
import com.liangzhicheng.modules.entity.vo.TestPersonVO;
import com.liangzhicheng.modules.service.ITestPersonService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value="Api-PersonApiController", description="测试人员")
@RestController
@RequestMapping("/api/personApiController")
public class PersonApiController extends BaseController {

    @Resource
    private ITestPersonService testPersonService;

    @ApiOperation(value = "分页查询1")
    @RequestMapping(value = "/page1", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestPersonVO.class)})
    public ResponseResult page1(@ApiParam(value = "页码，1为第一页") @RequestParam(required = true) Integer page,
                                @ApiParam(value = "每页数量") @RequestParam(required = true) Integer pageSize,
                                @ApiParam(value = "头像") @RequestParam(required=false)  String avatar,
                                @ApiParam(value = "姓名") @RequestParam(required=false)  String name,
                                @ApiParam(value = "密码") @RequestParam(required=false)  String password,
                                @ApiParam(value = "年龄") @RequestParam(required=false)  Integer age,
                                @ApiParam(value = "性别：0女 1男") @RequestParam(required=false)  Boolean gender,
                                @ApiParam(value = "睡觉时间") @RequestParam(required=false) @DateTimeFormat(pattern = "HH:mm:ss") Date sleepTime,
                                @ApiParam(value = "上班时间") @RequestParam(required=false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date workTime,
                                @ApiParam(value = "出生时间") @RequestParam(required=false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday,
                                @ApiParam(value = "个人简介") @RequestParam(required=false)  String intro){
        TestPersonEntity entity = new TestPersonEntity();
        if(SysToolUtil.isNotBlank(avatar)){
            entity.setAvatar(avatar);
        }
        if(SysToolUtil.isNotBlank(name)){
            entity.setAvatar(name);
        }
        if(SysToolUtil.isNotBlank(password)){
            entity.setAvatar(password);
        }
        entity.setAge(age);
        entity.setGender(gender);
        entity.setSleepTime(sleepTime);
        entity.setWorkTime(workTime);
        entity.setBirthday(birthday);
        if(SysToolUtil.isNotBlank(intro)){
            entity.setIntro(intro);
        }
        QueryWrapper<TestPersonEntity> testPersonEntityQueryWrapper = new QueryWrapper<>(entity);
        IPage result = testPersonService.page(new Page<TestPersonEntity>(page, pageSize), testPersonEntityQueryWrapper);
        List<TestPersonVO> voList = SysBeanUtil.copyList(result.getRecords(), TestPersonVO.class);
        result.setRecords(voList);
        return buildSuccessInfo(result);
    }

    @ApiOperation(value = "分页查询2")
    @RequestMapping(value = "/page2", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestPersonVO.class)})
    public ResponseResult page2(@RequestBody TestPersonDTO personDto){
        PageResult resultList = testPersonService.page2(personDto);
        return buildSuccessInfo(resultList);
    }

    @ApiOperation(value = "分页查询3")
    @RequestMapping(value = "/page3", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestPersonVO.class)})
    public ResponseResult page3(@RequestBody TestPersonDTO personDto){
        IPage<TestPersonVO> resultList = testPersonService.page3(personDto);
        return buildSuccessInfo(SysToolUtil.pageResult(resultList));
    }

    @ApiOperation(value = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestPersonVO.class)})
    public ResponseResult save(@ApiParam(value = "id") @RequestParam(required=false) Long id,
                               @ApiParam(value = "头像") @RequestParam(required=false)  String avatar,
                               @ApiParam(value = "姓名") @RequestParam(required=false)  String name,
                               @ApiParam(value = "密码") @RequestParam(required=false)  String password,
                               @ApiParam(value = "年龄") @RequestParam(required=false)  Integer age,
                               @ApiParam(value = "性别：0女 1男") @RequestParam(required=false)  Boolean gender,
                               @ApiParam(value = "睡觉时间") @RequestParam(required=false) @DateTimeFormat(pattern = "HH:mm:ss") Date sleepTime,
                               @ApiParam(value = "上班时间") @RequestParam(required=false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date workTime,
                               @ApiParam(value = "出生时间") @RequestParam(required=false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday,
                               @ApiParam(value = "个人简介") @RequestParam(required=false)  String intro
                          ){
        TestPersonEntity entity = null;
        if(id != null){
            entity = testPersonService.getById(id);
        }else{
            entity = new TestPersonEntity();
        }
        entity.setAvatar(avatar);
        entity.setName(name);
        entity.setPassword(password);
        entity.setAge(age);
        entity.setGender(gender);
        entity.setSleepTime(sleepTime);
        entity.setWorkTime(workTime);
        entity.setBirthday(birthday);
        entity.setIntro(intro);
        entity.setUserNo(generateUserNo());
        testPersonService.saveOrUpdate(entity);
        TestPersonVO vo = new TestPersonVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = Boolean.class)})
    public ResponseResult delete(@RequestParam Long id){
        boolean delete = testPersonService.removeById(id);
        return buildSuccessInfo(delete);
    }

    @ApiOperation(value = "获取详情")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestPersonVO.class)})
    public ResponseResult get(@RequestParam Long id){
        TestPersonEntity entity = testPersonService.getById(id);
        TestPersonVO vo = null;
        if(entity != null){
            vo = new TestPersonVO();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }

    /**
     * @description 用户编号生成
     * @return String
     */
    public synchronized String generateUserNo(){
        Long count = testPersonService.getCountUserNo(); //select count(1) as count from test_person
        if(count.intValue() < 1){
            return "000001";
        }else{
            String max = testPersonService.getRankUserNo(); //select coalesce(max(user_no), '000001') as max from test_person
            String userNo = SysToolUtil.toLength(Integer.parseInt(max) + 1, 6);
            return userNo;
        }
    }

}
