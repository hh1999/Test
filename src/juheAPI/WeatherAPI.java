package juheAPI;
import java.io.BufferedReader;  
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;  
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map; 
import net.sf.json.JSONArray;  
import net.sf.json.JSONObject;  

public class WeatherAPI {
    public static final String API_URL = "http://apis.juhe.cn/simpleWeather/query"; // 请求第三方服务器地址
    public static final String API_KEY = "cbdb91f07db2908ea07987faa53762d7"; // 请求key
    public static final int RESULT_CODE = 200; // 响应结果

    public static void main(String[] args) {
        JSONObject jsonObject = queryWeather("上海");
        System.out.println(jsonObject);
    }


    public static JSONObject queryWeather(String city) {
        JSONObject jsonObject = null;
        HashMap<String, String> params = new HashMap<>();
        params.put("city",city);
        params.put("key",API_KEY);
        // 请求参数进行编码操作
        String requestParams = paramsEncode(params);
        // 请求第三方服务器
        String response = doGet(API_URL, requestParams);
        try {
            jsonObject = JSONObject.fromObject(response);
            int errorCode = jsonObject.getInt("error_code");
            if (errorCode == 0) {
                System.out.println("调用接口成功");
            } else {
                System.out.println("调用接口失败：" + jsonObject.getString("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
      return jsonObject;
    }

    /**
     * get方式的http请求
     *
     * @param httpUrl 请求地址
     * @return 返回结果
     */
    public static String doGet(String httpUrl, String queryParams) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String result = null;
        try {
            // 创建远程url连接对象
            URL url = new URL(httpUrl + "?" + queryParams);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(5000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(6000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == RESULT_CODE) {
                inputStream = connection.getInputStream();
                // 封装输入流，并指定字符集
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                // 存放数据
                StringBuilder sbf = new StringBuilder();
                String temp;
                while ((temp = bufferedReader.readLine()) != null) {
                    sbf.append(temp);
                }
                result = sbf.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                // 关闭远程连接
                connection.disconnect();
            }
        }
        return result;
    }

    /***
     *
     * @param param 请求参数
     * @return 编码过后的请求参数
     */
    public static String paramsEncode(HashMap<String, String> param) {
        StringBuffer str = new StringBuffer();
        for (Map.Entry<String, String> res : param.entrySet()) {
            try {
                str.append(res.getKey()).append("=").append(URLEncoder.encode(res.getValue(), "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        int index = str.lastIndexOf("&");
        return str.toString().substring(0, index);
    }
}
