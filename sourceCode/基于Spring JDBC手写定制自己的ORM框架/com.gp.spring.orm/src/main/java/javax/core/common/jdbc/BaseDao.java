 package javax.core.common.jdbc;

import java.util.List;
import java.util.Map;

import javax.core.common.Page;

import com.gp.vip.orm.framework.QueryRule;

public interface BaseDao<T, PK> {

    /**
     * 获取列表
     * @param queryRule
     * @return
     * @throws Exception
     */
     List<T> select(QueryRule queryRule) throws Exception; 
     
     /**
      * 分页
      * @param queryRule
      * @param pageNo
      * @param pageSize
      * @return
      * @throws Exception
      */
     Page<?> select (QueryRule queryRule, int pageNo, int pageSize) throws Exception;
     
     /**
      * 根据sql获取列表
      * @param sql
      * @param args
      * @return
      * @throws Exception
      */
     List<Map<String, Object>> selectBySql(String sql, Object... args) throws Exception;
     
     /**
      * 根据sql获取分页
      * @param sql
      * @param param
      * @param pageNo
      * @param pageSize
      * @return
      * @throws Exception
      */
     Page<Map<String, Object>> selectBySqlToPage(String sql, Object[] param, int pageNo, int pageSize) throws Exception;
     
     boolean delete(T entity) throws Exception;
     
     int deleteAll(List<T> list) throws Exception;
     
     /**
      * 插入一条记录并返回插入后的id
      * @param entity
      * @return
      * @throws Exception
      */
     PK insertAndReturnId(T entity) throws Exception;
     
     boolean update(T entity) throws Exception;
     
     boolean insert(T entity) throws Exception;
     
     int insertAll(List<T> list) throws Exception;
     
     
}
