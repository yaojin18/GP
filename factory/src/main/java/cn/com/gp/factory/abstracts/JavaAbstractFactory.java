package cn.com.gp.factory.abstracts;

public class JavaAbstractFactory implements AbstractFactory {

    public Technology createInstance() {
        return new Java();
    }
}
