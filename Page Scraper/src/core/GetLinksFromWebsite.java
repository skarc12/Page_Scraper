package core;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class GetLinksFromWebsite {

	public static void main(String[] args) throws IOException {

		String websiteURL = getUserInput();
		HtmlGenerator generator = new HtmlGenerator(websiteURL);
		String html = generator.getHtmlFile();

		HtmlImageReader imageReader = new HtmlImageReader(html, websiteURL);
		List<String> imageLinks = imageReader.getImages();

		HtmlLinkReader linkReader = new HtmlLinkReader(html, websiteURL);
		List<String> links = linkReader.getLinks();

		String filename = generatefileName(websiteURL);

		putResultIntoFile(links, filename, "Links");
		putResultIntoFile(imageLinks, filename, "Images");
		System.out.println("Links exported into " + filename + ".txt file successfully.");
		downloadPicture(imageLinks);

	}

	private static void downloadPicture(List<String> imageLinks) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.print("Do you want to download images? Y/N:  ");
		String input = sc.nextLine();
		if (input.equals("Y")) {
			downloadPicturesFromlinks(imageLinks);
			System.out.println("Done.");
		} else {
			System.out.println("Bye :)");
		}

	}

	private static void downloadPicturesFromlinks(List<String> imageLinks) {
		String filename = "";
		URL u = null;
		for (String imgSrc : imageLinks) {
			filename = imgSrc.substring(imgSrc.lastIndexOf('/'), imgSrc.indexOf('.', imgSrc.lastIndexOf('/')));
			try {
				u = new URL(imgSrc);
				BufferedImage image = ImageIO.read(u);
				ImageIO.write(image, "jpg", new File("src" + filename + ".jpg"));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static String generatefileName(String websiteURL) {
		String filename = websiteURL.substring(websiteURL.indexOf('/'), websiteURL.lastIndexOf('.'));

		File xx = new File("src/" + filename + ".txt");
		if (xx.exists()) {
			xx.delete();
		}
		return filename;
	}

	private static void putResultIntoFile(List<String> resultSet, String filename, String type) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream("src/" + filename + ".txt", true), "UTF-8"));
			out.write(type + "\n");
			for (String k : resultSet) {
				out.write(k);
				out.write("\n");
				// System.out.println(k);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static String getUserInput() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter website url: ");
		String input = sc.nextLine();
		String websiteURL = "";

		if (input != null) {
			websiteURL = input;
		}
		return websiteURL;
	}

}
