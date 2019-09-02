 package javax.core.common.jdbc.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
  * 动态数据源
  * @author YAOJIN18
  * @date 2019/08/30
  */
 public class DynamicDataSource extends AbstractRoutingDataSource{

     private DynamicDataSourceEntry dataSourceEntry;
     
     
    public DynamicDataSourceEntry getDataSourceEntry() {
        return dataSourceEntry;
    }


    public void setDataSourceEntry(DynamicDataSourceEntry dataSourceEntry) {
        this.dataSourceEntry = dataSourceEntry;
    }


    @Override
    protected Object determineCurrentLookupKey() {
         return this.dataSourceEntry.get();
    }
     
     
     
     
     
     
     
     
     
     
     
     
     
     

}
