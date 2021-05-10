package com.liangzhicheng.modules.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangzhicheng.common.basic.WebResult;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.controller.TestApiController;
import com.liangzhicheng.modules.controller.client.WebSocketController;
import com.liangzhicheng.modules.dao.ITestDepartmentPersonDao;
import com.liangzhicheng.modules.entity.TestDepartmentPersonEntity;
import com.liangzhicheng.modules.service.ITestDepartmentPersonService;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

/**
 * <p>
 * 测试部门人员 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2020-08-04
 */
@Service
public class TestDepartmentPersonServiceImpl extends ServiceImpl<ITestDepartmentPersonDao, TestDepartmentPersonEntity> implements ITestDepartmentPersonService {

    /**
     * @description 测试在线人数
     * @param personId
     */
    @Override
    public void testOnlinePerson(String personId) {
        WebResult webResult = new WebResult(ApiConstant.BASE_SUCCESS_CODE, "未读消息总数");
        String str = "person:";
        try {
            String key = str + personId;
            Session session = WebSocketController.clients.get(key);
            if (session != null) {
                webResult.setData(getCountByPersonId(personId));
                session.getBasicRemote().sendText(JSONObject.toJSONString(webResult));
                SysToolUtil.info("[未读消息总数] 通知App用户id:" + personId  + "的消息成功");
            } else {
                SysToolUtil.info("[未读消息总数] 通知App消息失败,无法获取到用户" + personId + "的连接");
            }
        } catch (Exception e) {
            SysToolUtil.error("[未读消息总数] 通知App消息失败" + e, TestApiController.class);
        }
    }

    /**
     * @description 获取在线人数
     * @param personId
     * @return int
     */
    private int getCountByPersonId(String personId) {
        return baseMapper.selectCount(new QueryWrapper<TestDepartmentPersonEntity>().eq("person_id", personId));
    }

}
