package dev.ohhoonim.demo_restclient.jsonPlaceholder.internal;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import dev.ohhoonim.demo_restclient.jsonPlaceholder.application.CreatePostActivity;
import dev.ohhoonim.demo_restclient.jsonPlaceholder.application.DeletePostActivity;
import dev.ohhoonim.demo_restclient.jsonPlaceholder.application.ListAllPosts;
import dev.ohhoonim.demo_restclient.jsonPlaceholder.application.SearchFilterPostsActivity;
import dev.ohhoonim.demo_restclient.jsonPlaceholder.application.SearchReq;
import dev.ohhoonim.demo_restclient.jsonPlaceholder.application.UpdatePostActivity;
import dev.ohhoonim.demo_restclient.jsonPlaceholder.application.ViewPostComments;
import dev.ohhoonim.demo_restclient.jsonPlaceholder.application.ViewPostDetailsActivity;
import dev.ohhoonim.demo_restclient.jsonPlaceholder.model.Comment;
import dev.ohhoonim.demo_restclient.jsonPlaceholder.model.Post;
import dev.ohhoonim.demo_restclient.jsonPlaceholder.port.PostClient;

@Service
public class PostService implements CreatePostActivity, DeletePostActivity,
        ListAllPosts, SearchFilterPostsActivity, UpdatePostActivity,
        ViewPostComments, ViewPostDetailsActivity {

    private final PostClient postClient;

    public PostService(PostClient postClient) {
        this.postClient = postClient;
    }

    @Override
    public Post postDetails(int postId) {
        return postClient.postDetails(postId)
                .map(post -> {
                    var comments = postClient.postComments(post.id());
                    return post.withAddComments(comments);
                }).orElseThrow(() -> new RuntimeException("post가 존재하지 않습니다"));
    }

    @Override
    public List<Comment> postComments(int postId) {
        return Objects.requireNonNullElse(
                postClient.postComments(postId), List.of());
    }

    @Override
    public Post updatePost(Post post) {
        return postClient.updatePost(post);
    }

    @Override
    public List<Post> searchFilterPosts(SearchReq searchReq) {
        return Objects.requireNonNullElse(
                postClient.searchFilterPosts(searchReq.toMap()), List.of());
    }

    @Override
    public List<Post> allPosts() {
        return Objects.requireNonNullElse(
                postClient.searchFilterPosts(null), List.of());
    }

    @Override
    public void deletePost(int postId) {
        postClient.deletePost(postId);
    }

    @Override
    public Post createPost(Post newPost) {
        return postClient.createPost(newPost);
    }

}
