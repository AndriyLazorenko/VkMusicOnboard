package Lazorenko.Controller;

import Lazorenko.Logger.LoggerToFile;
import Lazorenko.Model.Model;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.TreeSet;

/**
 * ControllerDeprecated class responsible for logic
 * @author andriylazorenko
 */

public abstract class ControllerDeprecated {

    /**
     * Variables
     */

    protected LoggerToFile loggerToFile = LoggerToFile.getInstance();
    protected static final String att = "rel";
    protected String uri =null;
    protected String dest=null;
    protected String choice=null;
    public static final String destinationByDefault = "/home/andriylazorenko/Music";
    protected BufferedReader br;

    /**
     * Constructor class initializes input from console
     */

    public ControllerDeprecated() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Main aggregated method. Does everything
     */

    public void run(){
        getData();
        download();
    }

    /**
     * Method asking for URL for download
     */

    protected void getURL(){
        System.out.println("Insert an URL: ");
        try {
            uri = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            loggerToFile.getLogger().error(e.getMessage());
        }
    }

    /**
     * Method asking for path of download
     */

    protected void getPath(){
        System.out.println("Insert download path. Press 'd' for default path");
        try {
            dest = br.readLine();
            if (dest.equals("d")||dest.equals("D")){
                dest=destinationByDefault;
            }
            //May want to add additional destination checks later on
        } catch (IOException e) {
            e.printStackTrace();
            loggerToFile.getLogger().error(e.getMessage());
        }
    }

    /**
     * Method asking for format of downloaded content - loaded from prop file. Easily edited.
     */

    protected void getFormat(){
        System.out.println("Select format of downloaded data:");
        Model model = Model.getInstance();
        for (Object o : new TreeSet<>(model.getProperties().keySet())) {
            System.out.println(o.toString()+". "+model.getProperties().getProperty(o.toString()));
        }
        try {
            int choice = Integer.parseInt(br.readLine());
            this.choice = model.getProperties().getProperty(Integer.toString(choice));
        } catch (IOException e) {
            e.printStackTrace();
            loggerToFile.getLogger().error(e.getMessage());
            getFormat();
        }

    }

    /**
     * Aggregator method to obtain data
     */

    protected void getData(){
        getURL();
        getPath();
        getFormat();
    }

    /**
     * Method using to download everything
     */

    protected void download(){
        Document document = null;
        try {
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
        Elements elements = document.getElementsByAttribute(att);

        for (Element e : elements) {
            if (e.text().contains(choice)) {
                String downloadURL = uri.substring(0, uri.lastIndexOf("/"));
                downloadURL = downloadURL + e.attr("href");
                downloadURL = downloadURL.replace("get", "load");
                String finalDestination = dest + "/" + e.text();
                System.out.println(downloadURL);
                downloadOne(downloadURL, finalDestination);
            }
        }
    }

    /**
     * Internal method used to download one object (object class loaded from list of properties)
     * @param url - url of an object
     * @param dest - destination of an object
     */

    protected void downloadOne(String url, String dest){
        try (OutputStream os = new FileOutputStream(dest)){
            URL downloadUrl = new URI(url).toURL();
            InputStream is = downloadUrl.openStream();

            byte[] buff = new byte[1000000];
            int count = 0;
            while((count = is.read(buff)) != -1){
                os.write(buff,0,count);
                os.flush();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            loggerToFile.getLogger().error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            loggerToFile.getLogger().error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            loggerToFile.getLogger().error(e.getMessage());
        }

    }
}
