package org.it.learn.netty.mapper.base;

import java.util.List;

import org.apache.ibatis.annotations.*;

public interface WindyMapper<T> {

    @SelectProvider(type = WindyMapperProvider.class, method = "dynamicSQL")
    T selectOne(T record);

    /**
     * 根据实体类不为null的字段进行查询，条件全部使用=号and条件
     * 
     * @param record
     * @return
     */
    @SelectProvider(type = WindyMapperProvider.class, method = "dynamicSQL")
    List<T> select(T record);

    /**
     * 根据实体类不为null的字段查询总数,条件全部使用=号and条件
     * 
     * @param record
     * @return
     */
    @SelectProvider(type = WindyMapperProvider.class, method = "dynamicSQL")
    int selectCount(T record);

    /**
     * 根据主键进行查询，必须保证结果唯一。单个字段做主键时,可以直接写主键的值。联合主键时,key可以是实体类,也可以是Map
     * 
     * @param key
     * @return
     */
    @SelectProvider(type = WindyMapperProvider.class, method = "dynamicSQL")
    T selectByPrimaryKey(Object key);

    // ---------------------------------------------------------------------------------------------------------------------------------------------------//

    /**
     * 插入一条数据。支持Oracle序列,UUID,类似MYSQL的INDENTITY自动增长(自动回写)。优先使用传入的参数值,参数值空时,
     * 才会使用序列、UUID,自动增长
     * 
     * @param record
     * @return
     */
    @InsertProvider(type = WindyMapperProvider.class, method = "dynamicSQL")
    int insert(T record);

    /**
     * 插入一条数据,只插入不为null的字段,不会影响有默认值的字段。支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(
     * 自动回写)。优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
     * 
     * @param record
     * @return
     */
    int insertSelective(T record);

    /**
     * 批量插入，支持数据库自增字段，支持回写
     *
     * @param recordList
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @InsertProvider(type = WindyMapperProvider.class, method = "dynamicSQL")
    int insertList(List<T> recordList);

    // ---------------------------------------------------------------------------------------------------------------------------------------------------//

    /**
     * 根据实体类中字段不为null的条件进行删除,条件全部使用=号and条件
     * 
     * @param key
     * @return
     */
    @InsertProvider(type = WindyMapperProvider.class, method = "dynamicSQL")
    int delete(T key);

    /**
     * 通过主键进行删除,这里最多只会删除一条数据。单个字段做主键时,可以直接写主键的值。联合主键时,key可以是实体类,也可以是Map
     * 
     * @param key
     * @return
     */
    @DeleteProvider(type = WindyMapperProvider.class, method = "dynamicSQL")
    int deleteByPrimaryKey(Object key);

    /**
     * 根据主键ID批量删除
     * 
     * @param key
     * @return
     */
    @DeleteProvider(type = WindyMapperProvider.class, method = "dynamicSQL")
    int deleteByIDs(@Param("ids") Object[] key);

    // ---------------------------------------------------------------------------------------------------------------------------------------------------//

    /**
     * 根据主键进行更新,这里最多只会更新一条数据。参数为实体类
     * 
     * @param record
     * @return
     */
    @UpdateProvider(type = WindyMapperProvider.class, method = "dynamicSQL")
    int updateByPrimaryKey(T record);

    /**
     * 根据主键进行更新。只会更新不是null的数据
     * 
     * @param record
     * @return
     */
    @UpdateProvider(type = WindyMapperProvider.class, method = "dynamicSQL")
    int updateByPrimaryKeySelective(T record);
}
