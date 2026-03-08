package com.dingzk.dingapicommon.rpc;

public interface UserRpcService {
    String getUserSecretKeyByAccessKey(String accessKey);

    boolean consumeUserInvokeLimit(long userId);
}
