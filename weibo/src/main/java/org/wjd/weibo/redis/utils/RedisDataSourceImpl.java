package org.wjd.weibo.redis.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisDataSourceImpl implements RedisDataSource{  
	  
    //private static final org.slf4j.Logger log = LoggerFactory.getLogger(RedisDataSourceImpl.class);  
	
	private static final Log log = LogFactory.getLog(RedisClientTemplate.class);
      
    @Autowired  
    private ShardedJedisPool shardedJedisPool;  
      
    public ShardedJedisPool getShardedJedisPool() {  
        return shardedJedisPool;  
    }  
  
    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {  
        this.shardedJedisPool = shardedJedisPool;  
    }  
  
    /** 
     * ��ȡredis�ͻ��� 
     */  
    public ShardedJedis getRedisClient() {  
        try {  
            ShardedJedis shardedJedis=shardedJedisPool.getResource();  
            return shardedJedis;  
        } catch (Exception e) {  
            log.error("getRedisClient ,error",e);  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * ����Դ������pool 
     */  
    @SuppressWarnings("deprecation")  
    public void returnResource(ShardedJedis shardedJedis) {  
        shardedJedisPool.returnResource(shardedJedis);  
    }  
  
    /** 
     * �����쳣�󷵻���Դ��pool 
     */  
    @SuppressWarnings("deprecation")  
    public void returnResource(ShardedJedis shardedJedis, boolean broken) {  
        if(broken){  
            shardedJedisPool.returnBrokenResource(shardedJedis);  
        }else{  
            shardedJedisPool.returnResource(shardedJedis);  
        }  
          
    }  
  
}  


