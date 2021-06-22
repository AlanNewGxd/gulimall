package com.gxd.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxd.common.utils.PageUtils;
import com.gxd.common.utils.Query;
import com.gxd.gulimall.ware.dao.WareInfoDao;
import com.gxd.gulimall.ware.entity.WareInfoEntity;
import com.gxd.gulimall.ware.service.WareInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<WareInfoEntity> page = this.page(
//                new Query<WareInfoEntity>().getPage(params),
//                new QueryWrapper<WareInfoEntity>()
//        );
//
//        return new PageUtils(page);
//    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareInfoEntity> queryWrapper = new QueryWrapper<>();
        String key = (String)params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.eq("id", key).or()
                    .like("name", key).or()
                    .like("address", key).or()
                    .like("areacode", key);
        }

        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}