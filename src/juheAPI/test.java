package juheAPI;

import org.junit.jupiter.api.Test;
import juheAPI.WeatherAPI;
import juheAPI.LifeAPI;
import net.sf.json.JSONObject;
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
public class test {
	WeatherAPI wea = new WeatherAPI();
	LifeAPI life = new LifeAPI();
	WidsAPI wids = new WidsAPI();
	CityListAPI citylist = new CityListAPI();
	@Test
	public void Weather() {
		JSONObject jsonObject = wea.queryWeather("上海");
		System.out.println(jsonObject);
	}
	@Test
	public void Life() {
		JSONObject jsonObject = life.queryLife("上海");
		System.out.println(jsonObject);
	}
	@Test
	public void Wids() {
		JSONObject jsonObject = wids.queryWids();
		System.out.println(jsonObject);
	}
	@Test
	public void CityList() {
		JSONObject jsonObject = citylist.queryCityList();
		System.out.println(jsonObject);
	}
}
