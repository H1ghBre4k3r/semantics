/*
 * generated by Xtext 2.10.0
 */
package de.cau.cs.kieler.ehelp.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.common.services.TerminalsGrammarAccess;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractGrammarElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class EHelpGrammarAccess extends AbstractGrammarElementFinder {
	
	public class EHelpModelElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.cau.cs.kieler.ehelp.EHelp.EHelpModel");
		private final Assignment cChaptersAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cChaptersChapterParserRuleCall_0 = (RuleCall)cChaptersAssignment.eContents().get(0);
		
		//EHelpModel:
		//	chapters+=Chapter*;
		@Override public ParserRule getRule() { return rule; }
		
		//chapters+=Chapter*
		public Assignment getChaptersAssignment() { return cChaptersAssignment; }
		
		//Chapter
		public RuleCall getChaptersChapterParserRuleCall_0() { return cChaptersChapterParserRuleCall_0; }
	}
	public class ChapterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.cau.cs.kieler.ehelp.EHelp.Chapter");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cChapterKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Assignment cTitleAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cTitleSTRINGTerminalRuleCall_2_0 = (RuleCall)cTitleAssignment_2.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cContentAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cContentContentParserRuleCall_4_0 = (RuleCall)cContentAssignment_4.eContents().get(0);
		private final Assignment cSubchaptersAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cSubchaptersChapterParserRuleCall_5_0 = (RuleCall)cSubchaptersAssignment_5.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_6 = (Keyword)cGroup.eContents().get(6);
		
		//Chapter:
		//	'chapter' name=ID? title=STRING '{' content+=Content* subchapters+=Chapter* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//'chapter' name=ID? title=STRING '{' content+=Content* subchapters+=Chapter* '}'
		public Group getGroup() { return cGroup; }
		
		//'chapter'
		public Keyword getChapterKeyword_0() { return cChapterKeyword_0; }
		
		//name=ID?
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }
		
		//title=STRING
		public Assignment getTitleAssignment_2() { return cTitleAssignment_2; }
		
		//STRING
		public RuleCall getTitleSTRINGTerminalRuleCall_2_0() { return cTitleSTRINGTerminalRuleCall_2_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_3() { return cLeftCurlyBracketKeyword_3; }
		
		//content+=Content*
		public Assignment getContentAssignment_4() { return cContentAssignment_4; }
		
		//Content
		public RuleCall getContentContentParserRuleCall_4_0() { return cContentContentParserRuleCall_4_0; }
		
		//subchapters+=Chapter*
		public Assignment getSubchaptersAssignment_5() { return cSubchaptersAssignment_5; }
		
		//Chapter
		public RuleCall getSubchaptersChapterParserRuleCall_5_0() { return cSubchaptersChapterParserRuleCall_5_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_6() { return cRightCurlyBracketKeyword_6; }
	}
	public class ListElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.cau.cs.kieler.ehelp.EHelp.List");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cListKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cItemsAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cItemsListItemParserRuleCall_2_0 = (RuleCall)cItemsAssignment_2.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//List:
		//	'list' '{' items+=ListItem* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//'list' '{' items+=ListItem* '}'
		public Group getGroup() { return cGroup; }
		
		//'list'
		public Keyword getListKeyword_0() { return cListKeyword_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_1() { return cLeftCurlyBracketKeyword_1; }
		
		//items+=ListItem*
		public Assignment getItemsAssignment_2() { return cItemsAssignment_2; }
		
		//ListItem
		public RuleCall getItemsListItemParserRuleCall_2_0() { return cItemsListItemParserRuleCall_2_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_3() { return cRightCurlyBracketKeyword_3; }
	}
	public class EnumElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.cau.cs.kieler.ehelp.EHelp.Enum");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cEnumKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cItemsAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cItemsListItemParserRuleCall_2_0 = (RuleCall)cItemsAssignment_2.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//Enum:
		//	'enum' '{' items+=ListItem* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//'enum' '{' items+=ListItem* '}'
		public Group getGroup() { return cGroup; }
		
		//'enum'
		public Keyword getEnumKeyword_0() { return cEnumKeyword_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_1() { return cLeftCurlyBracketKeyword_1; }
		
		//items+=ListItem*
		public Assignment getItemsAssignment_2() { return cItemsAssignment_2; }
		
		//ListItem
		public RuleCall getItemsListItemParserRuleCall_2_0() { return cItemsListItemParserRuleCall_2_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_3() { return cRightCurlyBracketKeyword_3; }
	}
	public class ListItemElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.cau.cs.kieler.ehelp.EHelp.ListItem");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cHyphenMinusKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cContentAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cContentContentParserRuleCall_1_0 = (RuleCall)cContentAssignment_1.eContents().get(0);
		
		//ListItem:
		//	'-' content+=Content*;
		@Override public ParserRule getRule() { return rule; }
		
		//'-' content+=Content*
		public Group getGroup() { return cGroup; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_0() { return cHyphenMinusKeyword_0; }
		
		//content+=Content*
		public Assignment getContentAssignment_1() { return cContentAssignment_1; }
		
		//Content
		public RuleCall getContentContentParserRuleCall_1_0() { return cContentContentParserRuleCall_1_0; }
	}
	public class ContentElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.cau.cs.kieler.ehelp.EHelp.Content");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cKeywordParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cContextParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cTextParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cImagesParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cLinkParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cListParserRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		private final RuleCall cEnumParserRuleCall_6 = (RuleCall)cAlternatives.eContents().get(6);
		private final RuleCall cTableParserRuleCall_7 = (RuleCall)cAlternatives.eContents().get(7);
		
		//Content:
		//	Keyword | Context | Text | Images | Link | List | Enum | Table;
		@Override public ParserRule getRule() { return rule; }
		
		//Keyword | Context | Text | Images | Link | List | Enum | Table
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//Keyword
		public RuleCall getKeywordParserRuleCall_0() { return cKeywordParserRuleCall_0; }
		
		//Context
		public RuleCall getContextParserRuleCall_1() { return cContextParserRuleCall_1; }
		
		//Text
		public RuleCall getTextParserRuleCall_2() { return cTextParserRuleCall_2; }
		
		//Images
		public RuleCall getImagesParserRuleCall_3() { return cImagesParserRuleCall_3; }
		
		//Link
		public RuleCall getLinkParserRuleCall_4() { return cLinkParserRuleCall_4; }
		
		//List
		public RuleCall getListParserRuleCall_5() { return cListParserRuleCall_5; }
		
		//Enum
		public RuleCall getEnumParserRuleCall_6() { return cEnumParserRuleCall_6; }
		
		//Table
		public RuleCall getTableParserRuleCall_7() { return cTableParserRuleCall_7; }
	}
	public class TableElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.cau.cs.kieler.ehelp.EHelp.Table");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cTableKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cWidthAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cWidthINTTerminalRuleCall_1_0 = (RuleCall)cWidthAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cSpacingKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Keyword cEqualsSignKeyword_2_1 = (Keyword)cGroup_2.eContents().get(1);
		private final Assignment cSpacingAssignment_2_2 = (Assignment)cGroup_2.eContents().get(2);
		private final RuleCall cSpacingINTTerminalRuleCall_2_2_0 = (RuleCall)cSpacingAssignment_2_2.eContents().get(0);
		private final Assignment cStretchAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final Keyword cStretchStretchKeyword_3_0 = (Keyword)cStretchAssignment_3.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		private final Assignment cRowsAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final RuleCall cRowsTableRowParserRuleCall_5_0 = (RuleCall)cRowsAssignment_5.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_6 = (Keyword)cGroup.eContents().get(6);
		
		//Table:
		//	'table' width=INT? ('spacing' '=' spacing=INT)? stretch?='stretch'? '{' rows+=TableRow* '}';
		@Override public ParserRule getRule() { return rule; }
		
		//'table' width=INT? ('spacing' '=' spacing=INT)? stretch?='stretch'? '{' rows+=TableRow* '}'
		public Group getGroup() { return cGroup; }
		
		//'table'
		public Keyword getTableKeyword_0() { return cTableKeyword_0; }
		
		//width=INT?
		public Assignment getWidthAssignment_1() { return cWidthAssignment_1; }
		
		//INT
		public RuleCall getWidthINTTerminalRuleCall_1_0() { return cWidthINTTerminalRuleCall_1_0; }
		
		//('spacing' '=' spacing=INT)?
		public Group getGroup_2() { return cGroup_2; }
		
		//'spacing'
		public Keyword getSpacingKeyword_2_0() { return cSpacingKeyword_2_0; }
		
		//'='
		public Keyword getEqualsSignKeyword_2_1() { return cEqualsSignKeyword_2_1; }
		
		//spacing=INT
		public Assignment getSpacingAssignment_2_2() { return cSpacingAssignment_2_2; }
		
		//INT
		public RuleCall getSpacingINTTerminalRuleCall_2_2_0() { return cSpacingINTTerminalRuleCall_2_2_0; }
		
		//stretch?='stretch'?
		public Assignment getStretchAssignment_3() { return cStretchAssignment_3; }
		
		//'stretch'
		public Keyword getStretchStretchKeyword_3_0() { return cStretchStretchKeyword_3_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_4() { return cLeftCurlyBracketKeyword_4; }
		
		//rows+=TableRow*
		public Assignment getRowsAssignment_5() { return cRowsAssignment_5; }
		
		//TableRow
		public RuleCall getRowsTableRowParserRuleCall_5_0() { return cRowsTableRowParserRuleCall_5_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_6() { return cRightCurlyBracketKeyword_6; }
	}
	public class TableRowElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.cau.cs.kieler.ehelp.EHelp.TableRow");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cHyphenMinusKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cCellsAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cCellsTableCellParserRuleCall_1_0 = (RuleCall)cCellsAssignment_1.eContents().get(0);
		private final Group cGroup_2 = (Group)cGroup.eContents().get(2);
		private final Keyword cVerticalLineKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cCellsAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cCellsTableCellParserRuleCall_2_1_0 = (RuleCall)cCellsAssignment_2_1.eContents().get(0);
		
		//TableRow:
		//	'-' cells+=TableCell ('|' cells+=TableCell)*;
		@Override public ParserRule getRule() { return rule; }
		
		//'-' cells+=TableCell ('|' cells+=TableCell)*
		public Group getGroup() { return cGroup; }
		
		//'-'
		public Keyword getHyphenMinusKeyword_0() { return cHyphenMinusKeyword_0; }
		
		//cells+=TableCell
		public Assignment getCellsAssignment_1() { return cCellsAssignment_1; }
		
		//TableCell
		public RuleCall getCellsTableCellParserRuleCall_1_0() { return cCellsTableCellParserRuleCall_1_0; }
		
		//('|' cells+=TableCell)*
		public Group getGroup_2() { return cGroup_2; }
		
		//'|'
		public Keyword getVerticalLineKeyword_2_0() { return cVerticalLineKeyword_2_0; }
		
		//cells+=TableCell
		public Assignment getCellsAssignment_2_1() { return cCellsAssignment_2_1; }
		
		//TableCell
		public RuleCall getCellsTableCellParserRuleCall_2_1_0() { return cCellsTableCellParserRuleCall_2_1_0; }
	}
	public class TableCellElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.cau.cs.kieler.ehelp.EHelp.TableCell");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cCenterAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final Keyword cCenterCenterKeyword_0_0 = (Keyword)cCenterAssignment_0.eContents().get(0);
		private final Assignment cLeftAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final Keyword cLeftLeftKeyword_1_0 = (Keyword)cLeftAssignment_1.eContents().get(0);
		private final Assignment cRightAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final Keyword cRightRightKeyword_2_0 = (Keyword)cRightAssignment_2.eContents().get(0);
		private final Assignment cTopAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final Keyword cTopTopKeyword_3_0 = (Keyword)cTopAssignment_3.eContents().get(0);
		private final Assignment cMiddleAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final Keyword cMiddleMiddleKeyword_4_0 = (Keyword)cMiddleAssignment_4.eContents().get(0);
		private final Assignment cBottomAssignment_5 = (Assignment)cGroup.eContents().get(5);
		private final Keyword cBottomBottomKeyword_5_0 = (Keyword)cBottomAssignment_5.eContents().get(0);
		private final Assignment cContentAssignment_6 = (Assignment)cGroup.eContents().get(6);
		private final RuleCall cContentContentParserRuleCall_6_0 = (RuleCall)cContentAssignment_6.eContents().get(0);
		
		//TableCell:
		//	center?='center'? left?='left'? right?='right'? top?='top'? middle?='middle'? bottom?='bottom'? content+=Content*;
		@Override public ParserRule getRule() { return rule; }
		
		//center?='center'? left?='left'? right?='right'? top?='top'? middle?='middle'? bottom?='bottom'? content+=Content*
		public Group getGroup() { return cGroup; }
		
		//center?='center'?
		public Assignment getCenterAssignment_0() { return cCenterAssignment_0; }
		
		//'center'
		public Keyword getCenterCenterKeyword_0_0() { return cCenterCenterKeyword_0_0; }
		
		//left?='left'?
		public Assignment getLeftAssignment_1() { return cLeftAssignment_1; }
		
		//'left'
		public Keyword getLeftLeftKeyword_1_0() { return cLeftLeftKeyword_1_0; }
		
		//right?='right'?
		public Assignment getRightAssignment_2() { return cRightAssignment_2; }
		
		//'right'
		public Keyword getRightRightKeyword_2_0() { return cRightRightKeyword_2_0; }
		
		//top?='top'?
		public Assignment getTopAssignment_3() { return cTopAssignment_3; }
		
		//'top'
		public Keyword getTopTopKeyword_3_0() { return cTopTopKeyword_3_0; }
		
		//middle?='middle'?
		public Assignment getMiddleAssignment_4() { return cMiddleAssignment_4; }
		
		//'middle'
		public Keyword getMiddleMiddleKeyword_4_0() { return cMiddleMiddleKeyword_4_0; }
		
		//bottom?='bottom'?
		public Assignment getBottomAssignment_5() { return cBottomAssignment_5; }
		
		//'bottom'
		public Keyword getBottomBottomKeyword_5_0() { return cBottomBottomKeyword_5_0; }
		
		//content+=Content*
		public Assignment getContentAssignment_6() { return cContentAssignment_6; }
		
		//Content
		public RuleCall getContentContentParserRuleCall_6_0() { return cContentContentParserRuleCall_6_0; }
	}
	public class TextElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.cau.cs.kieler.ehelp.EHelp.Text");
		private final Assignment cTextAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cTextSTRINGTerminalRuleCall_0 = (RuleCall)cTextAssignment.eContents().get(0);
		
		//Text:
		//	text=STRING;
		@Override public ParserRule getRule() { return rule; }
		
		//text=STRING
		public Assignment getTextAssignment() { return cTextAssignment; }
		
		//STRING
		public RuleCall getTextSTRINGTerminalRuleCall_0() { return cTextSTRINGTerminalRuleCall_0; }
	}
	public class KeywordElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.cau.cs.kieler.ehelp.EHelp.Keyword");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cQuestionMarkKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cTextAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cTextSTRINGTerminalRuleCall_1_0 = (RuleCall)cTextAssignment_1.eContents().get(0);
		private final Keyword cQuestionMarkKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//Keyword:
		//	'?' text=STRING '?';
		@Override public ParserRule getRule() { return rule; }
		
		//'?' text=STRING '?'
		public Group getGroup() { return cGroup; }
		
		//'?'
		public Keyword getQuestionMarkKeyword_0() { return cQuestionMarkKeyword_0; }
		
		//text=STRING
		public Assignment getTextAssignment_1() { return cTextAssignment_1; }
		
		//STRING
		public RuleCall getTextSTRINGTerminalRuleCall_1_0() { return cTextSTRINGTerminalRuleCall_1_0; }
		
		//'?'
		public Keyword getQuestionMarkKeyword_2() { return cQuestionMarkKeyword_2; }
	}
	public class ContextElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.cau.cs.kieler.ehelp.EHelp.Context");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cExclamationMarkKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cIdAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cIdIDTerminalRuleCall_1_0 = (RuleCall)cIdAssignment_1.eContents().get(0);
		private final Assignment cLabelAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cLabelSTRINGTerminalRuleCall_2_0 = (RuleCall)cLabelAssignment_2.eContents().get(0);
		private final Keyword cExclamationMarkKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//Context:
		//	'!' id=ID label=STRING '!';
		@Override public ParserRule getRule() { return rule; }
		
		//'!' id=ID label=STRING '!'
		public Group getGroup() { return cGroup; }
		
		//'!'
		public Keyword getExclamationMarkKeyword_0() { return cExclamationMarkKeyword_0; }
		
		//id=ID
		public Assignment getIdAssignment_1() { return cIdAssignment_1; }
		
		//ID
		public RuleCall getIdIDTerminalRuleCall_1_0() { return cIdIDTerminalRuleCall_1_0; }
		
		//label=STRING
		public Assignment getLabelAssignment_2() { return cLabelAssignment_2; }
		
		//STRING
		public RuleCall getLabelSTRINGTerminalRuleCall_2_0() { return cLabelSTRINGTerminalRuleCall_2_0; }
		
		//'!'
		public Keyword getExclamationMarkKeyword_3() { return cExclamationMarkKeyword_3; }
	}
	public class ImagesElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.cau.cs.kieler.ehelp.EHelp.Images");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cLeftSquareBracketKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cImagesAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cImagesSTRINGTerminalRuleCall_1_0 = (RuleCall)cImagesAssignment_1.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//Images:
		//	'[' images+=STRING+ ']';
		@Override public ParserRule getRule() { return rule; }
		
		//'[' images+=STRING+ ']'
		public Group getGroup() { return cGroup; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_0() { return cLeftSquareBracketKeyword_0; }
		
		//images+=STRING+
		public Assignment getImagesAssignment_1() { return cImagesAssignment_1; }
		
		//STRING
		public RuleCall getImagesSTRINGTerminalRuleCall_1_0() { return cImagesSTRINGTerminalRuleCall_1_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_2() { return cRightSquareBracketKeyword_2; }
	}
	public class LinkElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "de.cau.cs.kieler.ehelp.EHelp.Link");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cLeftSquareBracketKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cLinkAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final CrossReference cLinkChapterCrossReference_1_0 = (CrossReference)cLinkAssignment_1.eContents().get(0);
		private final RuleCall cLinkChapterIDTerminalRuleCall_1_0_1 = (RuleCall)cLinkChapterCrossReference_1_0.eContents().get(1);
		private final Assignment cCaptionAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cCaptionSTRINGTerminalRuleCall_2_0 = (RuleCall)cCaptionAssignment_2.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_3 = (Keyword)cGroup.eContents().get(3);
		
		//Link:
		//	'[' link=[Chapter] caption=STRING? ']';
		@Override public ParserRule getRule() { return rule; }
		
		//'[' link=[Chapter] caption=STRING? ']'
		public Group getGroup() { return cGroup; }
		
		//'['
		public Keyword getLeftSquareBracketKeyword_0() { return cLeftSquareBracketKeyword_0; }
		
		//link=[Chapter]
		public Assignment getLinkAssignment_1() { return cLinkAssignment_1; }
		
		//[Chapter]
		public CrossReference getLinkChapterCrossReference_1_0() { return cLinkChapterCrossReference_1_0; }
		
		//ID
		public RuleCall getLinkChapterIDTerminalRuleCall_1_0_1() { return cLinkChapterIDTerminalRuleCall_1_0_1; }
		
		//caption=STRING?
		public Assignment getCaptionAssignment_2() { return cCaptionAssignment_2; }
		
		//STRING
		public RuleCall getCaptionSTRINGTerminalRuleCall_2_0() { return cCaptionSTRINGTerminalRuleCall_2_0; }
		
		//']'
		public Keyword getRightSquareBracketKeyword_3() { return cRightSquareBracketKeyword_3; }
	}
	
	
	private final EHelpModelElements pEHelpModel;
	private final ChapterElements pChapter;
	private final ListElements pList;
	private final EnumElements pEnum;
	private final ListItemElements pListItem;
	private final ContentElements pContent;
	private final TableElements pTable;
	private final TableRowElements pTableRow;
	private final TableCellElements pTableCell;
	private final TextElements pText;
	private final KeywordElements pKeyword;
	private final ContextElements pContext;
	private final ImagesElements pImages;
	private final LinkElements pLink;
	
	private final Grammar grammar;
	
	private final TerminalsGrammarAccess gaTerminals;

	@Inject
	public EHelpGrammarAccess(GrammarProvider grammarProvider,
			TerminalsGrammarAccess gaTerminals) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaTerminals = gaTerminals;
		this.pEHelpModel = new EHelpModelElements();
		this.pChapter = new ChapterElements();
		this.pList = new ListElements();
		this.pEnum = new EnumElements();
		this.pListItem = new ListItemElements();
		this.pContent = new ContentElements();
		this.pTable = new TableElements();
		this.pTableRow = new TableRowElements();
		this.pTableCell = new TableCellElements();
		this.pText = new TextElements();
		this.pKeyword = new KeywordElements();
		this.pContext = new ContextElements();
		this.pImages = new ImagesElements();
		this.pLink = new LinkElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("de.cau.cs.kieler.ehelp.EHelp".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	
	
	public TerminalsGrammarAccess getTerminalsGrammarAccess() {
		return gaTerminals;
	}

	
	//EHelpModel:
	//	chapters+=Chapter*;
	public EHelpModelElements getEHelpModelAccess() {
		return pEHelpModel;
	}
	
	public ParserRule getEHelpModelRule() {
		return getEHelpModelAccess().getRule();
	}
	
	//Chapter:
	//	'chapter' name=ID? title=STRING '{' content+=Content* subchapters+=Chapter* '}';
	public ChapterElements getChapterAccess() {
		return pChapter;
	}
	
	public ParserRule getChapterRule() {
		return getChapterAccess().getRule();
	}
	
	//List:
	//	'list' '{' items+=ListItem* '}';
	public ListElements getListAccess() {
		return pList;
	}
	
	public ParserRule getListRule() {
		return getListAccess().getRule();
	}
	
	//Enum:
	//	'enum' '{' items+=ListItem* '}';
	public EnumElements getEnumAccess() {
		return pEnum;
	}
	
	public ParserRule getEnumRule() {
		return getEnumAccess().getRule();
	}
	
	//ListItem:
	//	'-' content+=Content*;
	public ListItemElements getListItemAccess() {
		return pListItem;
	}
	
	public ParserRule getListItemRule() {
		return getListItemAccess().getRule();
	}
	
	//Content:
	//	Keyword | Context | Text | Images | Link | List | Enum | Table;
	public ContentElements getContentAccess() {
		return pContent;
	}
	
	public ParserRule getContentRule() {
		return getContentAccess().getRule();
	}
	
	//Table:
	//	'table' width=INT? ('spacing' '=' spacing=INT)? stretch?='stretch'? '{' rows+=TableRow* '}';
	public TableElements getTableAccess() {
		return pTable;
	}
	
	public ParserRule getTableRule() {
		return getTableAccess().getRule();
	}
	
	//TableRow:
	//	'-' cells+=TableCell ('|' cells+=TableCell)*;
	public TableRowElements getTableRowAccess() {
		return pTableRow;
	}
	
	public ParserRule getTableRowRule() {
		return getTableRowAccess().getRule();
	}
	
	//TableCell:
	//	center?='center'? left?='left'? right?='right'? top?='top'? middle?='middle'? bottom?='bottom'? content+=Content*;
	public TableCellElements getTableCellAccess() {
		return pTableCell;
	}
	
	public ParserRule getTableCellRule() {
		return getTableCellAccess().getRule();
	}
	
	//Text:
	//	text=STRING;
	public TextElements getTextAccess() {
		return pText;
	}
	
	public ParserRule getTextRule() {
		return getTextAccess().getRule();
	}
	
	//Keyword:
	//	'?' text=STRING '?';
	public KeywordElements getKeywordAccess() {
		return pKeyword;
	}
	
	public ParserRule getKeywordRule() {
		return getKeywordAccess().getRule();
	}
	
	//Context:
	//	'!' id=ID label=STRING '!';
	public ContextElements getContextAccess() {
		return pContext;
	}
	
	public ParserRule getContextRule() {
		return getContextAccess().getRule();
	}
	
	//Images:
	//	'[' images+=STRING+ ']';
	public ImagesElements getImagesAccess() {
		return pImages;
	}
	
	public ParserRule getImagesRule() {
		return getImagesAccess().getRule();
	}
	
	//Link:
	//	'[' link=[Chapter] caption=STRING? ']';
	public LinkElements getLinkAccess() {
		return pLink;
	}
	
	public ParserRule getLinkRule() {
		return getLinkAccess().getRule();
	}
	
	//terminal ID:
	//	'^'? ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*;
	public TerminalRule getIDRule() {
		return gaTerminals.getIDRule();
	}
	
	//terminal INT returns ecore::EInt:
	//	'0'..'9'+;
	public TerminalRule getINTRule() {
		return gaTerminals.getINTRule();
	}
	
	//terminal STRING:
	//	'"' ('\\' . | !('\\' | '"'))* '"' | "'" ('\\' . | !('\\' | "'"))* "'";
	public TerminalRule getSTRINGRule() {
		return gaTerminals.getSTRINGRule();
	}
	
	//terminal ML_COMMENT:
	//	'/ *'->'* /';
	public TerminalRule getML_COMMENTRule() {
		return gaTerminals.getML_COMMENTRule();
	}
	
	//terminal SL_COMMENT:
	//	'//' !('\n' | '\r')* ('\r'? '\n')?;
	public TerminalRule getSL_COMMENTRule() {
		return gaTerminals.getSL_COMMENTRule();
	}
	
	//terminal WS:
	//	' ' | '\t' | '\r' | '\n'+;
	public TerminalRule getWSRule() {
		return gaTerminals.getWSRule();
	}
	
	//terminal ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaTerminals.getANY_OTHERRule();
	}
}