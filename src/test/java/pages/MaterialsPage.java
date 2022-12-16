package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class MaterialsPage {
    By tabs = By.className("ant-tabs-tab-btn");
    By checkBox = By.className("ant-checkbox-wrapper");
    By checkBoxBox = By.className("ant-checkbox");
    By button = By.className("ant-btn");
    private final WebDriver driver;

    public MaterialsPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<String> getTabsNames() {
        List<WebElement> elements = driver.findElements(tabs);
        List<String> tabsList = new ArrayList<String>();
        elements.forEach(element -> tabsList.add(element.getText()));
        return tabsList;
    }
    public String getCheckBoxText() {
        return driver.findElement(checkBox).getText();
    }

    public String getButtonText() {
        return driver.findElement(button).getText();
    }

    public WebElement getCkeckBoxBox() {
        return driver.findElement(checkBoxBox);
    }
    public WebElement checkCkeckBox() {
        WebElement chBox = getCkeckBoxBox();
        chBox.click();
        System.out.println(chBox.isSelected());
        return chBox;
    }

    public boolean isButtonActive(){
        return driver.findElement(button).isEnabled();
    }

    public void pressButton() {
        driver.findElement(button).click();
    }
}
