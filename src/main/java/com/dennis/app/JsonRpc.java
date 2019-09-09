package com.dennis.app;

import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcMethod;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcParam;
import com.github.arteam.simplejsonrpc.core.annotation.JsonRpcService;

@JsonRpcService
public class JsonRpc{
    @JsonRpcMethod
    public boolean add(@JsonRpcParam("player") String player){
        if(player.contentEquals("dennis")) {
            return false;
        };
        return true;
    }
}