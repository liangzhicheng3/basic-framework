package com.liangzhicheng.modules.entity.query;

import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.config.page.annotation.Query;
import com.liangzhicheng.modules.entity.dto.SysRoleDTO;
import com.liangzhicheng.modules.entity.query.basic.BaseQueryEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description 角色查询实体类
 * @author liangzhicheng
 * @since 2021-09-10
 */
@Data
@NoArgsConstructor
public class SysRoleQueryEntity extends BaseQueryEntity {

    @Query(type = Query.Type.INNER_LIKE, blurry = "id,name")
    private String keyword;

    @Query(type = Query.Type.BETWEEN)
    private String createDate;

    public SysRoleQueryEntity(SysRoleDTO roleDTO) {
        super(roleDTO);
        String keyword = roleDTO.getKeyword();
        if(SysToolUtil.isNotBlank(keyword)) {
            this.keyword = keyword;
        }
        String createDate = roleDTO.getCreateDate();
        if(SysToolUtil.isNotBlank(createDate)){
            this.createDate = createDate;
        }
    }

}
