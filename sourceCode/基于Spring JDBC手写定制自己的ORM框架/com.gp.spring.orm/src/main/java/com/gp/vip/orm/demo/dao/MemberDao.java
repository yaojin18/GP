 package com.gp.vip.orm.demo.dao;

import java.util.List;

import javax.sql.DataSource;

import com.gp.vip.orm.demo.entity.Member;
import com.gp.vip.orm.framework.BaseDaoSupport;
import com.gp.vip.orm.framework.QueryRule;

public class MemberDao extends BaseDaoSupport<Member, Long>{

    @Override
    protected String getPKColumn() {
         return "id";
    }

    public List<Member> selectAll() throws Exception{
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andLike("name", "yj");
        return super.select(queryRule);
    }
    
//    @Resource(name="dataSource")
    @Override
    protected void setDataSource(DataSource dataSource) {
        super.setDataSourceReadOnly(dataSource);
        super.setDataSourceWrite(dataSource);
    }

}
