package db;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;

public class CatchData
{
    public void startCatchData() throws Exception
    {

        // database object
        DatabaseHandle databaseHandle = new DatabaseHandle();

        // web driver objects
        WebDriver driver = new RemoteWebDriver(
                new URL("http://127.0.0.1:9515"),
                new ChromeOptions());
        Actions actions = new Actions(driver);

        String baseUrl = "https://www.instagram.com/";

        String handleUri = databaseHandle.querySettingHandleUri();
        driver.get(baseUrl + handleUri);


        List<WebElement> images = driver.findElements(By.className("_bz0w"));

        String imageCurr  = images.get(Integer.valueOf(databaseHandle.querySettingInstaPostImageNumber())).findElement(By.tagName("a")).getAttribute("href");
        driver.get(imageCurr);
        while (true)
        {
            try {
                WebElement loadMore = driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/main/div/div/article/div[2]/div[1]/ul/li/div/button/span"));
                Action action = actions.moveToElement(loadMore).build();
                action.perform();
                loadMore.click();

                try {
                    Thread.sleep(3000);
                }catch (InterruptedException e)
                {

                }

            }
            catch (Exception e)
            {
                break;
            }
        }


        // temporary variable for database
        int id = 1;
        String dir = databaseHandle.querySettingImagePath();

        List<WebElement> comments = driver.findElements(By.className("C4VMK"));
        List<WebElement> profileImages = driver.findElements(By.className("_6q-tv"));
        for (WebElement c : comments)
        {
            String comment = c.findElement(By.cssSelector("span")).getAttribute("textContent");
            String user = c.findElement(By.className("TlrDj")).getAttribute("textContent");


            if ( !user.equals(handleUri))
            {
                databaseHandle.insertInstaData(String.valueOf(id) , user , StringUtils.abbreviate(comment , 76));


                for ( WebElement pi : profileImages)
                {
                    String imageSrc = pi.getAttribute("src");
                    String profileCheck = pi.getAttribute("alt");

                    if ( profileCheck.equals(user+"\'s profile picture") )
                    {
                        URL imageURL = new URL(imageSrc);
                        BufferedImage saveImage = ImageIO.read(imageURL);
                        ImageIO.write(saveImage,"png", new File(dir + id + ".png"));

                        id++;
                    }

                }
            }


        }

        driver.close();

    }
}
