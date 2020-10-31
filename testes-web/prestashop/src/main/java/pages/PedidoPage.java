package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import util.Funcoes;

public class PedidoPage {
	private WebDriver driver;
	
	public PedidoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	private By textoPedidoConfirmado = By.cssSelector("#content-hook_order_confirmation h3");
	private By email = By.cssSelector("#content-hook_order_confirmation p");
	private By totalProdutos = By.cssSelector(".order-confirmation-table div.bold");
	private By totalTaxIncl = By.cssSelector(".total-value td:nth-child(2)");
	private By metodoPagemento = By.cssSelector("#order-details li:nth-child(2)");
	
	public String obter_textoPedidoConfirmado() {
		return driver.findElement(textoPedidoConfirmado).getText();
	}
	public String obter_email() {
		String texto = driver.findElement(email).getText();
		texto = Funcoes.removeTexto(texto, "An email has been sent to the ");
		texto = Funcoes.removeTexto(texto, " address.");
		return texto;
	}
	
	public Double obter_totalProdutos() {
		return Funcoes.removeCifraoDevolveDouble(driver.findElement(totalProdutos).getText());
	}
	
	public Double obter_totalTaxIncl() {
		return Funcoes.removeCifraoDevolveDouble(driver.findElement(totalTaxIncl).getText());
	}
	
	public String obter_metodoPagemento() {
		return Funcoes.removeTexto(driver.findElement(metodoPagemento).getText(), "Payment method: Payments by ");
	}

}
