package gp.mybatis.custom.v2.plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author YAOJIN18
 * @date 2019/10/14
 */
public class InterceptorChain {

    private final List<Interceptor> interceptors = new ArrayList();

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public Object pluginAll(Object target) {
        for (Interceptor interceptor : interceptors) {
            target = interceptor.plugin(target);
        }

        return target;
    }

    public boolean hasPlugin() {
        return interceptors.size() == 0 ? false : true;
    }

}
