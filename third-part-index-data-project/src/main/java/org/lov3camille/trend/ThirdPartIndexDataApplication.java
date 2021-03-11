package org.lov3camille.trend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ThirdPartIndexDataApplication {

    public static void main(String[] args) {
        int port = 8090;
        int eurekaServerPort = 8761;

        if (NetUtil.isUsableLocalPort(eurekaServerPort)) {
            System.err.printf("Port %d is not in use, which means eureka server has not been launched" +
                    " ,so this service cannot be used, exit%n", eurekaServerPort);
            System.exit(1); // 非正常退出程序，如果是0意味着将整个虚拟机里的内容都关掉并且内存都释放掉，正常退出程序。
        }

        if (args != null && args.length != 0) {
            for (String arg : args) {
                if (arg.startsWith("ports=")) {
                    String strPort = StrUtil.subAfter(arg, "port=", true);
                    if (NumberUtil.isNumber(strPort)) {
                        port = Convert.toInt(strPort);
                    }
                }
            }
        }

        if (!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("port %d has been occupied, launching failed!", port);
            System.exit(1);
        }
        new SpringApplicationBuilder(ThirdPartIndexDataApplication.class).properties("server.port="
        + port).run(args);
    }
}
