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


public class AperturaCuentaahorroFlowdefinition implements Serializable {
    
    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
        String flowId = "aperturarCuentaahorro-flow";
        flowBuilder.id("", flowId);
        flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").markAsStartNode();
        
        flowBuilder.flowCallNode("imprimirAperturaCuenta-flow").flowReference("", "imprimirAperturaCuenta-flow")
		.outboundParameter("tipocuenta", "CUENTA DE AHORRO")
		.outboundParameter("numerocuenta", "#{aperturaCuentaahorroBean.cuentaahorro.numerocuentaahorro}")
		.outboundParameter("moneda", "#{aperturaCuentaahorroBean.cuentaahorro.tipomoneda.denominacion}")
		.outboundParameter("fechaapertura", "#{aperturaCuentaahorroBean.cuentaahorro.fechaapertura}")
					
		.outboundParameter("isPersonanatural", "#{aperturaCuentaahorroBean.personaNatural}")
		.outboundParameter("isPersonajuridica", "#{aperturaCuentaahorroBean.personaJuridica}")
		
		.outboundParameter("dniPersonanatural", "#{aperturaCuentaahorroBean.cuentaahorro.socio.personanatural.dni}")
		.outboundParameter("nombrecompletoPersonanatural", "#{aperturaCuentaahorroBean.cuentaahorro.socio.personanatural.nombreCompleto}")
		.outboundParameter("sexoPersonanatural", "#{aperturaCuentaahorroBean.cuentaahorro.socio.personanatural.sexo.denominacion}")
		.outboundParameter("fechanacimientoPersonanatural", "#{aperturaCuentaahorroBean.cuentaahorro.socio.personanatural.fechanacimiento}")
		
		.outboundParameter("ruc", "#{aperturaCuentaahorroBean.cuentaahorro.socio.personajuridica.ruc}")
		.outboundParameter("razonsocial", "#{aperturaCuentaahorroBean.cuentaahorro.socio.personajuridica.razonsocial}")
		.outboundParameter("fechaconstitucion", "#{aperturaCuentaahorroBean.cuentaahorro.socio.personajuridica.fechaconstitucion}")
		.outboundParameter("dniPersonajuridica", "#{aperturaCuentaahorroBean.cuentaahorro.socio.personajuridica.personanatural.dni}")
		.outboundParameter("nombrecompletoPersonajuridica", "#{aperturaCuentaahorroBean.cuentaahorro.socio.personajuridica.personanatural.nombreCompleto}")
		.outboundParameter("fechanacimientoPersonajuridica", "#{aperturaCuentaahorroBean.cuentaahorro.socio.personajuridica.personanatural.fechanacimiento}")
		.outboundParameter("sexoPersonajuridica", "#{aperturaCuentaahorroBean.cuentaahorro.socio.personajuridica.personanatural.sexo.denominacion}")
		
		.outboundParameter("titulares", "#{aperturaCuentaahorroBean.cuentaahorro.titularcuentas}")
		.outboundParameter("beneficiarios", "#{aperturaCuentaahorroBean.cuentaahorro.beneficiariocuentas}");

        return flowBuilder.getFlow();
    }
}