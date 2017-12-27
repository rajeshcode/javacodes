

Checkout 
 rajeshcode/javacodes/vertxsample
#### Build 

cd javacodes/vertxsample
mvn clean compile

mvn package 


To execute:

1)
 java -cp /Users/rajec/.m2/repository/io/vertx/vertx-core/3.5.0/vertx-core-3.5.0.jar:/Users/rajec/.m2/repository/io/vertx/vertx-platform/2.1.6/vertx-platform-2.1.6.jar:./target/VertxSample-1.0-SNAPSHOT.jar:/Users/rajec/.m2/repository/io/netty/netty-common/4.1.15.Final/netty-common-4.1.15.Final.jar:/Users/rajec/.m2/repository/io/netty/netty-handler/4.1.15.Final/netty-handler-4.1.15.Final.jar:/Users/rajec/.m2/repository/io/netty/netty-buffer/4.1.15.Final/netty-buffer-4.1.15.Final.jar:/Users/rajec/.m2/repository/io/netty/netty-codec/4.1.15.Final/netty-codec-4.1.15.Final.jar:/Users/rajec/.m2/repository/io/netty/netty-handler-proxy/4.1.15.Final/netty-handler-proxy-4.1.15.Final.jar:/Users/rajec/.m2/repository/io/netty/netty-resolver/4.1.15.Final/netty-resolver-4.1.15.Final.jar:/Users/rajec/.m2/repository/io/netty/netty-transport/4.1.15.Final/netty-transport-4.1.15.Final.jar:/Users/rajec/.m2/repository/io/netty/netty-codec-dns/4.1.15.Final/netty-codec-dns-4.1.15.Final.jar:/Users/rajec/.m2/repository/io/netty/netty-codec-socks/4.1.15.Final/netty-codec-socks-4.1.15.Final.jar:/Users/rajec/.m2/repository/io/netty/netty-codec-http2/4.1.15.Final/netty-codec-http2-4.1.15.Final.jar:/Users/rajec/.m2/repository/io/netty/netty-codec-http/4.1.15.Final/netty-codec-http-4.1.15.Final.jar  io.vertx.core.Launcher run  org.sample.reactivePrg
 
Dec 26, 2017 4:53:06 PM io.vertx.core.spi.resolver.ResolverProvider
INFO: Using the default address resolver as the dns resolver could not be loaded
Dec 26, 2017 4:53:06 PM io.vertx.core.impl.launcher.commands.VertxIsolatedDeployer
INFO: Succeeded in deploying verticle

## Then Browse http://localhost:8081/
Hello from Rajesh first Vertx Application


OR Using Fat Jar

2)
 11002684  ~/sandbox/RajeshGithub/javacodes/vertxsample  master ?  java -jar target/VertxSample-1.0-SNAPSHOT-fat.jar                  
Dec 26, 2017 4:52:50 PM io.vertx.core.impl.launcher.commands.VertxIsolatedDeployer
INFO: Succeeded in deploying verticle

## Then Browse http://localhost:8081/
Hello from Rajesh first Vertx Application
