package cn.com.gp.factory.abstracts;

public class AbstractFactoryTest {
    public static void main(String[] args) {

       new JavaAbstractFactory().createInstance().test();

       new PhpAbstractFactory().createInstance().test();
    }

}
