package cn.com.gp.singleton.register;


/**
 * Created by Tom.
 */
public class ContainerSingletonTest {
    public static void main(String[] args) {


        try {
            long start = System.currentTimeMillis();
            ConcurrentExecutor.execute(new ConcurrentExecutor.RunHandler() {
                public void handler() {
                    Object obj = null;
                    try {
                        obj = RegisterSingleton.getInstance(Singleton.class);
                        System.out.println(System.currentTimeMillis() + ": " + obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 10,6);
            long end = System.currentTimeMillis();
            System.out.println("总耗时：" + (end - start) + " ms.");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
