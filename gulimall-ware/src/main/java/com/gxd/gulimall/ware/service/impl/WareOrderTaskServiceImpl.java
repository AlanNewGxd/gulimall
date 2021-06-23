package com.gxd.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxd.common.utils.PageUtils;
import com.gxd.common.utils.Query;
import com.gxd.gulimall.ware.dao.WareOrderTaskDao;
import com.gxd.gulimall.ware.entity.WareOrderTaskEntity;
import com.gxd.gulimall.ware.service.WareOrderTaskService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("wareOrderTaskService")
public class WareOrderTaskServiceImpl extends ServiceImpl<WareOrderTaskDao, WareOrderTaskEntity> implements WareOrderTaskService {

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<WareOrderTaskEntity> page = this.page(
//                new Query<WareOrderTaskEntity>().getPage(params),
//                new QueryWrapper<WareOrderTaskEntity>()
//        );
//
//        return new PageUtils(page);
//    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareOrderTaskEntity> queryWrapper = new QueryWrapper<>();
        String key = (String)params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.eq("id", key).or()
                    .like("consignee", key).or()
                    .like("consignee_tel", key).or()
                    .like("delivery_address", key);
        }

        IPage<WareOrderTaskEntity> page = this.page(
                new Query<WareOrderTaskEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}