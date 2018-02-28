package com.yunwenlong.utils;

//import java.io.Serializable;
//import java.util.Set;
//import java.util.concurrent.locks.ReadWriteLock;
//import java.util.concurrent.locks.ReentrantReadWriteLock;
//
//import org.apache.ibatis.cache.Cache;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;

//public class RedisCache implements Cache,Serializable{
public class RedisCache{
//	private static final Logger LOGGER = LoggerFactory.getLogger(RedisCache.class);
//	private static final long serialVersionUID = 1L;
//	
//	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
//	
//	private Jedis redisClient = createClient();
//	private String id ;
//	
//	
//	public RedisCache(final String id) {
//		if(id == null){
//			throw new IllegalArgumentException("必须传入ID");
//		}
//		System.out.println("............MybatisRedisCache:id=" + id);
//		LOGGER.debug("MybatisRedisCache:id=" + id);
//		this.id = id;
//	}
//
//	public String getId() {
//		// TODO Auto-generated method stub
//		return this.id;
//	}
//
//	public void putObject(Object key, Object value) {
//		LOGGER.debug("存入的键值对>>>>>>>>>>>>>>>>>>>>>>>>putObject:" + key + "=" + value);
//		redisClient.set(SerializeUtil.serialize(key.toString()), SerializeUtil.serialize(value));
//	}
//
//	public Object getObject(Object key) {
//		byte[] ob = redisClient.get(SerializeUtil.serialize(key.toString()));
//		if(ob == null){
//			return null;
//		}
//		Object value = SerializeUtil.unSerialize(ob);
//		LOGGER.debug("获取的键值对>>>>>>>>>>>>>>>>>>>>>>>>getObject:" + key + "=" + value);
//		return value;
//	}
//
//	public Object removeObject(Object key) {
//		return redisClient.expire(SerializeUtil.serialize(key.toString()), 0);
//	}
//
//	public void clear() {
//		redisClient.flushDB();
//	}
//
//	public int getSize() {
//		return Integer.parseInt(redisClient.dbSize().toString());
//	}
//
//	public ReadWriteLock getReadWriteLock() {
//		return readWriteLock;
//	}
//	
//	protected static Jedis createClient() {
//		try {
////			JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1",6379,8000,"foobared");
//			JedisPool pool = new JedisPool("127.0.0.1");
//			return pool.getResource();
//		} catch (Exception e) {
//			throw new RuntimeException("初始化连接池错误");
//		}
//	}
//	
//	public static void main(String[] args) {
//		Jedis jedis = createClient();
//		Set<String> keys = jedis.keys("*");
//		for (String string : keys) {
//			System.out.println("key=="+string);
//		}
//		
//	}

}
