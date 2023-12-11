package com.example.cachedemo;

import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CachedemoApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(CachedemoApplication.class, args);
		
    	Config config = new Config();
    	String cachekey = "CacheKey=";
    	//String url = "rediss://mpg-np.redis.cache.windows.net:6380";
    	String url = "redis://localhost:6379";
    	//local testing with non ssl port 6379
    	config.useSingleServer().setAddress(url);
    	//actual redis cache test with ssl enabled port(change port number in url to 6380)
    	//config.useSingleServer().setAddress(url).setPassword(cachekey).setSslEnableEndpointIdentification(true);
    	RedissonClient client = Redisson.create(config);
    	RMapCache<String, String> mapCache = client.getMapCache("TestMapCache");
    	mapCache.putIfAbsent("PING", "PONG",5,TimeUnit.SECONDS);
    	System.out.println(mapCache.get("PING"));
    	System.out.println(mapCache.get("PING"));
    	Thread.sleep(5000);
    	System.out.println(mapCache.get("PING"));
    	mapCache.putIfAbsent("Message", "Hello! The cache is working from Java!");
    	System.out.println(mapCache.get("Message"));   	
    	if(mapCache.delete())
    		System.out.println("TestMapCache deleted");
    	System.out.println(client.getMapCache("TestMapCache").get("Message"));
    	
	}

}
