package com.seckill.ordersystem.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

public class LuaScriptUtil {
    public static DefaultRedisScript<Long> stockDeductScript() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("lua/stock_deduct.lua"));
        script.setResultType(Long.class);
        return script;
    }
}
