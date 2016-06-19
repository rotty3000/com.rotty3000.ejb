package com.rotty3000.ejbclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

import com.rotty3000.ejb.EJBConverterLocal;

@Component(
	property = {
		HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_PATTERN +
			"=/ConverterServlet"
	},
	service = Servlet.class
)
public class ConverterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		PrintWriter w = response.getWriter();

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);

		try {
			InitialContext context = new InitialContext();
			EJBConverterLocal converter = (EJBConverterLocal)context.lookup(
				"osgi:service/" + EJBConverterLocal.class.getName());

			String temperature = request.getParameter("temperature");

			if (temperature == null) {
				w.println("<p>A temperature value was not specified.</p>");
			}
			else if (!temperature.matches("[-+]?[0-9]*\\.?[0-9]+")) {
				w.println("Invalid temperature specified.");
			}
			else {
				double degrees = Double.parseDouble(temperature);

				double celsius = converter.convertToCelsius(degrees);

				w.println(
					"<p>" + temperature + " degrees fahrenheit is " + nf.format(celsius) +
						" degrees celsius</p>");

				double fahrenheit = converter.convertToFahrenheit(degrees);

				w.println("<p>" + temperature + " degrees celsius is " + nf.format(fahrenheit) + " degrees fahrenheit</p>");
			}

			w.println("<a href='index.html'>Back</a>");
		}
		catch (NamingException e) {
			w.println(e.getMessage());
		}
		catch (NumberFormatException e) {
			w.println("An incorrect temperature was specified");
		}
	}

	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		doGet(request, response);
	}

}