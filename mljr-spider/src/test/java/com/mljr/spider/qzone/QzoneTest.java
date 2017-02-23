/**
 * 
 */
package com.mljr.spider.qzone;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

import com.google.common.base.Splitter;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import us.codecraft.webmagic.downloader.HttpClientGenerator;

/**
 * @author Ckex zha </br>
 *         Jan 20, 2017,10:19:13 AM
 *
 */
public class QzoneTest {

  /**
   * 
   */
  public QzoneTest() {
    // TODO Auto-generated constructor stub
  }

  private static final String url =
      "http://h5.qzone.qq.com/proxy/domain/ic2.qzone.qq.com/cgi-bin/feeds/feeds_html_act_all?uin=543109152&hostuin=467732755&scope=0&filter=all&flag=1&refresh=0&firstGetGroup=0&mixnocache=0&scene=0&begintime=undefined&icServerTime=&start=0&count=10&sidomain=qzonestyle.gtimg.cn&useutf8=1&outputhtmlfeed=1&refer=2&r=0.8536552901318883&g_tk=56408880";

  private static final String cookieStr =
      "ptisp=ctc;pgv_info=ssid=s7095828286;pt2gguin=o0543109152;pgv_pvi=5707152384;ptcz=e14a38506c4c2cfa9fb978995962f2195cd6f8a481fa168ff9ff20976d407c4f;pgv_pvid=3892314838;p_skey=N2uVkWcuRr*5T3VoljxkhBQOWYhATUDBUIFPqCDmawo_;Loading=Yes;ptui_loginuin=543109152;pgv_si=s603205632;qz_screen=1024x768;skey=@TUeqqIoFJ;RK=kdcS5dqbep;pt4_token=8MnHnDrsDJ1REI49sIunPWo2pV4sI7DF*f5YfH2UHK8_;uin=o0543109152;QZ_FE_WEBP_SUPPORT=0;p_uin=o0543109152";

  private static final String json =
      "[{\"domain\":\".qq.com\",\"httpOnly\":false,\"name\":\"ptisp\",\"path\":\"/\",\"secure\":false,\"value\":\"ctc\"},{\"domain\":\".qq.com\",\"httpOnly\":false,\"name\":\"pgv_info\",\"path\":\"/\",\"secure\":false,\"value\":\"ssid=s7095828286\"},{\"domain\":\".qq.com\",\"expiry\":1577923200000,\"httpOnly\":false,\"name\":\"pt2gguin\",\"path\":\"/\",\"secure\":false,\"value\":\"o0543109152\"},{\"domain\":\".qq.com\",\"expiry\":2147385600000,\"httpOnly\":false,\"name\":\"pgv_pvi\",\"path\":\"/\",\"secure\":false,\"value\":\"5707152384\"},{\"domain\":\".qq.com\",\"expiry\":1577923200000,\"httpOnly\":false,\"name\":\"ptcz\",\"path\":\"/\",\"secure\":false,\"value\":\"e14a38506c4c2cfa9fb978995962f2195cd6f8a481fa168ff9ff20976d407c4f\"},{\"domain\":\".qq.com\",\"expiry\":2147385600000,\"httpOnly\":false,\"name\":\"pgv_pvid\",\"path\":\"/\",\"secure\":false,\"value\":\"3892314838\"},{\"domain\":\".qzone.qq.com\",\"httpOnly\":false,\"name\":\"p_skey\",\"path\":\"/\",\"secure\":false,\"value\":\"N2uVkWcuRr*5T3VoljxkhBQOWYhATUDBUIFPqCDmawo_\"},{\"domain\":\".qzone.qq.com\",\"expiry\":1484928000000,\"httpOnly\":false,\"name\":\"Loading\",\"path\":\"/\",\"secure\":false,\"value\":\"Yes\"},{\"domain\":\".qq.com\",\"expiry\":1487469441000,\"httpOnly\":false,\"name\":\"ptui_loginuin\",\"path\":\"/\",\"secure\":false,\"value\":\"543109152\"},{\"domain\":\".qq.com\",\"httpOnly\":false,\"name\":\"pgv_si\",\"path\":\"/\",\"secure\":false,\"value\":\"s603205632\"},{\"domain\":\".user.qzone.qq.com\",\"expiry\":1487469444000,\"httpOnly\":false,\"name\":\"qz_screen\",\"path\":\"/\",\"secure\":false,\"value\":\"1024x768\"},{\"domain\":\".qq.com\",\"httpOnly\":false,\"name\":\"skey\",\"path\":\"/\",\"secure\":false,\"value\":\"@TUeqqIoFJ\"},{\"domain\":\".qq.com\",\"expiry\":1800237441000,\"httpOnly\":false,\"name\":\"RK\",\"path\":\"/\",\"secure\":false,\"value\":\"kdcS5dqbep\"},{\"domain\":\".qzone.qq.com\",\"httpOnly\":false,\"name\":\"pt4_token\",\"path\":\"/\",\"secure\":false,\"value\":\"8MnHnDrsDJ1REI49sIunPWo2pV4sI7DF*f5YfH2UHK8_\"},{\"domain\":\".qq.com\",\"httpOnly\":false,\"name\":\"uin\",\"path\":\"/\",\"secure\":false,\"value\":\"o0543109152\"},{\"domain\":\".qzone.qq.com\",\"expiry\":1800237450000,\"httpOnly\":false,\"name\":\"QZ_FE_WEBP_SUPPORT\",\"path\":\"/\",\"secure\":false,\"value\":\"0\"},{\"domain\":\".qzone.qq.com\",\"httpOnly\":false,\"name\":\"p_uin\",\"path\":\"/\",\"secure\":false,\"value\":\"o0543109152\"}]";

  private static final Gson gson = new Gson();

  /**
   * @param args
   * @throws IOException
   * @throws ClientProtocolException
   */
  public static void main(String[] args) throws Exception {
    test();
  }

  private static HttpClientGenerator httpClientGenerator = new HttpClientGenerator();

  private static void test() throws ClientProtocolException, IOException {

    CookieStore cookieStore = new BasicCookieStore();
    List<LinkedTreeMap<String, String>> list = gson.fromJson(json, ArrayList.class);
    list.forEach(e -> {
      System.out.println(e);
      BasicClientCookie clientCookie = new BasicClientCookie(e.get("name"), e.get("value"));
      clientCookie.setDomain(e.get("domain"));
      cookieStore.addCookie(clientCookie);
    });

    CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    HttpUriRequest request = RequestBuilder.get(URI.create(url)).build();
    HttpResponse response = httpClient.execute(request);
    String result = EntityUtils.toString(response.getEntity());
    System.out.println(result);

  }

}
