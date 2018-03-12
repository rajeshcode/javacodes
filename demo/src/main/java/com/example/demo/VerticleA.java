package com.example.demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.rxjava.servicediscovery.ServiceDiscovery;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
//import io.vertx.reactivex.servicediscovery.ServiceDiscovery;
//import io.vertx.core.Vertx.ServiceDiscovery;


public class VerticleA extends AbstractVerticle {



    public  static void main(String ... args) {

        /* Refered here http://vertx.io/docs/vertx-hazelcast/java/ */
        /* For Cluster   */
        ClusterManager mgr = new HazelcastClusterManager();
        //VertxOptions options = new VertxOptions().setClusterManager(mgr);

        VerticleA a = new VerticleA();

      Vertx.clusteredVertx(new VertxOptions().setClusterManager(mgr), res -> {
              res.result().deployVerticle(new VerticleA());

       });



        //a.start();


    }

    @Override
    public void start() {
        //System.out.println(" Hello Fine");
        //ServiceDiscovery discovery = ServiceDiscovery.create(vertx);

      /*  VerticleB component = new VerticleB();
        component.greeting("Vert.x");
    }*/
       /* Vertx.vertx().createHttpServer().requestHandler(req -> {
            req.response()
                .putHeader("content-type", "text/plain")
                .end("Hello from Vert.x!");
        }).listen(8080);
        System.out.println("HTTP server started on port 8080");
    }*/

//        vertx.deployVerticle(new VerticleB(), deployed -> {
//            vertx.eventBus().send("greetings", "VertexR.");
//        });

        vertx.setPeriodic(1000, rl-> {
        vertx.eventBus().<JsonObject>send(
            "Ping.Pong",
            new JsonObject().put("msg", "Ping"),
        reply->{
            System.out.println("Message Reply receive event"+ reply
            .result().body().getString("msg"));

        });
        });
        System.out.println("Messaged sent event");
    }

    /*@Override
    public void start(){
        vertx.createHttpServer()
            .requestHandler(req -> req.response().end("Hello"))
            .listen(8081, ready -> {
                if (ready.succeeded()) {
                    System.out.println("Server Ready");
                } else {
                    System.out.println("Server is not ready");

                }
            });

    }*/

}

