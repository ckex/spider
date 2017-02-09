/**
 * 
 */
package com.mljr.spider.unicom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author Ckex zha </br>
 *         Jan 20, 2017,4:02:35 PM
 *
 */
public class ChinaUnicomTest {

	private String name = "15601662655";
	private String pwd = "254059";
	private String loginUrl = "https://uac.10010.com/portal/Service/MallLogin?callback=jQuery17202691898950318097_1403425938090&redirectURL=http%3A%2F%2Fwww.10010.com&userName="
			+ name + "&password=" + pwd + "&pwdType=01&productType=01&redirectType=01&rememberMe=1";

	private RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).build();
	private CookieStore cookieStore = new BasicCookieStore();
	private CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore)
			.setDefaultRequestConfig(globalConfig).build();

	public ChinaUnicomTest() {
		super();
	}

	public static void main(String[] args) throws Exception {
		ChinaUnicomTest test = new ChinaUnicomTest();
		test.run();
	}

	private void run() throws ClientProtocolException, Exception {

		RequestConfig localConfig = RequestConfig.copy(globalConfig).setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
		HttpGet get = new HttpGet(loginUrl);
		get.setConfig(localConfig);
		HttpClientContext context = HttpClientContext.create();
		context.setCookieStore(cookieStore);
		CloseableHttpResponse response = httpclient.execute(get, context);
		String loginEntityContent = EntityUtils.toString(response.getEntity());
		System.out.println(loginEntityContent);
		//
		if (loginEntityContent.contains("resultCode:\"0000\"")) {

			// String subUrl =
			// "http://iservice.10010.com/e3/ToExcel.jsp?type=realTime";
			String subUrl = "http://iservice.10010.com/e3/static/query/searchPerInfo/?_=1485076150761&accessURL=http://iservice.10010.com/e4/query/basic/personal_xx_iframe.html";

			HttpPost httpPost = new HttpPost(subUrl);
			HttpResponse resp = httpclient.execute(httpPost);
			HttpEntity entity = resp.getEntity();
			System.out.println(EntityUtils.toString(entity));
			// -----------------------------
			// InputStream in = entity.getContent();
			// saveFile(in);
			// -----------------------------
			// FileOutputStream fileOut = new
			// FileOutputStream("/Users/ckex/work/workspace/github/crawl/result/test.xls");
			// int data = in.read();
			// while (data != -1) {
			// fileOut.write(data);
			// data = in.read();
			// }
			// fileOut.close();
			// -----------------------------
			// BufferedReader bufferedReader = new BufferedReader(new
			// InputStreamReader(in));
			// StringBuffer buffer = new StringBuffer();
			// String line = "";
			// while ((line = bufferedReader.readLine()) != null) {
			// buffer.append(line);
			// }
			// System.out.println(">>>" + buffer.toString());
			//
			// System.out.println("...> " + entity.getContentLength());
			// System.out.println("===>" + EntityUtils.toString(entity));
		}

	}

	public static void saveFile(InputStream in) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(in);
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = workbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}

				// 循环列Cell
				for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
					HSSFCell hssfCell = hssfRow.getCell(cellNum);
					if (hssfCell == null) {
						continue;
					}
					System.out.print("    " + getValue(hssfCell));
				}
				System.out.println();
			}
		}
	}

	private static String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	// inputStream转outputStream
	public static ByteArrayOutputStream parse(InputStream in) throws Exception {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		int ch;
		while ((ch = in.read()) != -1) {
			swapStream.write(ch);
		}
		return swapStream;
	}
}
