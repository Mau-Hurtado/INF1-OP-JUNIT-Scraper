
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

	public static int numberDownloaded = 0;
	public static int parseInput() {
		Scanner in = new Scanner(System.in);
        try {
            return in.nextInt();
        } catch (InputMismatchException missE) {
            in.next(); // discard invalid input
            return -1;
        }
    }
	
	
	
	public static void main(String args[]){
		String url = "http://www.inf.ed.ac.uk/teaching/courses/inf1/op/2019/labs/unittests.html";
		String suffix = "Test.java";
		
		print("Download 2019 JUNIT lab tests to \"/JUNIT tests\"?"
				+ "\n1: Download now" 
				+ "\n2: Exit");
		int userInput = parseInput();
		switch (userInput) {
		case 1:
			break;
		default:
			return;
		}
		
		print("Connecting...");
		downloadFilesEndingIn(suffix, url);
		print("Finished.\nDownloaded %s files ending in " + suffix, numberDownloaded);
	}
	
	public static void downloadFilesEndingIn(String suffix, String url) {
		try {
			Document doc;
			try {
				doc = Jsoup.connect(url).get();
			} catch (Exception e) {
				print("Unable to connect to " + url);
				parseInput();
				return;
			}

			
			String title = doc.title(); //Get title
			print("Connected to: " + title); //Print title.
			
			Elements links = doc.select("a[href]");
			for (Element ele : links) {
				if (ele.text().endsWith(suffix))
				{
					String link = ele.attr("abs:href");

					String[] parts = link.split("/");
					
					String folder = "unknown";
					for (int i = 0; i < parts.length-1; i++) {
						if (parts[i].endsWith("unittests"))
						{
							folder = parts[i+1];
							break;
						}
					}
					print("Downloading %s to /%s",parts[parts.length-1],folder);
					downloadFile(link, parts[parts.length-1], folder);
					numberDownloaded++;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void downloadFile(String urlString, String filename, String folder) {
		
		URL website;
		try {
			
		website = new URL(urlString);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		new File("JUNIT tests/" + folder).mkdirs();
		FileOutputStream fos = new FileOutputStream(folder + "/" + filename);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

}
