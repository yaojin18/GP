package javax.core.common.jdbc.datasource;

import org.aspectj.lang.JoinPoint;

/**
 * 动态切换数据源
 * 
 * @author YAOJIN18
 * @date 2019/08/30
 */
public class DynamicDataSourceEntry {

    public final static String DEFAULT_SOURCE = null;

    private final static ThreadLocal<String> local = new ThreadLocal<String>();

    private void clear() {
        local.remove();
    }

    public String get() {
        return local.get();
    }

    public void restore(JoinPoint join) {
        local.set(DEFAULT_SOURCE);
    }

    public void restore() {
        local.set(DEFAULT_SOURCE);
    }

    public void set(String source) {
        local.set(source);
    }

    public void set(int year) {
        local.set("DB_" + year);
    }

}
