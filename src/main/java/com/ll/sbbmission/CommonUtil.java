package com.ll.sbbmission;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component // 스프링부트에 의해 관리되는 빈(bean, 자바객체)으로 등록
public class CommonUtil {
    /*
    markdown 메서드는 마크다운 텍스트를 HTML 문서로 변환하여 리턴한다.
    즉, 마크다운 문법이 적용된 일반 텍스트를 변환된(소스코드, 기울이기, 굵게, 링크 등) HTML로 리턴
     */
    public String markdown(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
