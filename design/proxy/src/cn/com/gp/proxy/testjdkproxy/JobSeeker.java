package cn.com.gp.proxy.testjdkproxy;

public class JobSeeker implements Person{
    @Override
    public void findJob() {
        System.out.println("find job");
    }
}
