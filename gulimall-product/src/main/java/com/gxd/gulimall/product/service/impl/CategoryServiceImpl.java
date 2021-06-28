package com.gxd.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxd.common.utils.PageUtils;
import com.gxd.common.utils.Query;
import com.gxd.gulimall.product.dao.CategoryDao;
import com.gxd.gulimall.product.entity.CategoryEntity;
import com.gxd.gulimall.product.service.CategoryBrandRelationService;
import com.gxd.gulimall.product.service.CategoryService;
import com.gxd.gulimall.product.vo.Catalog2Vo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

//    TODO: 2021/4/28 可以注入使用，也可以用mybatisplus的ServiceImpl<CategoryDao, CategoryEntity>
//    @Autowired
//    CategoryDao categoryDao;
    @Resource
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listCategoryTree() {
        //查出所有分类
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        //组装成父子的树形结构
        //找到所有的一级分类  java8的新方法
//      List<CategoryEntity> level1Menus =  categoryEntities.stream().filter(categoryEntity -> {
//            return categoryEntity.getParentCid() == 0 ;
//        }).collect(Collectors.toList());

        //只有一个参数，简化写法
        List<CategoryEntity> level1Menus = categoryEntities.stream().filter(categoryEntity ->
             categoryEntity.getParentCid() == 0
        ).map((menu)->{
            menu.setChildren(getChildrens(menu,categoryEntities));
            return menu;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0:menu1.getSort())-(menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());
        return level1Menus;
    }

    //递归查询所有菜单的子菜单
    private List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all){

        List<CategoryEntity> cildren = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid().equals(root.getCatId());
        }).map((categoryBean) -> {
            //找子菜单
            categoryBean.setChildren(getChildrens(categoryBean, all));
            return categoryBean;
        }).sorted((menu1,menu2)->{
            //菜单排序
            return (menu1.getSort()==null?0:menu1.getSort())-(menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());
        return cildren;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        // TODO: 2021/5/10 1，检测当前删除菜单是否被其他地方引用，以后有业务需求再具体实现（物理删除）

        //目前实现逻辑删除
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCateLogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        paths = findParentPath(catelogId, paths);
        // 收集的时候是顺序 前端是逆序显示的 所以用集合工具类给它逆序一下
        // 子父 转 父子
        Collections.reverse(paths);
        return paths.toArray(new Long[paths.size()]); // 1级  2级  3级
    }

    /**
     * 递归收集所有父分类
     */
    private List<Long> findParentPath(Long catlogId, List<Long> paths) {
        // 1、收集当前节点id
        paths.add(catlogId);// 比如父子孙层级，返回的是 孙 子 父
        CategoryEntity parent_Id = this.getById(catlogId);
        if (parent_Id.getParentCid() != 0) {
            // 递归
            findParentPath(parent_Id.getParentCid(), paths);
        }
        return paths;
    }
    /**
     * 级联更新所有关联的数据
     * @param category
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCascade(CategoryEntity category) {
        this.baseMapper.updateById(category);

        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    //[2,29,20]
    @Override
    public Long[] findCatelogPath(Long catelogId) {

        List<Long> paths = new ArrayList<>();

        //递归查询是否还有父节点
        List<Long> parentPath = findParentPath(catelogId, paths);

        //进行一个逆序排列
        Collections.reverse(parentPath);

        return (Long[]) parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 查询一级分类。
     * 父ID是0， 或者  层级是1
     */
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        System.out.println("调用了 getLevel1Categorys  查询了数据库........【一级分类】");
        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
    }

    // TODO 产生堆外内存溢出：OutOfDirectMemoryError
    // 1）springboot2.0以后默认使用lettuce作为操作redis的客户端。他使用netty进行网络通信
    // 2）lettuce的bug导致netty堆外内存溢出 -Xmx1024m；netty如果没有指定堆外内存，默认使用-Xmx1024m，跟jvm设置的一样【迟早会出异常】
    //  可以通过-Dio.netty.maxDirectMemory进行设置【仍然会异常】
    //  解决方案：不能使用-Dio.netty.maxDirectMemory
    //  1）升级lettuce客户端；【2.3.2已解决】【lettuce使用netty吞吐量很大】
    //  2）切换使用jedis客户端【这里学习一下如何使用jedis，但是最后不选用】

    /**
     * 使用Spring Cache简化 缓存存取操作【不需要自己调用redistemplate客户端来存取数据了，直接加注解】
     * 【但是读写锁还是需要redisson配合使用】
     */
    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        // 一次性获取所有数据
        List<CategoryEntity> selectList = baseMapper.selectList(null);
        System.out.println("调用了 getCatalogJson  查询了数据库........【三级分类】");
        // 1）、所有1级分类
        List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);

        // 2）、封装数据
        Map<String, List<Catalog2Vo>> collect = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), level1 -> {
            // 查到当前1级分类的2级分类
            List<CategoryEntity> category2level = getParent_cid(selectList, level1.getCatId());
            List<Catalog2Vo> catalog2Vos = null;
            if (category2level != null) {
                catalog2Vos = category2level.stream().map(level12 -> {
                    // 查询当前2级分类的3级分类
                    List<CategoryEntity> category3level = getParent_cid(selectList, level12.getCatId());
                    List<Catalog2Vo.Catalog3Vo> catalog3Vos = null;
                    if (category3level != null) {
                        catalog3Vos = category3level.stream().map(level13 -> {
                            return new Catalog2Vo.Catalog3Vo(level12.getCatId().toString(), level13.getCatId().toString(), level13.getName());
                        }).collect(Collectors.toList());
                    }
                    return new Catalog2Vo(level1.getCatId().toString(), catalog3Vos, level12.getCatId().toString(), level12.getName());
                }).collect(Collectors.toList());
            }
            return catalog2Vos;
        }));
        return collect;
    }

    /**
     * 查询出父ID为 parent_cid的List集合
     */
    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parent_cid) {
        return selectList.stream().filter(item -> item.getParentCid() == parent_cid).collect(Collectors.toList());
        //return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", level.getCatId()));
    }
}