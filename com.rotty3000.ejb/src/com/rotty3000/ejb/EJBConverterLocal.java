package com.rotty3000.ejb;

import javax.ejb.Local;

@Local
public interface EJBConverterLocal {

	public double convertToFahrenheit(double celsius);
	public double convertToCelsius(double fahrenheit);

}
