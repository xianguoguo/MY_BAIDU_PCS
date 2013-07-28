package sample;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Thumbnails.of(new File("D:/wakao/webimage/hbimage/20130728/0105").listFiles()).size(200, 100).toFiles(Rename.PREFIX_HYPHEN_THUMBNAIL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
