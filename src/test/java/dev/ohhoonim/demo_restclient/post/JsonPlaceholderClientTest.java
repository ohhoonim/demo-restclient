package dev.ohhoonim.demo_restclient.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import dev.ohhoonim.demo_restclient.jsonPlaceholder.model.Post;
import dev.ohhoonim.demo_restclient.jsonPlaceholder.port.PostClient;

public class JsonPlaceholderClientTest {

    private MockRestServiceServer mockServer;
    private PostClient postClient;

    @BeforeEach
    void setUp() {
        RestClient.Builder sharedBuilder = RestClient.builder();

        this.mockServer = MockRestServiceServer.bindTo(sharedBuilder).build();

        RestClientAdapter adapter = RestClientAdapter.create(sharedBuilder.build());
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        this.postClient = factory.createClient(PostClient.class);
    }

    @Test
    void postDetails() {
        String expectedResponseBody = """
                {
                  "userId": 1,
                  "id": 1,
                  "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                  "body": "quia et suscipitsuscipit recusandae consequuntur expedita et cumreprehenderit molestiae ut ut quas totam"
                }
                                """;

        mockServer.expect(requestTo("/posts/1"))
                .andRespond(withSuccess(expectedResponseBody, MediaType.APPLICATION_JSON));

        Optional<Post> post = postClient.postDetails(1);

        mockServer.verify();
        assertThat(post.get().id()).isEqualTo(1);
        assertThat(post.get().body()).contains("expedita");
    }

}
