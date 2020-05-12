package cn.com.xdeas.apigateway.core.whiteblacklist;

import cn.com.xdeas.apigateway.common.constants.StringConsts;
import cn.com.xdeas.apigateway.model.ReturnData;
import cn.com.xdeas.apigateway.util.DateUtil;
import cn.com.xdeas.apigateway.util.ReturnDataUtil;
import cn.com.xdeas.apigateway.common.constants.CacheNameConsts;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @author : AlinPan
 * @version : V1.0
 * @date : 2019/11/16 17:39
 */
@Slf4j
@Service
public class IpBlacklistResolver {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public Mono<ReturnData> getIpList() {
        List result = new ArrayList<>();

        redisTemplate.keys(CacheNameConsts.BLACKLIST_IP_KEY + StringConsts.COLON + StringConsts.ASTERISK).stream().
                collect(Collectors.toMap(key -> key.substring(key.indexOf(StringConsts.COLON)+1, key.length()),
                        key -> redisTemplate.opsForValue().get(key)))
        .forEach((k,v)->{
            JSONObject ipObj = new JSONObject();
            ipObj.put(k,v);
            result.add(ipObj);
        });
        return Mono.just(ReturnDataUtil.getSuccussReturn(result));
    }

    public Mono<ReturnData> addIp(String ip, String remark) {
        redisTemplate.opsForValue().set(CacheNameConsts.BLACKLIST_IP_KEY + StringConsts.COLON + ip, DateUtil.format() + StringConsts.COMMA + remark, 1, TimeUnit.DAYS);
        return Mono.just(ReturnDataUtil.getSuccussReturn());
    }

    public Mono<ReturnData> delIp(String ip) {
        redisTemplate.delete(CacheNameConsts.BLACKLIST_IP_KEY + StringConsts.COLON + ip);
        return Mono.just(ReturnDataUtil.getSuccussReturn());
    }
}
