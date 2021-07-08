package com.liangzhicheng.modules.entity.query.basic;

import com.liangzhicheng.modules.entity.dto.basic.BaseDTO;
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

    public BaseQueryEntity(BaseDTO baseDTO){
        Integer page = baseDTO.getPage();
        Integer pageSize = baseDTO.getPageSize();
        if(page == null || page < 1){
            page = 1;
        }
        if(pageSize == null || pageSize < 1){
            pageSize = 10;
        }
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPageNo(){
        return (this.page - 1) * pageSize;
    }

}
