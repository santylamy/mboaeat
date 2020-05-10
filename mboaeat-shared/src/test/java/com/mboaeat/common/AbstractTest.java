package com.mboaeat.common;

import com.pszymczyk.consul.ConsulPorts;
import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.junit.ConsulExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.shaded.okhttp3.OkHttpClient;
import org.testcontainers.shaded.okhttp3.Request;

import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTest {

    @RegisterExtension
    ConsulExtension consul = new ConsulExtension(
            ConsulStarterBuilder
                    .consulStarter()
                    .withWaitTimeout(600)
                    .withConsulPorts(
                            ConsulPorts
                                    .consulPorts()
                                    .withHttpPort(8501)
                                    .build()
                    )
                    .build()
    );

    private OkHttpClient client = new OkHttpClient();

    @Test
    void shouldStartConsul() throws Throwable {
        await().atMost(30, TimeUnit.SECONDS).until(() -> {
            Request request = new Request.Builder()
                    .url("http://localhost:" + consul.getHttpPort() + "/v1/agent/self")
                    .build();

            return client.newCall(request).execute().code() == 200;
        });
    }
}
