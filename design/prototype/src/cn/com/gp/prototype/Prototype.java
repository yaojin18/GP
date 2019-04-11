package cn.com.gp.prototype;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;

public class Prototype implements Cloneable ,Serializable{
    private String name;

    private String code;

    private String age;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this.deepClone();
    }

    public Object deepClone() {
        try {
            ByteArrayOutputStream bos =  new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            Object o = ois.readObject();
            System.out.println(o);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
