package com.dingzk.dingapi;

import com.dingzk.dinginterfacesdk.client.ApiClient;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DingApiBdApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private ApiClient apiClient;

    @Test
    void testInvokeInterfaceFailedWithWrongSecretKey() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> apiClient.getRandomInteger("{\"rangeMax\":1000}"));
        System.out.println(exception.getMessage());
    }

}
