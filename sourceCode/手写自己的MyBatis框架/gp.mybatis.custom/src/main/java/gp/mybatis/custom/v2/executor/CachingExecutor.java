package gp.mybatis.custom.v2.executor;

import java.util.HashMap;
import java.util.Map;

import gp.mybatis.custom.v2.cache.CacheKey;

/**
 * 带缓存的执行器，用于装饰基本执行器
 * 
 * @author YAOJIN18
 * @date 2019/10/14
 */
public class CachingExecutor implements Executor {

    private Executor delegate;
    private static final Map<Integer, Object> cache = new HashMap();

    public CachingExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    public <T> T query(String statement, Object[] parameter, Class pojo) {
        // TODO Auto-generated method stub

        CacheKey cacheKey = new CacheKey();
        cacheKey.update(statement);
        cacheKey.update(joinStr(parameter));

        if (cache.containsKey(cacheKey.getCode())) {
            System.out.println("命中缓存");
            return (T)cache.get(cacheKey.getCode());
        } else {
            Object obj = delegate.query(statement, parameter, pojo);
            cache.put(cacheKey.getCode(), obj);
            return (T)obj;
        }
    }

    // 为了命中缓存，把Object[]转换成逗号拼接的字符串，因为对象的HashCode都不一样
    public String joinStr(Object[] objs) {
        StringBuffer sb = new StringBuffer();
        if (objs != null && objs.length > 0) {
            for (Object objStr : objs) {
                sb.append(objStr.toString() + ",");
            }
        }
        int len = sb.length();
        if (len > 0) {
            sb.deleteCharAt(len - 1);
        }
        return sb.toString();
    }

}
