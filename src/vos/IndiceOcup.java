package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class IndiceOcup {

	@JsonProperty("idApto")
	Integer idApto;
	
	@JsonProperty("indice")
	Double indice;
	
	public IndiceOcup(@JsonProperty("idApto") Integer idApto, @JsonProperty("indice") Double indice)
	{
		this.idApto=idApto;
		this.indice=indice;
	}

	public Integer getIdApto() {
		return idApto;
	}

	public void setIdApto(Integer idApto) {
		this.idApto = idApto;
	}

	public Double getIndice() {
		return indice;
	}

	public void setIndice(Double indice) {
		this.indice = indice;
	}
	
	
	
}
