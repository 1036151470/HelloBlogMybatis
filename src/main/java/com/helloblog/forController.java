package com.helloblog;

import com.github.pagehelper.PageHelper;
import com.helloblog.database.blogcontext;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/database")
public class forController {

    @Autowired
    SqlSession sqlSession;

    //指定写入数据的数量
    @RequestMapping("/wirtedatahtml")
    public String wirtedatahtml() {
        return "/database/wirtedatahtml";
    }

    //指定写入数据的数量
    @RequestMapping("/w/wirtedatahtml")
    public String wwirtedatahtml(String title, String con, int num) {
        String wtitle = title;
        String wcon = con;
        int wnum = num - 1;
        for (int i = 0; i <= wnum; i++) {
            blogcontext c = new blogcontext();
            c.setTitle(wtitle);
            c.setContext(wcon);
            sqlSession.insert("insertinto",c);
        }
        return "redirect:/database";
    }


    //编辑更新
    @RequestMapping("/editup/{num}")
    public String editup(@PathVariable int num, String title, String con) {
        blogcontext c = new blogcontext();
        c.setTitle(title);
        c.setContext(con);
        sqlSession.insert("insertinto",c);
        return "redirect:/database";
    }

    //编辑
    @RequestMapping("/edit/{num}")
    public String edit(@PathVariable int num, Map<String, Object> map) {
        blogcontext c = sqlSession.selectOne("getone",num);
        map.put("context", c);
        return "/database/edit";
    }

    //删除指定id
    @RequestMapping("/delid/{num}")
    public String delid(@PathVariable int num, Map<String, Object> map) {
        sqlSession.delete("delid",num);
        return "redirect:/database";
    }

    //分页查询
    @RequestMapping("/page/{num}")
    public String index(@PathVariable int num, Map<String, Object> map) {
        if (num <= 0) {
            return "redirect:/database";
        }
        PageHelper.startPage(num, 10);
        Iterable<blogcontext> iterable = sqlSession.selectList("getall");
        map.put("context", iterable);
        map.put("lastpage", num - 1);
        map.put("nextpage", num + 1);
        return "/database/index";
    }


    //获得网址后面的数字
    @RequestMapping("/get/{num}")
    public String index(@PathVariable int num) {
        System.out.println(num);
        return "/database/index";
    }


    //批量写入
    @RequestMapping("/wirtedata")
    public String wirtedata() {
        for (int i = 0; i < 100; i++) {
            blogcontext c = new blogcontext();
            c.setTitle("title" + String.valueOf(i));
            c.setContext("context" + String.valueOf(i));

        }
        return "redirect:/database";
    }

    //批量删除
    @RequestMapping("/del")
    public String del() {
        Iterable<blogcontext> iterable = sqlSession.selectList("getall");

        for (blogcontext c : iterable) {
            if (c != null) {
                sqlSession.delete("delid",c.getId());
            }
        }
        return "redirect:/database";
    }

    //不填database默认指向
    @RequestMapping()
    public String index(Map<String, Object> map) {
        int basenumber = sqlSession.selectOne("getallcount");
        int page = 0;
        if (basenumber %10 ==0){
            page = (int) (basenumber / 10);
        }else {

            page = (int) (basenumber / 10)+1;
        }

        PageHelper.startPage(1, 10);

        Iterable<blogcontext> iterable = sqlSession.selectList("getall");
        map.put("context", iterable);
        map.put("message", "数据库分页查询。 查询页数从1开始，每页10个。 共" + String.valueOf(page) + "页。 总数为个" + String.valueOf(basenumber));
        map.put("nextpage", "/database/page/1");
        return "/database/index";
    }


    //@RequestMapping("/")
    //public String index(Map<String, Object> map,int num) {
    //    System.out.println(num);
    //    Iterable<context> iterable = re.findAll();
    //    map.put("context", iterable);
    //    return "index";
    //}

}
