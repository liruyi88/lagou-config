package com.lagou.service;

import com.lagou.domain.PromotionSpace;

import java.util.List;

public interface PromotionSpaceService {
    public List<PromotionSpace> findAllPromotionSpace();
    /**
     * 添加广告位
     */
    public void savePromotionSpace(PromotionSpace promotionSpace);
    /**
     * 根据id查询广告位信息
     */
    public PromotionSpace findPromotionSpaceById(int id);
    /**
     * 更新广告位信息
     */
    public void updatePromotionSpace(PromotionSpace promotionSpace);
}
