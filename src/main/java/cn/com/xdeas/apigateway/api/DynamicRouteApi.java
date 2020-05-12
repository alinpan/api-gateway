package cn.com.xdeas.apigateway.api;

import cn.com.xdeas.apigateway.core.route.DynamicRouteResolver;
import cn.com.xdeas.apigateway.model.ReturnData;
import cn.com.xdeas.apigateway.util.ReturnDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * @author : AlinPan
 * @version : V1.0
 * @date : 2019/11/12 11:26
 */
@Slf4j
@RestController
@RequestMapping("/gateway/routes")
public class DynamicRouteApi {


    @Autowired
    private DynamicRouteResolver dynamicRouteResolver;

    @GetMapping()
    public Mono<List<Map<String, Object>>> list() {
        return dynamicRouteResolver.getRoutesList();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<RouteDefinition>> detail(@PathVariable String id) {
        return dynamicRouteResolver.getRouteById(id);
    }

    @PostMapping()
    public ReturnData add(@RequestBody RouteDefinition definition) {
        try {
            return this.dynamicRouteResolver.add(definition);
        } catch (Exception e) {
            log.error("e", e);
            return ReturnDataUtil.systemError();
        }
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable String id) {
        return this.dynamicRouteResolver.delete(id);
    }

    @PutMapping("/{id}")
    public ReturnData update(@RequestBody RouteDefinition definition, @PathVariable String id) {
        return this.dynamicRouteResolver.update(definition);
    }



}
