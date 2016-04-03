package web.lab;

import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ServerTest {

    @Test
    @Ignore
    public void itShouldReturnUnmodifiedIfServerWasDown() throws Exception {
        Server server = new Server();
        server.start();

        server.handler.handle(new Request.Builder("url").setMethod(HttpMethod.POST).build());
        LocalDateTime time = LocalDateTime.now();

        server.stop();

        server.start();

        assertThat(
                server.handler.handle(new Request.Builder("url").setIfModifiedSince(time).build()),
                is(new Response.Builder().setStatus(304).build())
        );
    }
}
