package cn.edu.lzu.jr.newcommunity.controller;

import cn.edu.lzu.jr.newcommunity.dto.AccessTokenDTO;
import cn.edu.lzu.jr.newcommunity.dto.GithubUser;
import cn.edu.lzu.jr.newcommunity.mapper.UserMapper;
import cn.edu.lzu.jr.newcommunity.model.User;
import cn.edu.lzu.jr.newcommunity.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
                           HttpServletRequest request){
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
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            userMapper.insert(user);
            HttpSession session = request.getSession();
            session.setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }
}
