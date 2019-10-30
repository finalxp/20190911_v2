package cn.productivetech.shtelcom.enrol.dao;


public interface IBaseDao<T,K> {
    int deleteByPrimaryKey(K id);

    int insert(T record);

    T selectByPrimaryKey(K id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}