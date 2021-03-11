package org.lov3camille.trend;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.Converter;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableCaching
public class IndexGatherStoreApplication {
    public static void main(String[] args) {
        int port = 0;
        int defaultPort = 8001;
        int redisPort = 6379;
        int eurekaServerPort = 8761;
        port = defaultPort;
        if (NetUtil.isUsableLocalPort(eurekaServerPort)) {
            System.err.printf("Noticing that port %d has not been used, so eureka server has not been launched, this service cannot be used, exit!%n",
                    eurekaServerPort);
            System.exit(1);
        }

        if (NetUtil.isUsableLocalPort(redisPort)) {
            System.err.printf("Noticing that port %d has not been used, so redis server has not been launched, this service cannot be used, exit!%n",
                    redisPort);
            System.exit(1);
        }

        if (null != args && 0 != args.length) {
            for (String arg : args) {
                if (arg.startsWith("port=")) {
                    String strPort = StrUtil.subAfter(arg, "port=", true);
                    if (NumberUtil.isNumber((strPort))) {
                        port = Convert.toInt(strPort);
                    }
                }
            }
        }

        if (!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("port %d has been occupied, launching failed!%n", port);
            System.exit(1);
        }
        new SpringApplicationBuilder(IndexGatherStoreApplication.class).properties("server.port=" + port).run(args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
