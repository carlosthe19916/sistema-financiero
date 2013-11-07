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


public class AperturaCuentaplazofijoFlowdefinition implements Serializable {
    
    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
        String flowId = "aperturarCuentaplazofijo-flow";
        flowBuilder.id("", flowId);
        flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").markAsStartNode();
        
        flowBuilder.returnNode("returnFromAperturarCuentaplazofijoFlow").fromOutcome("#{aperturarCuentaPlazofijoBean.returnValue}");

		flowBuilder.flowCallNode("imprimirAperturaCuenta-flow").flowReference("", "imprimirAperturaCuenta-flow")
		.outboundParameter("tipocuenta", "CUENTA A PLAZO FIJO")
		.outboundParameter("numerocuenta", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.numerocuentaplazofijo}")
		.outboundParameter("moneda", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.tipomoneda.denominacion}")
		.outboundParameter("fechaapertura", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.fechaapertura}")
					
		.outboundParameter("isPersonanatural", "#{aperturarCuentaPlazofijoBean.personaNatural}")
		.outboundParameter("isPersonajuridica", "#{aperturarCuentaPlazofijoBean.personaJuridica}")
		
		.outboundParameter("dniPersonanatural", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.socio.personanatural.dni}")
		.outboundParameter("nombrecompletoPersonanatural", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.socio.personanatural.nombreCompleto}")
		.outboundParameter("sexoPersonanatural", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.socio.personanatural.sexo.denominacion}")
		.outboundParameter("fechanacimientoPersonanatural", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.socio.personanatural.fechanacimiento}")
		
		.outboundParameter("ruc", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.socio.personajuridica.ruc}")
		.outboundParameter("razonsocial", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.socio.personajuridica.razonsocial}")
		.outboundParameter("fechaconstitucion", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.socio.personajuridica.fechaconstitucion}")
		.outboundParameter("dniPersonajuridica", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.socio.personajuridica.personanatural.dni}")
		.outboundParameter("nombrecompletoPersonajuridica", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.socio.personajuridica.personanatural.nombreCompleto}")
		.outboundParameter("fechanacimientoPersonajuridica", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.socio.personajuridica.personanatural.fechanacimiento}")
		.outboundParameter("sexoPersonajuridica", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.socio.personajuridica.personanatural.sexo.denominacion}")
		
		.outboundParameter("titulares", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.titularcuentas}")
		.outboundParameter("beneficiarios", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo.beneficiariocuentas}");
       
        return flowBuilder.getFlow();
    }
}