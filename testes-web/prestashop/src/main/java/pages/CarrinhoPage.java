package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CarrinhoPage {
	
	private WebDriver driver;
	
	public CarrinhoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	private By nomeProduto = By.cssSelector(".product-line-info a");
	private By precoProduto = By.cssSelector("span.price");
	//private By tamanhoProduto = By ("");

}
