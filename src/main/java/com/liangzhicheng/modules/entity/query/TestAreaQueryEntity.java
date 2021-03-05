package com.liangzhicheng.modules.entity.query;

import com.alibaba.fastjson.JSONObject;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.entity.dto.TestAreaDto;
import com.liangzhicheng.modules.entity.query.basic.BaseQueryEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 地区查询实体类
 * @author liangzhicheng
 * @since 2021-03-05
 */
@Data
@NoArgsConstructor
public class TestAreaQueryEntity extends BaseQueryEntity {

    /**
     * 地区id
     */
    private String areaId;
    /**
     * 地区层级
     */
    private String areaLevel;
    /**
     * 类型
     */
    private String langType;
    /**
     * 长度
     */
    private String length;

    public TestAreaQueryEntity(TestAreaDto areaDto) {
        super(areaDto);
        String areaId = areaDto.getAreaId();
        if(SysToolUtil.isNotBlank(areaId)) {
            this.areaId = areaId;
            this.length = String.valueOf(areaId.length());
        }
        String areaLevel = areaDto.getAreaLevel();
        if(SysToolUtil.isNotBlank(areaLevel)) {
            this.areaLevel = areaLevel;
        }
        String langType = "zh_CN";
        this.langType = langType;
    }

}
