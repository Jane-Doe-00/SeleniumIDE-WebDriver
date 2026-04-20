package org;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class EnhancedTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        // Setup driver (Ensure chromedriver is in your PATH)
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // safety net
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Helper Methods ---

    // --- login ---
    private void login(String user, String pass) {
        driver.get("https://www.saucedemo.com/");
        findElement(By.id("user-name")).sendKeys(user);
        findElement(By.id("password")).sendKeys(pass);
        findElement(By.id("login-button")).click();
    }

    // --- wait until the element appear ---
    private WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // --- Test Cases ---

    //add item to cart
    @Test
    public void AddItemToCart() {
        login("standard_user", "secret_sauce");
        findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")).click();

        // 2. Optimization: Verify Badge Count (State Check)
        String badgeCount = findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals("Cart badge count is wrong!", "1", badgeCount);

        // click the cart
        findElement(By.cssSelector("[data-test='shopping-cart-link']")).click();

        // 4. Optimization: List Handling & Content Verification
        List<WebElement> elements = driver.findElements(By.cssSelector("[data-test='inventory-item-name']"));
        Assert.assertTrue("The cart is empty!", elements.size() > 0);

        // Get the name of the first item in the cart
        String labelName = elements.get(0).getText();

        // Verify the product name
        Assert.assertEquals("Product name in cart doesn't match!", "Sauce Labs Backpack", labelName);
    }

    //logout
    @Test
    public void Logout() {
        // login
        login("problem_user", "secret_sauce");
        // click the menu button
        findElement(By.id("react-burger-menu-btn")).click();
        // click logout button
        findElement(By.cssSelector("*[data-test=\"logout-sidebar-link\"]")).click();
        // find login button
        Assert.assertTrue("Login button is not visible!", findElement(By.id("login-button")).isDisplayed());
    }

    // --- Another test --- //



    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}