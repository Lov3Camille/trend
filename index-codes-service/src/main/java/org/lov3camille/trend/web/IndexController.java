package org.lov3camille.trend.web;

import com.netflix.discovery.converters.Auto;
import org.lov3camille.trend.config.IpConfiguration;
import org.lov3camille.trend.pojo.Index;
import org.lov3camille.trend.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Autowired
    IndexService indexService;

    @Autowired
    IpConfiguration ipConfiguration;

    // http://127.0.0.1:8011/codes

    @GetMapping("/codes")
    @CrossOrigin
    public List<Index> codes() throws Exception {
        System.out.println("current instance's port is " + ipConfiguration.getPort());
        return indexService.get();
    }
}
