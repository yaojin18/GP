package gp.mybatis.custom.v2;

import gp.mybatis.custom.v2.mapper.Blog;
import gp.mybatis.custom.v2.mapper.BlogMapper;
import gp.mybatis.custom.v2.session.DefaultSqlSession;
import gp.mybatis.custom.v2.session.SqlSessionFactory;

public class TestMybatis {

    public static void main(String[] args) {
        SqlSessionFactory factory = new SqlSessionFactory();
        DefaultSqlSession sqlSession = factory.build().openSqlSession();

        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = mapper.selectBlogById(1);
        System.out.println("第一次查询：" + blog);
        blog = mapper.selectBlogById(1);
        System.out.println("第二次查询：" + blog);
    }
}
