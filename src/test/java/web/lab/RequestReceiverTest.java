package web.lab;

import org.junit.Before;
import org.junit.Test;
import web.lab.requests.Request;
import web.lab.requests.RequestReceiver;
import web.lab.response.Response;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static web.lab.http.Method.DELETE;
import static web.lab.http.Method.GET;
import static web.lab.http.Method.POST;
import static web.lab.http.Method.PUT;
import static web.lab.http.Status.CREATED;
import static web.lab.http.Status.NOT_FOUND;
import static web.lab.http.Status.NOT_MODIFIED;
import static web.lab.http.Status.OK;

public class RequestReceiverTest {

    private RequestReceiver requestReceiver;

    @Before
    public void setUp() throws Exception {
        requestReceiver = new RequestReceiver();
    }

    @Test
    public void itShouldReturnNotFoundForNotExistedResources() throws Exception {
        assertThat(
                requestReceiver.handle(new Request.Builder("url").build()),
                is(new Response.Builder().setStatus(NOT_FOUND).build())
        );
    }

    @Test
    public void itShouldReturnNotFoundForPostOnNonExistedResource() throws Exception {
        assertThat(
                requestReceiver.handle(new Request.Builder("url").setMethod(POST).build()),
                is(new Response.Builder().setStatus(NOT_FOUND).build())
        );
    }

    @Test
    public void itShouldReturnOkAfterResourceModification() throws Exception {
        requestReceiver.handle(new Request.Builder("url").setMethod(PUT).build());
        assertThat(
                requestReceiver.handle(new Request.Builder("url").build()),
                is(new Response.Builder().setStatus(NOT_MODIFIED).build())
        );

        assertThat(
                requestReceiver.handle(new Request.Builder("url").setMethod(POST).build()),
                is(new Response.Builder().setStatus(OK).build())
        );
    }

    @Test
    public void itShouldUnmodifiedAfterOnSecondTimeAfterPost() throws Exception {
        requestReceiver.handle(new Request.Builder("url").setMethod(PUT).build());
        requestReceiver.handle(new Request.Builder("url").setMethod(POST).build());
        assertThat(
                requestReceiver.handle(new Request.Builder("url")
                        .setIfModifiedSince(LocalDateTime.now()).build()),
                is(new Response.Builder().setStatus(NOT_MODIFIED).build())
        );
    }

    @Test
    public void itShouldCreateNewUrlByPut() throws Exception {
        assertThat(
                requestReceiver.handle(new Request.Builder("url").setMethod(GET).build()),
                is(new Response.Builder().setStatus(NOT_FOUND).build())
        );

        assertThat(
                requestReceiver.handle(new Request.Builder("url").setMethod(PUT).build()),
                is(new Response.Builder().setStatus(CREATED).build())
        );
    }

    @Test
    public void itShouldNotModifiedIfPutSecondTime() throws Exception {
        requestReceiver.handle(new Request.Builder("url").setMethod(GET).build());
        requestReceiver.handle(new Request.Builder("url").setMethod(PUT).build());

        assertThat(
                requestReceiver.handle(new Request.Builder("url").setMethod(GET).build()),
                is(new Response.Builder().setStatus(NOT_MODIFIED).build())
        );
    }

    @Test
    public void itShouldDeleteUrlByDeleteMethod() throws Exception {
        requestReceiver.handle(new Request.Builder("url").setMethod(PUT).build());

        assertThat(
                requestReceiver.handle(new Request.Builder("url").setMethod(DELETE).build()),
                is(new Response.Builder().setStatus(OK).build())
        );

        assertThat(
                requestReceiver.handle(new Request.Builder("url").setMethod(GET).build()),
                is(new Response.Builder().setStatus(NOT_FOUND).build())
        );
    }

    @Test
    public void itShouldNotFoundIfDeleteSecondTime() throws Exception {
        requestReceiver.handle(new Request.Builder("url").setMethod(PUT).build());
        requestReceiver.handle(new Request.Builder("url").setMethod(DELETE).build());

        assertThat(
                requestReceiver.handle(new Request.Builder("url").setMethod(DELETE).build()),
                is(new Response.Builder().setStatus(NOT_FOUND).build())
        );
    }
}
