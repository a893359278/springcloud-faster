package com.csp.github.rabc.controller;

import com.csp.github.resource.annotation.ResourceCollection;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈少平
 * @date 2019-11-16 09:29
 */
@RequestMapping("/qweqwe")
@RestController
public class TestController {

//    @ResourceCollection(name = "测试", description = "测试", extra = "{\"qweqwe\":\"qweqwwqe\"}")
    @ApiOperation(value = "财务室", notes = "2<=>驱蚊器翁")
    @GetMapping("/test")
    public void t() {}

//    @ResourceCollection(name = "测试2", description = "测试2", extra = "{\"qweqwe\":\"qweqwwqe\"}")
    @ApiOperation(value = "爱仕达撒所多", notes = "3<=>去问问请问群二")
    @PutMapping("/qwe")
    public void tt() {}
}
