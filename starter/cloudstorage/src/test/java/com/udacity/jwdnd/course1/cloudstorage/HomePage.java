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
    private WebDriverWait webDriverWait;
    public HomePage(WebDriver webDriver) {
        webDriverWait = new WebDriverWait(webDriver, 2);
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesNavigationTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionTextArea;

    @FindBy(id = "note-submit-button")
    private WebElement noteSubmitButton;

    @FindBy(id = "noteTable")
    private WebElement noteTable;

    public void logout() {
        logoutButton.click();
        webDriverWait.until(ExpectedConditions.titleContains("Login"));
    }

    public void navigateToNotes() {
        notesNavigationTab.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes")));
    }

    public List<WebElement> getNoteTableRows() {
        return noteTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
    }

    public void openAddNoteModal() {
        addNoteButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));
    }

    public void openEditNoteModel(WebElement row) {
        WebElement editButton = row.findElement(By.tagName("td")).findElement(By.tagName("button"));
        editButton.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteModal")));
    }

    public void saveNote() {
        noteSubmitButton.click();
        webDriverWait.until(ExpectedConditions.titleContains("Result"));
    }

    public void addNewNote(String noteTitle, String noteDescription) {
        navigateToNotes();
        openAddNoteModal();
        // type in note title
        noteTitleInput.click();
        noteTitleInput.sendKeys(noteTitle);
        // type in note description
        noteDescriptionTextArea.click();
        noteDescriptionTextArea.sendKeys(noteDescription);
        // submit note
        saveNote();
    }

    public void editNote(WebElement row, String noteTitle, String noteDescription) {
        openEditNoteModel(row);
        // type in note title
        noteTitleInput.click();
        noteTitleInput.clear();
        noteTitleInput.sendKeys(noteTitle);
        // type in note description
        noteDescriptionTextArea.click();
        noteDescriptionTextArea.clear();
        noteDescriptionTextArea.sendKeys(noteDescription);
        // submit note
        saveNote();
    }

    public void deleteNote(WebElement row) {
        WebElement deleteLink = row.findElement(By.tagName("td")).findElement(By.tagName("a"));
        deleteLink.click();
        webDriverWait.until(ExpectedConditions.titleContains("Result"));
    }
}
