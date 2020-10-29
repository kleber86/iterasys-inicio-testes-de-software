package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class ModalProdutoPage {
	private WebDriver driver;
	
	public ModalProdutoPage(WebDriver driver) {
		this.driver = driver;
	}

	private By mensagemProdutoAdicionado = By.id("myModalLabel");
	
	public String obterMensagemProdutoAdicionado() {
		FluentWait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(5))
				.pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchFieldException.class);
		
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(mensagemProdutoAdicionado));
		return driver.findElement(mensagemProdutoAdicionado).getText();
	}

}
