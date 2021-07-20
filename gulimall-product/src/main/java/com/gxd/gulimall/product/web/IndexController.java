package com.gxd.gulimall.product.web;

import com.gxd.gulimall.product.entity.CategoryEntity;
import com.gxd.gulimall.product.service.CategoryService;
import com.gxd.gulimall.product.vo.Catalog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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

    @Autowired
    RedissonClient redisson;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


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
        //1.获取一把锁，只要锁的名字一样就是同一把锁
        RLock mylock = redisson.getLock("mylock");

        //2,加锁
        mylock.lock(); //阻塞式等待

        try {
            System.out.println("加锁成功，执行业务。。。"+Thread.currentThread().getId());
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //3.解锁
            mylock.unlock();
            System.out.println("解锁成功。。。"+Thread.currentThread().getId());
        }
        //此锁不会出现死锁，内置有自动续期功能--看门狗
        //大家都知道，如果负责储存这个分布式锁的Redisson节点宕机以后，
        //而且这个锁正好处于锁住的状态时，这个锁会出现锁死的状态。
        //为了避免这种情况的发生，Redisson内部提供了一个监控锁的看门狗，
        //它的作用是在Redisson实例被关闭前，不断的延长锁的有效期。
        //默认情况下，看门狗的检查锁的超时时间是30秒钟，也可以通过修改Config.lockWatchdogTimeout来另行指定。
        //另外Redisson还通过加锁的方法提供了leaseTime的参数来指定加锁的时间。超过这个时间后锁便自动解开了。
        return "hello";
    }
    /**
     * @Description 作用：限流，所有服务上来了去获取一个信号量，一个一个放行  ps金豆商城-兑换2元话费（秒杀限流）
     * @Param []
     * @Author guxiaodong
     * @Date 11:14 2021/7/20
     **/
    @GetMapping("/park")
    @ResponseBody
    public String park(){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("park","3");
        //获取信号量
        RSemaphore park = redisson.getSemaphore("park");
        //流量减一  尝试获取一个信号量，一次性不阻塞
        boolean b = park.tryAcquire();
        if(b){
            return "ok-park："+ops.get("park");
        }else {
            return "超量了。。。";
        }
    }

    @GetMapping("/go")
    @ResponseBody
    public String go(){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        RSemaphore park = redisson.getSemaphore("park");
        //释放一个信号量，+1
        park.release();
        return "ok-go："+ops.get("park");
    }

    @GetMapping("/max")
    @ResponseBody
    public String max(){
        //设置信号量大小 3
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("park","3");
        return "ok-go："+ops.get("park");
    }

}
