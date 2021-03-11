package org.lov3camille.trend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import cn.hutool.core.util.NetUtil;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        //8761 这个端口是默认的，无需修改，后面的子项目都会访问这个端口。
        int port = 8761;
        if (!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("The port %d has been occupied, launching failed!", port);
            System.exit(1);
        }
        new SpringApplicationBuilder(EurekaServerApplication.class).properties("server.port=" +
                port).run(args);
    }
}
