package gp.mybatis.custom.v2.session;

import gp.mybatis.custom.v2.executor.Executor;

/**
 * 提供给应用层接口
 * 
 * @author YAOJIN18
 * @date 2019/10/17
 */
public class DefaultSqlSession {

    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = configuration.newExecutor();
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public <T> T getMapper(Class<T> clazz) {
        return configuration.getMapper(clazz, this);
    }

    public <T> T selectOne(String statement, Object[] parameter, Class pojo) {
        String sql = getConfiguration().getMappedStatement(statement);
        // 打印代理对象时会自动调用toString()方法，触发invoke()
        return executor.query(sql, parameter, pojo);
    }
}
