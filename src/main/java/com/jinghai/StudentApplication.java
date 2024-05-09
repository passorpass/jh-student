package com.jinghai;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
@EnableScheduling // 启用定时任务
@EnableTransactionManagement // 启用事务管理
@MapperScan(basePackages = "com.jinghai.system.mapper")
public class StudentApplication {

    public static void main(String[] args) throws UnknownHostException {

        LocalDateTime start = LocalDateTime.now();
        ConfigurableApplicationContext application = SpringApplication.run(StudentApplication.class, args);
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start,end);
        long second = duration.getSeconds();
        log.info("服务启动成功 " + second + " 秒");
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String name = env.getProperty("spring.application.name");
        System.out.println("\n----------------------------------------------------------\n\t" +
                "Application system is running! Access URLs:\n\t" +
                "CustomerLocal: \t\thttp://localhost:"+ port + "/"+name+ "/\n\t" +
                "BackLocal:  \t\thttp://localhost:" + port +  "/"+name+ "/\n\t" +
                "External:  \t\thttp://" + ip + ":" + port  + "/"+name+"/\n\t" +
                "----------------------------------------------------------");

    }

}
