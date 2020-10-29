package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	
	private WebDriver driver;
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	private By email = By.name("email");
	private By senha = By.name("password");
	private By botaoSignIn = By.id("submit-login");
	
	public void preencherEmail(String texto) {
		driver.findElement(email).sendKeys(texto);
	}
	
	public void preencherSenha(String texto) {
		driver.findElement(senha).sendKeys(texto);
	}
	
	public void clicarBotaoSignIn() {
		driver.findElement(botaoSignIn).click();
	}

}