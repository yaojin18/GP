package gp.mybatis.custom.v1.mapper;

import java.io.Serializable;

import lombok.Data;

@Data
public class Blog implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -101096880883657520L;
    Integer bid;
    String name;
    Integer authorId;
}
