package com.liangzhicheng.modules.entity.query;

import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.config.page.annotation.Query;
import com.liangzhicheng.modules.entity.dto.TestPersonDTO;
import com.liangzhicheng.modules.entity.query.basic.BaseQueryEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class TestPersonQueryCondition extends BaseQueryEntity implements Serializable {

    @Query(type = Query.Type.INNER_LIKE, blurry = "name,password")
    private String keyword;

    @Query
    private String age;

    @Query(type = Query.Type.UNIX_TIMESTAMP)
    List<String> createDate;

    public TestPersonQueryCondition(TestPersonDTO personDTO) {
        super(personDTO);
        String keyword = personDTO.getKeyword();
        if(SysToolUtil.isNotBlank(keyword)) {
            this.keyword = keyword;
        }
        String age = personDTO.getAge();
        if(SysToolUtil.isNotBlank(age) && SysToolUtil.isNumber(age)) {
            this.age = age;
        }
        List<String> dateList = personDTO.getCreateDate();
        if(SysToolUtil.listSizeGT(dateList)){
            this.createDate = dateList;
        }
    }

}
