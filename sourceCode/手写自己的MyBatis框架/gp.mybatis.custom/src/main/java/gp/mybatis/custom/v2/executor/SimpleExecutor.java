package gp.mybatis.custom.v2.executor;

public class SimpleExecutor implements Executor {

    public <T> T query(String statement, Object[] parameter, Class pojo) {
        // TODO Auto-generated method stub
        StatementHandler statementHandler = new StatementHandler();

        return statementHandler.query(statement, parameter, pojo);
    }

}
