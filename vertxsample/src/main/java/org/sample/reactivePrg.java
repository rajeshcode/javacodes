package org.sample;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class reactivePrg extends AbstractVerticle {


        @Override
        public void start (Future < Void > fut) {

            // Giving in this way ia lambda expression, so java1.5 not supported . So added build information with java1.8 in pom

            vertx.createHttpServer().requestHandler(r -> {
                r.response().end("<h1>Hello from my first " + "Vertx Application</h1>" + "<h2> Rajesh Updated first vertices</h2>");
            }).listen(8081, result -> {
                if (result.succeeded()) {
                    fut.complete();
                } else {
                    fut.fail(result.cause());
                }
            });
//Future object that will let us inform Vert.x when our start sequence is completed or report an error.
// One of the particularity of Vert.x is its asynchronous / non-blocking aspect. When our verticle is going to be deployed it won’t wait until the start method has been completed. So,
// the Future parameter is important to notify of the completion.
            //The start method creates a HTTP server and attaches a request handler to it.
            // The request handler is a lambda, passed in the requestHandler method, called every time the server receives a request.
            // Here, we just reply Hello ... (nothing fancy I told you). Finally, the server is bound to the 8080 port. As this may fails (because the port may already be used), we pass another lambda expression checking whether or not the connection has succeeded. As mentioned above it calls either fut.
            // complete in case of success or fut.fail to report an error.
        }



}
