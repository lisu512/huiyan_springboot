package com.example.demo;

//import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import utils.Base64Util;
import utils.FileUtil;
import utils.HttpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

@RestController
public class myapp {
    @RequestMapping(value = "/getResult", method = RequestMethod.POST)
    public String getModifiedPicture(@RequestParam("pic_ori") String pic_ori) {

        String s = medicalReportDetection(pic_ori);
        String s1 = modifiedStr(s);

        String s2 = askQuestion("以下是我的体检数据 请问我有什么问题？" + s1);
        Map<String, Object> map = new HashMap<>();
        map.put("result", s2);
        map.put("passed", 1);
        System.out.println(s2);
        return com.alibaba.fastjson2.JSONObject.toJSONString(map);


    }

    public String modifiedStr(String ori_str) throws JSONException {
        // String json_data = "{\"words_result\":{\"CommonData\":[{\"word_name\":\"报告单名称\",\"word\":\"报告时间该报告的数据仅对所检测的标本负责\"},{\"word_name\":\"科室\",\"word\":\"\"},{\"word_name\":\"姓名\",\"word\":\"\"},{\"word_name\":\"临床诊断\",\"word\":\"\"},{\"word_name\":\"性别\",\"word\":\"\"},{\"word_name\":\"年龄\",\"word\":\"\"},{\"word_name\":\"标本种类\",\"word\":\"\"},{\"word_name\":\"标本情况\",\"word\":\"\"},{\"word_name\":\"临床症状\",\"word\":\"\"},{\"word_name\":\"时间\",\"word\":\"\"},{\"word_name\":\"建议\",\"word\":\"\"},{\"word_name\":\"检查结果\",\"word\":\"\"},{\"word_name\":\"检查目的\",\"word\":\"\"},{\"word_name\":\"检查项目\",\"word\":\"\"},{\"word_name\":\"医院\",\"word\":\"\"}],\"Item\":[[{\"word_name\":\"仪器类型\",\"word\":\"\"},{\"word_name\":\"单位\",\"word\":\"\"},{\"word_name\":\"参考区间\",\"word\":\"7.35-7.45\"},{\"word_name\":\"测试方法\",\"word\":\"\"},{\"word_name\":\"结果\",\"word\":\"7.36\"},{\"word_name\":\"结果提示\",\"word\":\"\"},{\"word_name\":\"项目代号\",\"word\":\"\"},{\"word_name\":\"项目名称\",\"word\":\"酸碱度pH\"}],[{\"word_name\":\"仪器类型\",\"word\":\"\"},{\"word_name\":\"单位\",\"word\":\"mmHg\"},{\"word_name\":\"参考区间\",\"word\":\"32-48\"},{\"word_name\":\"测试方法\",\"word\":\"\"},{\"word_name\":\"结果\",\"word\":\"46.60\"},{\"word_name\":\"结果提示\",\"word\":\"\"},{\"word_name\":\"项目代号\",\"word\":\"\"},{\"word_name\":\"项目名称\",\"word\":\"二氧化碳分压PCO2\"}],[{\"word_name\":\"仪器类型\",\"word\":\"\"},{\"word_name\":\"单位\",\"word\":\"mmHg\"},{\"word_name\":\"参考区间\",\"word\":\"83-108\"},{\"word_name\":\"测试方法\",\"word\":\"\"},{\"word_name\":\"结果\",\"word\":\"64.60\"},{\"word_name\":\"结果提示\",\"word\":\"↓\"},{\"word_name\":\"项目代号\",\"word\":\"\"},{\"word_name\":\"项目名称\",\"word\":\"氧分压PO2\"}],[{\"word_name\":\"仪器类型\",\"word\":\"\"},{\"word_name\":\"单位\",\"word\":\"mmol/L\"},{\"word_name\":\"参考区间\",\"word\":\"99-110\"},{\"word_name\":\"测试方法\",\"word\":\"\"},{\"word_name\":\"结果\",\"word\":\"101.50\"},{\"word_name\":\"结果提示\",\"word\":\"\"},{\"word_name\":\"项目代号\",\"word\":\"\"},{\"word_name\":\"项目名称\",\"word\":\"氯C1-\"}],[{\"word_name\":\"仪器类型\",\"word\":\"\"},{\"word_name\":\"单位\",\"word\":\"mmol/L\"},{\"word_name\":\"参考区间\",\"word\":\"21.4-27.3\"},{\"word_name\":\"测试方法\",\"word\":\"\"},{\"word_name\":\"结果\",\"word\":\"23.90\"},{\"word_name\":\"结果提示\",\"word\":\"\"},{\"word_name\":\"项目代号\",\"word\":\"\"},{\"word_name\":\"项目名称\",\"word\":\"碳酸氢根HCO3-\"}],[{\"word_name\":\"仪器类型\",\"word\":\"\"},{\"word_name\":\"单位\",\"word\":\"mmol/L\"},{\"word_name\":\"参考区间\",\"word\":\"46-50\"},{\"word_name\":\"测试方法\",\"word\":\"\"},{\"word_name\":\"结果\",\"word\":\"47.00\"},{\"word_name\":\"结果提示\",\"word\":\"\"},{\"word_name\":\"项目代号\",\"word\":\"\"},{\"word_name\":\"项目名称\",\"word\":\"缓冲碱BB\"}],[{\"word_name\":\"仪器类型\",\"word\":\"\"},{\"word_name\":\"单位\",\"word\":\"%\"},{\"word_name\":\"参考区间\",\"word\":\"91.9-99\"},{\"word_name\":\"测试方法\",\"word\":\"\"},{\"word_name\":\"结果\",\"word\":\"92.80\"},{\"word_name\":\"结果提示\",\"word\":\"\"},{\"word_name\":\"项目代号\",\"word\":\"\"},{\"word_name\":\"项目名称\",\"word\":\"氧饱合度O2sat\"}],[{\"word_name\":\"仪器类型\",\"word\":\"\"},{\"word_name\":\"单位\",\"word\":\"mmol/L\"},{\"word_name\":\"参考区间\",\"word\":\"3.5-5.3\"},{\"word_name\":\"测试方法\",\"word\":\"\"},{\"word_name\":\"结果\",\"word\":\"3.70\"},{\"word_name\":\"结果提示\",\"word\":\"\"},{\"word_name\":\"项目代号\",\"word\":\"\"},{\"word_name\":\"项目名称\",\"word\":\"钾K+\"}],[{\"word_name\":\"仪器类型\",\"word\":\"\"},{\"word_name\":\"单位\",\"word\":\"mmol/L\"},{\"word_name\":\"参考区间\",\"word\":\"137-147\"},{\"word_name\":\"测试方法\",\"word\":\"\"},{\"word_name\":\"结果\",\"word\":\"145.50\"},{\"word_name\":\"结果提示\",\"word\":\"\"},{\"word_name\":\"项目代号\",\"word\":\"\"},{\"word_name\":\"项目名称\",\"word\":\"钠Na+\"}]]},\"Item_row_num\":9,\"CommonData_result_num\":15,\"log_id\":1766115936304089050}";

        JSONObject jsonObject = new JSONObject(ori_str);
        JSONArray items = jsonObject.getJSONObject("words_result").getJSONArray("Item");
        StringBuilder mergedString = new StringBuilder();

        for (int i = 0; i < items.length(); i++) {
            JSONArray itemGroup = items.getJSONArray(i);
            String itemName = "", result = "", unit = "", referenceRange = "";

            for (int j = 0; j < itemGroup.length(); j++) {
                JSONObject item = itemGroup.getJSONObject(j);
                switch (item.getString("word_name")) {
                    case "项目名称":
                        itemName = item.getString("word");
                        break;
                    case "结果":
                        result = item.getString("word");
                        break;
                    case "单位":
                        unit = item.getString("word");
                        break;
                    case "参考区间":
                        referenceRange = item.getString("word");
                        break;
                }
            }

            if (!itemName.isEmpty() && !result.isEmpty()) {
                if (!mergedString.isEmpty()) {
                    mergedString.append(", ");
                }
                mergedString.append(itemName).append(": ").append(result);
                if (!unit.isEmpty()) {
                    mergedString.append(" ").append(unit);
                }
                if (!referenceRange.isEmpty()) {
                    mergedString.append(" (").append(referenceRange).append(")");
                }
            }
        }

        return (mergedString.toString());
    }

    public String askQuestion(String question) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://i-1.gpushare.com:27417/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String requestJson = "{\"question\": \"" + question + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, String.class);

        String responseBody = response.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject.getString("answer");
    }

    @GetMapping("/call-python")
    public String callPythonScript() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("python", "C:\\Users\\Yellowish\\Desktop\\glm\\2.py");
        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitVal = process.waitFor();
        if (exitVal == 0) {
            return output.toString();
        } else {
            // handle error scenario
            return "An error occurred!";
        }
    }

    public static String medicalReportDetection(String imgStr) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/medical_report_detection";
        try {
            // 本地文件路径
//            String filePath = "src/main/resources/templates/illu_item10-_D_3.jpg";
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
//            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.c064dc409a31c9e97b28f1eef2922082.2592000.1715403243.282335-55663655";

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
