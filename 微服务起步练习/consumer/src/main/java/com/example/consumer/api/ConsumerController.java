package com.example.consumer.api;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Description TODO
 * @Date 2022-08-30-15-53
 * @Author qianzhikang
 */
@RestController
public class ConsumerController {
    //private final String SERVICE_URL = "http://localhost:8080/api";
    private final String SERVICE_URL = "https://8ec8-221-226-155-12.ap.ngrok.io";
    @Resource
    private RestTemplate restTemplate;


    private WebClient webClient = WebClient.builder().baseUrl(SERVICE_URL).build();

    @GetMapping("do")
    public String httpClientTest() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(SERVICE_URL + "/hello");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200){
                String res = EntityUtils.toString(response.getEntity(),"utf-8");
                System.out.println(res);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }finally {
            if (response != null){
                response.close();
            }
            httpClient.close();
        }
        return "请求成功！";
    }


    @GetMapping("rest-template")
    public String restTemplateTest(){
        String forObject = restTemplate.getForObject(SERVICE_URL + "/user/3", String.class);
        System.out.println(forObject);
        return forObject;
    }

    @GetMapping("/web-client")
    public String webClientTest(){
        Mono<String> stringMono = webClient.get().uri("/api/get-pic").retrieve().bodyToMono(String.class);
        stringMono.subscribe(System.out::println);
        return "success";
    }
}
