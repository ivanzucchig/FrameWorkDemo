package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class TestScenarioSearchRoom {
    WebDriver driver;

    @Given("^Open chrome$")
    public void open_chrome() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @And("^Accept cookies$")
    public void accept_cookies() {
        driver.findElement(By.xpath("//*[@id='didomi-notice-agree-button']")).click();
    }

    @And("^Navigate to \"([^\"]*)\"$")
    public void navigate_to_something(String strArg1) {
        driver.get(strArg1);
    }

    @When("^User enters \"([^\"]*)\"$")
    public void user_enters_something(String strArg1) {

        driver.findElement(By.xpath("//*[@id=\"mbe-destination-input\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"mbe-destination-input\"]")).sendKeys(strArg1);
    }

    @And("^Select Melia Castilla$")
    public void select_melia_castilla() {
        driver.findElement(By.xpath("//*[@onclick='too.be.destination.setHotel(\"Meliá Castilla\",\"2804\");']")).click();
    }

    @Then("^Calendar is displayed$")
    public void calendar_is_displayed() {
        driver.findElement(By.className("//*[@class='mbe-calendar']")).isDisplayed();
    }

    @And("^Select day of check in and check out$")
    public void select_day_of_check_in_and_check_out() {
        driver.findElement(By.className("//*[@class='day d2021-9-18 available']")).click();
        driver.findElement(By.className("//*[@class='day d2021-10-14 available']")).click();
    }


    @And("^Select Search$")
    public void select_search() {
        driver.findElement(By.xpath("//*[@id=\"mbe-search-button\"]")).click();
    }
    @After("^Close_driver")
    public void close(){
        driver.close();
        driver.quit();
    }

    @And("^Navigate to Melia Castilla$")
    public void navigate_to_melia_castilla() {
        driver.get("https://www.melia.com/es/hoteles/espana/madrid/melia-castilla/habitaciones.htm?fechaEntrada=1621015200&fechaSalida=1621188000");
    }

    @Then("^The rooms found are displayed$")
    public void the_rooms_found_are_displayed() {
        System.out.println(driver.findElements(By.cssSelector(".c-hotel-sheet-room")).size());
        driver.close();
        driver.quit();
    }

    @Given("^Navigate to anz$")
    public void NavigateToAnz() throws Throwable {
        driver.get("https://www.anz.com.au/personal/home-loans/calculators-tools/much-borrow/");
    }

    @Given("^Navigate to melia$")
    public void NavigateToMelia() throws Throwable {
        driver.get("https://www.melia.com");
    }

    @Given("^Close melia cookies$")
    public void Close_melia_cookies() throws Throwable {
        driver.findElement(By.xpath("//button[contains(@id,'didomi-notice-agree-button')]")).click();
    }

    @When("^User enters valid details$")
    public void User_enters_valid_details() throws Throwable {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("application_type_single")).click();
        driver.findElement(By.xpath("//label[contains(@for,'home')]")).click();//input[contains(@aria-labelledby,'q2q2')]
        driver.findElement(By.xpath("//input[@aria-labelledby='q2q1']")).sendKeys("80000");
        driver.findElement(By.xpath("//input[@aria-labelledby='q2q2']")).sendKeys("10000");
        driver.findElement(By.xpath("//input[contains(@id,'expenses')]")).sendKeys("500");
        driver.findElement(By.xpath("//input[contains(@id,'otherloans')]")).sendKeys("100");
        driver.findElement(By.xpath("//input[@id='credit']")).sendKeys("10000");
        driver.findElement(By.xpath("//button[contains(@class,'calculate')]")).click();
    }

    @Then("^borrowing estimate is displayed correctly$")
    public void borrowing_estimate_is_displayed_correctly() throws Throwable {
        Thread.sleep(1000);
        String actualamount = "$467,000";
        String item = driver.findElement(By.xpath("//span[@class='borrow__result__text__amount']")).getText();
        Assert.assertTrue(item.contains(actualamount));
    }

    @When("^User clicks start over button$")
    public void User_clicks_start_over_button() throws Throwable {
        driver.findElement(By.xpath("(//span[contains(@class,'icon icon_restart')])[1]")).click();
    }

    @Then("^Form is cleared$")
    public void Form_is_cleared() throws Throwable {
        String resetvalue = "0";
        String item1 = driver.findElement(By.xpath("//input[@aria-labelledby='q2q1']")).getAttribute("value");
        String item2 = driver.findElement(By.xpath("//input[@aria-labelledby='q2q2']")).getAttribute("value");
        String item3 = driver.findElement(By.xpath("//input[contains(@id,'expenses')]")).getAttribute("value");
        String item4 = driver.findElement(By.xpath("//input[@id='homeloans']")).getAttribute("value");
        String item5 = driver.findElement(By.xpath("//input[@id='otherloans']")).getAttribute("value");
        String item6 = driver.findElement(By.xpath("//input[contains(@aria-labelledby,'q3q4')]")).getAttribute("value");
        String item7 = driver.findElement(By.xpath("//input[@id='credit']")).getAttribute("value");

        Assert.assertTrue(item1.contains(resetvalue));
        Assert.assertTrue(item2.contains(resetvalue));
        Assert.assertTrue(item3.contains(resetvalue));
        Assert.assertTrue(item4.contains(resetvalue));
        Assert.assertTrue(item5.contains(resetvalue));
        Assert.assertTrue(item6.contains(resetvalue));
        Assert.assertTrue(item7.contains(resetvalue));
    }

    @When("^User does not enter all details$")
    public void User_does_not_enter_all_details() throws Throwable {
        driver.findElement(By.xpath("//input[@id='expenses']")).sendKeys("1");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[contains(@class,'calculate')]")).click();
    }

    @Then("^Correct error is displayed$")
    public void Correct_error_is_displayed() throws Throwable {
        String errorMessage = "Based on the details you've entered, we're unable to give you an estimate of your borrowing power with this calculator. For questions, call us on 1800 100 641.";
        String item = driver.findElement(By.xpath("//span[contains(@class,'borrow__error__text')]")).getText();
        Assert.assertTrue(item.contains(errorMessage));
    }

    @Then("^Check link ios app$")
    public void Check_link_app() throws Throwable {
        String linkApp = "https://app.melia.com";
        String item = driver.findElement(By.xpath("//div[contains(@class,'u-download-apps')]//a[1]")).getAttribute("href");
        Assert.assertTrue(item.contains(linkApp));
    }

    @Then("^Click link ios app$")
    public void Click_link_ios_app() throws Throwable {
        driver.findElement(By.xpath("//div[contains(@class,'u-download-apps')]//a[1]")).click();
        //Ver visualmente el resultado
        Thread.sleep(4000);
    }

    @Then("^Application should be closed$")
    public void Application_should_be_closed() throws Throwable {
        driver.quit();
    }

}


/*El test hace simplemente lo que debe hacer ni más ni menos, aunque no se ha revisado que la ejecución en su
conjunto haga lo que debe hacer, por ejemplo, que el navegador se cierre después de cada ejecución
(se quedan todas las instancias abiertas) cosa que hace que se vayan almacenando instancias de Chrome sin cerrar.
 Eso puede provocar fallas a la larga.  Es un fallo de concepto.
 La revisión de código y ejecución del proyecto en conjunto es algo que todo automatizador debe también realizar.

El código podría haberse limpiado un poco más, haber sido más ordenado y pulido.
La obtención de los XPATH en algunos casos ha sido correcta y en otros incorrecta
utilizando rutas DOM que pudieran posibilitar errores futuros.
 */