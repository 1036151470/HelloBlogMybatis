package com.helloblog;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.helloblog.database.blogcontext;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Controller
public class blogController {
    @Autowired
    SqlSession sqlSession;

    @PostMapping("/reguser")
    @ResponseBody
    public String postreguser(String username, String password, String permissteam) {
        System.out.println(username);
        System.out.println(password);
        System.out.println(permissteam);
        return username + " " + password + " " + permissteam;
    }


    @GetMapping("/reguser")
    public String reguser() {
        return "reguser";
    }


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    //搜索，支持模糊搜索,或者部分匹配
    @RequestMapping("/search")
    public String search(Map<String, Object> map, String search) {
        Map<String, String> justsearch = new HashMap<>();
        search = "'" + search + "'";
        justsearch.put("regexp", search);

        Iterable<blogcontext> iterable = sqlSession.selectList("justsearch", justsearch);
        map.put("context", iterable);
        return "index";

    }

    @RequestMapping("/aboutsearch")
    public String aboutsearch() {
        return "aboutsearch";
    }


    //编辑更新
    @RequestMapping("/blogcontext/{num}")
    public String blogcontext(@PathVariable int num, Map<String, Object> map) {
        Date d = new Date();
        blogcontext c = sqlSession.selectOne("getone", num);
        map.put("context", c);
        return "blogcontext";
    }


    //指定写入数据的数量
    @RequestMapping("/wirtedatahtml")
    public String wirtedatahtml() {
        return "wirtedatahtml";
    }


    //指定写入数据的数量
    @RequestMapping("/w/wirtedatahtml")
    public String wwirtedatahtml(String title, String con) {
        blogcontext c = new blogcontext();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        c.setCreatedtime(sdf.format(d));
        c.setTitle(title);
        c.setContext(con);
        sqlSession.insert("insertinto", c);
        return "redirect:/";
    }


    //编辑更新
    @RequestMapping("/editup/{num}")
    public String editup(@PathVariable int num, String title, String con) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        blogcontext c = new blogcontext();
        c.setChangetime(sdf.format(d));
        c.setTitle(title);
        c.setContext(con);
        c.setId(num);
        sqlSession.selectOne("update", c);
        return "redirect:/";
    }


    //编辑
    @RequestMapping("/edit/{num}")
    public String edit(@PathVariable int num, Map<String, Object> map) {
        blogcontext c = sqlSession.selectOne("getone", num);
        map.put("context", c);
        return "edit";
    }


    //删除指定id
    @RequestMapping("/delid/{num}")
    public String delid(@PathVariable int num) {
        sqlSession.selectList("delid", num);
        return "redirect:/";
    }


    @RequestMapping("/page/{num}")
    public String index(@PathVariable int num, Map<String, Object> map) {
        if (num <= 0) {
            return "redirect:/";
        }
        PageHelper.startPage(num, 10);
        Iterable<blogcontext> iterable = sqlSession.selectList("getall");
        map.put("context", iterable);
        map.put("lastpage", num + 1);
        map.put("nextpage", num - 1);
        return "index";
    }


    @RequestMapping({"/", "/index"})
    public String index(Map<String, Object> map, HttpServletRequest request) {
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");

        try {
            securityContextImpl.getAuthentication().getName();
        } catch (Exception e) {
            return "/login";
        }

        List<blogcontext> userlist = sqlSession.selectList("getall");
        PageInfo<blogcontext> pageInfo = new PageInfo<blogcontext>(userlist);
        int basesize = (int) pageInfo.getTotal();


        if (basesize <= 0) {
            return "/index";
        } else {

            int page = 0;

            if (basesize % 10 == 0) {
                page = (int) (basesize / 10);
            } else {

                page = (int) (basesize / 10) + 1;
            }

            PageHelper.startPage(page, 10);
            Iterable<blogcontext> iterable = sqlSession.selectList("getall");
            map.put("context", iterable);
            map.put("nextpage", "/page/" + String.valueOf(page - 1));
            return "/index";
        }
    }

}
