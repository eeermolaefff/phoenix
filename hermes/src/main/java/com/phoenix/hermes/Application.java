package com.phoenix.hermes;

import com.phoenix.hermes.grpc.impl.HermesImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
//        Server server = ServerBuilder.forPort(9090)
//                .addService(new HermesImpl())
//                .build();
//
//        server.start();
//        log.info("Server started at port: {}", server.getPort());
//        server.awaitTermination();

        while (true) {
            log.info("I'm alive!");
            Thread.sleep(1000);
        }
    }
}