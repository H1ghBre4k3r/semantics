/*
 * generated by Xtext 2.10.0
 */
package de.cau.cs.kieler.cview.model.serializer;

import com.google.inject.Inject;
import de.cau.cs.kieler.cview.model.services.CViewModelGrammarAccess;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.GroupAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class CViewModelSyntacticSequencer extends AbstractSyntacticSequencer {

	protected CViewModelGrammarAccess grammarAccess;
	protected AbstractElementAlias match_Component___ChildrenKeyword_22_0_EqualsSignKeyword_22_1__q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (CViewModelGrammarAccess) access;
		match_Component___ChildrenKeyword_22_0_EqualsSignKeyword_22_1__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getComponentAccess().getChildrenKeyword_22_0()), new TokenAlias(false, false, grammarAccess.getComponentAccess().getEqualsSignKeyword_22_1()));
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		return "";
	}
	
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if (match_Component___ChildrenKeyword_22_0_EqualsSignKeyword_22_1__q.equals(syntax))
				emit_Component___ChildrenKeyword_22_0_EqualsSignKeyword_22_1__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Ambiguous syntax:
	 *     ('children' '=')?
	 *
	 * This ambiguous syntax occurs at:
	 *     location=STRING (ambiguity) 'tooltip' tooltip=STRING
	 *     referenceLine=INT (ambiguity) 'tooltip' tooltip=STRING
	 */
	protected void emit_Component___ChildrenKeyword_22_0_EqualsSignKeyword_22_1__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}