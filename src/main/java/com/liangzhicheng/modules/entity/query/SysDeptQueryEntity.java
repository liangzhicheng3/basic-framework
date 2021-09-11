package com.liangzhicheng.modules.entity.query;

import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.config.page.annotation.Query;
import com.liangzhicheng.modules.entity.dto.SysDeptDTO;
import com.liangzhicheng.modules.entity.query.basic.BaseQueryEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description 部门查询实体类
 * @author liangzhicheng
 * @since 2021-09-10
 */
@Data
@NoArgsConstructor
public class SysDeptQueryEntity extends BaseQueryEntity {

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query(type = Query.Type.BETWEEN)
    private String createDate;

    public SysDeptQueryEntity(SysDeptDTO deptDTO) {
        super(deptDTO);
        String keyword = deptDTO.getKeyword();
        if(SysToolUtil.isNotBlank(keyword)) {
            this.name = keyword;
        }
        String createDate = deptDTO.getCreateDate();
        if(SysToolUtil.isNotBlank(createDate)){
            this.createDate = createDate;
        }
    }

}
