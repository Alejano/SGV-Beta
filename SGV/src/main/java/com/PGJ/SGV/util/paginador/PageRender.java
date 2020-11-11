package com.PGJ.SGV.util.paginador;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;

public class PageRender<T> {
		
	private String url;
	private Page<T> page;
	
	private int totalPaginas;
	private int numElementos;
	private int paginaActual;
	
	private List<pageItem> paginas;
	
	public PageRender(String url,Page<T> page) {
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<pageItem>();
		
		numElementos = page.getSize();
		totalPaginas = page.getTotalPages();
		paginaActual = page.getNumber() + 1;
		
		int desde,hasta;
		if(totalPaginas <= numElementos) {
			desde =1;
			hasta = totalPaginas;
		}else {
			if(paginaActual <= numElementos/2) {
				desde = 1;
				hasta = numElementos;
			}else if(paginaActual >= totalPaginas - numElementos/2) {
				desde = totalPaginas - numElementos+1;
				hasta = numElementos;
			}else {
				desde= paginaActual - numElementos/2;
				hasta = numElementos;
			}												
		}
		
		for(int i=0; i<hasta; i++) {
			paginas.add(new pageItem(desde + i, paginaActual == desde+i));
		}				
		
	}
		

	public String getUrl() {
		return url;
	}	

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public List<pageItem> getPaginas() {
		return paginas;
	}
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
	
			
}
