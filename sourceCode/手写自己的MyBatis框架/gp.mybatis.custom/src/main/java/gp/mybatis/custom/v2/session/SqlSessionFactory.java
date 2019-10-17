package gp.mybatis.custom.v2.session;

/**
 * 会话工厂类，用于解析配置文件，产生sqlSession
 * 
 * @author YAOJIN18
 * @date 2019/10/17
 */
public class SqlSessionFactory {

    private Configuration configuration;

    public SqlSessionFactory build() {
        configuration = new Configuration();
        return this;
    }

    public DefaultSqlSession openSqlSession() {
        return new DefaultSqlSession(configuration);
    }
}
