package com.hodolog.api.controller;


import com.hodolog.api.request.PostCreate;
import com.hodolog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public Map<String, String> get(@RequestBody @Valid PostCreate postCreate) {
        // db.save(params)
        postService.write(postCreate);

        return Map.of();
    }
}
