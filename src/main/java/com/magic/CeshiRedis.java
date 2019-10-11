package com.magic;

import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * @ClassName: CeshiRedis
 * @Author: shenyafei
 * @Date: 2019/9/11
 * @Desc
 **/
public class CeshiRedis extends RedisScheduler {

    public CeshiRedis(JedisPool pool) {
        super(pool);
    }
}
