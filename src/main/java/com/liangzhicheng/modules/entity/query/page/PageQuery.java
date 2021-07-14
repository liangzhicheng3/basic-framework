package com.liangzhicheng.modules.entity.query.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.entity.dto.basic.BaseDTO;

/**
 * @description 分页查询类
 * @author liangzhicheng
 * @since 2021-07-14
 */
public class PageQuery {

    public static Page queryDispose(BaseDTO baseDTO) {
        return new Page(SysToolUtil.getPage(baseDTO.getPage()),
                SysToolUtil.getPageSize(baseDTO.getPageSize()));
    }

}
