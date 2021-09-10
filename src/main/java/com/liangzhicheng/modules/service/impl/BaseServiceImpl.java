package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.entity.query.basic.BaseQueryEntity;
import com.liangzhicheng.modules.service.IBaseService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 服务实现基类
 * @author liangzhicheng
 * @since 2021-09-09
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

    /**
     * @description 自定义排序
     * @param orders
     * @return Pageable
     */
    protected Pageable customizeSort(BaseQueryEntity query, List<Sort.Order> orders){
        return PageRequest.of(query.getPage(), query.getPageSize(), Sort.by(orders));
    }

    /**
     * @description 分页处理
     * @param pageable
     * @param pageNo
     * @param pageSize
     */
    protected void getPage(Pageable pageable, int pageNo, int pageSize){
        String sort = "";
        if(SysToolUtil.isNotNull(pageable.getSort())){
            sort = pageable.getSort().toString();
            sort = sort.replace(":", "");
            if("UNSORTED".equals(sort)){
                sort = "create_date DESC";
            }
        }
        PageHelper.startPage(pageNo, pageSize, sort);
    }

    /**
     * @description 分页结果集
     * @param records
     * @param page
     * @param <T>
     * @return Map<String, Object>
     */
    protected  <T> Map<String, Object> pageResult(List<T> records, PageInfo<T> page){
        Map<String, Object> resultMap = new LinkedHashMap<>(4);
        resultMap.put("records", records);
        resultMap.put("total", page.getTotal());
        resultMap.put("pageNo", page.getPageNum());
        resultMap.put("pageSize", page.getPageSize());
        return resultMap;
    }

}
