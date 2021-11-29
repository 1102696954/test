package com.example.mobile.mobile.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description:
 * @Author ts-lingcai.kong
 * @Date 2021/11/29 9:54
 */
@Api(tags = "swagger test")
@RestController("api/test")
public class TestController {

  @ApiOperation(value = "方法描述")
  @ApiResponses({@ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 400, message = "error")})
  @GetMapping(value = "/getInfo", produces = {MediaType.APPLICATION_JSON_VALUE})
  public Map<String, String> getInfo() {
    var info = new HashMap<String, String>();
    info.put("name", "lingcai");
    info.put("age", "27");
    info.put("sex", "男");
    return info;
  }
}
