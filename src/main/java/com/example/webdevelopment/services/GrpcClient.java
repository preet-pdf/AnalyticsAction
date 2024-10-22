package com.example.webdevelopment.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.grpc.GetRuleResponse;
import com.org.grpc.GetRuleServiceGrpc;
import com.org.grpc.Void;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrpcClient {

    @Autowired
    ObjectMapper mapper;

    public JsonNode getRulesAsJson() throws JsonProcessingException {
        // Create a channel to connect to the gRPC server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        // Create a stub for making gRPC requests
        GetRuleServiceGrpc.GetRuleServiceBlockingStub stub = GetRuleServiceGrpc.newBlockingStub(channel);

        // Create a request object (here we use an empty message `Void`)
        Void request = Void.newBuilder().build();

        // Make the RPC call and get the response
        GetRuleResponse response = stub.getRule(request);

        // Print the received rules (in JSON format)
        System.out.println("Received Rules: " + response.getResponse());

        // Close the channel after the call is complete
        channel.shutdown();
        String ruleResponse = response.getResponse();

        return mapper.readTree(response.getResponse());

    }
}
