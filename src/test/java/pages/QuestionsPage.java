package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class QuestionsPage {
    By userName = By.className("ant-typography");
    By timeLeftText = By.xpath("//*[@id=\"timer\"][1]");
    By inactiveSteps = By.cssSelector("div[class$='ant-steps-item-wait']");
    By activeStep = By.cssSelector("div[class$='steps-item-active']");
    By finishedStep = By.cssSelector("div[class$='ant-steps-item-finish']");
    By questionTitle = By.cssSelector("div[class='ant-card-head-title']");
    By question = By.cssSelector("label[class='ant-radio-wrapper']");
    By button = By.cssSelector("button[class^='ant-btn']");
    By finishedTestImg = By.cssSelector("span[class$='anticon-check-circle']");
    By finishedTestTitle = By.className("ant-result-title");
    By finishedTestSubtitle = By.className("ant-result-subtitle");
    By emailButton = By.cssSelector("button[class$='ant-btn-primary']");
    By finishButton = By.cssSelector("button[class$='control-panel__button']");
    By numField = By.className("ant-input-number-input");
    By codeField = By.className("w-tc-editor-text");


    private final WebDriver driver;

    public QuestionsPage(WebDriver driver) {
        this.driver = driver;
    } // Конструктор?

    public String getUserName() {
        return driver.findElement(userName).getText();
    }
    public String getTimeLeft() {
        return driver.findElement(timeLeftText).getText();
    }

    public boolean timeChange() throws InterruptedException {
        String firstTime = driver.findElement(timeLeftText).getText();
        Thread.sleep(1000);
        String secondTime = driver.findElement(timeLeftText).getText();
        return !firstTime.equals(secondTime);
    }

    public int getAllStepsAmount() {
        List<WebElement> elements = driver.findElements(inactiveSteps);
        elements.add(driver.findElement(activeStep));
        return elements.size();
    }

    public int getActiveQuestion() {
        return Integer.parseInt(driver.findElement(activeStep).getText());
    }

    public int getInactiveStepsAmount() {
        List<WebElement> elements = driver.findElements(inactiveSteps);
        return elements.size();
    }

    public String getQuestionTitle() {
        return driver.findElement(questionTitle).getText();
    }

    public int checkQuestionsAmountAndText() {
        List<WebElement> elements = driver.findElements(question);
        List<String> questionsList = new ArrayList<>();
        elements.forEach(element -> questionsList.add(element.getText()));
        if (Integer.toString(elements.size()).equals(Integer.toString(questionsList.size()))) {
            return elements.size();
        }
        else {
            return 0;
        }
    }
    public String getButtonText() {
        return driver.findElement(button).getText();
    }

    public boolean isButtonActive(){
        return driver.findElement(button).isEnabled();
    }

    public boolean checkPageTitleAfterBtnClick() {
        String firstTitle = driver.getTitle();
        clickNextQuestionBtn();
        String secondTitle = driver.getTitle();
        return firstTitle.equals(secondTitle);
    }
    public void pickRandomAnswer(){
        List<WebElement> elements = driver.findElements(question);
        int min = 0;
        int max = elements.size() - 1;
        int answer = (int)(Math.random()*(max-min+1)+min);
        elements.get(answer).click();
    }

    public void clickNextQuestionBtn() {
        driver.findElement(button).click();
    }

    public boolean previousQuestionsChecked(){
        List<WebElement> elements = driver.findElements(finishedStep);
        return elements.size() + 1 == getActiveQuestion();
    }

    public String checkURL() {
        return driver.getCurrentUrl();
    }

    public boolean checkFinishedTestImg() {
        List<WebElement> elements = driver.findElements(finishedTestImg);
        return elements.size() == 1;
    }

    public String finishedText() {
        return driver.findElement(finishedTestTitle).getText() + " " + driver.findElement(finishedTestSubtitle).getText();
    }

    public String emailButtonText() {
        return driver.findElement(emailButton).getText();
    }

    public void sendInt() {
        String text = "1";
        driver.findElement(numField).sendKeys(text);
    }

    public void sendSql() {
        String sql = "select * from employees;";
        driver.findElement(codeField).sendKeys(sql);
    }

    public void sendJavaCode() {
        String javaCode = "public static boolean isPalindrome(int x) {\n" +
                "        String s = Integer.toString(x);\n" +
                "        String sCopy = \"\";\n" +
                "        for (int i = s.length() -1; i >= 0; i--)\n" +
                "        {\n" +
                "            sCopy += s.charAt(i);\n" +
                "        }\n" +
                "        if(sCopy.equals(s))\n" +
                "            return true;\n" +
                "        return false;\n" +
                "    }";
        driver.findElement(codeField).sendKeys(javaCode);
    }

    public String getFinishButtonText() {
        return driver.findElement(finishButton).getText();
    }

    public boolean isFinishButtonActive(){
        return driver.findElement(finishButton).isEnabled();
    }
    public void finishTest() {
        driver.findElement(finishButton).click();
    }

}
