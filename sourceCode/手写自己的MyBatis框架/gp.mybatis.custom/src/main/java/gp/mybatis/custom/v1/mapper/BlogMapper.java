 package gp.mybatis.custom.v1.mapper;

import gp.mybatis.custom.v2.annotation.Entity;

@Entity(Blog.class)
 public interface BlogMapper {

    public Blog selectBlogById(Integer bid);
}
