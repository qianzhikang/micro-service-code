package com.example.provider.api;

import com.example.provider.PicResultEntity;
import com.google.gson.Gson;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2022-08-30-16-57
 * @Author qianzhikang
 */
@RestController
@RequestMapping("/api")
public class PicController {
    private final String SERVICE_URL = "https://tuapi.eees.cc/api.php?category=dongman&type=json";
    @Resource
    private RestTemplate restTemplate;


    @GetMapping("/get-pic")
    public String getPicUrl(){
        Gson gson = new Gson();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(SERVICE_URL, String.class);
        //System.out.println(forEntity.getBody());
        if (forEntity.getStatusCode().equals(HttpStatus.OK)) {
            String bodyAsString = forEntity.getBody();
            System.out.println(bodyAsString);
            PicResultEntity picResultEntity = gson.fromJson(bodyAsString, PicResultEntity.class);
            System.out.println(picResultEntity);
            return picResultEntity.getImg();
        }else {
            return "请求失败";
        }
    }
}
