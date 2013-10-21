/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ventura.flow;

import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;


public class AperturaCuentaahorro implements Serializable {
    
    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
        String flowId = "aperturarCuentaahorro-flow";
        flowBuilder.id("", flowId);
        flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").markAsStartNode();
        
        flowBuilder.returnNode("returnFromCustomerFlow").fromOutcome("#{aperturaCuentaahorroBean.returnValue}");
       
        flowBuilder.inboundParameter("cuentaahorro", "#{aperturaCuentaahorroBean.cuentaahorro}");
       
        return flowBuilder.getFlow();
    }
}