package org.lov3camille.trend.job;

import cn.hutool.core.date.DateUtil;
import org.lov3camille.trend.pojo.Index;
import org.lov3camille.trend.service.IndexDataService;
import org.lov3camille.trend.service.IndexService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public class IndexDataSyncJob extends QuartzJobBean {

    @Autowired
    private IndexDataService indexDataService;

    @Autowired
    private IndexService indexService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Quartz starts: " + DateUtil.now());
        List<Index> indexes = indexService.fresh();
        for (Index index : indexes) {
            indexDataService.fresh(index.getCode());
        }
        System.out.println("Quartz ends: " + DateUtil.now());
    }
}
