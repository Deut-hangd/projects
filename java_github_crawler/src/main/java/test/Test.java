package test;

import crawler.Crawler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

public class Test {
    public static void main(String[] args) throws IOException {
//        Crawler crawler = new Crawler();
//        String html = crawler.getPage("https://github.com/doov-io/doov");
//        System.out.println(html);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = simpleDateFormat.format(System.currentTimeMillis());
        System.out.println(result);
    }
}
