package org.sample;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import sun.security.provider.certpath.Vertex;

import javax.xml.ws.Response;

@RunWith(VertxUnitRunner.class)
public class reactivePrgTest {
    private Vertx vertx;

    @Before
    public void setUp(TestContext context){
        vertx = Vertx.vertx();
        vertx.deployVerticle(reactivePrg.class.getName(),
                context.asyncAssertSuccess());

    }
    @After
    public void tearDown(TestContext context){
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testmyApp(TestContext context){
        final Async async = context.async();

        vertx.createHttpClient().getNow(8081,"localhost","/",
                respose->{
              respose.handler(body ->{
                  context.assertTrue(body.toString().contains("Hello"));
                  async.complete();
              });
                });

    }
}
