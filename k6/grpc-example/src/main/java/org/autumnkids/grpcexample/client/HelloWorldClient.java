package org.autumnkids.grpcexample.client;

import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import org.autumnkids.grpcexample.helloworld.GreeterGrpc;
import org.autumnkids.grpcexample.helloworld.HelloRequest;
import org.autumnkids.grpcexample.helloworld.HelloResponse;
import org.autumnkids.grpcexample.helloworld.GreeterGrpc.GreeterBlockingStub;

public class HelloWorldClient {

  private static final String SERVER = "localhost:9001";

  private final GreeterBlockingStub serverStub;

  public HelloWorldClient(Channel channel) {
    serverStub = GreeterGrpc.newBlockingStub(channel);
  }

  public void greet() {
    HelloRequest request = HelloRequest.newBuilder()
        .setName("World")
        .build();
    HelloResponse response = serverStub.sayHello(request);
    System.out.println(response.getMessage());
  }

  public static void main(String[] args) throws InterruptedException {
    ManagedChannel channel = Grpc
        .newChannelBuilder(SERVER, InsecureChannelCredentials.create())
        .build();
    HelloWorldClient client = new HelloWorldClient(channel);
    client.greet();
    channel.shutdownNow();
  }
}
