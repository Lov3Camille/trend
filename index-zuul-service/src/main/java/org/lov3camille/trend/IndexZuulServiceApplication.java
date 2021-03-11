package org.lov3camille.trend;

import cn.hutool.core.util.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
public class IndexZuulServiceApplication {
    // httlp://127.0.0.1:8031/api-codes/codes

    public static void main(String[] args) {
        int port = 8031;
        if (!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("port %d has been occupied, unable to launch it.%n", port);
            System.exit(1);
        }

        new SpringApplicationBuilder(IndexZuulServiceApplication.class).properties("server.port="
        + port).run(args);
    }
}
