package de.cau.cs.kieler.simulation.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalKiSimLexer extends Lexer {
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
    public static final int RULE_HOSTCODE=7;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=5;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=11;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__20=20;
    public static final int T__64=64;
    public static final int T__21=21;
    public static final int T__65=65;
    public static final int RULE_COMMENT_ANNOTATION=10;
    public static final int RULE_STRING=9;
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
    public static final int RULE_FLOAT=6;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;

    // delegates
    // delegators

    public InternalKiSimLexer() {;} 
    public InternalKiSimLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalKiSimLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalKiSim.g"; }

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKiSim.g:11:7: ( 'configure' )
            // InternalKiSim.g:11:9: 'configure'
            {
            match("configure"); 


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
            // InternalKiSim.g:12:7: ( '{' )
            // InternalKiSim.g:12:9: '{'
            {
            match('{'); 

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
            // InternalKiSim.g:13:7: ( '}' )
            // InternalKiSim.g:13:9: '}'
            {
            match('}'); 

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
            // InternalKiSim.g:14:7: ( 'initialization' )
            // InternalKiSim.g:14:9: 'initialization'
            {
            match("initialization"); 


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
            // InternalKiSim.g:15:7: ( 'execution' )
            // InternalKiSim.g:15:9: 'execution'
            {
            match("execution"); 


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
            // InternalKiSim.g:16:7: ( 'model' )
            // InternalKiSim.g:16:9: 'model'
            {
            match("model"); 


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
            // InternalKiSim.g:17:7: ( 'compiler' )
            // InternalKiSim.g:17:9: 'compiler'
            {
            match("compiler"); 


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
            // InternalKiSim.g:18:7: ( 'simulation' )
            // InternalKiSim.g:18:9: 'simulation'
            {
            match("simulation"); 


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
            // InternalKiSim.g:19:7: ( 'process' )
            // InternalKiSim.g:19:9: 'process'
            {
            match("process"); 


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
            // InternalKiSim.g:20:7: ( 'template' )
            // InternalKiSim.g:20:9: 'template'
            {
            match("template"); 


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
            // InternalKiSim.g:21:7: ( 'wrapper' )
            // InternalKiSim.g:21:9: 'wrapper'
            {
            match("wrapper"); 


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
            // InternalKiSim.g:22:7: ( ':' )
            // InternalKiSim.g:22:9: ':'
            {
            match(':'); 

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
            // InternalKiSim.g:23:7: ( ',' )
            // InternalKiSim.g:23:9: ','
            {
            match(','); 

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
            // InternalKiSim.g:24:7: ( '[' )
            // InternalKiSim.g:24:9: '['
            {
            match('['); 

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
            // InternalKiSim.g:25:7: ( ']' )
            // InternalKiSim.g:25:9: ']'
            {
            match(']'); 

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
            // InternalKiSim.g:26:7: ( 'schedule' )
            // InternalKiSim.g:26:9: 'schedule'
            {
            match("schedule"); 


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
            // InternalKiSim.g:27:7: ( '||' )
            // InternalKiSim.g:27:9: '||'
            {
            match("||"); 


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
            // InternalKiSim.g:28:7: ( '&&' )
            // InternalKiSim.g:28:9: '&&'
            {
            match("&&"); 


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
            // InternalKiSim.g:29:7: ( '|' )
            // InternalKiSim.g:29:9: '|'
            {
            match('|'); 

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
            // InternalKiSim.g:30:7: ( '^' )
            // InternalKiSim.g:30:9: '^'
            {
            match('^'); 

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
            // InternalKiSim.g:31:7: ( '&' )
            // InternalKiSim.g:31:9: '&'
            {
            match('&'); 

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
            // InternalKiSim.g:32:7: ( '<<' )
            // InternalKiSim.g:32:9: '<<'
            {
            match("<<"); 


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
            // InternalKiSim.g:33:7: ( '>>' )
            // InternalKiSim.g:33:9: '>>'
            {
            match(">>"); 


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
            // InternalKiSim.g:34:7: ( '>>>' )
            // InternalKiSim.g:34:9: '>>>'
            {
            match(">>>"); 


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
            // InternalKiSim.g:35:7: ( '+' )
            // InternalKiSim.g:35:9: '+'
            {
            match('+'); 

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
            // InternalKiSim.g:36:7: ( '-' )
            // InternalKiSim.g:36:9: '-'
            {
            match('-'); 

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
            // InternalKiSim.g:37:7: ( '*' )
            // InternalKiSim.g:37:9: '*'
            {
            match('*'); 

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
            // InternalKiSim.g:38:7: ( '/' )
            // InternalKiSim.g:38:9: '/'
            {
            match('/'); 

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
            // InternalKiSim.g:39:7: ( '%' )
            // InternalKiSim.g:39:9: '%'
            {
            match('%'); 

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
            // InternalKiSim.g:40:7: ( '(' )
            // InternalKiSim.g:40:9: '('
            {
            match('('); 

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
            // InternalKiSim.g:41:7: ( ')' )
            // InternalKiSim.g:41:9: ')'
            {
            match(')'); 

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
            // InternalKiSim.g:42:7: ( '\\'' )
            // InternalKiSim.g:42:9: '\\''
            {
            match('\''); 

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
            // InternalKiSim.g:43:7: ( '.' )
            // InternalKiSim.g:43:9: '.'
            {
            match('.'); 

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
            // InternalKiSim.g:44:7: ( '()' )
            // InternalKiSim.g:44:9: '()'
            {
            match("()"); 


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
            // InternalKiSim.g:45:7: ( 'extern' )
            // InternalKiSim.g:45:9: 'extern'
            {
            match("extern"); 


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
            // InternalKiSim.g:46:7: ( '!' )
            // InternalKiSim.g:46:9: '!'
            {
            match('!'); 

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
            // InternalKiSim.g:47:7: ( '_' )
            // InternalKiSim.g:47:9: '_'
            {
            match('_'); 

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
            // InternalKiSim.g:48:7: ( '@' )
            // InternalKiSim.g:48:9: '@'
            {
            match('@'); 

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
            // InternalKiSim.g:49:7: ( '#' )
            // InternalKiSim.g:49:9: '#'
            {
            match('#'); 

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
            // InternalKiSim.g:50:7: ( '==' )
            // InternalKiSim.g:50:9: '=='
            {
            match("=="); 


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
            // InternalKiSim.g:51:7: ( '<' )
            // InternalKiSim.g:51:9: '<'
            {
            match('<'); 

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
            // InternalKiSim.g:52:7: ( '<=' )
            // InternalKiSim.g:52:9: '<='
            {
            match("<="); 


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
            // InternalKiSim.g:53:7: ( '>' )
            // InternalKiSim.g:53:9: '>'
            {
            match('>'); 

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
            // InternalKiSim.g:54:7: ( '>=' )
            // InternalKiSim.g:54:9: '>='
            {
            match(">="); 


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
            // InternalKiSim.g:55:7: ( '!=' )
            // InternalKiSim.g:55:9: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKiSim.g:56:7: ( 'pre' )
            // InternalKiSim.g:56:9: 'pre'
            {
            match("pre"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKiSim.g:57:7: ( '~' )
            // InternalKiSim.g:57:9: '~'
            {
            match('~'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKiSim.g:58:7: ( 'val' )
            // InternalKiSim.g:58:9: 'val'
            {
            match("val"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKiSim.g:59:7: ( '?' )
            // InternalKiSim.g:59:9: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "RULE_BOOLEAN"
    public final void mRULE_BOOLEAN() throws RecognitionException {
        try {
            int _type = RULE_BOOLEAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKiSim.g:5796:14: ( ( 'true' | 'false' ) )
            // InternalKiSim.g:5796:16: ( 'true' | 'false' )
            {
            // InternalKiSim.g:5796:16: ( 'true' | 'false' )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='t') ) {
                alt1=1;
            }
            else if ( (LA1_0=='f') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalKiSim.g:5796:17: 'true'
                    {
                    match("true"); 


                    }
                    break;
                case 2 :
                    // InternalKiSim.g:5796:24: 'false'
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

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKiSim.g:5798:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '/' | '\\\\' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | '.' | '/' | '\\\\' | '0' .. '9' )* )
            // InternalKiSim.g:5798:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '/' | '\\\\' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | '.' | '/' | '\\\\' | '0' .. '9' )*
            {
            // InternalKiSim.g:5798:11: ( '^' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='^') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalKiSim.g:5798:11: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( input.LA(1)=='/'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='\\'||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalKiSim.g:5798:49: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | '.' | '/' | '\\\\' | '0' .. '9' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='-' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='Z')||LA3_0=='\\'||LA3_0=='_'||(LA3_0>='a' && LA3_0<='z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalKiSim.g:
            	    {
            	    if ( (input.LA(1)>='-' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='\\'||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop3;
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

    // $ANTLR start "RULE_HOSTCODE"
    public final void mRULE_HOSTCODE() throws RecognitionException {
        try {
            int _type = RULE_HOSTCODE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKiSim.g:5800:15: ( '`' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '`' ) ) )* '`' )
            // InternalKiSim.g:5800:17: '`' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '`' ) ) )* '`'
            {
            match('`'); 
            // InternalKiSim.g:5800:21: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '`' ) ) )*
            loop4:
            do {
                int alt4=3;
                int LA4_0 = input.LA(1);

                if ( (LA4_0=='\\') ) {
                    alt4=1;
                }
                else if ( ((LA4_0>='\u0000' && LA4_0<='[')||(LA4_0>=']' && LA4_0<='_')||(LA4_0>='a' && LA4_0<='\uFFFF')) ) {
                    alt4=2;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalKiSim.g:5800:22: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
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
            	    // InternalKiSim.g:5800:63: ~ ( ( '\\\\' | '`' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='_')||(input.LA(1)>='a' && input.LA(1)<='\uFFFF') ) {
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

            match('`'); 

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
            // InternalKiSim.g:5802:25: ( '/**' ( options {greedy=false; } : . )* '*/' )
            // InternalKiSim.g:5802:27: '/**' ( options {greedy=false; } : . )* '*/'
            {
            match("/**"); 

            // InternalKiSim.g:5802:33: ( options {greedy=false; } : . )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0=='*') ) {
                    int LA5_1 = input.LA(2);

                    if ( (LA5_1=='/') ) {
                        alt5=2;
                    }
                    else if ( ((LA5_1>='\u0000' && LA5_1<='.')||(LA5_1>='0' && LA5_1<='\uFFFF')) ) {
                        alt5=1;
                    }


                }
                else if ( ((LA5_0>='\u0000' && LA5_0<=')')||(LA5_0>='+' && LA5_0<='\uFFFF')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalKiSim.g:5802:61: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop5;
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
            // InternalKiSim.g:5804:17: ( '/*' ~ ( '*' ) ( options {greedy=false; } : . )* '*/' )
            // InternalKiSim.g:5804:19: '/*' ~ ( '*' ) ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            if ( (input.LA(1)>='\u0000' && input.LA(1)<=')')||(input.LA(1)>='+' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalKiSim.g:5804:31: ( options {greedy=false; } : . )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='*') ) {
                    int LA6_1 = input.LA(2);

                    if ( (LA6_1=='/') ) {
                        alt6=2;
                    }
                    else if ( ((LA6_1>='\u0000' && LA6_1<='.')||(LA6_1>='0' && LA6_1<='\uFFFF')) ) {
                        alt6=1;
                    }


                }
                else if ( ((LA6_0>='\u0000' && LA6_0<=')')||(LA6_0>='+' && LA6_0<='\uFFFF')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalKiSim.g:5804:59: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop6;
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
            // InternalKiSim.g:5806:28: ( '//*' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalKiSim.g:5806:30: '//*' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//*"); 

            // InternalKiSim.g:5806:36: (~ ( ( '\\n' | '\\r' ) ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='\u0000' && LA7_0<='\t')||(LA7_0>='\u000B' && LA7_0<='\f')||(LA7_0>='\u000E' && LA7_0<='\uFFFF')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalKiSim.g:5806:36: ~ ( ( '\\n' | '\\r' ) )
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

            // InternalKiSim.g:5806:52: ( ( '\\r' )? '\\n' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='\n'||LA9_0=='\r') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalKiSim.g:5806:53: ( '\\r' )? '\\n'
                    {
                    // InternalKiSim.g:5806:53: ( '\\r' )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='\r') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // InternalKiSim.g:5806:53: '\\r'
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
            // InternalKiSim.g:5808:17: ( '//' ~ ( '*' ) (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalKiSim.g:5808:19: '//' ~ ( '*' ) (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            if ( (input.LA(1)>='\u0000' && input.LA(1)<=')')||(input.LA(1)>='+' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalKiSim.g:5808:31: (~ ( ( '\\n' | '\\r' ) ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='\u0000' && LA10_0<='\t')||(LA10_0>='\u000B' && LA10_0<='\f')||(LA10_0>='\u000E' && LA10_0<='\uFFFF')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalKiSim.g:5808:31: ~ ( ( '\\n' | '\\r' ) )
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
            	    break loop10;
                }
            } while (true);

            // InternalKiSim.g:5808:47: ( ( '\\r' )? '\\n' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='\n'||LA12_0=='\r') ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalKiSim.g:5808:48: ( '\\r' )? '\\n'
                    {
                    // InternalKiSim.g:5808:48: ( '\\r' )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0=='\r') ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // InternalKiSim.g:5808:48: '\\r'
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
            // InternalKiSim.g:5810:22: ( '0' .. '9' )
            // InternalKiSim.g:5810:24: '0' .. '9'
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
            // InternalKiSim.g:5812:10: ( ( RULE_NUMBER )+ )
            // InternalKiSim.g:5812:12: ( RULE_NUMBER )+
            {
            // InternalKiSim.g:5812:12: ( RULE_NUMBER )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>='0' && LA13_0<='9')) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalKiSim.g:5812:12: RULE_NUMBER
            	    {
            	    mRULE_NUMBER(); 

            	    }
            	    break;

            	default :
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
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
            // InternalKiSim.g:5814:12: ( ( ( RULE_NUMBER )+ '.' ( RULE_NUMBER )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )? ( 'f' )? | ( RULE_NUMBER )+ 'f' ) )
            // InternalKiSim.g:5814:14: ( ( RULE_NUMBER )+ '.' ( RULE_NUMBER )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )? ( 'f' )? | ( RULE_NUMBER )+ 'f' )
            {
            // InternalKiSim.g:5814:14: ( ( RULE_NUMBER )+ '.' ( RULE_NUMBER )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )? ( 'f' )? | ( RULE_NUMBER )+ 'f' )
            int alt21=2;
            alt21 = dfa21.predict(input);
            switch (alt21) {
                case 1 :
                    // InternalKiSim.g:5814:15: ( RULE_NUMBER )+ '.' ( RULE_NUMBER )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )? ( 'f' )?
                    {
                    // InternalKiSim.g:5814:15: ( RULE_NUMBER )+
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
                    	    // InternalKiSim.g:5814:15: RULE_NUMBER
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

                    match('.'); 
                    // InternalKiSim.g:5814:32: ( RULE_NUMBER )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( ((LA15_0>='0' && LA15_0<='9')) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // InternalKiSim.g:5814:32: RULE_NUMBER
                    	    {
                    	    mRULE_NUMBER(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);

                    // InternalKiSim.g:5814:45: ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0=='E'||LA18_0=='e') ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // InternalKiSim.g:5814:46: ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+
                            {
                            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}

                            // InternalKiSim.g:5814:56: ( '+' | '-' )?
                            int alt16=2;
                            int LA16_0 = input.LA(1);

                            if ( (LA16_0=='+'||LA16_0=='-') ) {
                                alt16=1;
                            }
                            switch (alt16) {
                                case 1 :
                                    // InternalKiSim.g:
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

                            // InternalKiSim.g:5814:67: ( RULE_NUMBER )+
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
                            	    // InternalKiSim.g:5814:67: RULE_NUMBER
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


                            }
                            break;

                    }

                    // InternalKiSim.g:5814:82: ( 'f' )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0=='f') ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // InternalKiSim.g:5814:82: 'f'
                            {
                            match('f'); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // InternalKiSim.g:5814:87: ( RULE_NUMBER )+ 'f'
                    {
                    // InternalKiSim.g:5814:87: ( RULE_NUMBER )+
                    int cnt20=0;
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( ((LA20_0>='0' && LA20_0<='9')) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // InternalKiSim.g:5814:87: RULE_NUMBER
                    	    {
                    	    mRULE_NUMBER(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt20 >= 1 ) break loop20;
                                EarlyExitException eee =
                                    new EarlyExitException(20, input);
                                throw eee;
                        }
                        cnt20++;
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

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKiSim.g:5816:13: ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' )
            // InternalKiSim.g:5816:15: '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
            {
            match('\"'); 
            // InternalKiSim.g:5816:19: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )*
            loop22:
            do {
                int alt22=3;
                int LA22_0 = input.LA(1);

                if ( (LA22_0=='\\') ) {
                    alt22=1;
                }
                else if ( ((LA22_0>='\u0000' && LA22_0<='!')||(LA22_0>='#' && LA22_0<='[')||(LA22_0>=']' && LA22_0<='\uFFFF')) ) {
                    alt22=2;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalKiSim.g:5816:20: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
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
            	    // InternalKiSim.g:5816:61: ~ ( ( '\\\\' | '\"' ) )
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
            	    break loop22;
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

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKiSim.g:5818:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalKiSim.g:5818:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalKiSim.g:5818:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
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
            	    // InternalKiSim.g:
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
            // InternalKiSim.g:5820:16: ( . )
            // InternalKiSim.g:5820:18: .
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
        // InternalKiSim.g:1:8: ( T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | RULE_BOOLEAN | RULE_ID | RULE_HOSTCODE | RULE_COMMENT_ANNOTATION | RULE_ML_COMMENT | RULE_SL_COMMENT_ANNOTATION | RULE_SL_COMMENT | RULE_INT | RULE_FLOAT | RULE_STRING | RULE_WS | RULE_ANY_OTHER )
        int alt24=61;
        alt24 = dfa24.predict(input);
        switch (alt24) {
            case 1 :
                // InternalKiSim.g:1:10: T__17
                {
                mT__17(); 

                }
                break;
            case 2 :
                // InternalKiSim.g:1:16: T__18
                {
                mT__18(); 

                }
                break;
            case 3 :
                // InternalKiSim.g:1:22: T__19
                {
                mT__19(); 

                }
                break;
            case 4 :
                // InternalKiSim.g:1:28: T__20
                {
                mT__20(); 

                }
                break;
            case 5 :
                // InternalKiSim.g:1:34: T__21
                {
                mT__21(); 

                }
                break;
            case 6 :
                // InternalKiSim.g:1:40: T__22
                {
                mT__22(); 

                }
                break;
            case 7 :
                // InternalKiSim.g:1:46: T__23
                {
                mT__23(); 

                }
                break;
            case 8 :
                // InternalKiSim.g:1:52: T__24
                {
                mT__24(); 

                }
                break;
            case 9 :
                // InternalKiSim.g:1:58: T__25
                {
                mT__25(); 

                }
                break;
            case 10 :
                // InternalKiSim.g:1:64: T__26
                {
                mT__26(); 

                }
                break;
            case 11 :
                // InternalKiSim.g:1:70: T__27
                {
                mT__27(); 

                }
                break;
            case 12 :
                // InternalKiSim.g:1:76: T__28
                {
                mT__28(); 

                }
                break;
            case 13 :
                // InternalKiSim.g:1:82: T__29
                {
                mT__29(); 

                }
                break;
            case 14 :
                // InternalKiSim.g:1:88: T__30
                {
                mT__30(); 

                }
                break;
            case 15 :
                // InternalKiSim.g:1:94: T__31
                {
                mT__31(); 

                }
                break;
            case 16 :
                // InternalKiSim.g:1:100: T__32
                {
                mT__32(); 

                }
                break;
            case 17 :
                // InternalKiSim.g:1:106: T__33
                {
                mT__33(); 

                }
                break;
            case 18 :
                // InternalKiSim.g:1:112: T__34
                {
                mT__34(); 

                }
                break;
            case 19 :
                // InternalKiSim.g:1:118: T__35
                {
                mT__35(); 

                }
                break;
            case 20 :
                // InternalKiSim.g:1:124: T__36
                {
                mT__36(); 

                }
                break;
            case 21 :
                // InternalKiSim.g:1:130: T__37
                {
                mT__37(); 

                }
                break;
            case 22 :
                // InternalKiSim.g:1:136: T__38
                {
                mT__38(); 

                }
                break;
            case 23 :
                // InternalKiSim.g:1:142: T__39
                {
                mT__39(); 

                }
                break;
            case 24 :
                // InternalKiSim.g:1:148: T__40
                {
                mT__40(); 

                }
                break;
            case 25 :
                // InternalKiSim.g:1:154: T__41
                {
                mT__41(); 

                }
                break;
            case 26 :
                // InternalKiSim.g:1:160: T__42
                {
                mT__42(); 

                }
                break;
            case 27 :
                // InternalKiSim.g:1:166: T__43
                {
                mT__43(); 

                }
                break;
            case 28 :
                // InternalKiSim.g:1:172: T__44
                {
                mT__44(); 

                }
                break;
            case 29 :
                // InternalKiSim.g:1:178: T__45
                {
                mT__45(); 

                }
                break;
            case 30 :
                // InternalKiSim.g:1:184: T__46
                {
                mT__46(); 

                }
                break;
            case 31 :
                // InternalKiSim.g:1:190: T__47
                {
                mT__47(); 

                }
                break;
            case 32 :
                // InternalKiSim.g:1:196: T__48
                {
                mT__48(); 

                }
                break;
            case 33 :
                // InternalKiSim.g:1:202: T__49
                {
                mT__49(); 

                }
                break;
            case 34 :
                // InternalKiSim.g:1:208: T__50
                {
                mT__50(); 

                }
                break;
            case 35 :
                // InternalKiSim.g:1:214: T__51
                {
                mT__51(); 

                }
                break;
            case 36 :
                // InternalKiSim.g:1:220: T__52
                {
                mT__52(); 

                }
                break;
            case 37 :
                // InternalKiSim.g:1:226: T__53
                {
                mT__53(); 

                }
                break;
            case 38 :
                // InternalKiSim.g:1:232: T__54
                {
                mT__54(); 

                }
                break;
            case 39 :
                // InternalKiSim.g:1:238: T__55
                {
                mT__55(); 

                }
                break;
            case 40 :
                // InternalKiSim.g:1:244: T__56
                {
                mT__56(); 

                }
                break;
            case 41 :
                // InternalKiSim.g:1:250: T__57
                {
                mT__57(); 

                }
                break;
            case 42 :
                // InternalKiSim.g:1:256: T__58
                {
                mT__58(); 

                }
                break;
            case 43 :
                // InternalKiSim.g:1:262: T__59
                {
                mT__59(); 

                }
                break;
            case 44 :
                // InternalKiSim.g:1:268: T__60
                {
                mT__60(); 

                }
                break;
            case 45 :
                // InternalKiSim.g:1:274: T__61
                {
                mT__61(); 

                }
                break;
            case 46 :
                // InternalKiSim.g:1:280: T__62
                {
                mT__62(); 

                }
                break;
            case 47 :
                // InternalKiSim.g:1:286: T__63
                {
                mT__63(); 

                }
                break;
            case 48 :
                // InternalKiSim.g:1:292: T__64
                {
                mT__64(); 

                }
                break;
            case 49 :
                // InternalKiSim.g:1:298: T__65
                {
                mT__65(); 

                }
                break;
            case 50 :
                // InternalKiSim.g:1:304: RULE_BOOLEAN
                {
                mRULE_BOOLEAN(); 

                }
                break;
            case 51 :
                // InternalKiSim.g:1:317: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 52 :
                // InternalKiSim.g:1:325: RULE_HOSTCODE
                {
                mRULE_HOSTCODE(); 

                }
                break;
            case 53 :
                // InternalKiSim.g:1:339: RULE_COMMENT_ANNOTATION
                {
                mRULE_COMMENT_ANNOTATION(); 

                }
                break;
            case 54 :
                // InternalKiSim.g:1:363: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 55 :
                // InternalKiSim.g:1:379: RULE_SL_COMMENT_ANNOTATION
                {
                mRULE_SL_COMMENT_ANNOTATION(); 

                }
                break;
            case 56 :
                // InternalKiSim.g:1:406: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 57 :
                // InternalKiSim.g:1:422: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 58 :
                // InternalKiSim.g:1:431: RULE_FLOAT
                {
                mRULE_FLOAT(); 

                }
                break;
            case 59 :
                // InternalKiSim.g:1:442: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 60 :
                // InternalKiSim.g:1:454: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 61 :
                // InternalKiSim.g:1:462: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA21 dfa21 = new DFA21(this);
    protected DFA24 dfa24 = new DFA24(this);
    static final String DFA21_eotS =
        "\4\uffff";
    static final String DFA21_eofS =
        "\4\uffff";
    static final String DFA21_minS =
        "\1\60\1\56\2\uffff";
    static final String DFA21_maxS =
        "\1\71\1\146\2\uffff";
    static final String DFA21_acceptS =
        "\2\uffff\1\1\1\2";
    static final String DFA21_specialS =
        "\4\uffff}>";
    static final String[] DFA21_transitionS = {
            "\12\1",
            "\1\2\1\uffff\12\1\54\uffff\1\3",
            "",
            ""
    };

    static final short[] DFA21_eot = DFA.unpackEncodedString(DFA21_eotS);
    static final short[] DFA21_eof = DFA.unpackEncodedString(DFA21_eofS);
    static final char[] DFA21_min = DFA.unpackEncodedStringToUnsignedChars(DFA21_minS);
    static final char[] DFA21_max = DFA.unpackEncodedStringToUnsignedChars(DFA21_maxS);
    static final short[] DFA21_accept = DFA.unpackEncodedString(DFA21_acceptS);
    static final short[] DFA21_special = DFA.unpackEncodedString(DFA21_specialS);
    static final short[][] DFA21_transition;

    static {
        int numStates = DFA21_transitionS.length;
        DFA21_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA21_transition[i] = DFA.unpackEncodedString(DFA21_transitionS[i]);
        }
    }

    static class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = DFA21_eot;
            this.eof = DFA21_eof;
            this.min = DFA21_min;
            this.max = DFA21_max;
            this.accept = DFA21_accept;
            this.special = DFA21_special;
            this.transition = DFA21_transition;
        }
        public String getDescription() {
            return "5814:14: ( ( RULE_NUMBER )+ '.' ( RULE_NUMBER )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )? ( 'f' )? | ( RULE_NUMBER )+ 'f' )";
        }
    }
    static final String DFA24_eotS =
        "\1\uffff\1\55\2\uffff\7\55\4\uffff\1\76\1\100\1\101\1\104\1\107\3\uffff\1\115\1\uffff\1\120\3\uffff\1\125\1\126\2\uffff\1\53\1\uffff\1\55\1\uffff\1\55\1\uffff\1\53\1\137\1\53\2\uffff\1\55\3\uffff\11\55\14\uffff\1\162\6\uffff\1\55\16\uffff\1\55\1\uffff\1\55\2\uffff\1\137\3\uffff\11\55\1\u0083\3\55\5\uffff\1\55\1\uffff\1\u0088\12\55\1\uffff\1\55\1\u0094\2\55\1\uffff\6\55\1\u009c\4\55\1\uffff\1\55\1\u0094\4\55\1\u00a6\1\uffff\11\55\1\uffff\2\55\1\u00b2\1\55\1\u00b4\1\55\1\u00b6\3\55\1\u00ba\1\uffff\1\u00bb\1\uffff\1\u00bc\1\uffff\1\55\1\u00be\1\55\3\uffff\1\55\1\uffff\1\u00c1\1\55\1\uffff\2\55\1\u00c5\1\uffff";
    static final String DFA24_eofS =
        "\u00c6\uffff";
    static final String DFA24_minS =
        "\1\0\1\157\2\uffff\1\156\1\170\1\157\1\143\1\162\1\145\1\162\4\uffff\1\174\1\46\1\57\1\74\1\75\3\uffff\1\52\1\uffff\1\51\3\uffff\1\75\1\55\2\uffff\1\75\1\uffff\1\141\1\uffff\1\141\1\uffff\1\0\1\56\1\0\2\uffff\1\155\3\uffff\1\151\1\145\1\144\1\155\1\150\1\145\1\155\1\165\1\141\14\uffff\1\76\5\uffff\2\0\16\uffff\1\154\1\uffff\1\154\2\uffff\1\56\3\uffff\1\146\1\160\1\164\1\143\2\145\1\165\1\145\1\143\1\55\1\160\1\145\1\160\5\uffff\1\0\1\uffff\1\55\1\163\3\151\1\165\1\162\2\154\1\144\1\145\1\uffff\1\154\1\55\1\160\1\0\1\uffff\1\145\1\147\1\154\1\141\1\164\1\156\1\55\1\141\1\165\1\163\1\141\1\uffff\1\145\1\55\1\165\1\145\1\154\1\151\1\55\1\uffff\1\164\1\154\1\163\1\164\3\162\1\151\1\157\1\uffff\1\151\1\145\1\55\1\145\1\55\1\145\1\55\1\172\1\156\1\157\1\55\1\uffff\1\55\1\uffff\1\55\1\uffff\1\141\1\55\1\156\3\uffff\1\164\1\uffff\1\55\1\151\1\uffff\1\157\1\156\1\55\1\uffff";
    static final String DFA24_maxS =
        "\1\uffff\1\157\2\uffff\1\156\1\170\1\157\1\151\3\162\4\uffff\1\174\1\46\1\172\1\75\1\76\3\uffff\1\172\1\uffff\1\51\3\uffff\1\75\1\172\2\uffff\1\75\1\uffff\1\141\1\uffff\1\141\1\uffff\1\uffff\1\146\1\uffff\2\uffff\1\156\3\uffff\1\151\1\164\1\144\1\155\1\150\1\157\1\155\1\165\1\141\14\uffff\1\76\5\uffff\2\uffff\16\uffff\1\154\1\uffff\1\154\2\uffff\1\146\3\uffff\1\146\1\160\1\164\1\143\2\145\1\165\1\145\1\143\1\172\1\160\1\145\1\160\5\uffff\1\uffff\1\uffff\1\172\1\163\3\151\1\165\1\162\2\154\1\144\1\145\1\uffff\1\154\1\172\1\160\1\uffff\1\uffff\1\145\1\147\1\154\1\141\1\164\1\156\1\172\1\141\1\165\1\163\1\141\1\uffff\1\145\1\172\1\165\1\145\1\154\1\151\1\172\1\uffff\1\164\1\154\1\163\1\164\3\162\1\151\1\157\1\uffff\1\151\1\145\1\172\1\145\1\172\1\145\2\172\1\156\1\157\1\172\1\uffff\1\172\1\uffff\1\172\1\uffff\1\141\1\172\1\156\3\uffff\1\164\1\uffff\1\172\1\151\1\uffff\1\157\1\156\1\172\1\uffff";
    static final String DFA24_acceptS =
        "\2\uffff\1\2\1\3\7\uffff\1\14\1\15\1\16\1\17\5\uffff\1\31\1\32\1\33\1\uffff\1\35\1\uffff\1\37\1\40\1\41\2\uffff\1\46\1\47\1\uffff\1\57\1\uffff\1\61\1\uffff\1\63\3\uffff\1\74\1\75\1\uffff\1\63\1\2\1\3\11\uffff\1\14\1\15\1\16\1\17\1\21\1\23\1\22\1\25\1\24\1\26\1\52\1\51\1\uffff\1\54\1\53\1\31\1\32\1\33\2\uffff\1\34\1\35\1\42\1\36\1\37\1\40\1\41\1\55\1\44\1\45\1\46\1\47\1\50\1\57\1\uffff\1\61\1\uffff\1\64\1\71\1\uffff\1\72\1\73\1\74\15\uffff\1\30\1\27\1\65\1\66\1\67\1\uffff\1\70\13\uffff\1\56\4\uffff\1\60\13\uffff\1\62\7\uffff\1\6\11\uffff\1\43\13\uffff\1\11\1\uffff\1\13\1\uffff\1\7\3\uffff\1\20\1\12\1\1\1\uffff\1\5\2\uffff\1\10\3\uffff\1\4";
    static final String DFA24_specialS =
        "\1\2\46\uffff\1\1\1\uffff\1\5\41\uffff\1\6\1\0\51\uffff\1\4\20\uffff\1\3\76\uffff}>";
    static final String[] DFA24_transitionS = {
            "\11\53\2\52\2\53\1\52\22\53\1\52\1\35\1\51\1\40\1\53\1\30\1\20\1\33\1\31\1\32\1\26\1\24\1\14\1\25\1\34\1\27\12\50\1\13\1\53\1\22\1\41\1\23\1\44\1\37\32\46\1\15\1\46\1\16\1\21\1\36\1\47\2\46\1\1\1\46\1\5\1\45\2\46\1\4\3\46\1\6\2\46\1\10\2\46\1\7\1\11\1\46\1\43\1\12\3\46\1\2\1\17\1\3\1\42\uff81\53",
            "\1\54",
            "",
            "",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\64\5\uffff\1\63",
            "\1\65",
            "\1\66\14\uffff\1\67",
            "\1\70",
            "",
            "",
            "",
            "",
            "\1\75",
            "\1\77",
            "\1\55\21\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "\1\102\1\103",
            "\1\106\1\105",
            "",
            "",
            "",
            "\1\113\2\uffff\2\55\1\114\12\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "",
            "\1\117",
            "",
            "",
            "",
            "\1\124",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "",
            "",
            "\1\131",
            "",
            "\1\133",
            "",
            "\1\135",
            "",
            "\0\136",
            "\1\141\1\uffff\12\140\54\uffff\1\141",
            "\0\142",
            "",
            "",
            "\1\145\1\144",
            "",
            "",
            "",
            "\1\146",
            "\1\147\16\uffff\1\150",
            "\1\151",
            "\1\152",
            "\1\153",
            "\1\155\11\uffff\1\154",
            "\1\156",
            "\1\157",
            "\1\160",
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
            "\1\161",
            "",
            "",
            "",
            "",
            "",
            "\52\164\1\163\uffd5\164",
            "\52\167\1\165\2\167\15\166\7\167\32\166\1\167\1\166\2\167\1\166\1\167\32\166\uff85\167",
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
            "",
            "",
            "\1\170",
            "",
            "\1\171",
            "",
            "",
            "\1\141\1\uffff\12\140\54\uffff\1\141",
            "",
            "",
            "",
            "\1\172",
            "\1\173",
            "\1\174",
            "\1\175",
            "\1\176",
            "\1\177",
            "\1\u0080",
            "\1\u0081",
            "\1\u0082",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "\1\u0084",
            "\1\u0085",
            "\1\u0086",
            "",
            "",
            "",
            "",
            "",
            "\55\167\15\u0087\7\167\32\u0087\1\167\1\u0087\2\167\1\u0087\1\167\32\u0087\uff85\167",
            "",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "\1\u0089",
            "\1\u008a",
            "\1\u008b",
            "\1\u008c",
            "\1\u008d",
            "\1\u008e",
            "\1\u008f",
            "\1\u0090",
            "\1\u0091",
            "\1\u0092",
            "",
            "\1\u0093",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "\1\u0095",
            "\55\167\15\u0087\7\167\32\u0087\1\167\1\u0087\2\167\1\u0087\1\167\32\u0087\uff85\167",
            "",
            "\1\u0096",
            "\1\u0097",
            "\1\u0098",
            "\1\u0099",
            "\1\u009a",
            "\1\u009b",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "\1\u009d",
            "\1\u009e",
            "\1\u009f",
            "\1\u00a0",
            "",
            "\1\u00a1",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "\1\u00a2",
            "\1\u00a3",
            "\1\u00a4",
            "\1\u00a5",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "",
            "\1\u00a7",
            "\1\u00a8",
            "\1\u00a9",
            "\1\u00aa",
            "\1\u00ab",
            "\1\u00ac",
            "\1\u00ad",
            "\1\u00ae",
            "\1\u00af",
            "",
            "\1\u00b0",
            "\1\u00b1",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "\1\u00b3",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "\1\u00b5",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "\1\u00b7",
            "\1\u00b8",
            "\1\u00b9",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "",
            "\1\u00bd",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "\1\u00bf",
            "",
            "",
            "",
            "\1\u00c0",
            "",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
            "\1\u00c2",
            "",
            "\1\u00c3",
            "\1\u00c4",
            "\15\55\7\uffff\32\55\1\uffff\1\55\2\uffff\1\55\1\uffff\32\55",
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
            return "1:1: Tokens : ( T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | RULE_BOOLEAN | RULE_ID | RULE_HOSTCODE | RULE_COMMENT_ANNOTATION | RULE_ML_COMMENT | RULE_SL_COMMENT_ANNOTATION | RULE_SL_COMMENT | RULE_INT | RULE_FLOAT | RULE_STRING | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA24_76 = input.LA(1);

                        s = -1;
                        if ( (LA24_76=='*') ) {s = 117;}

                        else if ( ((LA24_76>='-' && LA24_76<='9')||(LA24_76>='A' && LA24_76<='Z')||LA24_76=='\\'||LA24_76=='_'||(LA24_76>='a' && LA24_76<='z')) ) {s = 118;}

                        else if ( ((LA24_76>='\u0000' && LA24_76<=')')||(LA24_76>='+' && LA24_76<=',')||(LA24_76>=':' && LA24_76<='@')||LA24_76=='['||(LA24_76>=']' && LA24_76<='^')||LA24_76=='`'||(LA24_76>='{' && LA24_76<='\uFFFF')) ) {s = 119;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA24_39 = input.LA(1);

                        s = -1;
                        if ( ((LA24_39>='\u0000' && LA24_39<='\uFFFF')) ) {s = 94;}

                        else s = 43;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA24_0 = input.LA(1);

                        s = -1;
                        if ( (LA24_0=='c') ) {s = 1;}

                        else if ( (LA24_0=='{') ) {s = 2;}

                        else if ( (LA24_0=='}') ) {s = 3;}

                        else if ( (LA24_0=='i') ) {s = 4;}

                        else if ( (LA24_0=='e') ) {s = 5;}

                        else if ( (LA24_0=='m') ) {s = 6;}

                        else if ( (LA24_0=='s') ) {s = 7;}

                        else if ( (LA24_0=='p') ) {s = 8;}

                        else if ( (LA24_0=='t') ) {s = 9;}

                        else if ( (LA24_0=='w') ) {s = 10;}

                        else if ( (LA24_0==':') ) {s = 11;}

                        else if ( (LA24_0==',') ) {s = 12;}

                        else if ( (LA24_0=='[') ) {s = 13;}

                        else if ( (LA24_0==']') ) {s = 14;}

                        else if ( (LA24_0=='|') ) {s = 15;}

                        else if ( (LA24_0=='&') ) {s = 16;}

                        else if ( (LA24_0=='^') ) {s = 17;}

                        else if ( (LA24_0=='<') ) {s = 18;}

                        else if ( (LA24_0=='>') ) {s = 19;}

                        else if ( (LA24_0=='+') ) {s = 20;}

                        else if ( (LA24_0=='-') ) {s = 21;}

                        else if ( (LA24_0=='*') ) {s = 22;}

                        else if ( (LA24_0=='/') ) {s = 23;}

                        else if ( (LA24_0=='%') ) {s = 24;}

                        else if ( (LA24_0=='(') ) {s = 25;}

                        else if ( (LA24_0==')') ) {s = 26;}

                        else if ( (LA24_0=='\'') ) {s = 27;}

                        else if ( (LA24_0=='.') ) {s = 28;}

                        else if ( (LA24_0=='!') ) {s = 29;}

                        else if ( (LA24_0=='_') ) {s = 30;}

                        else if ( (LA24_0=='@') ) {s = 31;}

                        else if ( (LA24_0=='#') ) {s = 32;}

                        else if ( (LA24_0=='=') ) {s = 33;}

                        else if ( (LA24_0=='~') ) {s = 34;}

                        else if ( (LA24_0=='v') ) {s = 35;}

                        else if ( (LA24_0=='?') ) {s = 36;}

                        else if ( (LA24_0=='f') ) {s = 37;}

                        else if ( ((LA24_0>='A' && LA24_0<='Z')||LA24_0=='\\'||(LA24_0>='a' && LA24_0<='b')||LA24_0=='d'||(LA24_0>='g' && LA24_0<='h')||(LA24_0>='j' && LA24_0<='l')||(LA24_0>='n' && LA24_0<='o')||(LA24_0>='q' && LA24_0<='r')||LA24_0=='u'||(LA24_0>='x' && LA24_0<='z')) ) {s = 38;}

                        else if ( (LA24_0=='`') ) {s = 39;}

                        else if ( ((LA24_0>='0' && LA24_0<='9')) ) {s = 40;}

                        else if ( (LA24_0=='\"') ) {s = 41;}

                        else if ( ((LA24_0>='\t' && LA24_0<='\n')||LA24_0=='\r'||LA24_0==' ') ) {s = 42;}

                        else if ( ((LA24_0>='\u0000' && LA24_0<='\b')||(LA24_0>='\u000B' && LA24_0<='\f')||(LA24_0>='\u000E' && LA24_0<='\u001F')||LA24_0=='$'||LA24_0==';'||(LA24_0>='\u007F' && LA24_0<='\uFFFF')) ) {s = 43;}

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA24_135 = input.LA(1);

                        s = -1;
                        if ( ((LA24_135>='\u0000' && LA24_135<=',')||(LA24_135>=':' && LA24_135<='@')||LA24_135=='['||(LA24_135>=']' && LA24_135<='^')||LA24_135=='`'||(LA24_135>='{' && LA24_135<='\uFFFF')) ) {s = 119;}

                        else if ( ((LA24_135>='-' && LA24_135<='9')||(LA24_135>='A' && LA24_135<='Z')||LA24_135=='\\'||LA24_135=='_'||(LA24_135>='a' && LA24_135<='z')) ) {s = 135;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA24_118 = input.LA(1);

                        s = -1;
                        if ( ((LA24_118>='-' && LA24_118<='9')||(LA24_118>='A' && LA24_118<='Z')||LA24_118=='\\'||LA24_118=='_'||(LA24_118>='a' && LA24_118<='z')) ) {s = 135;}

                        else if ( ((LA24_118>='\u0000' && LA24_118<=',')||(LA24_118>=':' && LA24_118<='@')||LA24_118=='['||(LA24_118>=']' && LA24_118<='^')||LA24_118=='`'||(LA24_118>='{' && LA24_118<='\uFFFF')) ) {s = 119;}

                        else s = 45;

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA24_41 = input.LA(1);

                        s = -1;
                        if ( ((LA24_41>='\u0000' && LA24_41<='\uFFFF')) ) {s = 98;}

                        else s = 43;

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA24_75 = input.LA(1);

                        s = -1;
                        if ( (LA24_75=='*') ) {s = 115;}

                        else if ( ((LA24_75>='\u0000' && LA24_75<=')')||(LA24_75>='+' && LA24_75<='\uFFFF')) ) {s = 116;}

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