package org.ventura.cuentapersonal.flow.definition;

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
		String flowId = "aperturaCuentaahorro-flow";
		flowBuilder.id("", flowId);
		flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml")
				.markAsStartNode();

		flowBuilder.returnNode("returnFromAperturaCuentaahorroFlow")
				.fromOutcome("aperturaCuentaahorro-flowC");

		return flowBuilder.getFlow();
	}
}
