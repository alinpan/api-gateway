package cn.com.xdeas.apigateway.api;

import cn.com.xdeas.apigateway.core.whiteblacklist.IpBlacklistResolver;
import cn.com.xdeas.apigateway.model.ReturnData;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author : AlinPan
 * @version : V1.0
 * @date : 2019/11/16 17:37
 */
@RestController
@RequestMapping("/gateway/blacklist/ip")
public class IpBlackListApi {

    @Autowired
    private IpBlacklistResolver ipBlacklistResolver;

    @GetMapping
    public Mono<ReturnData> getIpBlacklist() {
        return ipBlacklistResolver.getIpList();
    }

    @PostMapping
    public Mono<ReturnData> addIp(@RequestBody JSONObject object) {
        return ipBlacklistResolver.addIp(object.getString("ip"), object.getString("remark"));
    }

    @DeleteMapping("/{ip}")
    public Mono<ReturnData> delIp(@PathVariable("ip") String ip) {
        return ipBlacklistResolver.delIp(ip);
    }

}
