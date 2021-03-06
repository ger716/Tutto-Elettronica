package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.ProdottoManager;
import it.unisa.model.ProdottoManagerDM;

@WebServlet("/VisualizzaDettaglioProdottoControl")
public class VisualizzaDettaglioProdottoControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static ProdottoManager model = new ProdottoManagerDM();
	
    public VisualizzaDettaglioProdottoControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			request.getSession().setAttribute("prodottoCliccato", model.doRetrieveByKey(id, "prodottoinmagazzino"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher view = getServletContext().getRequestDispatcher("/product.jsp");
		view.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
