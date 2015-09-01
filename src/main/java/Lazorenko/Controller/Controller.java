package Lazorenko.Controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Controller class responsible for logic
 * @author andriylazorenko
 */

public class Controller extends ControllerDeprecated {

    @Override
    protected void getFormat(){
        choice = ".mp3";
    }

    @Override
    protected void getURL(){
        uri = "https://vk.com/audios63063935";
    }

    @Override
    protected void download(){
        Document document = null;
        try {
            //TODO debug. Doesn't connect to this url. There seems to be a problem
            document = Jsoup.connect(uri).get();
        } catch (IOException e) {
            loggerToFile.getLogger().error(e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e){
            loggerToFile.getLogger().error(e.getMessage());
            e.printStackTrace();
            //TODO
            //If incorrect URL - return on previous step.
        }

        Elements elements = document.getElementsByClass("play_btn fl_l");
//        Elements elements = document.getElementsByAttribute(att);

        for (Element e : elements) {
            System.out.println(e.child(1));
//            if (e.text().contains(choice)) {
//                String downloadURL = uri.substring(0, uri.lastIndexOf("/"));
//                downloadURL = downloadURL + e.attr("href");
//                downloadURL = downloadURL.replace("get", "load");
//                String finalDestination = dest + "/" + e.text();
//                System.out.println(downloadURL);
//                downloadOne(downloadURL, finalDestination);
//            }
        }
    }

}
