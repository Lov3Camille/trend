package org.lov3camille.trend.service;

import cn.hutool.core.collection.CollUtil;
import org.lov3camille.trend.pojo.Index;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@CacheConfig(cacheNames = "indexes")
public class IndexService {

    private List<Index> indexes;

    @Cacheable(key = "'all_codes'")
    public List<Index> get() {
        Index index = new Index();
        index.setName("useless code");
        index.setCode("000000");
        return CollUtil.toList(index);
    }

}
