package com.hodolog.api.service;

import com.hodolog.api.domain.Post;
import com.hodolog.api.repository.PostRepository;
import com.hodolog.api.request.PostCreate;
import com.hodolog.api.request.PostSearch;
import com.hodolog.api.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureMockMvc
@SpringBootTest
class PostServiceTest {

    /*@Autowired
    private MockMvc mockMvc;*/

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given(준비)
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when(실행)
        postService.write(postCreate);

        // then(검증)
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        // given(준비)
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar").build();
        postRepository.save(requestPost);

        Long postId = 1L;

        // when(실행)
        PostResponse response = postService.get(requestPost.getId());

        // then(검증)
        assertNotNull(response);
        assertEquals(1L, postRepository.count());
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());
    }

    /*@Test
    @DisplayName("글 여러개 조회")
    void test3() {
        // given(준비)
        postRepository.saveAll(List.of(
                Post.builder()
                        .title("title_1")
                        .content("content_1")
                        .build(),
                Post.builder()
                        .title("title_1")
                        .content("content_1")
                        .build()
        ));

        // when(실행)
        List<PostResponse> posts = postService.getList();

        // then(검증)
        assertEquals(2L, posts.size());

    }*/

    @Test
    @DisplayName("글 1페이지 조회")
    void test3() {
        // given(준비)
        List<Post> requestPosts = IntStream.range(0, 20)
                        .mapToObj(i -> Post.builder()
                                    .title("호돌맨 제목 " + i)
                                    .content("반포자이 " + i)
                                    .build())
                        .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
               /* .size(10)*/
                .build();

        // when(실행)
        List<PostResponse> posts = postService.getList(postSearch);

        // then(검증)
        assertEquals(10L, posts.size());
        assertEquals("호돌맨 제목 19", posts.get(0).getTitle());


    }

}