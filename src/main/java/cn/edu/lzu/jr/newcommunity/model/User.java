package cn.edu.lzu.jr.newcommunity.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModify;
    private String avatarUrl;
}
