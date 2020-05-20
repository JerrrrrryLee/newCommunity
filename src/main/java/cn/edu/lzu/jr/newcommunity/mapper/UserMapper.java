package cn.edu.lzu.jr.newcommunity.mapper;

import cn.edu.lzu.jr.newcommunity.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user (account_id,name,token,gmt_create,gmt_modify) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModify})")
    void insert(User user);
}
