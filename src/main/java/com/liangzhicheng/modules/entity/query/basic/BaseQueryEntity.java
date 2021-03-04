package com.liangzhicheng.modules.entity.query.basic;

import com.liangzhicheng.modules.entity.dto.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 基础查询实体类
 * @author liangzhicheng
 * @since 2021-03-04
 */
@Data
@NoArgsConstructor
public class BaseQueryEntity {

    private int page;
    private int pageSize;

    public BaseQueryEntity(BaseDto dto){
        this.page = dto.getPage();
        this.pageSize = dto.getPageSize();
    }

    public int getPage(){
        return (this.page - 1) * pageSize;
    }

}
