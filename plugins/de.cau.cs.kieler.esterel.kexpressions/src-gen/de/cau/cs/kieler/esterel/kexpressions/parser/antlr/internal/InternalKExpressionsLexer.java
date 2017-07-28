package de.cau.cs.kieler.esterel.kexpressions.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalKExpressionsLexer extends Lexer {
    public static final int T__50=50;
    public static final int RULE_BOOLEAN=8;
    public static final int T__19=19;
    public static final int T__59=59;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int RULE_ID=4;
    public static final int T__26=26;
    public static final int RULE_HOSTCODE=5;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=6;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=11;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int RULE_COMMENT_ANNOTATION=9;
    public static final int RULE_STRING=10;
    public static final int RULE_SL_COMMENT=13;
    public static final int T__37=37;
    public static final int RULE_SL_COMMENT_ANNOTATION=12;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_WS=15;
    public static final int RULE_ANY_OTHER=16;
    public static final int RULE_NUMBER=14;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int RULE_FLOAT=7;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;

    // delegates
    // delegators

    public InternalKExpressionsLexer() {;} 
    public InternalKExpressionsLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalKExpressionsLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalKExpressions.g"; }

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:11:7: ( '(' )
            // InternalKExpressions.g:11:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:12:7: ( ')' )
            // InternalKExpressions.g:12:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:13:7: ( 'input' )
            // InternalKExpressions.g:13:9: 'input'
            {
            match("input"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:14:7: ( ',' )
            // InternalKExpressions.g:14:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:15:7: ( ';' )
            // InternalKExpressions.g:15:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:16:7: ( 'output' )
            // InternalKExpressions.g:16:9: 'output'
            {
            match("output"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:17:7: ( 'inputoutput' )
            // InternalKExpressions.g:17:9: 'inputoutput'
            {
            match("inputoutput"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:18:7: ( 'return' )
            // InternalKExpressions.g:18:9: 'return'
            {
            match("return"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:19:7: ( ':' )
            // InternalKExpressions.g:19:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:20:7: ( ':=' )
            // InternalKExpressions.g:20:9: ':='
            {
            match(":="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:21:7: ( 'var' )
            // InternalKExpressions.g:21:9: 'var'
            {
            match("var"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:22:7: ( 'combine' )
            // InternalKExpressions.g:22:9: 'combine'
            {
            match("combine"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:23:7: ( 'with' )
            // InternalKExpressions.g:23:9: 'with'
            {
            match("with"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:24:7: ( '@' )
            // InternalKExpressions.g:24:9: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:25:7: ( '#' )
            // InternalKExpressions.g:25:9: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:26:7: ( '[' )
            // InternalKExpressions.g:26:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:27:7: ( ']' )
            // InternalKExpressions.g:27:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:28:7: ( '.' )
            // InternalKExpressions.g:28:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:29:7: ( '-' )
            // InternalKExpressions.g:29:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:30:7: ( '=' )
            // InternalKExpressions.g:30:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:31:7: ( '<' )
            // InternalKExpressions.g:31:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:32:7: ( '<=' )
            // InternalKExpressions.g:32:9: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:33:7: ( '>' )
            // InternalKExpressions.g:33:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:34:7: ( '>=' )
            // InternalKExpressions.g:34:9: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:35:7: ( '<>' )
            // InternalKExpressions.g:35:9: '<>'
            {
            match("<>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:36:7: ( 'pre' )
            // InternalKExpressions.g:36:9: 'pre'
            {
            match("pre"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:37:7: ( 'or' )
            // InternalKExpressions.g:37:9: 'or'
            {
            match("or"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:38:7: ( 'and' )
            // InternalKExpressions.g:38:9: 'and'
            {
            match("and"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:39:7: ( 'not' )
            // InternalKExpressions.g:39:9: 'not'
            {
            match("not"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:40:7: ( '+' )
            // InternalKExpressions.g:40:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:41:7: ( '*' )
            // InternalKExpressions.g:41:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:42:7: ( 'mod' )
            // InternalKExpressions.g:42:9: 'mod'
            {
            match("mod"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:43:7: ( '/' )
            // InternalKExpressions.g:43:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:44:7: ( '?' )
            // InternalKExpressions.g:44:9: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:45:7: ( 'pure' )
            // InternalKExpressions.g:45:9: 'pure'
            {
            match("pure"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__51"

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:46:7: ( 'boolean' )
            // InternalKExpressions.g:46:9: 'boolean'
            {
            match("boolean"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__52"

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:47:7: ( 'unsigned' )
            // InternalKExpressions.g:47:9: 'unsigned'
            {
            match("unsigned"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:48:7: ( 'integer' )
            // InternalKExpressions.g:48:9: 'integer'
            {
            match("integer"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:49:7: ( 'float' )
            // InternalKExpressions.g:49:9: 'float'
            {
            match("float"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:50:7: ( 'double' )
            // InternalKExpressions.g:50:9: 'double'
            {
            match("double"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:51:7: ( 'string' )
            // InternalKExpressions.g:51:9: 'string'
            {
            match("string"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:52:7: ( 'host' )
            // InternalKExpressions.g:52:9: 'host'
            {
            match("host"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:53:7: ( 'none' )
            // InternalKExpressions.g:53:9: 'none'
            {
            match("none"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__59"

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:54:7: ( 'max' )
            // InternalKExpressions.g:54:9: 'max'
            {
            match("max"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:55:7: ( 'min' )
            // InternalKExpressions.g:55:9: 'min'
            {
            match("min"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__61"

    // $ANTLR start "RULE_HOSTCODE"
    public final void mRULE_HOSTCODE() throws RecognitionException {
        try {
            int _type = RULE_HOSTCODE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:3573:15: ( '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            // InternalKExpressions.g:3573:17: '\\'' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
            {
            match('\''); 
            // InternalKExpressions.g:3573:22: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\\'' ) ) )*
            loop1:
            do {
                int alt1=3;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='\\') ) {
                    alt1=1;
                }
                else if ( ((LA1_0>='\u0000' && LA1_0<='&')||(LA1_0>='(' && LA1_0<='[')||(LA1_0>=']' && LA1_0<='\uFFFF')) ) {
                    alt1=2;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalKExpressions.g:3573:23: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
            	    {
            	    match('\\'); 
            	    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;
            	case 2 :
            	    // InternalKExpressions.g:3573:64: ~ ( ( '\\\\' | '\\'' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_HOSTCODE"

    // $ANTLR start "RULE_COMMENT_ANNOTATION"
    public final void mRULE_COMMENT_ANNOTATION() throws RecognitionException {
        try {
            int _type = RULE_COMMENT_ANNOTATION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:3575:25: ( '/**' ( options {greedy=false; } : . )* '*/' )
            // InternalKExpressions.g:3575:27: '/**' ( options {greedy=false; } : . )* '*/'
            {
            match("/**"); 

            // InternalKExpressions.g:3575:33: ( options {greedy=false; } : . )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='*') ) {
                    int LA2_1 = input.LA(2);

                    if ( (LA2_1=='/') ) {
                        alt2=2;
                    }
                    else if ( ((LA2_1>='\u0000' && LA2_1<='.')||(LA2_1>='0' && LA2_1<='\uFFFF')) ) {
                        alt2=1;
                    }


                }
                else if ( ((LA2_0>='\u0000' && LA2_0<=')')||(LA2_0>='+' && LA2_0<='\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalKExpressions.g:3575:61: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_COMMENT_ANNOTATION"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:3577:17: ( '/*' ~ ( '*' ) ( options {greedy=false; } : . )* '*/' )
            // InternalKExpressions.g:3577:19: '/*' ~ ( '*' ) ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            if ( (input.LA(1)>='\u0000' && input.LA(1)<=')')||(input.LA(1)>='+' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalKExpressions.g:3577:31: ( options {greedy=false; } : . )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='*') ) {
                    int LA3_1 = input.LA(2);

                    if ( (LA3_1=='/') ) {
                        alt3=2;
                    }
                    else if ( ((LA3_1>='\u0000' && LA3_1<='.')||(LA3_1>='0' && LA3_1<='\uFFFF')) ) {
                        alt3=1;
                    }


                }
                else if ( ((LA3_0>='\u0000' && LA3_0<=')')||(LA3_0>='+' && LA3_0<='\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalKExpressions.g:3577:59: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT_ANNOTATION"
    public final void mRULE_SL_COMMENT_ANNOTATION() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT_ANNOTATION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:3579:28: ( '//*' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalKExpressions.g:3579:30: '//*' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//*"); 

            // InternalKExpressions.g:3579:36: (~ ( ( '\\n' | '\\r' ) ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='\u0000' && LA4_0<='\t')||(LA4_0>='\u000B' && LA4_0<='\f')||(LA4_0>='\u000E' && LA4_0<='\uFFFF')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalKExpressions.g:3579:36: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            // InternalKExpressions.g:3579:52: ( ( '\\r' )? '\\n' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='\n'||LA6_0=='\r') ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalKExpressions.g:3579:53: ( '\\r' )? '\\n'
                    {
                    // InternalKExpressions.g:3579:53: ( '\\r' )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0=='\r') ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // InternalKExpressions.g:3579:53: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT_ANNOTATION"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:3581:17: ( '//' ~ ( '*' ) (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalKExpressions.g:3581:19: '//' ~ ( '*' ) (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            if ( (input.LA(1)>='\u0000' && input.LA(1)<=')')||(input.LA(1)>='+' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalKExpressions.g:3581:31: (~ ( ( '\\n' | '\\r' ) ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='\u0000' && LA7_0<='\t')||(LA7_0>='\u000B' && LA7_0<='\f')||(LA7_0>='\u000E' && LA7_0<='\uFFFF')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalKExpressions.g:3581:31: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            // InternalKExpressions.g:3581:47: ( ( '\\r' )? '\\n' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='\n'||LA9_0=='\r') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalKExpressions.g:3581:48: ( '\\r' )? '\\n'
                    {
                    // InternalKExpressions.g:3581:48: ( '\\r' )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='\r') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // InternalKExpressions.g:3581:48: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_NUMBER"
    public final void mRULE_NUMBER() throws RecognitionException {
        try {
            // InternalKExpressions.g:3583:22: ( '0' .. '9' )
            // InternalKExpressions.g:3583:24: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "RULE_NUMBER"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:3585:10: ( ( RULE_NUMBER )+ )
            // InternalKExpressions.g:3585:12: ( RULE_NUMBER )+
            {
            // InternalKExpressions.g:3585:12: ( RULE_NUMBER )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='0' && LA10_0<='9')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalKExpressions.g:3585:12: RULE_NUMBER
            	    {
            	    mRULE_NUMBER(); 

            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_INT"

    // $ANTLR start "RULE_FLOAT"
    public final void mRULE_FLOAT() throws RecognitionException {
        try {
            int _type = RULE_FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:3587:12: ( ( ( RULE_NUMBER )+ '.' ( RULE_NUMBER )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )? ( 'f' )? | ( RULE_NUMBER )+ 'f' ) )
            // InternalKExpressions.g:3587:14: ( ( RULE_NUMBER )+ '.' ( RULE_NUMBER )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )? ( 'f' )? | ( RULE_NUMBER )+ 'f' )
            {
            // InternalKExpressions.g:3587:14: ( ( RULE_NUMBER )+ '.' ( RULE_NUMBER )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )? ( 'f' )? | ( RULE_NUMBER )+ 'f' )
            int alt18=2;
            alt18 = dfa18.predict(input);
            switch (alt18) {
                case 1 :
                    // InternalKExpressions.g:3587:15: ( RULE_NUMBER )+ '.' ( RULE_NUMBER )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )? ( 'f' )?
                    {
                    // InternalKExpressions.g:3587:15: ( RULE_NUMBER )+
                    int cnt11=0;
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // InternalKExpressions.g:3587:15: RULE_NUMBER
                    	    {
                    	    mRULE_NUMBER(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt11 >= 1 ) break loop11;
                                EarlyExitException eee =
                                    new EarlyExitException(11, input);
                                throw eee;
                        }
                        cnt11++;
                    } while (true);

                    match('.'); 
                    // InternalKExpressions.g:3587:32: ( RULE_NUMBER )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>='0' && LA12_0<='9')) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // InternalKExpressions.g:3587:32: RULE_NUMBER
                    	    {
                    	    mRULE_NUMBER(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
                    } while (true);

                    // InternalKExpressions.g:3587:45: ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0=='E'||LA15_0=='e') ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // InternalKExpressions.g:3587:46: ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+
                            {
                            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}

                            // InternalKExpressions.g:3587:56: ( '+' | '-' )?
                            int alt13=2;
                            int LA13_0 = input.LA(1);

                            if ( (LA13_0=='+'||LA13_0=='-') ) {
                                alt13=1;
                            }
                            switch (alt13) {
                                case 1 :
                                    // InternalKExpressions.g:
                                    {
                                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                                        input.consume();

                                    }
                                    else {
                                        MismatchedSetException mse = new MismatchedSetException(null,input);
                                        recover(mse);
                                        throw mse;}


                                    }
                                    break;

                            }

                            // InternalKExpressions.g:3587:67: ( RULE_NUMBER )+
                            int cnt14=0;
                            loop14:
                            do {
                                int alt14=2;
                                int LA14_0 = input.LA(1);

                                if ( ((LA14_0>='0' && LA14_0<='9')) ) {
                                    alt14=1;
                                }


                                switch (alt14) {
                            	case 1 :
                            	    // InternalKExpressions.g:3587:67: RULE_NUMBER
                            	    {
                            	    mRULE_NUMBER(); 

                            	    }
                            	    break;

                            	default :
                            	    if ( cnt14 >= 1 ) break loop14;
                                        EarlyExitException eee =
                                            new EarlyExitException(14, input);
                                        throw eee;
                                }
                                cnt14++;
                            } while (true);


                            }
                            break;

                    }

                    // InternalKExpressions.g:3587:82: ( 'f' )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0=='f') ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // InternalKExpressions.g:3587:82: 'f'
                            {
                            match('f'); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // InternalKExpressions.g:3587:87: ( RULE_NUMBER )+ 'f'
                    {
                    // InternalKExpressions.g:3587:87: ( RULE_NUMBER )+
                    int cnt17=0;
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( ((LA17_0>='0' && LA17_0<='9')) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // InternalKExpressions.g:3587:87: RULE_NUMBER
                    	    {
                    	    mRULE_NUMBER(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt17 >= 1 ) break loop17;
                                EarlyExitException eee =
                                    new EarlyExitException(17, input);
                                throw eee;
                        }
                        cnt17++;
                    } while (true);

                    match('f'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_FLOAT"

    // $ANTLR start "RULE_BOOLEAN"
    public final void mRULE_BOOLEAN() throws RecognitionException {
        try {
            int _type = RULE_BOOLEAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:3589:14: ( ( 'true' | 'false' ) )
            // InternalKExpressions.g:3589:16: ( 'true' | 'false' )
            {
            // InternalKExpressions.g:3589:16: ( 'true' | 'false' )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0=='t') ) {
                alt19=1;
            }
            else if ( (LA19_0=='f') ) {
                alt19=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // InternalKExpressions.g:3589:17: 'true'
                    {
                    match("true"); 


                    }
                    break;
                case 2 :
                    // InternalKExpressions.g:3589:24: 'false'
                    {
                    match("false"); 


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_BOOLEAN"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:3591:13: ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' )
            // InternalKExpressions.g:3591:15: '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
            {
            match('\"'); 
            // InternalKExpressions.g:3591:19: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )*
            loop20:
            do {
                int alt20=3;
                int LA20_0 = input.LA(1);

                if ( (LA20_0=='\\') ) {
                    alt20=1;
                }
                else if ( ((LA20_0>='\u0000' && LA20_0<='!')||(LA20_0>='#' && LA20_0<='[')||(LA20_0>=']' && LA20_0<='\uFFFF')) ) {
                    alt20=2;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalKExpressions.g:3591:20: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
            	    {
            	    match('\\'); 
            	    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;
            	case 2 :
            	    // InternalKExpressions.g:3591:61: ~ ( ( '\\\\' | '\"' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:3593:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // InternalKExpressions.g:3593:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            // InternalKExpressions.g:3593:11: ( '^' )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0=='^') ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalKExpressions.g:3593:11: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalKExpressions.g:3593:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>='0' && LA22_0<='9')||(LA22_0>='A' && LA22_0<='Z')||LA22_0=='_'||(LA22_0>='a' && LA22_0<='z')) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalKExpressions.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:3595:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalKExpressions.g:3595:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalKExpressions.g:3595:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt23=0;
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( ((LA23_0>='\t' && LA23_0<='\n')||LA23_0=='\r'||LA23_0==' ') ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalKExpressions.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt23 >= 1 ) break loop23;
                        EarlyExitException eee =
                            new EarlyExitException(23, input);
                        throw eee;
                }
                cnt23++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKExpressions.g:3597:16: ( . )
            // InternalKExpressions.g:3597:18: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // InternalKExpressions.g:1:8: ( T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | RULE_HOSTCODE | RULE_COMMENT_ANNOTATION | RULE_ML_COMMENT | RULE_SL_COMMENT_ANNOTATION | RULE_SL_COMMENT | RULE_INT | RULE_FLOAT | RULE_BOOLEAN | RULE_STRING | RULE_ID | RULE_WS | RULE_ANY_OTHER )
        int alt24=57;
        alt24 = dfa24.predict(input);
        switch (alt24) {
            case 1 :
                // InternalKExpressions.g:1:10: T__17
                {
                mT__17(); 

                }
                break;
            case 2 :
                // InternalKExpressions.g:1:16: T__18
                {
                mT__18(); 

                }
                break;
            case 3 :
                // InternalKExpressions.g:1:22: T__19
                {
                mT__19(); 

                }
                break;
            case 4 :
                // InternalKExpressions.g:1:28: T__20
                {
                mT__20(); 

                }
                break;
            case 5 :
                // InternalKExpressions.g:1:34: T__21
                {
                mT__21(); 

                }
                break;
            case 6 :
                // InternalKExpressions.g:1:40: T__22
                {
                mT__22(); 

                }
                break;
            case 7 :
                // InternalKExpressions.g:1:46: T__23
                {
                mT__23(); 

                }
                break;
            case 8 :
                // InternalKExpressions.g:1:52: T__24
                {
                mT__24(); 

                }
                break;
            case 9 :
                // InternalKExpressions.g:1:58: T__25
                {
                mT__25(); 

                }
                break;
            case 10 :
                // InternalKExpressions.g:1:64: T__26
                {
                mT__26(); 

                }
                break;
            case 11 :
                // InternalKExpressions.g:1:70: T__27
                {
                mT__27(); 

                }
                break;
            case 12 :
                // InternalKExpressions.g:1:76: T__28
                {
                mT__28(); 

                }
                break;
            case 13 :
                // InternalKExpressions.g:1:82: T__29
                {
                mT__29(); 

                }
                break;
            case 14 :
                // InternalKExpressions.g:1:88: T__30
                {
                mT__30(); 

                }
                break;
            case 15 :
                // InternalKExpressions.g:1:94: T__31
                {
                mT__31(); 

                }
                break;
            case 16 :
                // InternalKExpressions.g:1:100: T__32
                {
                mT__32(); 

                }
                break;
            case 17 :
                // InternalKExpressions.g:1:106: T__33
                {
                mT__33(); 

                }
                break;
            case 18 :
                // InternalKExpressions.g:1:112: T__34
                {
                mT__34(); 

                }
                break;
            case 19 :
                // InternalKExpressions.g:1:118: T__35
                {
                mT__35(); 

                }
                break;
            case 20 :
                // InternalKExpressions.g:1:124: T__36
                {
                mT__36(); 

                }
                break;
            case 21 :
                // InternalKExpressions.g:1:130: T__37
                {
                mT__37(); 

                }
                break;
            case 22 :
                // InternalKExpressions.g:1:136: T__38
                {
                mT__38(); 

                }
                break;
            case 23 :
                // InternalKExpressions.g:1:142: T__39
                {
                mT__39(); 

                }
                break;
            case 24 :
                // InternalKExpressions.g:1:148: T__40
                {
                mT__40(); 

                }
                break;
            case 25 :
                // InternalKExpressions.g:1:154: T__41
                {
                mT__41(); 

                }
                break;
            case 26 :
                // InternalKExpressions.g:1:160: T__42
                {
                mT__42(); 

                }
                break;
            case 27 :
                // InternalKExpressions.g:1:166: T__43
                {
                mT__43(); 

                }
                break;
            case 28 :
                // InternalKExpressions.g:1:172: T__44
                {
                mT__44(); 

                }
                break;
            case 29 :
                // InternalKExpressions.g:1:178: T__45
                {
                mT__45(); 

                }
                break;
            case 30 :
                // InternalKExpressions.g:1:184: T__46
                {
                mT__46(); 

                }
                break;
            case 31 :
                // InternalKExpressions.g:1:190: T__47
                {
                mT__47(); 

                }
                break;
            case 32 :
                // InternalKExpressions.g:1:196: T__48
                {
                mT__48(); 

                }
                break;
            case 33 :
                // InternalKExpressions.g:1:202: T__49
                {
                mT__49(); 

                }
                break;
            case 34 :
                // InternalKExpressions.g:1:208: T__50
                {
                mT__50(); 

                }
                break;
            case 35 :
                // InternalKExpressions.g:1:214: T__51
                {
                mT__51(); 

                }
                break;
            case 36 :
                // InternalKExpressions.g:1:220: T__52
                {
                mT__52(); 

                }
                break;
            case 37 :
                // InternalKExpressions.g:1:226: T__53
                {
                mT__53(); 

                }
                break;
            case 38 :
                // InternalKExpressions.g:1:232: T__54
                {
                mT__54(); 

                }
                break;
            case 39 :
                // InternalKExpressions.g:1:238: T__55
                {
                mT__55(); 

                }
                break;
            case 40 :
                // InternalKExpressions.g:1:244: T__56
                {
                mT__56(); 

                }
                break;
            case 41 :
                // InternalKExpressions.g:1:250: T__57
                {
                mT__57(); 

                }
                break;
            case 42 :
                // InternalKExpressions.g:1:256: T__58
                {
                mT__58(); 

                }
                break;
            case 43 :
                // InternalKExpressions.g:1:262: T__59
                {
                mT__59(); 

                }
                break;
            case 44 :
                // InternalKExpressions.g:1:268: T__60
                {
                mT__60(); 

                }
                break;
            case 45 :
                // InternalKExpressions.g:1:274: T__61
                {
                mT__61(); 

                }
                break;
            case 46 :
                // InternalKExpressions.g:1:280: RULE_HOSTCODE
                {
                mRULE_HOSTCODE(); 

                }
                break;
            case 47 :
                // InternalKExpressions.g:1:294: RULE_COMMENT_ANNOTATION
                {
                mRULE_COMMENT_ANNOTATION(); 

                }
                break;
            case 48 :
                // InternalKExpressions.g:1:318: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 49 :
                // InternalKExpressions.g:1:334: RULE_SL_COMMENT_ANNOTATION
                {
                mRULE_SL_COMMENT_ANNOTATION(); 

                }
                break;
            case 50 :
                // InternalKExpressions.g:1:361: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 51 :
                // InternalKExpressions.g:1:377: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 52 :
                // InternalKExpressions.g:1:386: RULE_FLOAT
                {
                mRULE_FLOAT(); 

                }
                break;
            case 53 :
                // InternalKExpressions.g:1:397: RULE_BOOLEAN
                {
                mRULE_BOOLEAN(); 

                }
                break;
            case 54 :
                // InternalKExpressions.g:1:410: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 55 :
                // InternalKExpressions.g:1:422: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 56 :
                // InternalKExpressions.g:1:430: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 57 :
                // InternalKExpressions.g:1:438: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA18 dfa18 = new DFA18(this);
    protected DFA24 dfa24 = new DFA24(this);
    static final String DFA18_eotS =
        "\4\uffff";
    static final String DFA18_eofS =
        "\4\uffff";
    static final String DFA18_minS =
        "\1\60\1\56\2\uffff";
    static final String DFA18_maxS =
        "\1\71\1\146\2\uffff";
    static final String DFA18_acceptS =
        "\2\uffff\1\1\1\2";
    static final String DFA18_specialS =
        "\4\uffff}>";
    static final String[] DFA18_transitionS = {
            "\12\1",
            "\1\2\1\uffff\12\1\54\uffff\1\3",
            "",
            ""
    };

    static final short[] DFA18_eot = DFA.unpackEncodedString(DFA18_eotS);
    static final short[] DFA18_eof = DFA.unpackEncodedString(DFA18_eofS);
    static final char[] DFA18_min = DFA.unpackEncodedStringToUnsignedChars(DFA18_minS);
    static final char[] DFA18_max = DFA.unpackEncodedStringToUnsignedChars(DFA18_maxS);
    static final short[] DFA18_accept = DFA.unpackEncodedString(DFA18_acceptS);
    static final short[] DFA18_special = DFA.unpackEncodedString(DFA18_specialS);
    static final short[][] DFA18_transition;

    static {
        int numStates = DFA18_transitionS.length;
        DFA18_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA18_transition[i] = DFA.unpackEncodedString(DFA18_transitionS[i]);
        }
    }

    static class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = DFA18_eot;
            this.eof = DFA18_eof;
            this.min = DFA18_min;
            this.max = DFA18_max;
            this.accept = DFA18_accept;
            this.special = DFA18_special;
            this.transition = DFA18_transition;
        }
        public String getDescription() {
            return "3587:14: ( ( RULE_NUMBER )+ '.' ( RULE_NUMBER )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )? ( 'f' )? | ( RULE_NUMBER )+ 'f' )";
        }
    }
    static final String DFA24_eotS =
        "\3\uffff\1\56\2\uffff\2\56\1\65\3\56\7\uffff\1\102\1\104\3\56\2\uffff\1\56\1\120\1\uffff\6\56\1\52\1\132\1\56\2\52\5\uffff\1\56\3\uffff\1\56\1\143\1\56\2\uffff\3\56\14\uffff\4\56\2\uffff\3\56\4\uffff\7\56\3\uffff\1\132\1\56\2\uffff\3\56\1\uffff\1\56\1\u0080\2\56\1\u0083\1\56\1\u0085\1\u0086\1\56\1\u0088\1\u0089\1\u008a\4\uffff\14\56\1\uffff\1\56\1\u0098\1\uffff\1\u0099\2\uffff\1\u009a\3\uffff\6\56\1\u00a1\1\u00a2\1\u00a4\4\56\3\uffff\2\56\1\u00ab\1\u00a2\2\56\2\uffff\1\56\1\uffff\1\56\1\u00b0\1\u00b1\3\56\1\uffff\1\u00b5\1\u00b6\1\56\1\u00b8\2\uffff\1\u00b9\1\u00ba\1\56\2\uffff\1\56\3\uffff\1\u00bd\1\56\1\uffff\1\56\1\u00c0\1\uffff";
    static final String DFA24_eofS =
        "\u00c1\uffff";
    static final String DFA24_minS =
        "\1\0\2\uffff\1\156\2\uffff\1\162\1\145\1\75\1\141\1\157\1\151\7\uffff\2\75\1\162\1\156\1\157\2\uffff\1\141\1\52\1\uffff\1\157\1\156\1\141\1\157\1\164\1\157\1\0\1\56\1\162\1\0\1\101\5\uffff\1\160\3\uffff\1\164\1\60\1\164\2\uffff\1\162\1\155\1\164\14\uffff\1\145\1\162\1\144\1\156\2\uffff\1\144\1\170\1\156\2\0\2\uffff\1\157\1\163\1\157\1\154\1\165\1\162\1\163\3\uffff\1\56\1\165\2\uffff\1\165\1\145\1\160\1\uffff\1\165\1\60\1\142\1\150\1\60\1\145\2\60\1\145\3\60\4\uffff\1\154\1\151\1\141\1\163\1\142\1\151\1\164\1\145\1\164\1\147\1\165\1\162\1\uffff\1\151\1\60\1\uffff\1\60\2\uffff\1\60\3\uffff\1\145\1\147\1\164\1\145\1\154\1\156\3\60\1\145\1\164\2\156\3\uffff\1\141\1\156\2\60\1\145\1\147\2\uffff\1\165\1\uffff\1\162\2\60\1\145\1\156\1\145\1\uffff\2\60\1\164\1\60\2\uffff\2\60\1\144\2\uffff\1\160\3\uffff\1\60\1\165\1\uffff\1\164\1\60\1\uffff";
    static final String DFA24_maxS =
        "\1\uffff\2\uffff\1\156\2\uffff\1\165\1\145\1\75\1\141\1\157\1\151\7\uffff\1\76\1\75\1\165\1\156\1\157\2\uffff\1\157\1\57\1\uffff\1\157\1\156\1\154\1\157\1\164\1\157\1\uffff\1\146\1\162\1\uffff\1\172\5\uffff\1\164\3\uffff\1\164\1\172\1\164\2\uffff\1\162\1\155\1\164\14\uffff\1\145\1\162\1\144\1\164\2\uffff\1\144\1\170\1\156\2\uffff\2\uffff\1\157\1\163\1\157\1\154\1\165\1\162\1\163\3\uffff\1\146\1\165\2\uffff\1\165\1\145\1\160\1\uffff\1\165\1\172\1\142\1\150\1\172\1\145\2\172\1\145\3\172\4\uffff\1\154\1\151\1\141\1\163\1\142\1\151\1\164\1\145\1\164\1\147\1\165\1\162\1\uffff\1\151\1\172\1\uffff\1\172\2\uffff\1\172\3\uffff\1\145\1\147\1\164\1\145\1\154\1\156\3\172\1\145\1\164\2\156\3\uffff\1\141\1\156\2\172\1\145\1\147\2\uffff\1\165\1\uffff\1\162\2\172\1\145\1\156\1\145\1\uffff\2\172\1\164\1\172\2\uffff\2\172\1\144\2\uffff\1\160\3\uffff\1\172\1\165\1\uffff\1\164\1\172\1\uffff";
    static final String DFA24_acceptS =
        "\1\uffff\1\1\1\2\1\uffff\1\4\1\5\6\uffff\1\16\1\17\1\20\1\21\1\22\1\23\1\24\5\uffff\1\36\1\37\2\uffff\1\42\13\uffff\1\67\1\70\1\71\1\1\1\2\1\uffff\1\67\1\4\1\5\3\uffff\1\12\1\11\3\uffff\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\26\1\31\1\25\1\30\1\27\4\uffff\1\36\1\37\5\uffff\1\41\1\42\7\uffff\1\56\1\63\1\64\2\uffff\1\66\1\70\3\uffff\1\33\14\uffff\1\57\1\60\1\61\1\62\14\uffff\1\13\2\uffff\1\32\1\uffff\1\34\1\35\1\uffff\1\40\1\54\1\55\15\uffff\1\15\1\43\1\53\6\uffff\1\52\1\65\1\uffff\1\3\6\uffff\1\47\4\uffff\1\6\1\10\3\uffff\1\50\1\51\1\uffff\1\46\1\14\1\44\2\uffff\1\45\2\uffff\1\7";
    static final String DFA24_specialS =
        "\1\1\42\uffff\1\0\2\uffff\1\4\47\uffff\1\2\1\3\161\uffff}>";
    static final String[] DFA24_transitionS = {
            "\11\52\2\51\2\52\1\51\22\52\1\51\1\52\1\46\1\15\3\52\1\43\1\1\1\2\1\31\1\30\1\4\1\21\1\20\1\33\12\44\1\10\1\5\1\23\1\22\1\24\1\34\1\14\32\50\1\16\1\52\1\17\1\47\1\50\1\52\1\26\1\35\1\12\1\40\1\50\1\37\1\50\1\42\1\3\3\50\1\32\1\27\1\6\1\25\1\50\1\7\1\41\1\45\1\36\1\11\1\13\3\50\uff85\52",
            "",
            "",
            "\1\55",
            "",
            "",
            "\1\62\2\uffff\1\61",
            "\1\63",
            "\1\64",
            "\1\66",
            "\1\67",
            "\1\70",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\100\1\101",
            "\1\103",
            "\1\105\2\uffff\1\106",
            "\1\107",
            "\1\110",
            "",
            "",
            "\1\114\7\uffff\1\115\5\uffff\1\113",
            "\1\116\4\uffff\1\117",
            "",
            "\1\122",
            "\1\123",
            "\1\125\12\uffff\1\124",
            "\1\126",
            "\1\127",
            "\1\130",
            "\0\131",
            "\1\133\1\uffff\12\134\54\uffff\1\133",
            "\1\135",
            "\0\136",
            "\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "",
            "",
            "",
            "",
            "\1\140\3\uffff\1\141",
            "",
            "",
            "",
            "\1\142",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\144",
            "",
            "",
            "\1\145",
            "\1\146",
            "\1\147",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\150",
            "\1\151",
            "\1\152",
            "\1\154\5\uffff\1\153",
            "",
            "",
            "\1\155",
            "\1\156",
            "\1\157",
            "\52\161\1\160\uffd5\161",
            "\52\163\1\162\uffd5\163",
            "",
            "",
            "\1\164",
            "\1\165",
            "\1\166",
            "\1\167",
            "\1\170",
            "\1\171",
            "\1\172",
            "",
            "",
            "",
            "\1\133\1\uffff\12\134\54\uffff\1\133",
            "\1\173",
            "",
            "",
            "\1\174",
            "\1\175",
            "\1\176",
            "",
            "\1\177",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u0081",
            "\1\u0082",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u0084",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u0087",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "",
            "",
            "",
            "\1\u008b",
            "\1\u008c",
            "\1\u008d",
            "\1\u008e",
            "\1\u008f",
            "\1\u0090",
            "\1\u0091",
            "\1\u0092",
            "\1\u0093",
            "\1\u0094",
            "\1\u0095",
            "\1\u0096",
            "",
            "\1\u0097",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "",
            "",
            "\1\u009b",
            "\1\u009c",
            "\1\u009d",
            "\1\u009e",
            "\1\u009f",
            "\1\u00a0",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\16\56\1\u00a3\13\56",
            "\1\u00a5",
            "\1\u00a6",
            "\1\u00a7",
            "\1\u00a8",
            "",
            "",
            "",
            "\1\u00a9",
            "\1\u00aa",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00ac",
            "\1\u00ad",
            "",
            "",
            "\1\u00ae",
            "",
            "\1\u00af",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00b2",
            "\1\u00b3",
            "\1\u00b4",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00b7",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00bb",
            "",
            "",
            "\1\u00bc",
            "",
            "",
            "",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            "\1\u00be",
            "",
            "\1\u00bf",
            "\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32\56",
            ""
    };

    static final short[] DFA24_eot = DFA.unpackEncodedString(DFA24_eotS);
    static final short[] DFA24_eof = DFA.unpackEncodedString(DFA24_eofS);
    static final char[] DFA24_min = DFA.unpackEncodedStringToUnsignedChars(DFA24_minS);
    static final char[] DFA24_max = DFA.unpackEncodedStringToUnsignedChars(DFA24_maxS);
    static final short[] DFA24_accept = DFA.unpackEncodedString(DFA24_acceptS);
    static final short[] DFA24_special = DFA.unpackEncodedString(DFA24_specialS);
    static final short[][] DFA24_transition;

    static {
        int numStates = DFA24_transitionS.length;
        DFA24_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA24_transition[i] = DFA.unpackEncodedString(DFA24_transitionS[i]);
        }
    }

    class DFA24 extends DFA {

        public DFA24(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 24;
            this.eot = DFA24_eot;
            this.eof = DFA24_eof;
            this.min = DFA24_min;
            this.max = DFA24_max;
            this.accept = DFA24_accept;
            this.special = DFA24_special;
            this.transition = DFA24_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | RULE_HOSTCODE | RULE_COMMENT_ANNOTATION | RULE_ML_COMMENT | RULE_SL_COMMENT_ANNOTATION | RULE_SL_COMMENT | RULE_INT | RULE_FLOAT | RULE_BOOLEAN | RULE_STRING | RULE_ID | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA24_35 = input.LA(1);

                        s = -1;
                        if ( ((LA24_35>='\u0000' && LA24_35<='\uFFFF')) ) {s = 89;}

                        else s = 42;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA24_0 = input.LA(1);

                        s = -1;
                        if ( (LA24_0=='(') ) {s = 1;}

                        else if ( (LA24_0==')') ) {s = 2;}

                        else if ( (LA24_0=='i') ) {s = 3;}

                        else if ( (LA24_0==',') ) {s = 4;}

                        else if ( (LA24_0==';') ) {s = 5;}

                        else if ( (LA24_0=='o') ) {s = 6;}

                        else if ( (LA24_0=='r') ) {s = 7;}

                        else if ( (LA24_0==':') ) {s = 8;}

                        else if ( (LA24_0=='v') ) {s = 9;}

                        else if ( (LA24_0=='c') ) {s = 10;}

                        else if ( (LA24_0=='w') ) {s = 11;}

                        else if ( (LA24_0=='@') ) {s = 12;}

                        else if ( (LA24_0=='#') ) {s = 13;}

                        else if ( (LA24_0=='[') ) {s = 14;}

                        else if ( (LA24_0==']') ) {s = 15;}

                        else if ( (LA24_0=='.') ) {s = 16;}

                        else if ( (LA24_0=='-') ) {s = 17;}

                        else if ( (LA24_0=='=') ) {s = 18;}

                        else if ( (LA24_0=='<') ) {s = 19;}

                        else if ( (LA24_0=='>') ) {s = 20;}

                        else if ( (LA24_0=='p') ) {s = 21;}

                        else if ( (LA24_0=='a') ) {s = 22;}

                        else if ( (LA24_0=='n') ) {s = 23;}

                        else if ( (LA24_0=='+') ) {s = 24;}

                        else if ( (LA24_0=='*') ) {s = 25;}

                        else if ( (LA24_0=='m') ) {s = 26;}

                        else if ( (LA24_0=='/') ) {s = 27;}

                        else if ( (LA24_0=='?') ) {s = 28;}

                        else if ( (LA24_0=='b') ) {s = 29;}

                        else if ( (LA24_0=='u') ) {s = 30;}

                        else if ( (LA24_0=='f') ) {s = 31;}

                        else if ( (LA24_0=='d') ) {s = 32;}

                        else if ( (LA24_0=='s') ) {s = 33;}

                        else if ( (LA24_0=='h') ) {s = 34;}

                        else if ( (LA24_0=='\'') ) {s = 35;}

                        else if ( ((LA24_0>='0' && LA24_0<='9')) ) {s = 36;}

                        else if ( (LA24_0=='t') ) {s = 37;}

                        else if ( (LA24_0=='\"') ) {s = 38;}

                        else if ( (LA24_0=='^') ) {s = 39;}

                        else if ( ((LA24_0>='A' && LA24_0<='Z')||LA24_0=='_'||LA24_0=='e'||LA24_0=='g'||(LA24_0>='j' && LA24_0<='l')||LA24_0=='q'||(LA24_0>='x' && LA24_0<='z')) ) {s = 40;}

                        else if ( ((LA24_0>='\t' && LA24_0<='\n')||LA24_0=='\r'||LA24_0==' ') ) {s = 41;}

                        else if ( ((LA24_0>='\u0000' && LA24_0<='\b')||(LA24_0>='\u000B' && LA24_0<='\f')||(LA24_0>='\u000E' && LA24_0<='\u001F')||LA24_0=='!'||(LA24_0>='$' && LA24_0<='&')||LA24_0=='\\'||LA24_0=='`'||(LA24_0>='{' && LA24_0<='\uFFFF')) ) {s = 42;}

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA24_78 = input.LA(1);

                        s = -1;
                        if ( (LA24_78=='*') ) {s = 112;}

                        else if ( ((LA24_78>='\u0000' && LA24_78<=')')||(LA24_78>='+' && LA24_78<='\uFFFF')) ) {s = 113;}

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA24_79 = input.LA(1);

                        s = -1;
                        if ( (LA24_79=='*') ) {s = 114;}

                        else if ( ((LA24_79>='\u0000' && LA24_79<=')')||(LA24_79>='+' && LA24_79<='\uFFFF')) ) {s = 115;}

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA24_38 = input.LA(1);

                        s = -1;
                        if ( ((LA24_38>='\u0000' && LA24_38<='\uFFFF')) ) {s = 94;}

                        else s = 42;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 24, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}