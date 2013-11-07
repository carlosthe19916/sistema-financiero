package org.ventura.flow.definition;

import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowCallNode;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowCallBuilder;
import javax.faces.flow.builder.FlowDefinition;
import javax.inject.Inject;

import org.ventura.entity.schema.cuentapersonal.Titularcuenta;
import org.ventura.flow.AperturarCuentaaporteBean;

public class AperturarCuentaaporteFlowdefinition implements Serializable {
	
	@Produces
	@FlowDefinition
	public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
		String flowId = "aperturarCuentaaporte-flow";
		flowBuilder.id("", flowId);
		flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").markAsStartNode();

		flowBuilder.returnNode("returnFromCustomerFlow").fromOutcome("#{aperturarCuentaaporteBean.returnValue}");

		flowBuilder.flowCallNode("imprimirAperturaCuenta-flow").flowReference("", "imprimirAperturaCuenta-flow")
		.outboundParameter("tipocuenta", "CUENTA DE APORTES")
		.outboundParameter("numerocuenta", "#{aperturarCuentaaporteBean.socio.cuentaaporte.numerocuentaaporte}")
		.outboundParameter("moneda", "#{aperturarCuentaaporteBean.socio.cuentaaporte.tipomoneda.denominacion}")
		.outboundParameter("fechaapertura", "#{aperturarCuentaaporteBean.socio.cuentaaporte.fechaapertura}")
					
		.outboundParameter("isPersonanatural", "#{aperturarCuentaaporteBean.personaNatural}")
		.outboundParameter("isPersonajuridica", "#{aperturarCuentaaporteBean.personaJuridica}")
		
		.outboundParameter("dniPersonanatural", "#{aperturarCuentaaporteBean.socio.personanatural.dni}")
		.outboundParameter("nombrecompletoPersonanatural", "#{aperturarCuentaaporteBean.socio.personanatural.nombreCompleto}")
		.outboundParameter("sexoPersonanatural", "#{aperturarCuentaaporteBean.socio.personanatural.sexo.denominacion}")
		.outboundParameter("fechanacimientoPersonanatural", "#{aperturarCuentaaporteBean.socio.personanatural.fechanacimiento}")
		
		.outboundParameter("ruc", "#{aperturarCuentaaporteBean.socio.personajuridica.ruc}")
		.outboundParameter("razonsocial", "#{aperturarCuentaaporteBean.socio.personajuridica.razonsocial}")
		.outboundParameter("fechaconstitucion", "#{aperturarCuentaaporteBean.socio.personajuridica.fechaconstitucion}")
		.outboundParameter("dniPersonajuridica", "#{aperturarCuentaaporteBean.socio.personajuridica.personanatural.dni}")
		.outboundParameter("nombrecompletoPersonajuridica", "#{aperturarCuentaaporteBean.socio.personajuridica.personanatural.nombreCompleto}")
		.outboundParameter("fechanacimientoPersonajuridica", "#{aperturarCuentaaporteBean.socio.personajuridica.personanatural.fechanacimiento}")
		.outboundParameter("sexoPersonajuridica", "#{aperturarCuentaaporteBean.socio.personajuridica.personanatural.sexo.denominacion}")
		
		.outboundParameter("beneficiarios", "#{aperturarCuentaaporteBean.socio.cuentaaporte.beneficiarios}");
			
		return flowBuilder.getFlow();
	}
}
