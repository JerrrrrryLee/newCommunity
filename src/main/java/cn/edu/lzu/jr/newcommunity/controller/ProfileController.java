package cn.edu.lzu.jr.newcommunity.controller;

import cn.edu.lzu.jr.newcommunity.dto.PaginationDTO;
import cn.edu.lzu.jr.newcommunity.model.User;
import cn.edu.lzu.jr.newcommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size",defaultValue = "5") Integer size){
        User user= (User) request.getSession().getAttribute("user");
        if(user==null) return "redirect:/";
        if("question".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");
        }else if("reply".equals(action)){
            model.addAttribute("section","reply");
            model.addAttribute("sectionName","我的回复");
        }

        PaginationDTO pagination = questionService.list(user.getId(),page,size);
        model.addAttribute("pagination",pagination);
        return "profile";
    }
}
