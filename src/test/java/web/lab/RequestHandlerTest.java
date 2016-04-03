package web.lab;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static web.lab.RequestHandler.MODIFIABLE_URL;
import static web.lab.RequestHandler.NEVER_MODIFIED_URL;
import static web.lab.RequestHandler.NON_EXISTED_URL;

public class RequestHandlerTest {

    private RequestHandler requestHandler;

    @Before
    public void setUp() throws Exception {
        requestHandler = new RequestHandler();
    }

    @Test
    public void itShouldReturnNotFoundForNotExistedResources() throws Exception {
        assertThat(
                requestHandler.handle(new Request.Builder(NON_EXISTED_URL).build()),
                is(new Response.Builder().setStatus(404).build())
        );
    }

    @Test
    public void itShouldReturnNotFoundForPostOnNonExistedResource() throws Exception {
        assertThat(
                requestHandler.handle(new Request.Builder(NON_EXISTED_URL).setMethod(HttpMethod.POST).build()),
                is(new Response.Builder().setStatus(404).build())
        );
    }

    @Test
    public void itShouldReturnNotModifiedForNotModifiedResource() throws Exception {
        assertThat(
                requestHandler.handle(new Request.Builder(NEVER_MODIFIED_URL).build()),
                is(new Response.Builder().setStatus(304).build())
        );
    }

    @Test
    public void itShouldReturnOkAfterResourceModification() throws Exception {
        assertThat(
                requestHandler.handle(new Request.Builder(MODIFIABLE_URL).build()),
                is(new Response.Builder().setStatus(304).build())
        );

        assertThat(
                requestHandler.handle(new Request.Builder(MODIFIABLE_URL).setMethod(HttpMethod.POST).build()),
                is(new Response.Builder().setStatus(200).build())
        );
    }

    @Test
    public void itShouldUnmodifiedAfterOnSecondTimeAfterPost() throws Exception {
        requestHandler.handle(new Request.Builder(MODIFIABLE_URL).build());
        requestHandler.handle(new Request.Builder(MODIFIABLE_URL).setMethod(HttpMethod.POST).build());
        assertThat(
                requestHandler.handle(new Request.Builder(MODIFIABLE_URL).setIfModifiedSince(LocalDateTime.now())
                        .build()),
                is(new Response.Builder().setStatus(304).build())
        );
    }
}
