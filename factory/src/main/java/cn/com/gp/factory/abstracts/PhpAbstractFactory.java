package cn.com.gp.factory.abstracts;

public class PhpAbstractFactory implements AbstractFactory {

    public Technology createInstance() {
        return new Php();
    }
}
