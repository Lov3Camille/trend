package org.lov3camille.trend.client;

import cn.hutool.core.collection.CollectionUtil;
import org.lov3camille.trend.pojo.IndexData;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class IndexDataClientFeignHystrix implements IndexDataClient {

    @Override
    public List<IndexData> getIndexData(String code) {
        IndexData indexData = new IndexData();
        indexData.setClosePoint(0);
        indexData.setDate("0000-00-00");
        return CollectionUtil.toList(indexData);
    }
}
