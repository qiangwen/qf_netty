package com.qf.netty;

import com.qf.netty.app.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: wenqiang
 * @date: 2019-09-23 14:17
 */
@SpringBootApplication
public class NettyServerApplication {

    public static void main(String[] args) {
        NettyServer server = new NettyServer(9001);
        server.start();
        SpringApplication.run(NettyServerApplication.class,args);
    }
}
