package dev.ohhoonim.demo_restclient.jsonPlaceholder.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.With;

@With
public record Post(
    int userId,
    int id,
    String title,
    String body,
    @With(AccessLevel.PRIVATE)
    List<Comment> comments
) {
    
    public Post {
        comments = List.copyOf(
                Objects.requireNonNullElse(comments, List.of()));
    }

    public Post(int userId, int id, String title, String body) {
        this(userId, id, title, body, List.of());
    }

    public Post withAddComments(List<Comment> newComments) {
        List<Comment> commentsToAdd = Objects.requireNonNullElse(
            comments, List.of()
        ); 
        if (commentsToAdd.isEmpty()) {
            return this;
        }
        var combindComments = new ArrayList<>(this.comments);
        combindComments.addAll(commentsToAdd);

        return withComments(combindComments);
    }

}
/*

@startuml
left to right direction
actor User

rectangle "Post Management System" {
  usecase "게시물 생성" as UC1
  usecase "게시물 상세 조회" as UC2
  usecase "모든 게시물 목록 조회" as UC3
  usecase "게시물 수정" as UC4
  usecase "게시물 삭제" as UC5
  usecase "게시물 검색/필터링" as UC6
  usecase "게시물 댓글 조회" as UC7

  User --> UC1
  User --> UC2
  User --> UC3
  User --> UC4
  User --> UC5
  User --> UC6
  User --> UC7
}
@enduml

 */