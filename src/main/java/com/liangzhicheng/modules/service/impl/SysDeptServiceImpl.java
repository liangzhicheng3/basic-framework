package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.liangzhicheng.common.constant.ApiConstant;
import com.liangzhicheng.common.constant.Constants;
import com.liangzhicheng.common.exception.BusinessException;
import com.liangzhicheng.common.exception.TransactionException;
import com.liangzhicheng.common.utils.SysBeanUtil;
import com.liangzhicheng.common.utils.SysSnowFlakeUtil;
import com.liangzhicheng.common.utils.SysToolUtil;
import com.liangzhicheng.modules.entity.SysDeptEntity;
import com.liangzhicheng.modules.dao.ISysDeptDao;
import com.liangzhicheng.modules.entity.dto.SysDeptDTO;
import com.liangzhicheng.modules.entity.query.page.PageQuery;
import com.liangzhicheng.modules.entity.vo.SysDeptDescVO;
import com.liangzhicheng.modules.entity.vo.SysDeptVO;
import com.liangzhicheng.modules.service.ISysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 部门信息表 服务实现类
 * </p>
 *
 * @author liangzhicheng
 * @since 2021-08-06
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<ISysDeptDao, SysDeptEntity> implements ISysDeptService {

    /**
     * @description 部门列表
     * @param deptDTO
     * @return IPage
     */
    @Override
    public IPage listDept(SysDeptDTO deptDTO) {
        String keyword = deptDTO.getKeyword();
        String dateStartStr = deptDTO.getDateStart();
        String dateEndStr = deptDTO.getDateEnd();
        QueryWrapper<SysDeptEntity> wrapperDept = new QueryWrapper<SysDeptEntity>();
        wrapperDept.like(SysToolUtil.isNotBlank(keyword), "name", keyword);
        if(SysToolUtil.isNotBlank(dateStartStr, dateEndStr)){
            LocalDateTime dateStart = SysToolUtil.stringToLocalDateTime(dateStartStr, null);
            LocalDateTime dateEnd = SysToolUtil.stringToLocalDateTime(dateEndStr, null);
            if(dateStart.isAfter(dateEnd)){
                throw new TransactionException(ApiConstant.PARAM_DATE_ERROR);
            }
            wrapperDept.between("create_date", dateStart, dateEnd);
        }
        IPage resultList = baseMapper.selectPage(PageQuery.queryDispose(deptDTO),
                wrapperDept.eq(Constants.DEL_FLAG, Constants.ZERO).orderByDesc(Constants.CREATE_DATE));
        List<SysDeptEntity> deptList = resultList.getRecords();
        List<SysDeptVO> deptVOList = Lists.newArrayList();
        if(SysToolUtil.listSizeGT(deptList)){
            deptVOList = SysBeanUtil.copyList(deptList, SysDeptVO.class);
        }
        return resultList.setRecords(deptVOList);
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
                throw new BusinessException(ApiConstant.BASE_FAIL_CODE, "部门名称字数过长");
            }
            dept.setName(name);
        }
        if(SysToolUtil.isNotBlank(description)){
            if(description.length() > 200){
                throw new BusinessException(ApiConstant.BASE_FAIL_CODE, "部门描述字数过长");
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
