package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {
    private WebDriverWait webDriverWait;
    public ResultPage(WebDriver webDriver) {
        webDriverWait = new WebDriverWait(webDriver, 2);
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(id = "success")
    private WebElement successHeader;

    @FindBy(id = "success-back-to-home")
    private WebElement successBackToHomeLink;

    @FindBy(id = "error")
    private WebElement errorHeader;

    @FindBy(id = "error-back-to-home")
    private WebElement errorBackToHomeLink;

    @FindBy(id = "saveError")
    private WebElement saveErrorHeader;

    @FindBy(id = "save-error-back-to-home")
    private WebElement saveErrorBackToHomeLink;

    public void navigateToHomeAfterSuccess() {
        successBackToHomeLink.click();
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
    }

    public void navigateToHomeAfterError() {
        errorBackToHomeLink.click();
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
    }

    public void navigateToHomeAfterSaveError() {
        saveErrorBackToHomeLink.click();
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
    }
}
