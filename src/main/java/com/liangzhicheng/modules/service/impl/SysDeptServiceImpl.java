package com.liangzhicheng.modules.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.exception.CustomizeException;
import com.liangzhicheng.common.exception.TransactionException;
import com.liangzhicheng.common.utils.SysBeanUtil;
import com.liangzhicheng.common.utils.SysQueryUtil;
import com.liangzhicheng.common.utils.SysSnowFlakeUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.entity.SysDeptEntity;
import com.liangzhicheng.modules.dao.ISysDeptDao;
import com.liangzhicheng.modules.entity.dto.SysDeptDTO;
import com.liangzhicheng.modules.entity.query.SysDeptQueryEntity;
import com.liangzhicheng.modules.entity.vo.SysDeptDescVO;
import com.liangzhicheng.modules.entity.vo.SysDeptVO;
import com.liangzhicheng.modules.service.ISysDeptService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门信息表 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-08-06
 */
@Service
public class SysDeptServiceImpl extends BaseServiceImpl<ISysDeptDao, SysDeptEntity> implements ISysDeptService {

    /**
     * @description 部门列表
     * @param deptDTO
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> listDept(SysDeptDTO deptDTO, Pageable pageable) {
        SysDeptQueryEntity deptQuery = new SysDeptQueryEntity(deptDTO);
        this.getPage(pageable, deptQuery.getPage(), deptQuery.getPageSize());
        List<SysDeptEntity> deptList = baseMapper.selectList(
                SysQueryUtil.getQueryWrapper(SysDeptEntity.class, deptQuery));
        PageInfo<SysDeptEntity> page = new PageInfo<>();
        List records = Lists.newArrayList();
        if(SysToolUtil.listSizeGT(deptList)){
            page = new PageInfo<>(deptList);
            records = SysBeanUtil.copyList(page.getList(), SysDeptVO.class);
        }
        return this.pageResult(records, page);
    }

    /**
     * @description 获取部门
     * @param deptDTO
     * @return SysDeptVO
     */
    @Override
    public SysDeptDescVO getDept(SysDeptDTO deptDTO) {
        SysDeptEntity dept = baseMapper.selectById(deptDTO.getId());
        if(SysToolUtil.isNull(dept)){
            throw new TransactionException(ApiConstant.LOG_NOT_EXIST);
        }
        return SysBeanUtil.copyEntity(dept, SysDeptDescVO.class);
    }

    /**
     * @description 保存部门
     * @param deptDTO
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveDept(SysDeptDTO deptDTO) {
        String id = deptDTO.getId();
        String name = deptDTO.getName();
        String description = deptDTO.getDescription();
        SysDeptEntity dept = baseMapper.selectById(id);
        if(SysToolUtil.isNull(dept)){
            dept = new SysDeptEntity();
            dept.setId(SysSnowFlakeUtil.get().nextId() + "");
        }
        if(SysToolUtil.isNotBlank(name)){
            if(name.length() > 30){
                throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, "部门名称字数过长");
            }
            dept.setName(name);
        }
        if(SysToolUtil.isNotBlank(description)){
            if(description.length() > 200){
                throw new CustomizeException(ApiConstant.BASE_FAIL_CODE, "部门描述字数过长");
            }
            dept.setDescription(description);
        }
        saveOrUpdate(dept);
    }

    /**
     * @description 删除部门
     * @param deptDTO
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteDept(SysDeptDTO deptDTO) {
        SysDeptEntity dept = baseMapper.selectById(deptDTO.getId());
        if(SysToolUtil.isNull(dept)){
            throw new TransactionException(ApiConstant.LOG_NOT_EXIST);
        }
        dept.setDelFlag(Constants.ONE);
        baseMapper.updateById(dept);
    }

}
