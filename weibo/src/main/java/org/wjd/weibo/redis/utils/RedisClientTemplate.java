package org.wjd.weibo.redis.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

public class RedisClientTemplate {  
    
   // private static final org.slf4j.Logger log=LoggerFactory.getLogger(RedisClientTemplate.class);  
	
	//Logger log = 
	
	private static final Log log = LogFactory.getLog(RedisClientTemplate.class);
      
    @Autowired  
    private RedisDataSource redisDataSource;  
  
    public RedisDataSource getRedisDataSource() {  
        return redisDataSource;  
    }  
  
    public void setRedisDataSource(RedisDataSource redisDataSource) {  
        this.redisDataSource = redisDataSource;  
    }  
      
    public void disconnect(){  
        ShardedJedis shardedJedis=redisDataSource.getRedisClient();  
        shardedJedis.disconnect();  
    }  
      
    /** 
    * @Description: ��redis�����õ���ֵ 
    * @author Mr.chen 
    * @date 2016-10-21 ����04:37:06 
     */  
    public String set(String key, String value){  
        String result=null;  
        ShardedJedis shardedJedis=redisDataSource.getRedisClient();  
        if(shardedJedis==null){  
            return result;  
        }  
        boolean broken=false;  
        try {  
            result=shardedJedis.set(key, value);  
        } catch (Exception e) {  
            broken=true;  
            e.printStackTrace();  
        }finally{  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
          
        return result;  
    }  
      
    /** 
    * @Description: ��ȡredis����ֵ  
    * @author Mr.chen 
    * @date 2016-10-21 ����04:40:57 
     */  
    public String get(String key) {  
        String result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.get(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
      
    /** 
    * @Description: �ж�redis�Ƿ����key 
    * @author Mr.chen 
    * @date 2016-10-21 ����04:41:39 
     */  
    public Boolean exists(String key) {  
        Boolean result = false;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.exists(key);  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
      
    /** 
    * @Description: ��ȡkey���ش洢ֵ������  
    * @author Mr.chen 
    * @date 2016-10-21 ����04:42:08 
     */  
    public String type(String key) {  
        String result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.type(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
      
    /** 
    * @Description: ��ĳ��ʱ���ʵ�� 
    * @author Mr.chen 
    * @date 2016-10-21 ����04:43:25 
     */  
    public Long expire(String key, int seconds) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.expire(key, seconds);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    /** 
    * @Description: ��ĳ��ʱ���ʧЧ  
    * @author Mr.chen 
    * @date 2016-10-21 ����04:43:40 
     */  
    public Long expireAt(String key, long unixTime) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.expireAt(key, unixTime);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    /** 
    * @Description: ����Ϊ��λ�����ظ��� key ��ʣ������ʱ�� 
    * @author Mr.chen 
    * @date 2016-10-21 ����04:44:00 
     */  
    public Long ttl(String key) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.ttl(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    /** 
    * @Description: �� key ��ֵ��Ϊ value �����ҽ��� key ������ 
    * @author Mr.chen 
    * @date 2016-10-21 ����04:44:17 
     */  
    public Long setnx(String key, String value) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.setnx(key, value);  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ��ֵ value ������ key ������ key ������ʱ����Ϊ seconds (����Ϊ��λ) 
     * @param key 
     * @param seconds 
     * @param value 
     * @return 
     */  
    public String setex(String key, int seconds, String value) {  
        String result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.setex(key, seconds, value);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * �� key �������ֵ��ȥ���� integer  
     * @param key 
     * @param integer 
     * @return 
     */  
    public Long decrBy(String key, long integer) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.decrBy(key, integer);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * �� key �д��������ֵ��һ�� 
     * @param key 
     * @return 
     */  
    public Long decr(String key) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.decr(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * �� key �������ֵ�������� integer  
     * @param key 
     * @param integer 
     * @return 
     */  
    public Long incrBy(String key, long integer) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.incrBy(key, integer);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * �� key �д��������ֵ��һ 
     * @param key 
     * @return 
     */  
    public Long incr(String key) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.incr(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ��� key �Ѿ����ڲ�����һ���ַ����� APPEND ��� value ׷�ӵ� key ԭ����ֵ��ĩβ�� 
     * ��� key �����ڣ� APPEND �ͼ򵥵ؽ����� key ��Ϊ value ������ִ�� SET key value һ���� 
     * @param key 
     * @param value 
     * @return 
     */  
    public Long append(String key, String value) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.append(key, value);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ��������Ϊkey��string��value���Ӵ� 
     * @param key 
     * @param start 
     * @param end 
     * @return 
     */  
    public String substr(String key, int start, int end) {  
        String result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.substr(key, start, end);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ����ϣ�� key �е��� field ��ֵ��Ϊ value  
     * @param key 
     * @param field 
     * @param value 
     * @return 
     */  
    public Long hset(String key, String field, String value) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.hset(key, field, value);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ���ع�ϣ�� key �и����� field ��ֵ 
     * @param key 
     * @param field 
     * @return 
     */  
    public String hget(String key, String field) {  
        String result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.hget(key, field);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ͬʱ����� field-value (��-ֵ)�����õ���ϣ�� key �С� 
     * @param key 
     * @param hash 
     * @return 
     */  
    public String hmset(String key, Map<String, String> hash) {  
        String result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.hmset(key, hash);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ���ع�ϣ�� key �У�һ�������������ֵ 
     * @param key 
     * @param fields 
     * @return 
     */  
    public List<String> hmget(String key, String... fields) {  
        List<String> result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.hmget(key, fields);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * Ϊ��ϣ�� key �е��� field ��ֵ�������� value 
     * @param key 
     * @param field 
     * @param value 
     * @return 
     */  
    public Long hincrBy(String key, String field, long value) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.hincrBy(key, field, value);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * �鿴��ϣ�� key �У������� field �Ƿ���ڡ� 
     * @param key 
     * @param field 
     * @return 
     */  
    public Boolean hexists(String key, String field) {  
        Boolean result = false;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.hexists(key, field);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ɾ��key 
     * @param key 
     * @return 
     */  
    public Long del(String key) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.del(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ɾ����ϣ�� key �е�һ������ָ���� 
     * @param key 
     * @param field 
     * @return 
     */  
    public Long hdel(String key, String field) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.hdel(key, field);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ���ع�ϣ�� key ����������� 
     * @param key 
     * @return 
     */  
    public Long hlen(String key) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.hlen(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ���ع�ϣ�� key �е������� 
     * @param key 
     * @return 
     */  
    public Set<String> hkeys(String key) {  
        Set<String> result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.hkeys(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ���ع�ϣ�� key ���������ֵ�� 
     * @param key 
     * @return 
     */  
    public List<String> hvals(String key) {  
        List<String> result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.hvals(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
     /** 
      * ���ع�ϣ�� key �У����е����ֵ�� 
      * @param key 
      * @return 
      */  
    public Map<String, String> hgetAll(String key) {  
        Map<String, String> result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.hgetAll(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    // ================list ====== l��ʾ list�� left, r��ʾright====================  
    /** 
     * ��һ������ֵ value ���뵽�б� key �ı�β(���ұ�) 
     * @param key 
     * @param string 
     * @return 
     */  
    public Long rpush(String key, String string) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.rpush(key, string);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ��һ������ֵ value ���뵽�б� key �ı�ͷ 
     * @param key 
     * @param string 
     * @return 
     */  
    public Long lpush(String key, String string) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.lpush(key, string);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * �����б� key �ĳ��ȡ� 
     * @param key 
     * @return 
     */  
    public Long llen(String key) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.llen(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
     /** 
      * �����б� key ��ָ�������ڵ�Ԫ�أ�������ƫ���� start �� stop ָ�� 
      * @param key 
      * @param start 
      * @param end 
      * @return 
      */  
    public List<String> lrange(String key, long start, long end) {  
        List<String> result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.lrange(key, start, end);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ֻ����ָ�������ڵ�Ԫ�أ�����ָ������֮�ڵ�Ԫ�ض�����ɾ�� 
     * @param key 
     * @param start 
     * @param end 
     * @return 
     */  
    public String ltrim(String key, long start, long end) {  
        String result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.ltrim(key, start, end);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * �����б� key �У��±�Ϊ index ��Ԫ�ء� 
     * @param key 
     * @param index 
     * @return 
     */  
    public String lindex(String key, long index) {  
        String result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.lindex(key, index);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ���б� key �±�Ϊ index ��Ԫ�ص�ֵ����Ϊ value 
     * @param key 
     * @param index 
     * @param value 
     * @return 
     */  
    public String lset(String key, long index, String value) {  
        String result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.lset(key, index, value);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
  /** 
   * �Ƴ��������б� key ��ͷԪ�� 
   * @param key 
   * @return 
   */  
    public String lpop(String key) {  
        String result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.lpop(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * �Ƴ��������б� key ��βԪ�ء� 
     * @param key 
     * @return 
     */  
    public String rpop(String key) {  
        String result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.rpop(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    //return 1 add a not exist value ,  
    //return 0 add a exist value  
    /** 
     * ��һ������ member Ԫ�ؼ��뵽���� key ���� 
     * @param key 
     * @param member 
     * @return 
     */  
    public Long sadd(String key, String member) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.sadd(key, member);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ���ؼ��� key �е����г�Ա�� 
     * @param key 
     * @return 
     */  
    public Set<String> smembers(String key) {  
        Set<String> result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.smembers(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    /** 
     * ���ؼ��� key �Ļ���(������Ԫ�ص�����) 
     * @param key 
     * @return 
     */  
    public Long scard(String key) {  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        Long result = null;  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.scard(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ��һ������ member Ԫ�ؼ��� score ֵ���뵽���� key ���� 
     * @param key 
     * @param score 
     * @param member 
     * @return 
     */  
    public Long zadd(String key, double score, String member) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.zadd(key, score, member);  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * �������� key �У�ָ�������ڵĳ�Ա 
     * @param key 
     * @param start 
     * @param end 
     * @return 
     */  
    public Set<String> zrange(String key, int start, int end) {  
        Set<String> result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.zrange(key, start, end);  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * �Ƴ����� key �е�һ��������Ա�������ڵĳ�Ա�������� 
     * @param key 
     * @param member 
     * @return 
     */  
    public Long zrem(String key, String member) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.zrem(key, member);  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * Ϊ���� key �ĳ�Ա member �� score ֵ�������� member �� 
     * @param key 
     * @param score 
     * @param member 
     * @return 
     */  
    public Double zincrby(String key, double score, String member) {  
        Double result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.zincrby(key, score, member);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ������ key �г�Ա member ���������������򼯳�Ա�� score ֵ����(��С����)˳������ 
     * @param key 
     * @param member 
     * @return 
     */  
    public Long zrank(String key, String member) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.zrank(key, member);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * �������� key �Ļ��� 
     * @param key 
     * @return 
     */  
        public Long zcard(String key) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.zcard(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * �������� key �У���Ա member �� score ֵ�� 
     * @param key 
     * @param member 
     * @return 
     */  
    public Double zscore(String key, String member) {  
        Double result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.zscore(key, member);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
   /** 
    * �������� key �У� score ֵ�� min �� max ֮��(Ĭ�ϰ��� score ֵ���� min �� max )�ĳ�Ա������ 
    * @param key 
    * @param min 
    * @param max 
    * @return 
    */  
    public Long zcount(String key, double min, double max) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.zcount(key, min, max);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    /** 
     * �����洢 
     * @param key 
     * @param value 
     * @return 
     */  
    public String set(byte[] key, byte[] value) {  
        String result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.set(key, value);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ��ȡ���key��ֵ 
     * @param key 
     * @return 
     */  
    public byte[] get(byte[] key) {  
        byte[] result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.get(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * �ж϶��key���� 
     * @param key 
     * @return 
     */  
    public Boolean exists(byte[] key) {  
        Boolean result = false;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.exists(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public Long expire(byte[] key, int seconds) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.expire(key, seconds);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public Long expireAt(byte[] key, long unixTime) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.expireAt(key, unixTime);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public Long ttl(byte[] key) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.ttl(key);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public Long append(byte[] key, byte[] value) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.append(key, value);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
   /** 
    * �������ӵ�hash 
    * @param key 
    * @param field 
    * @param value 
    * @return 
    */  
    public Long hset(byte[] key, byte[] field, byte[] value) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.hset(key, field, value);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ������ȡfield��ֵ 
     * @param key 
     * @param field 
     * @return 
     */  
    public byte[] hget(byte[] key, byte[] field) {  
        byte[] result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.hget(key, field);  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public String hmset(byte[] key, Map<byte[], byte[]> hash) {  
        String result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.hmset(key, hash);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public List<byte[]> hmget(byte[] key, byte[]... fields) {  
        List<byte[]> result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.hmget(key, fields);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
      
    public Boolean hexists(byte[] key, byte[] field) {  
        Boolean result = false;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.hexists(key, field);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    /** 
     * ����ɾ��hash��key 
     * @param key 
     * @param field 
     * @return 
     */  
    public Long hdel(byte[] key, byte[] field) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.hdel(key, field);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    public Long rpush(byte[] key, byte[] string) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.rpush(key, string);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public Long lpush(byte[] key, byte[] string) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.lpush(key, string);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public Long llen(byte[] key) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.llen(key);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public List<byte[]> lrange(byte[] key, int start, int end) {  
        List<byte[]> result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.lrange(key, start, end);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
     
    public String lset(byte[] key, int index, byte[] value) {  
        String result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.lset(key, index, value);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
    public Long lrem(byte[] key, int count, byte[] value) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.lrem(key, count, value);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public byte[] lpop(byte[] key) {  
        byte[] result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.lpop(key);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public byte[] rpop(byte[] key) {  
        byte[] result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.rpop(key);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  /** 
   *   �������ӵ�set 
   * @param key 
   * @param member 
   * @return 
   */  
    public Long sadd(byte[] key, byte[] member) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.sadd(key, member);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public Set<byte[]> smembers(byte[] key) {  
        Set<byte[]> result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.smembers(key);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public Long scard(byte[] key) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.scard(key);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public Long zadd(byte[] key, double score, byte[] member) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.zadd(key, score, member);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
     
    public Long zcard(byte[] key) {  
        Long result = null;  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
  
            result = shardedJedis.zcard(key);  
  
        } catch (Exception e) {  
  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public JedisShardInfo getShardInfo(String key) {  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        JedisShardInfo result = null;  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.getShardInfo(key);  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public Collection<JedisShardInfo> getAllShardInfo() {  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        Collection<JedisShardInfo> result = null;  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.getAllShardInfo();  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
    public Collection<Jedis> getAllShards() {  
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
        Collection<Jedis> result = null;  
        if (shardedJedis == null) {  
            return result;  
        }  
        boolean broken = false;  
        try {  
            result = shardedJedis.getAllShards();  
  
        } catch (Exception e) {  
            log.error(e.getMessage(), e);  
            broken = true;  
        } finally {  
            redisDataSource.returnResource(shardedJedis, broken);  
        }  
        return result;  
    }  
  
}
