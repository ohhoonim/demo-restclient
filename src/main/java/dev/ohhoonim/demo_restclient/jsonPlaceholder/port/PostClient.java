package dev.ohhoonim.demo_restclient.jsonPlaceholder.port;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import dev.ohhoonim.demo_restclient.jsonPlaceholder.application.SearchReq;
import dev.ohhoonim.demo_restclient.jsonPlaceholder.model.Comment;
import dev.ohhoonim.demo_restclient.jsonPlaceholder.model.Post;

@HttpExchange
public interface PostClient {

    @GetExchange("/posts/{id}")
    Optional<Post> postDetails(@PathVariable("id") int id);

    List<Comment> postComments(int postId);

    Post udpatePost(Post post);

    List<Post> searchFilterPosts(SearchReq searchReq);

    void deletePost(int postId);

    Post createPost(Post newPost);
    


}
