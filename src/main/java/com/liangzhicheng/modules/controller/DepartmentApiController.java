package com.liangzhicheng.modules.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liangzhicheng.common.basic.BaseController;
import com.liangzhicheng.common.basic.ResponseResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.entity.TestDepartmentEntity;
import com.liangzhicheng.modules.entity.TestPersonEntity;
import com.liangzhicheng.modules.entity.vo.TestDepartmentVO;
import com.liangzhicheng.modules.service.ITestDepartmentService;
import com.liangzhicheng.modules.service.ITestPersonService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @description 测试部门
 * @author liangzhicheng
 * @since 2020-08-04
 */
@Api(value="api-DepartmentApiController",description="测试部门")
@RestController
@RequestMapping("/api/departmentApiController")
public class DepartmentApiController extends BaseController {

    @Resource
    private ITestDepartmentService testDepartmentService;
    @Resource
    private ITestPersonService testPersonService;


    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestDepartmentVO.class)})
    public ResponseResult page(@RequestBody @Valid String param, BindingResult bindingResult
            /*@ApiParam(value = "页码，1为第一页",required = true) @RequestParam Integer page,
                          @ApiParam(value = "每页数量",required = true) @RequestParam Integer pageSize,
                          @ApiParam(value = "部门id") @RequestParam(required=false)  Integer deptId*/
                          /*@Validated TestDepartmentPersonEntity entity*/){
        /*TestDepartmentEntity entity = new TestDepartmentEntity();
        entity.setDeptName(deptName);
        QueryWrapper queryWrapper = new QueryWrapper<TestDepartmentEntity>(entity);
        IPage result = ITestDepartmentService.page(new Page<TestDepartmentEntity>(page,pageSize),queryWrapper);
        List<TestDepartmentVO> voList = SysBeanUtil.copyList(result.getRecords(),TestDepartmentVO.class);
        result.setRecords(voList);*/
        SysToolUtil.info("--- param : " + param);
        JSONObject json = JSON.parseObject(param);
        Integer page = json.getInteger("page");
        Integer pageSize = json.getInteger("pageSize");
        Integer deptId = json.getInteger("deptId");
        TestPersonEntity person = null;
        Page<Map<String, Object>> resultList = testDepartmentService.getDepartmentPersonList(page, pageSize, deptId);
        List<Map<String, Object>> records = resultList.getRecords();
        for(Map<String, Object> map : records){
            Long personId = (Long) map.get("personId");
            person = new TestPersonEntity();
            person.setId(personId);
            QueryWrapper queryWrapper = new QueryWrapper<TestPersonEntity>(person);
            person = testPersonService.getOne(queryWrapper);
            map.put("person", person);
            map.remove("personId");
        }
        resultList.setRecords(records);
        return buildSuccessInfo(resultList);
    }

    @ApiOperation(value = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestDepartmentVO.class)})
    public ResponseResult save(@ApiParam(value = "id") @RequestParam(required=false) Long id,
                               @ApiParam(value = "部门名称") @RequestParam(required=false)  String deptName
                          ){
        TestDepartmentEntity entity = null;
        if(id != null){
            entity = testDepartmentService.getById(id);
        }else{
            entity = new TestDepartmentEntity();
        }
        entity.setDeptName(deptName);
        testDepartmentService.saveOrUpdate(entity);
        TestDepartmentVO vo = new TestDepartmentVO();
        BeanUtils.copyProperties(entity,vo);
        return buildSuccessInfo(vo);
    }


    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = Boolean.class)})
    public ResponseResult delete(@RequestParam Long id){
        boolean delete = testDepartmentService.removeById(id);
        return buildSuccessInfo(delete);
    }


    @ApiOperation(value = "获取详情")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = ApiConstant.BASE_SUCCESS_CODE, message = "成功", response = TestDepartmentVO.class)})
    public ResponseResult get(@RequestParam Long id){
        TestDepartmentEntity entity = testDepartmentService.getById(id);
        TestDepartmentVO vo = null;
        if(entity != null){
            vo = new TestDepartmentVO();
            BeanUtils.copyProperties(entity,vo);
        }
        return buildSuccessInfo(vo);
    }



}
