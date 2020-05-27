package cn.edu.lzu.jr.newcommunity.service;

import cn.edu.lzu.jr.newcommunity.dto.PaginationDTO;
import cn.edu.lzu.jr.newcommunity.dto.QuestionDTO;
import cn.edu.lzu.jr.newcommunity.mapper.QuestionMapper;
import cn.edu.lzu.jr.newcommunity.mapper.UserMapper;
import cn.edu.lzu.jr.newcommunity.model.Question;
import cn.edu.lzu.jr.newcommunity.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO pagination = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        pagination.setPagination(totalCount,page,size);

        if(page<=0) page=1;
        if(page > pagination.getPagesNumber()) page=pagination.getPagesNumber();
        Integer offset = size*(page-1);
        List<Question>questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question:questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pagination.setQuestions(questionDTOS);
        return pagination;
    }

    public PaginationDTO list(Integer userId,Integer page, Integer size) {
        PaginationDTO pagination = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(userId);
        pagination.setPagination(totalCount,page,size);
        if(page<=0) page=1;
        if(page > pagination.getPagesNumber()) page=pagination.getPagesNumber();
        Integer offset = size*(page-1);
        List<Question>questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question:questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pagination.setQuestions(questionDTOS);
        return pagination;
    }


    public QuestionDTO getById(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.getById(id);
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}
