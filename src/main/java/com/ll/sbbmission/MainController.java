package com.ll.sbbmission;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/sbb")
    @ResponseBody // URL 요청에 대한 응답으로 문자열을 리턴
    public String index() {
        return "안녕하세요 sbb에 오신것을 환영합니다.";
    }
}
