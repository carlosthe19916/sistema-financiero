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
        
        flowBuilder.returnNode("returnFromCustomerFlow").fromOutcome("#{aperturarCuentaPlazofijoBean.returnValue}");
       
        flowBuilder.inboundParameter("cuentaplazofijo", "#{aperturarCuentaPlazofijoBean.cuentaplazofijo}");
       
        return flowBuilder.getFlow();
    }
}