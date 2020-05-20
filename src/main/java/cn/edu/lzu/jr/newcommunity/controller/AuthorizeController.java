package cn.edu.lzu.jr.newcommunity.controller;

import cn.edu.lzu.jr.newcommunity.dto.AccessTokenDTO;
import cn.edu.lzu.jr.newcommunity.dto.GithubUser;
import cn.edu.lzu.jr.newcommunity.mapper.UserMapper;
import cn.edu.lzu.jr.newcommunity.model.User;
import cn.edu.lzu.jr.newcommunity.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider provider;
    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String token = provider.getAccessToken(accessTokenDTO);
        String[] strs = token.split("&");
        GithubUser githubUser = provider.getUser(strs[0]);
        if(githubUser!=null){
            User user = new User();
            user.setAccountId(""+githubUser.getId());
            user.setName(githubUser.getName());
            String cookieToken = UUID.randomUUID().toString();
            user.setToken(cookieToken);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",cookieToken));
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }
}
