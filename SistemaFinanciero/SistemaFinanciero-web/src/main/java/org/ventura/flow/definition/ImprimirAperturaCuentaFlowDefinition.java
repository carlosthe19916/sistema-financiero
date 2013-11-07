package org.ventura.flow.definition;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

public class ImprimirAperturaCuentaFlowDefinition implements Serializable{

    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
        String flowId = "imprimirAperturaCuenta-flow";
        flowBuilder.id("", flowId);
        flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").markAsStartNode();
        
        flowBuilder.returnNode("returnFromCustomerFlow").fromOutcome("#{imprimirAperturaCuentaBean.returnValue}");

        flowBuilder.inboundParameter("tipocuenta", "#{flowScope.tipocuenta}")
        .inboundParameter("numerocuenta", "#{flowScope.numerocuenta}")
        .inboundParameter("moneda", "#{flowScope.moneda}")
        .inboundParameter("fechaapertura", "#{flowScope.fechaapertura}")
        
        .inboundParameter("isPersonanatural", "#{flowScope.isPersonanatural}")
        .inboundParameter("isPersonajuridica", "#{flowScope.isPersonajuridica}")
        
        .inboundParameter("dniPersonanatural", "#{flowScope.dniPersonanatural}")
        .inboundParameter("nombrecompletoPersonanatural", "#{flowScope.nombrecompletoPersonanatural}")
        .inboundParameter("sexoPersonanatural", "#{flowScope.sexoPersonanatural}")
        .inboundParameter("fechanacimientoPersonanatural", "#{flowScope.fechanacimientoPersonanatural}")
        
        .inboundParameter("ruc", "#{flowScope.ruc}")
        .inboundParameter("razonsocial", "#{flowScope.razonsocial}")
        .inboundParameter("fechaconstitucion", "#{flowScope.fechaconstitucion}")
        .inboundParameter("dniPersonajuridica", "#{flowScope.dniPersonajuridica}")
        .inboundParameter("nombrecompletoPersonajuridica", "#{flowScope.nombrecompletoPersonajuridica}")
        .inboundParameter("fechanacimientoPersonajuridica", "#{flowScope.fechanacimientoPersonajuridica}")
        .inboundParameter("sexoPersonajuridica", "#{flowScope.sexoPersonajuridica}")

         
        .inboundParameter("titulares", "#{flowScope.titulares}")
        .inboundParameter("beneficiarios", "#{flowScope.beneficiarios}");
        
        return flowBuilder.getFlow();
    }
}
