package cn.sh.ideal.util;

/**
 * <p>Title: </p>
 * <p>Description: 2007-01</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @version 1.0
 */
import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class SetCharacterEncodingFilter
    implements Filter {
  protected String encoding = null;
  protected FilterConfig filterConfig = null;
  protected boolean ignore = true;
  private Pattern scriptP;
  private Pattern sqlP;
  private Pattern notP;

  public void destroy() {
    this.encoding = null;
    this.filterConfig = null;
  }

  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain) throws IOException, ServletException {
	
	if (ignore || (request.getCharacterEncoding() == null)) {
		String encodings = selectEncoding(request);
	    if (encodings != null){
	        request.setCharacterEncoding(encodings);
	    }
	}

	HttpServletRequest req = (HttpServletRequest)request;
	String url = req.getRequestURI();
	
	if(!notP.matcher(url).find()){
		Enumeration params = req.getParameterNames();
		
		boolean isSecurity = true;
		while(params.hasMoreElements()){
			String para_name = (String)params.nextElement();
			String[] para_value = (String[])req.getParameterValues(para_name);
			isSecurity=checkMatcher(para_value,isSecurity);
			if(!isSecurity){ break;}
		}
		if(!isSecurity){
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write("<script language='javascript'>alert('输入有误!');history.go(-1);</script>");
			return;
		}
	}
	
	chain.doFilter(request, response);

  }
  
  public boolean checkMatcher(String[] para_value,boolean isSecurity){
	  for(int i=0 ; i<para_value.length ; i++){
			String _para_value = para_value[i].toLowerCase();
			if(scriptP.matcher(_para_value).matches()
					||sqlP.matcher(_para_value).matches()){
				isSecurity = false;
				break;
			}
		}
	  return isSecurity;
  }

  public void init(FilterConfig filterConfig) throws ServletException {

    this.filterConfig = filterConfig;
    this.encoding = filterConfig.getInitParameter("encoding");
    
    String scriptPara = filterConfig.getInitParameter("scriptRegx");
	String sqlPara = filterConfig.getInitParameter("sqlRegx");
	String notProtect = filterConfig.getInitParameter("notProtect");
	scriptP = Pattern.compile(scriptPara);
	sqlP = Pattern.compile(sqlPara);
	notP = Pattern.compile(notProtect);
	
    String value = filterConfig.getInitParameter("ignore");
    if (value == null){
      this.ignore = true;
    }else if ("true".equalsIgnoreCase(value)){
      this.ignore = true;
    }else if ("yes".equalsIgnoreCase(value)){
      this.ignore = true;
    }else{
      this.ignore = false;
    }
  }

  protected String selectEncoding(ServletRequest request) {
    return this.encoding;
  }
}