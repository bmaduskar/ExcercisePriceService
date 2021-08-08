package mytrader.model;

import java.util.Objects;

public class Security {
	
	  private String securityName;
	  private int volume=100;
	  private double price;
	  
	  public Security(String securityName) {
		super();
		this.securityName = securityName;
	  }

	public String getSecurity() {
		return securityName;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(securityName);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Security other = (Security) obj;
		return Objects.equals(securityName, other.securityName);
	}
	
	
	
}
