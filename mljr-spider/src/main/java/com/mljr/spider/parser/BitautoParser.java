package com.mljr.spider.parser;

import com.google.common.collect.Lists;
import us.codecraft.webmagic.selector.Html;

import java.io.File;
import java.util.List;

/**
 * Created by songchi on 17/1/10.
 */
public class BitautoParser extends AbstractXpathParser {
    public final static String HTML_FILE_PATH = "/Users/songchi/Desktop/bitauto-html-file/";
    public final static String CSV_FILE_PATH_PREFIX = "/Users/songchi/Desktop/bitauto-csv-file/";

    @Override
    public String[] getHeaders(ParsePage page) {
        Html html = page.getHtml();
        List<String> headers = Lists.newArrayList("款式");
        List<String> srcHearders = html.xpath("//*[@id=\"CarCompareContent\"]//th//tidyText()").all();
        if (srcHearders == null || srcHearders.isEmpty()) {
            return null;
        }
        for (String hearder : srcHearders) {
            headers.add(hearder.split(" ")[0]);
        }
        return headers.toArray(new String[headers.size()]);
    }

    public String getFullName(ParsePage page) {
        Html html = page.getHtml();
        List<String> nav = html.xpath("//*[@id=\"topBox\"]//div[1]//div//div//a//tidyText()").all();

        return CSV_FILE_PATH_PREFIX + System.currentTimeMillis() + ".csv";
    }

    @Override
    public List<String[]> getContent(ParsePage page) {
        Html html = page.getHtml();
        List<String[]> rows = Lists.newArrayList();
        int carNum = html.xpath("//tr[@class='trForPic']//td").all().size();

        String pattern = "//*[@id=\"CarCompareContent\"]//table//tbody/tr/td[%d]//tidyText()";
        for (int i = 1; i <= carNum; i++) {

            List<String> srcData = html.xpath(String.format(pattern, i)).all();

            List<String> destData = Lists.newArrayList();
            for (String src : srcData) {
                if (src.contains("参数纠错")) {
                    continue;
                }
                destData.add(src.replaceAll("<.*>", "").trim());
            }
            rows.add(destData.toArray(new String[destData.size()]));
        }
        return rows;
    }

    @Override
    public void parseToCsv(ParsePage page) {
        this.write(this.getFullName(page), this.getHeaders(page), this.getContent(page));
    }

    public static void main(String[] args) {
        BitautoParser parser = new BitautoParser();

        File[] htmlFiles = parser.getHtmlFiles(HTML_FILE_PATH);
        for (File htmlFile : htmlFiles) {
            ParsePage page = parser.readHtmlFile(htmlFile);
            if (page != null) {
                parser.parseToCsv(page);
            }
        }
    }
}
