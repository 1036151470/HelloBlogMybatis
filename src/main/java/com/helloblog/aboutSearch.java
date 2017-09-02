package com.helloblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class aboutSearch {

    @RequestMapping("/baidu")
    public String baidu(String s) throws UnsupportedEncodingException {
            return "redirect:https://www.baidu.com/baidu?wd=" + URLEncoder.encode(s, "UTF-8");
    }

    @RequestMapping("/github")
    public String github(String s) throws UnsupportedEncodingException {
        return "redirect:https://github.com/search?utf8=%E2%9C%93&q=" + URLEncoder.encode(s, "UTF-8")+"&type=";
    }

    @RequestMapping("/mavenRepository")
    public String mavenRepository(String s) throws UnsupportedEncodingException {
        return "redirect:http://mvnrepository.com/search?q=" + URLEncoder.encode(s, "UTF-8");
    }

}
