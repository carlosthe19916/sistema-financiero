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
        
        flowBuilder.returnNode("returnFromCustomerFlow").fromOutcome("#{aperturarCuentacorrienteBean.returnValue}");
       
        flowBuilder.inboundParameter("cuentacorriente", "#{aperturarCuentacorrienteBean.cuentacorriente}");
       
        return flowBuilder.getFlow();
    }
}