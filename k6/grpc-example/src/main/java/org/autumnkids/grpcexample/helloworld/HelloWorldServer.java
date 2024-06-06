package org.autumnkids.grpcexample.helloworld;

import io.grpc.stub.StreamObserver;
import org.autumnkids.grpcexample.helloworld.GreeterGrpc.GreeterImplBase;

public class HelloWorldServer {

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
