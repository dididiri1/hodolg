package com.hodolog.api.request;


import com.hodolog.api.exception.InvalidRequest;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    public String title;

    @NotBlank(message = "컨텐츠를 입력해주세요.")
    public String content;

    // new PostCreate

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 빌더의 장점
    //  - 가독성에 좋다. (값 생성에 대한 유연함)
    //  = 필요한 값만 받을 수 있다. => (오버로딩 가능한 조건 찾아보세요)
    //  - 객체의 불변성

    public void validate() {
        if (title.contains("바보")) {
            throw new InvalidRequest("title","제목에 바보를 포함할 수 없습니다.");
        }
    }
}
