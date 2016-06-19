package com.rotty3000.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class EJBConverter
 */
@Stateless
@LocalBean
class EJBConverter implements EJBConverterLocal {

	@Override
	public double convertToCelsius(double fahrenheit) {
		return (fahrenheit - 32) * (5 / 9d);
	}

	@Override
	public double convertToFahrenheit(double celsius) {
		return celsius * (9 / 5d) + 32;
	}

}
