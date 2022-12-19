package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MaterialsPage {
    By tabs = By.className("ant-tabs-tab-btn");
    By checkBox = By.className("ant-checkbox-wrapper");
    By checkBoxBox = By.className("ant-checkbox");
    By button = By.className("ant-btn");
    private final WebDriver driver;
    private final Logger logger = Logger.getLogger(MaterialsPage.class.getName());

    public MaterialsPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step ("Get list of tabs")
    public List<String> getTabsNames() {
        List<WebElement> elements = driver.findElements(tabs);
        List<String> tabsList = new ArrayList<>();
        elements.forEach(element -> tabsList.add(element.getText()));
        return tabsList;
    }
    @Step ("Get checkbox text")
    public String getCheckBoxText() {
        return driver.findElement(checkBox).getText();
    }

    @Step ("Get button text")
    public String getButtonText() {
        return driver.findElement(button).getText();
    }

    @Step ("Get checkbox element")
    public WebElement getCheckBoxBox() {
        return driver.findElement(checkBoxBox);
    }

    @Step ("Verify is checkbox checked")
    public WebElement checkCheckBox() {
        WebElement chBox = getCheckBoxBox();
        chBox.click();
        logger.info("Is checkbox selected" + chBox.isSelected());
        return chBox;
    }

    public boolean isButtonActive(){
        return driver.findElement(button).isEnabled();
    }

    public void pressButton() {
        driver.findElement(button).click();
    }
}
