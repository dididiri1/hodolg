package com.hodolog.api.controller;


import com.hodolog.api.domain.Post;
import com.hodolog.api.request.PostCreate;
import com.hodolog.api.request.PostSearch;
import com.hodolog.api.response.PostResponse;
import com.hodolog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate postCreate) {

        // Case1. 저장한 데이터 Entity => reponse로 응답하기
        // Case2. 저장한 데이터 primary_id -> reponse로 응답하기
        //         Client에서는 수신한 id를 글 조회 API를 통해서 데이터를 수신받음.
        // Case3. 응답 필요 없음 -> 클라이언트에서 모든 POST(글) 데이터ㅗ context를 잘 관리함
        // Bad Case: 서버에서 -> 반드시 이렇케 할겁니다.! FIX
        //      -> 서버에서 차라리 유연하게 대응하는게 좋음.

        postService.write(postCreate);

        //return Map.of();
    }

    /**
     * /posts -> 글 전체 조회(검색 + 페이징)
     * /posts/{postId} -> 글 한개만 조회
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long postId) {
        // 응답 클래스 분리하세요. (서비스 정책에 맞는)

        PostResponse response = postService.get(postId);

        return response;

    }

    // 조회 API
    // 지난 시간 = 단건 조회 API (1개의 글을 Post 가져오는 기능
    // 이번 시간 = 여러개의 글을 조회 API
    /*@GetMapping("/posts")
    public List<PostResponse> getList(@PageableDefault(size = 5) Pageable pageable) {
        return postService.getList(pageable);
    }*/

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }
}
