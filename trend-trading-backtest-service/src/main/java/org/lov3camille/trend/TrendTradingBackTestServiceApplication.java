package org.lov3camille.trend;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class TrendTradingBackTestServiceApplication {

    public static void main(String[] args) {
        int port = 0;
        int defaultPort = 8051;
        int eurekaServerPort = 8761;

        if (NetUtil.isUsableLocalPort(eurekaServerPort)) {
            System.err.printf("It has been detected that port %d has not been used, so eureka" +
                    "server has not been lauched, exit%n", eurekaServerPort);
            System.exit(1);
        }

        if (null != args && 0!= args.length) {
            for (String arg : args) {
                if (arg.startsWith("port= ")) {
                    String strPort = StrUtil.subAfter(arg, "port=", true);
                    if (NumberUtil.isNumber(strPort)) {
                        port = Convert.toInt(strPort);
                    }
                }
            }
        }

        if (0 == port) {
            Future<Integer> future = ThreadUtil.execAsync(() -> {
                int p = 0;
                System.out.printf("Please enter port number in 5 seconds, %d is recommended, or %d will be used as the default port%n", defaultPort, defaultPort);
                Scanner scanner = new Scanner(System.in);
                while(true) {
                    String strPort = scanner.nextLine();
                    if (!NumberUtil.isInteger(strPort)) {
                        System.err.println("Only number can be used!");
                        continue;
                    }
                    else {
                        p = Convert.toInt(strPort);
                        scanner.close();
                        break;
                    }
                }
                return p;
            });

            try {
                port = future.get(5, TimeUnit.SECONDS);
            }
            catch (InterruptedException | ExecutionException | TimeoutException e) {
                port = defaultPort;
            }
        }

        if (!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("port %d has been occupied, unable to launch it.%n", port);
            System.exit(1);
        }
        new SpringApplicationBuilder(TrendTradingBackTestServiceApplication.class).properties("server.port=" + port)
                .run(args);
    }
}
