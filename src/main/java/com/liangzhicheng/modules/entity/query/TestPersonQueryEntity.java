package com.liangzhicheng.modules.entity.query;

import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.entity.dto.TestPersonDTO;
import com.liangzhicheng.modules.entity.query.basic.BaseQueryEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description 个人查询实体类
 * @author liangzhicheng
 * @since 2021-03-04
 */
@Data
@NoArgsConstructor
public class TestPersonQueryEntity extends BaseQueryEntity {

    /**
     * 昵称，手机号
     */
    private String keyword;
    /**
     * 年龄
     */
    private String age;
    /**
     * 开始时间
     */
    private LocalDateTime dateStart;
    /**
     * 结束时间
     */
    private LocalDateTime dateEnd;

    public TestPersonQueryEntity(TestPersonDTO personDto) {
        super(personDto);
        String keyword = personDto.getKeyword();
        if(SysToolUtil.isNotBlank(keyword)) {
            this.keyword = keyword;
        }
        String age = personDto.getAge();
        if(SysToolUtil.isNotBlank(age) && "1".equals(age)) {
            this.age = age;
        }
        String dateStartStr = personDto.getDateStart();
        if(SysToolUtil.isNotBlank(dateStartStr)) {
            this.dateStart = SysToolUtil.stringToLocalDateTime(dateStartStr , null);
        }
        String dateEndStr = personDto.getDateEnd();
        if(SysToolUtil.isNotBlank(dateEndStr)) {
            this.dateEnd = SysToolUtil.stringToLocalDateTime(dateEndStr , null);
        }
    }

}