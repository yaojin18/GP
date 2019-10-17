package gp.mybatis.custom.v2.mapper;

import gp.mybatis.custom.v2.annotation.Entity;
import gp.mybatis.custom.v2.annotation.Select;

@Entity(Blog.class)
public interface BlogMapper {

    @Select("select * from blog where bid = ?")
    public Blog selectBlogById(Integer bid);
}
