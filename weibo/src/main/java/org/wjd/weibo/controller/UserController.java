package org.wjd.weibo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wjd.weibo.redis.utils.RedisClientTemplate;

@Controller
@RequestMapping("/user")
public class UserController {
	
	//@Autowired
   // private ShardedJedisPool shardedJedisPool;
	
	@Autowired
	RedisClientTemplate redisClientTemplate;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest req,HttpServletResponse res){
		
		//ShardedJedis jedis = shardedJedisPool.getResource();

        String name = redisClientTemplate.get("name");
        System.out.println(name);
		return "user/list";
	}
	
	@RequestMapping("/set")
	public String set(HttpServletRequest req,HttpServletResponse res){
		
		//ShardedJedis jedis = shardedJedisPool.getResource();
		
		String name = req.getParameter("name");

		redisClientTemplate.set("name", name);
        
        redisClientTemplate.set("name",name+"sssss");
        
        
		return "user/list";
	}
	
	@ResponseBody
	@RequestMapping("/get")
	public String get(HttpServletRequest req,HttpServletResponse res){
		
		//ShardedJedis jedis = shardedJedisPool.getResource();

        String name = redisClientTemplate.get("name");

        return name;
	}

}
