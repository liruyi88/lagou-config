package com.lagou.dao;

import com.lagou.domain.Resource;
import com.lagou.domain.ResourceVo;

import java.util.List;

public interface ResourceMapper {
    /**
     * 查询所有资源
     */
    public List<Resource> findAllResource(ResourceVo resourceVo);
}
