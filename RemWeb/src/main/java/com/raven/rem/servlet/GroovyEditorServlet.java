/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.servlet;

import com.raven.rem.bean.GroovyEditorBean;
import com.raven.rem.common.util.StringUtil;
import com.raven.rem.entity.PropertyCode;
import com.raven.rem.service.PropertyService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mehmet Adem Sengul
 */
@WebServlet(name = "groovyEditorServlet", urlPatterns = {"/groovyEditorServlet"})
public class GroovyEditorServlet extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String process = (request.getParameter("process") == null ? "" : request.getParameter("process"));
		String text = (request.getParameter("text") == null ? "" : request.getParameter("text"));

		if (text.trim().equals("")) {
			text = text.trim();
		} else {
			text = StringUtil.rtrim(text);
		}

		if (request.getSession(true).getAttribute("groovyEditorBean") == null) {
			writeToOutput(response, "");
			return;
		}

		GroovyEditorBean grovvyEditorBean = (GroovyEditorBean) request.getSession(true).getAttribute("groovyEditorBean");

		if (process.equals("setText")) {
			//grovvyEditorBean.setEditorText(editorText);
			grovvyEditorBean.getWorkingCopyScript().setScriptText(text);
		} else if (process.equals("getText")) {
			if (grovvyEditorBean.getWorkingCopyScript() == null) {
				text = "";
			} else {
				text = (grovvyEditorBean.getWorkingCopyScript().getScriptText() == null ? "" : grovvyEditorBean.getWorkingCopyScript().getScriptText());
			}
		} else if (process.equals("autocomplete")) {
			text = codeComplete(text);
		}

		writeToOutput(response, text);
	}

	private String codeComplete(String searchText) {
		StringBuilder jsonResult = new StringBuilder();

		try {
			PropertyService propertyService = (PropertyService) getService("propertyService");
			List<PropertyCode> propertyCodeList = propertyService.retrievePropertyCodesByDescription(searchText);
			jsonResult.append("[");
			for (PropertyCode propertyCode : propertyCodeList) {
				if (!jsonResult.toString().trim().equals("[")) {
					jsonResult.append(",");
				}

				String rowTemplate = "{\"word\":\"<word>\",\"description\":\"<description>\",\"score\":<score>}";
				rowTemplate = rowTemplate.replaceAll("<word>", propertyCode.getCode());
				rowTemplate = rowTemplate.replaceAll("<description>", propertyCode.getDescription());
				rowTemplate = rowTemplate.replaceAll("<score>", "1");
				jsonResult.append(rowTemplate);
			}
			jsonResult.append("]");
		} catch (Exception ex) {
			Logger.getLogger(GroovyEditorServlet.class.getName()).log(Level.SEVERE, null, ex);
		}

		return jsonResult.toString();
	}

	private Object getService(String serviceName) throws NamingException {
		Context context = new InitialContext();
		return context.lookup("java:app/IremService-1.0/" + serviceName);

	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

	protected void writeToOutput(HttpServletResponse response, String text) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			out.println(text);
		} finally {
			out.close();
		}
	}
}
