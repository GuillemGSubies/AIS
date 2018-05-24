package es.codeurjc.ais.tictactoe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;


public class SystemTests {

    private WebDriver plaierUan;
    private WebDriver plaierTchu;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
        //Otra forma de hacerlo
        //System.setProperty("webdriver.chrome.driver", "C:/Users/ORDENADOR/Downloads/chromedriver.exe");
        WebApp.start();
    }

    @AfterClass
    public static void teardownClass() {
        WebApp.stop();
    }

    @Before
    public void setupTest() throws InterruptedException{
        plaierUan = new ChromeDriver();
        plaierTchu = new ChromeDriver();
        plaierUan.get("http://localhost:8080/");
        plaierTchu.get("http://localhost:8080/");
//        TimeUnit.SECONDS.sleep(1);
        String nickname1 = "Paco";
        String nickname2 = "Pepe";
        plaierUan.findElement(By.id("nickname")).sendKeys(nickname1);
//        TimeUnit.SECONDS.sleep(1);
        plaierTchu.findElement(By.id("nickname")).sendKeys(nickname2);
//        TimeUnit.SECONDS.sleep(1);
        plaierUan.findElement(By.id("startBtn")).click();
        plaierTchu.findElement(By.id("startBtn")).click();
    }

    @After
    public void teardown() {
        if (plaierUan != null) {
            plaierUan.quit();
        }
        if (plaierTchu != null) {
            plaierTchu.quit();
        }
    }

    @Test
    public void uanWinsTest() throws InterruptedException {

        // Ponemos los sleeps para que los tests se puedan seguir de forma visual
        // Solo pondremos los sleep en el primer test por motivos didacticos ya
        // que realentizan demasiado el proceso de pruebas
        TimeUnit.SECONDS.sleep(2);
        ponFicha(plaierUan, 4);
        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierTchu, 3);
        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierUan, 0);
        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierTchu, 6);
        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierUan, 8);
        TimeUnit.SECONDS.sleep(3);
        String message = "Paco wins! Pepe looses.";
        TimeUnit.SECONDS.sleep(1);
        Assert.assertEquals(message, plaierTchu.switchTo().alert().getText());
        Assert.assertEquals(plaierUan.switchTo().alert().getText(), message);
        Assert.assertEquals(plaierUan.switchTo().alert().getText(), plaierTchu.switchTo().alert().getText());
    }

    @Test
    public void tchuWinsTest() throws InterruptedException {

        // Ponemos los sleeps para que los tests se puedan seguir de forma visual
        // Solo pondremos los sleep en el primer test por motivos didacticos ya
        // que realentizan demasiado el proceso de pruebas
        ponFicha(plaierUan, 4);
//        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierTchu, 3);
//        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierUan, 1);
//        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierTchu, 0);
//        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierUan, 8);
//        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierTchu, 6);
//        TimeUnit.SECONDS.sleep(3);
        String message = "Pepe wins! Paco looses.";
        TimeUnit.SECONDS.sleep(1);
        //Necesitamos este sleep porque cuando se ejecutan todos los
        //tests juntos, daba timeout y no saltaba la alert()
        Assert.assertEquals(message, plaierTchu.switchTo().alert().getText());
        Assert.assertEquals(plaierUan.switchTo().alert().getText(), message);
        Assert.assertEquals(plaierUan.switchTo().alert().getText(), plaierTchu.switchTo().alert().getText());
    }

    @Test
    public void drawTest() throws InterruptedException {

        // Ponemos los sleeps para que los tests se puedan seguir de forma visual
        // Solo pondremos los sleep en el primer test por motivos didacticos ya
        // que realentizan demasiado el proceso de pruebas
        ponFicha(plaierUan, 0);
//        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierTchu, 2);
//        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierUan, 1);
//        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierTchu, 4);
//        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierUan, 5);
//        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierTchu, 7);
//        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierUan, 6);
//        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierTchu, 3);
//        TimeUnit.SECONDS.sleep(1);
        ponFicha(plaierUan, 8);
//        TimeUnit.SECONDS.sleep(3);
        String message = "Draw!";
        TimeUnit.SECONDS.sleep(1);
        Assert.assertEquals(message, plaierTchu.switchTo().alert().getText());
        Assert.assertEquals(plaierUan.switchTo().alert().getText(), message);
        Assert.assertEquals(plaierUan.switchTo().alert().getText(), plaierTchu.switchTo().alert().getText());
    }
    private void ponFicha(WebDriver pla, int pos){
        pla.findElement(By.id(String.format("cell-%d", pos))).click();

    }
}
