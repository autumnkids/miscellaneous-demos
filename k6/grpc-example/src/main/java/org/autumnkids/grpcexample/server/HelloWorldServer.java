package org.autumnkids.grpcexample.server;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import org.autumnkids.grpcexample.helloworld.HelloRequest;
import org.autumnkids.grpcexample.helloworld.HelloResponse;
import org.autumnkids.grpcexample.helloworld.GreeterGrpc.GreeterImplBase;

public class HelloWorldServer {

  private static final int PORT = 9001;

  private final Server server;

  public HelloWorldServer() {
    server = Grpc.newServerBuilderForPort(PORT, InsecureServerCredentials.create())
        .addService(new GreeterImpl())
        .build();
  }

  private void start() throws IOException, InterruptedException {
    server.start();
    System.out.println(String.format("Server started at port %d", PORT));
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        HelloWorldServer.this.stop();
        System.out.println("Server is shut down");
      }
    });
    server.awaitTermination();
  }

  private void stop() {
    server.shutdownNow();
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    final HelloWorldServer server = new HelloWorldServer();
    server.start();
  }

  private static class GreeterImpl extends GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
      HelloResponse response = HelloResponse.newBuilder()
          .setMessage(String.format("Hello %s!", request.getName()))
          .build();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }
  }
}
