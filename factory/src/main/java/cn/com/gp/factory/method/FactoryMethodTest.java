package cn.com.gp.factory.method;

public class FactoryMethodTest {

    public static void main(String[] args) {
        FactoryMethod.createInstance("1").test();

        FactoryMethod.createInstance("2").test();
    }
}
