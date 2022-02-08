package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private HomePage home;
	private ResultPage result;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	/**
	 * an unauthorized user can access the login pages.
	 */
	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * an unauthorized user can access the signup pages.
	 */
	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	/**
	 * Verifies that an unauthorized user can only access the login and signup pages.
	 */
	@Test
	public void testBlockUnauthorizedAccess() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible.
	 */
	@Test
	public void testSignupLoginAndLogout() {
		// sign up a new user
		doMockSignUp("Logout","Test","LT","123");
		// log in and home page is accessible
		doLogIn("LT", "123");
		Assertions.assertEquals("Home", driver.getTitle());
		// log out and get redirected to login page
		home = new HomePage(driver);
		home.logout();
		Assertions.assertEquals("Login", driver.getTitle());
		// home page is no longer accessible
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	private void navigateFromResultToHome() {
		result = new ResultPage(driver);
		result.navigateToHomeAfterSuccess();
		home = new HomePage(driver);
	}

	private void navigateFromResultToHomePageNotes() {
		navigateFromResultToHome();
		home.navigateToNotes();
	}

	private void navigateFromResultToHomePageCredentials() {
		navigateFromResultToHome();
		home.navigateToCredentials();
	}

	private void createNote(String noteTitle, String noteDescription) {
		home = new HomePage(driver);
		home.addNewNote(noteTitle, noteDescription);
		navigateFromResultToHomePageNotes();
	}

	private void createCredential(String credentialUrl, String credentialUsername, String credentialPassword) {
		home = new HomePage(driver);
		home.addCredential(credentialUrl, credentialUsername, credentialPassword);
		navigateFromResultToHomePageCredentials();
	}

	private void editNote(WebElement row, String noteTitle, String noteDescription) {
		home.editNote(row, noteTitle, noteDescription);
		navigateFromResultToHomePageNotes();
	}

	private void deleteNote(WebElement row) {
		home.deleteNote(row);
		navigateFromResultToHomePageNotes();
	}

	private void deleteCredential(WebElement row) {
		home.deleteCredential(row);
		navigateFromResultToHomePageNotes();
	}

	private void verifyNoteRow(WebElement row, String noteTitle, String noteDescription) {
		Assertions.assertEquals(noteTitle, row.findElement(By.tagName("th")).getText());
		Assertions.assertEquals(noteDescription, row.findElements(By.tagName("td")).get(1).getText());
	}

	/**
	 * creates a note, and verifies it is displayed
	 */
	@Test
	public void testAddNewNote() {
		doMockSignUp("Add Note", "Test", "ANT", "123");
		doLogIn("ANT", "123");

		createNote("TestNoteTitle", "TestNoteDescription");
		List<WebElement> rows = home.getNoteTableRows();
		Assertions.assertEquals(1, rows.size());
		verifyNoteRow(rows.get(0), "TestNoteTitle", "TestNoteDescription");
	}

	/**
	 * edits an existing note and verifies that the changes are displayed.
	 */
	@Test
	public void testEditNote() {
		doMockSignUp("Edit Note","Test","ENT","123");
		doLogIn("ENT", "123");

		createNote("TestNoteTitle", "TestNoteDescription");
		List<WebElement> rows = home.getNoteTableRows();
		Assertions.assertEquals(1, rows.size());
		verifyNoteRow(rows.get(0), "TestNoteTitle", "TestNoteDescription");

		editNote(rows.get(0), "EditNoteTitle", "EditNoteDescription");
		rows = home.getNoteTableRows();
		Assertions.assertEquals(1, rows.size());
		verifyNoteRow(rows.get(0), "EditNoteTitle", "EditNoteDescription");
	}

	/**
	 * deletes a note and verifies that the note is no longer displayed.
	 */
	@Test
	public void testDeleteNote() {
		doMockSignUp("Delete Note","Test","DNT","123");
		doLogIn("DNT", "123");
		createNote("TestNoteTitle", "TestNoteDescription");
		List<WebElement> rows = home.getNoteTableRows();
		Assertions.assertEquals(1, rows.size());
		verifyNoteRow(rows.get(0), "TestNoteTitle", "TestNoteDescription");

		deleteNote(rows.get(0));
		rows = home.getNoteTableRows();
		Assertions.assertEquals(0, rows.size());
	}

	private void verifyCredentialRow(WebElement row, String credentialUrl, String credentialUsername, String credentialPassword) {
		Assertions.assertEquals(credentialUrl, row.findElement(By.tagName("th")).getText());
		Assertions.assertEquals(credentialUsername, row.findElements(By.tagName("td")).get(1).getText());
		WebElement encryptedPassword = row.findElements(By.tagName("td")).get(2);
		Assertions.assertNotEquals(credentialPassword, encryptedPassword.getText());
	}

	private void verifyCredentialModal(String credentialUrl, String credentialUsername, String credentialPassword) {
		Assertions.assertEquals(credentialUrl, home.getCredentialUrlInputValue());
		Assertions.assertEquals(credentialUsername, home.getCredentialUsernameInputValue());
		Assertions.assertEquals(credentialPassword, home.getCredentialPasswordInputValue());
	}

	/**
	 * creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
	 */
	@Test
	public void testAddNewCredential() {
		doMockSignUp("Add Credential","Test","ACT","123");
		doLogIn("ACT", "123");
		createCredential("testCredentialUrl.com", "testCredentialUsername", "testCredentialPassword");
		List<WebElement> rows = home.getCredentialTableRows();
		Assertions.assertEquals(1, rows.size());
		verifyCredentialRow(rows.get(0), "testCredentialUrl.com", "testCredentialUsername", "testCredentialPassword");
	}

	/**
	 * views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed
	 */
	@Test
	public void testEditCredential() {
		doMockSignUp("Edit Credential","Test","ECT","123");
		doLogIn("ECT", "123");
		createCredential("testCredentialUrl.com", "testCredentialUsername", "testCredentialPassword");
		List<WebElement> rows = home.getCredentialTableRows();
		Assertions.assertEquals(1, rows.size());
		home.openEditCredentialModal(rows.get(0));
		verifyCredentialModal("testCredentialUrl.com", "testCredentialUsername", "testCredentialPassword");
		home.setCredential("google.com", "ECT", "123");
		home.saveCredential();
		navigateFromResultToHomePageCredentials();
		rows = home.getCredentialTableRows();
		Assertions.assertEquals(1, rows.size());
		verifyCredentialRow(rows.get(0), "google.com", "ECT", "123");
		home.openEditCredentialModal(rows.get(0));
		verifyCredentialModal("google.com", "ECT", "123");
	}

	/**
	 * deletes an existing set of credentials and verifies that the credentials are no longer displayed.
	 */
	@Test
	public void testDeleteCredential() {
		doMockSignUp("Delete Credential","Test","DCT","123");
		doLogIn("DCT", "123");
		createCredential("testCredentialUrl.com", "testCredentialUsername", "testCredentialPassword");
		List<WebElement> rows = home.getCredentialTableRows();
		Assertions.assertEquals(1, rows.size());
		deleteCredential(rows.get(0));

		rows = home.getCredentialTableRows();
		Assertions.assertEquals(0, rows.size());
	}
}
