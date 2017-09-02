package com.helloblog;

import com.helloblog.database.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


public class ForDetailsService implements UserDetailsService {
    @Autowired
    SqlSession sqlSession;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User getuser = sqlSession.selectOne("getusername", s);

        List<User> user = sqlSession.selectList("getpermission", s);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (User u : user) {
            grantedAuthorities.add(new SimpleGrantedAuthority(u.getPermissions()));
        }

        return new org.springframework.security.core.userdetails.User(getuser.getUsername(), getuser.getUserpass(), grantedAuthorities);

    }


}
