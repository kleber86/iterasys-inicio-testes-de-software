package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
	
	private WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
	}
	
	private By totalTaxIncTotal = By.cssSelector("div.cart-total span.value");

	public String obter_totalTaxIncTotal(){
		return driver.findElement(totalTaxIncTotal).getText();
	}
}
