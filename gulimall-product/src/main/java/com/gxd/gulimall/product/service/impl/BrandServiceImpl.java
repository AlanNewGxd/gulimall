package com.gxd.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxd.common.utils.PageUtils;
import com.gxd.common.utils.Query;
import com.gxd.gulimall.product.dao.BrandDao;
import com.gxd.gulimall.product.entity.BrandEntity;
import com.gxd.gulimall.product.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    public PageUtils queryPage2(Map<String, Object> params) {
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                new QueryWrapper<BrandEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * @Description 改造模糊查询wrapper
     * @Param [params]
     * @Author guxiaodong
     * @Date 15:44 2021/6/4
     **/
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
        String key = params.get("key").toString();
        if(!StringUtils.isEmpty(key)){
            // 字段等于  or  模糊查询
            wrapper.eq("brand_id", key).or().like("name", key);
        }
        // 按照分页信息和查询条件  进行查询
        IPage<BrandEntity> page = this.page(
                // 传入一个IPage对象，他是接口，实现类是Page
                new Query<BrandEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }
}