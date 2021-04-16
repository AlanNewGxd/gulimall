package com.gxd.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxd.common.utils.PageUtils;
import com.gxd.gulimall.member.entity.MemberCollectSubjectEntity;

import java.util.Map;

/**
 * 会员收藏的专题活动
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-16 09:32:38
 */
public interface MemberCollectSubjectService extends IService<MemberCollectSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

