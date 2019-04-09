package cn.com.gp.factory.method;

public class FactoryMethod {

    public static Person createInstance(String type){
        if("1".equals(type)){
            return  new Father();
        }else if("2".equals(type)){
            return  new Son();
        }

        return new Father();
    }

}
