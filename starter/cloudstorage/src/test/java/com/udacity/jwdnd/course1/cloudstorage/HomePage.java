package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {
    private final WebDriverWait webDriverWait;
    public HomePage(WebDriver webDriver) {
        webDriverWait = new WebDriverWait(webDriver, 2);
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesNavigationTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsNavigationTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionTextArea;

    @FindBy(id = "note-submit-button")
    private WebElement noteSubmitButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlInput;

    public String getCredentialUrlInputValue() {
        return credentialUrlInput.getAttribute("value");
    }

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameInput;

    public String getCredentialUsernameInputValue() {
        return credentialUsernameInput.getAttribute("value");
    }

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordInput;

    public String getCredentialPasswordInputValue() {
        return credentialPasswordInput.getAttribute("value");
    }

    public String getCredentialPasswordInputType() {
        return credentialPasswordInput.getAttribute("type");
    }

    @FindBy(id = "credential-submit-button")
    private WebElement credentialSubmitButton;

    @FindBy(id = "noteTable")
    private WebElement noteTable;

    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

    public void logout() {
        logoutButton.click();
        webDriverWait.until(ExpectedConditions.titleContains("Login"));
    }

    public void navigateToNotes() {
        notesNavigationTab.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes")));
    }

    public void navigateToCredentials() {
        credentialsNavigationTab.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials")));
    }

    public List<WebElement> getNoteTableRows() {
        return noteTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
    }

    public List<WebElement> getCredentialTableRows() {
        return credentialTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
    }

    public void openAddNoteModal() {
        addNoteButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));
    }

    public void openAddCredentialModal() {
        addCredentialButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));
    }

    public void openEditNoteModal(WebElement row) {
        WebElement editButton = row.findElement(By.tagName("td")).findElement(By.tagName("button"));
        editButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));
    }

    public void openEditCredentialModal(WebElement row) {
        WebElement editButton = row.findElement(By.tagName("td")).findElement(By.tagName("button"));
        editButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));
    }

    public void saveNote() {
        noteSubmitButton.click();
        webDriverWait.until(ExpectedConditions.titleContains("Result"));
    }

    public void saveCredential() {
        credentialSubmitButton.click();
        webDriverWait.until(ExpectedConditions.titleContains("Result"));
    }

    private void setNote(String noteTitle, String noteDescription) {
        // type in note title
        noteTitleInput.click();
        noteTitleInput.clear();
        noteTitleInput.sendKeys(noteTitle);
        // type in note description
        noteDescriptionTextArea.click();
        noteDescriptionTextArea.clear();
        noteDescriptionTextArea.sendKeys(noteDescription);
    }

    public void addNewNote(String noteTitle, String noteDescription) {
        navigateToNotes();
        openAddNoteModal();
        setNote(noteTitle, noteDescription);
        saveNote();
    }

    public void setCredential(String credentialUrl, String credentialUsername, String credentialPassword){
        // type in credential url
        credentialUrlInput.click();
        credentialUrlInput.clear();
        credentialUrlInput.sendKeys(credentialUrl);
        // type in credential username
        credentialUsernameInput.click();
        credentialUsernameInput.clear();
        credentialUsernameInput.sendKeys(credentialUsername);
        // type in credential password
        credentialPasswordInput.click();
        credentialPasswordInput.clear();
        credentialPasswordInput.sendKeys(credentialPassword);
    }

    public void addCredential(String credentialUrl, String credentialUsername, String credentialPassword) {
        navigateToCredentials();
        openAddCredentialModal();
        setCredential(credentialUrl, credentialUsername, credentialPassword);
        saveCredential();
    }

    public void editNote(WebElement row, String noteTitle, String noteDescription) {
        openEditNoteModal(row);
        setNote(noteTitle, noteDescription);
        saveNote();
    }

    public void deleteNote(WebElement row) {
        WebElement deleteLink = row.findElement(By.tagName("td")).findElement(By.tagName("a"));
        deleteLink.click();
        webDriverWait.until(ExpectedConditions.titleContains("Result"));
    }

    public void deleteCredential(WebElement row) {
        WebElement deleteLink = row.findElement(By.tagName("td")).findElement(By.tagName("a"));
        deleteLink.click();
        webDriverWait.until(ExpectedConditions.titleContains("Result"));
    }
}
