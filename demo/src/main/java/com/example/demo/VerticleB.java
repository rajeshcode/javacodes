package com.example.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class VerticleB extends AbstractVerticle {
    public  static void main(String ... arg) {
        ClusterManager mgr = new HazelcastClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(mgr);



//        Vertx.clusteredVertx(new VertxOptions().setClustered(false), res -> {
//            res.result().deployVerticle(new VerticleB());
//
//        });
        Vertx.clusteredVertx(options, res -> {
            res.result().deployVerticle(new VerticleB());

        });


    }

    @Override
    public void start() throws Exception {
//        vertx.eventBus()
//            .<String>consumer("greetings")
//            .handler(message -> greeting(message.body()));
        vertx.eventBus().<JsonObject>consumer("Ping.Pong").handler(msg->{
            System.out.println("Message Received Enf"
            +msg.body().getString("msg"));

            msg.reply(new JsonObject().put("msg", "pong"));
            System.out.println("Message reply event");
        });
    }

//    public void  greeting(String name){
//    System.out.println("Hello" + name + "!" + " Wow");
//    }
}


        /*vertx.createHttpServer().requestHandler(req -> {
            req.response()
                .putHeader("content-type", "text/plain")
                .end("Hello from Vert.x!");
        }).listen(8080);
        System.out.println("HTTP server started on port 8080");
    }*/

