package cn.com.gp.prototype;

public class PrototypeTest {
    public static void main(String[] args) throws CloneNotSupportedException {

        Prototype source = new Prototype();
        source.setAge("18");
        source.setName("yj");
        source.setCode("name");

        Prototype target = (Prototype)source.clone();

        System.out.println(source +"=========="+target);
    }
}
