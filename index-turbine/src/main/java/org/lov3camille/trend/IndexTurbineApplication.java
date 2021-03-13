package org.lov3camille.trend;

import cn.hutool.core.util.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
public class IndexTurbineApplication {

    public static void main(String[] args) {
        int port = 8080;
        int eurekaServerPort = 8761;

        if(NetUtil.isUsableLocalPort(eurekaServerPort)) {
            System.err.printf("port %d is disabled，exit %n", eurekaServerPort );
            System.exit(1);
        }
        if(!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("port %d is occupied, exit %n", port );
            System.exit(1);
        }
        new SpringApplicationBuilder(IndexTurbineApplication.class).properties("server.port=" + port).run(args);

    }
}
