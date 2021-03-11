package org.lov3camille.trend.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.lov3camille.trend.pojo.IndexData;
import org.lov3camille.trend.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "index_data")
public class IndexDataService {
    private Map<String, List<IndexData>> indexData = new HashMap<>();
    @Autowired
    RestTemplate restTemplate;
    
    @HystrixCommand(fallbackMethod = "third_part_not_connected")
    public List<IndexData> fresh(String code) {
        List<IndexData> indexData2 = fetch_indexs_from_third_part(code);

        indexData.put(code, indexData2);

        System.out.println("code:" + code);
        System.out.println("indexData:" + indexData.get(code).size());

        IndexDataService indexDataService = SpringContextUtil.getBean(IndexDataService.class);

        IndexDataService.remove(code);
        return indexDataService.store(code);
    }

    @CacheEvict(key = "'indexData-code-' + #p0")
    public static void remove(String code) {

    }

    @CachePut(key = "'indexData-code-' + #p0")
    public List<IndexData> store(String code) {
        return indexData.get(code);
    }

    @Cacheable(key = "'indexData-code-' + #p0")
    public List<IndexData> get(String code) {
        return CollUtil.toList();
    }

    public List<IndexData> fetch_indexs_from_third_part(String code) {
        List<Map> temp = restTemplate.getForObject("http://127.0.0.1:8090/indexes/" + code + ".json", List.class);
        return map2IndexData(temp);
    }

    private List<IndexData> map2IndexData(List<Map> temp) {
        List<IndexData> indexData = new ArrayList<>();
        for (Map map : temp) {
            String date = map.get("date").toString();
            float closePoint = Convert.toFloat(map.get("closePoint"));
            IndexData indexData1 = new IndexData();

            indexData1.setDate(date);
            indexData1.setClosePoint(closePoint);
            indexData.add(indexData1);
        }
        return indexData;
    }

    private List<IndexData> third_part_not_connected(String code) {
        System.out.println("third_part_not_connected()");
        IndexData index = new IndexData();
        index.setClosePoint(0);
        index.setDate("n/a");
        return CollectionUtil.toList(index);
    }
}
