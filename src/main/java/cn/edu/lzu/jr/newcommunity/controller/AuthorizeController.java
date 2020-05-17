package cn.edu.lzu.jr.newcommunity.controller;

import cn.edu.lzu.jr.newcommunity.dto.AccessTokenDTO;
import cn.edu.lzu.jr.newcommunity.dto.GithubUser;
import cn.edu.lzu.jr.newcommunity.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String token = provider.getAccessToken(accessTokenDTO);
        String[] strs = token.split("&");
        GithubUser user = provider.getUser(strs[0]);
        System.out.println(user.getName());
        return "index";
    }
}
