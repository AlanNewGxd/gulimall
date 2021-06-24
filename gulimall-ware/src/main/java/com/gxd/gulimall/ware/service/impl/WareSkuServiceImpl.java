package com.gxd.gulimall.ware.service.impl;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxd.common.utils.PageUtils;
import com.gxd.common.utils.Query;
import com.gxd.common.utils.R;
import com.gxd.gulimall.ware.dao.WareSkuDao;
import com.gxd.gulimall.ware.entity.WareSkuEntity;
import com.gxd.gulimall.ware.feign.ProductFeignService;
import com.gxd.gulimall.ware.service.WareSkuService;
import com.gxd.gulimall.ware.vo.SkuHasStockVo;
import com.gxd.gulimall.ware.vo.WareSkuLockVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Resource
    WareSkuDao wareSkuDao;

    @Resource
    ProductFeignService productFeignService;

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<WareSkuEntity> page = this.page(
//                new Query<WareSkuEntity>().getPage(params),
//                new QueryWrapper<WareSkuEntity>()
//        );
//
//        return new PageUtils(page);
//    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        /**
         * 商品库存：没有key
         * skuId:1
         * wareId:2
         */
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        String skuId = (String) params.get("skuId");
        if (!StringUtils.isEmpty(skuId)) {
            queryWrapper.eq("sku_id", skuId);
        }

        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(wareId)) {
            queryWrapper.eq("ware_id", wareId);
        }

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }


    /**
     *  检查sku 是否有库存
     */
    @Override
    public List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds) {
        List<SkuHasStockVo> vos = skuIds.stream().map(skuId -> {
            SkuHasStockVo vo = new SkuHasStockVo();
            // 1、不止一个仓库有，多个仓库都有库存 sum
            // 2、锁定库存是别人下单但是还没下完
            Long count = baseMapper.getSkuStock(skuId);
            vo.setSkuId(skuId);
            vo.setHasStock(count == null ? false : count > 0);
            return vo;
        }).collect(Collectors.toList());
        return vos;
    }


    /**
     * 成功采购=》入库
     * 1、如果有库存记录，直接加库存
     * 2、如果没有库存记录，添加一条记录，并且设置锁定为0
     */
    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        // 1、判断：如果没有库存记录，则新增
        List<WareSkuEntity> wareSkuEntities = wareSkuDao.selectList(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if (CollectionUtils.isEmpty(wareSkuEntities)) {
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setStockLocked(0);
            // TODO 远程查询sku名字，如果失败不需要回滚
            // 方法一：自己catch异常
            // TODO 方法二：高级部分，出现异常不回滚
            try {
                R info = productFeignService.info(skuId);
                if (info.getCode() == 0) {
                    Map<String, Object> skuInfoMap = (Map<String, Object>) info.get("skuInfo");
                    // info.get("skuInfo")获得的是R对象内存的SkuInfoEntity对象
                    // 因为是传的json格式，所以可以强转为(Map<String, Object>)格式
                    wareSkuEntity.setSkuName((String) skuInfoMap.get("skuName"));
                }
            }catch (Exception e){

            }
            wareSkuDao.insert(wareSkuEntity);

        }else {
            wareSkuDao.addStock(skuId, wareId, skuNum);
        }
    }

    /**
     * 为某个订单锁定库存  todo 之后再实现16点30分
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean orderLockStock(WareSkuLockVo vo) {
        return false;
    }
}