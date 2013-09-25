package sample;



public class Main {

    public static void main(String[] args) {
    	new Thread(new UploadRobot("E:/img_article")).start();
    }
}