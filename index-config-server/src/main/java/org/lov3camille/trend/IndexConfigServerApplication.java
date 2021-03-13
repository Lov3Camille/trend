package org.lov3camille.trend;

import cn.hutool.core.util.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
@EnableEurekaClient
public class IndexConfigServerApplication {
    public static void main(String[] args) {
        int port = 8060;

        int eurekaServerPort = 8761;
        if(NetUtil.isUsableLocalPort(eurekaServerPort)) {
            System.err.printf("port%d is disabled，so eureka server has not been started，exit%n", eurekaServerPort );
            System.exit(1);
        }

        if(!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("port %d has been occupied, exit%n", port );
            System.exit(1);
        }
        new SpringApplicationBuilder(IndexConfigServerApplication.class).properties("server.port=" + port).run(args);

    }
}