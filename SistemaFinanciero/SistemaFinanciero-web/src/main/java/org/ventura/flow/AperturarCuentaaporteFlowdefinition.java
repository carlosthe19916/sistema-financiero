package org.ventura.flow;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

public class AperturarCuentaaporteFlowdefinition implements Serializable{

    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
        String flowId = "aperturarCuentaaporte-flow";
        flowBuilder.id("", flowId);
        flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").markAsStartNode();
        
        flowBuilder.returnNode("returnFromCustomerFlow").fromOutcome("#{aperturarCuentaaporteBean.returnValue}");

        flowBuilder.inboundParameter("cuentaaporte", "#{aperturarCuentaaporteBean.cuentaaporte}");

        return flowBuilder.getFlow();
    }
}
