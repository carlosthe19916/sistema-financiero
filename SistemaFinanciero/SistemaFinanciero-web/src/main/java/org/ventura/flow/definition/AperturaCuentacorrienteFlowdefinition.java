/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ventura.flow.definition;

import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;


public class AperturaCuentacorrienteFlowdefinition implements Serializable {
    
    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
        String flowId = "aperturarCuentacorriente-flow";
        flowBuilder.id("", flowId);
        flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").markAsStartNode();
        
        flowBuilder.returnNode("returnFromAperturarCorrienteFlow").fromOutcome("#{aperturarCuentacorrienteBean.returnValue}");

		flowBuilder.flowCallNode("imprimirAperturaCuenta-flow").flowReference("", "imprimirAperturaCuenta-flow")
		.outboundParameter("tipocuenta", "CUENTA A PLAZO FIJO")
		.outboundParameter("numerocuenta", "#{aperturarCuentacorrienteBean.cuentacorriente.numerocuentacorriente}")
		.outboundParameter("moneda", "#{aperturarCuentacorrienteBean.cuentacorriente.tipomoneda.denominacion}")
		.outboundParameter("fechaapertura", "#{aperturarCuentacorrienteBean.cuentacorriente.fechaapertura}")
					
		.outboundParameter("isPersonanatural", "#{aperturarCuentacorrienteBean.personaNatural}")
		.outboundParameter("isPersonajuridica", "#{aperturarCuentacorrienteBean.personaJuridica}")
		
		.outboundParameter("dniPersonanatural", "#{aperturarCuentacorrienteBean.cuentacorriente.socio.personanatural.dni}")
		.outboundParameter("nombrecompletoPersonanatural", "#{aperturarCuentacorrienteBean.cuentacorriente.socio.personanatural.nombreCompleto}")
		.outboundParameter("sexoPersonanatural", "#{aperturarCuentacorrienteBean.cuentacorriente.socio.personanatural.sexo.denominacion}")
		.outboundParameter("fechanacimientoPersonanatural", "#{aperturarCuentacorrienteBean.cuentacorriente.socio.personanatural.fechanacimiento}")
		
		.outboundParameter("ruc", "#{aperturarCuentacorrienteBean.cuentacorriente.socio.personajuridica.ruc}")
		.outboundParameter("razonsocial", "#{aperturarCuentacorrienteBean.cuentacorriente.socio.personajuridica.razonsocial}")
		.outboundParameter("fechaconstitucion", "#{aperturarCuentacorrienteBean.cuentacorriente.socio.personajuridica.fechaconstitucion}")
		.outboundParameter("dniPersonajuridica", "#{aperturarCuentacorrienteBean.cuentacorriente.socio.personajuridica.personanatural.dni}")
		.outboundParameter("nombrecompletoPersonajuridica", "#{aperturarCuentacorrienteBean.cuentacorriente.socio.personajuridica.personanatural.nombreCompleto}")
		.outboundParameter("fechanacimientoPersonajuridica", "#{aperturarCuentacorrienteBean.cuentacorriente.socio.personajuridica.personanatural.fechanacimiento}")
		.outboundParameter("sexoPersonajuridica", "#{aperturarCuentacorrienteBean.cuentacorriente.socio.personajuridica.personanatural.sexo.denominacion}")
		
		.outboundParameter("titulares", "#{aperturarCuentacorrienteBean.cuentacorriente.titularcuentas}")
		.outboundParameter("beneficiarios", "#{aperturarCuentacorrienteBean.cuentacorriente.beneficiariocuentas}");
       
        return flowBuilder.getFlow();
    }
}