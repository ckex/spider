package com.mljr.spider.parser;

import us.codecraft.webmagic.selector.Html;

import java.io.File;
import java.util.List;

/**
 * Created by songchi on 17/1/10.
 */
public class Che300Parser extends AbstractXpathParser {
    @Override
    public String[] getHearders(Html html) {
        return new String[0];
    }

    @Override
    public List<String[]> getContent(Html html) {
        return null;
    }

    @Override
    public void parseToCsv(Html html) {
        String filePath = "";
        this.write(filePath, this.getHearders(html), this.getContent(html));
    }

    public static void main(String[] args) {
        Che300Parser parser = new Che300Parser();

        File[] htmlFiles = parser.getHtmlFiles("");
        for (File htmlFile : htmlFiles) {
            Html html = parser.readHtmlFile(htmlFile);
            if(html!=null){
                parser.parseToCsv(html);
            }
        }
    }
}
