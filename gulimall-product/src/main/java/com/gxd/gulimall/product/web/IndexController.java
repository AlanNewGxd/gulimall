package com.gxd.gulimall.product.web;

import com.gxd.gulimall.product.entity.CategoryEntity;
import com.gxd.gulimall.product.service.CategoryService;
import com.gxd.gulimall.product.vo.Catalog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;


    @GetMapping({"/", "index.html"})
    public String indexPage(Model model) {
        // TODO 1、查出所有1级分类
        List<CategoryEntity> categoryEntitys = categoryService.getLevel1Categorys();

        model.addAttribute("categorys", categoryEntitys);
        return "index";
    }

    /**
     * 查出三级分类
     * 1级分类作为key，2级引用List
     */
    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        Map<String, List<Catalog2Vo>> map = categoryService.getCatalogJson();
        return map;
    }

    /**
     * redis缓存,分布式锁,缓存穿透，雪崩，击穿
     */
    @ResponseBody
    @GetMapping("/index/catalog2.json")
    public Map<String, List<Catalog2Vo>> getCatalogJson2() {
        Map<String, List<Catalog2Vo>> map = categoryService.getCatalogJson2();
        return map;
    }

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
