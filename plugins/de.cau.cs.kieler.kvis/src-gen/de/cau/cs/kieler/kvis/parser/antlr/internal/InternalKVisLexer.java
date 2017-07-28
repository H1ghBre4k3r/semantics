package de.cau.cs.kieler.kvis.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalKVisLexer extends Lexer {
    public static final int T__50=50;
    public static final int RULE_BOOLEAN=9;
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
    public static final int RULE_ID=5;
    public static final int T__26=26;
    public static final int RULE_HOSTCODE=8;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=6;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=11;
    public static final int T__23=23;
    public static final int T__67=67;
    public static final int T__24=24;
    public static final int T__68=68;
    public static final int T__25=25;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__20=20;
    public static final int T__64=64;
    public static final int T__21=21;
    public static final int T__65=65;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int RULE_COMMENT_ANNOTATION=10;
    public static final int RULE_STRING=4;
    public static final int RULE_SL_COMMENT=13;
    public static final int T__37=37;
    public static final int RULE_SL_COMMENT_ANNOTATION=12;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__73=73;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__74=74;
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

    public InternalKVisLexer() {;} 
    public InternalKVisLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalKVisLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalKVis.g"; }

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKVis.g:11:7: ( 'image' )
            // InternalKVis.g:11:9: 'image'
            {
            match("image"); 


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
            // InternalKVis.g:12:7: ( ':' )
            // InternalKVis.g:12:9: ':'
            {
            match(':'); 

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
            // InternalKVis.g:13:7: ( 'animate' )
            // InternalKVis.g:13:9: 'animate'
            {
            match("animate"); 


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
            // InternalKVis.g:14:7: ( '{' )
            // InternalKVis.g:14:9: '{'
            {
            match('{'); 

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
            // InternalKVis.g:15:7: ( '}' )
            // InternalKVis.g:15:9: '}'
            {
            match('}'); 

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
            // InternalKVis.g:16:7: ( 'perform' )
            // InternalKVis.g:16:9: 'perform'
            {
            match("perform"); 


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
            // InternalKVis.g:17:7: ( 'on' )
            // InternalKVis.g:17:9: 'on'
            {
            match("on"); 


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
            // InternalKVis.g:18:7: ( 'when' )
            // InternalKVis.g:18:9: 'when'
            {
            match("when"); 


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
            // InternalKVis.g:19:7: ( 'from' )
            // InternalKVis.g:19:9: 'from'
            {
            match("from"); 


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
            // InternalKVis.g:20:7: ( '=' )
            // InternalKVis.g:20:9: '='
            {
            match('='); 

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
            // InternalKVis.g:21:7: ( 'simulation' )
            // InternalKVis.g:21:9: 'simulation'
            {
            match("simulation"); 


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
            // InternalKVis.g:22:7: ( 'apply' )
            // InternalKVis.g:22:9: 'apply'
            {
            match("apply"); 


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
            // InternalKVis.g:23:7: ( 'using' )
            // InternalKVis.g:23:9: 'using'
            {
            match("using"); 


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
            // InternalKVis.g:24:7: ( ',' )
            // InternalKVis.g:24:9: ','
            {
            match(','); 

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
            // InternalKVis.g:25:7: ( 'is' )
            // InternalKVis.g:25:9: 'is'
            {
            match("is"); 


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
            // InternalKVis.g:26:7: ( 'and' )
            // InternalKVis.g:26:9: 'and'
            {
            match("and"); 


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
            // InternalKVis.g:27:7: ( '[' )
            // InternalKVis.g:27:9: '['
            {
            match('['); 

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
            // InternalKVis.g:28:7: ( ']' )
            // InternalKVis.g:28:9: ']'
            {
            match(']'); 

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
            // InternalKVis.g:29:7: ( '.' )
            // InternalKVis.g:29:9: '.'
            {
            match('.'); 

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
            // InternalKVis.g:30:7: ( '-' )
            // InternalKVis.g:30:9: '-'
            {
            match('-'); 

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
            // InternalKVis.g:31:7: ( 'model' )
            // InternalKVis.g:31:9: 'model'
            {
            match("model"); 


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
            // InternalKVis.g:32:7: ( 'compiler' )
            // InternalKVis.g:32:9: 'compiler'
            {
            match("compiler"); 


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
            // InternalKVis.g:33:7: ( 'process' )
            // InternalKVis.g:33:9: 'process'
            {
            match("process"); 


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
            // InternalKVis.g:34:7: ( 'template' )
            // InternalKVis.g:34:9: 'template'
            {
            match("template"); 


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
            // InternalKVis.g:35:7: ( 'wrapper' )
            // InternalKVis.g:35:9: 'wrapper'
            {
            match("wrapper"); 


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
            // InternalKVis.g:36:7: ( 'schedule' )
            // InternalKVis.g:36:9: 'schedule'
            {
            match("schedule"); 


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
            // InternalKVis.g:37:7: ( '||' )
            // InternalKVis.g:37:9: '||'
            {
            match("||"); 


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
            // InternalKVis.g:38:7: ( '&&' )
            // InternalKVis.g:38:9: '&&'
            {
            match("&&"); 


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
            // InternalKVis.g:39:7: ( '|' )
            // InternalKVis.g:39:9: '|'
            {
            match('|'); 

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
            // InternalKVis.g:40:7: ( '&' )
            // InternalKVis.g:40:9: '&'
            {
            match('&'); 

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
            // InternalKVis.g:41:7: ( '+' )
            // InternalKVis.g:41:9: '+'
            {
            match('+'); 

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
            // InternalKVis.g:42:7: ( '*' )
            // InternalKVis.g:42:9: '*'
            {
            match('*'); 

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
            // InternalKVis.g:43:7: ( '/' )
            // InternalKVis.g:43:9: '/'
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
            // InternalKVis.g:44:7: ( '%' )
            // InternalKVis.g:44:9: '%'
            {
            match('%'); 

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
            // InternalKVis.g:45:7: ( '(' )
            // InternalKVis.g:45:9: '('
            {
            match('('); 

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
            // InternalKVis.g:46:7: ( ')' )
            // InternalKVis.g:46:9: ')'
            {
            match(')'); 

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
            // InternalKVis.g:47:7: ( '\\'' )
            // InternalKVis.g:47:9: '\\''
            {
            match('\''); 

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
            // InternalKVis.g:48:7: ( '()' )
            // InternalKVis.g:48:9: '()'
            {
            match("()"); 


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
            // InternalKVis.g:49:7: ( 'extern' )
            // InternalKVis.g:49:9: 'extern'
            {
            match("extern"); 


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
            // InternalKVis.g:50:7: ( '<' )
            // InternalKVis.g:50:9: '<'
            {
            match('<'); 

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
            // InternalKVis.g:51:7: ( '>' )
            // InternalKVis.g:51:9: '>'
            {
            match('>'); 

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
            // InternalKVis.g:52:7: ( '!' )
            // InternalKVis.g:52:9: '!'
            {
            match('!'); 

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
            // InternalKVis.g:53:7: ( '@' )
            // InternalKVis.g:53:9: '@'
            {
            match('@'); 

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
            // InternalKVis.g:54:7: ( '#' )
            // InternalKVis.g:54:9: '#'
            {
            match('#'); 

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
            // InternalKVis.g:55:7: ( 'click' )
            // InternalKVis.g:55:9: 'click'
            {
            match("click"); 


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
            // InternalKVis.g:56:7: ( 'mousedown' )
            // InternalKVis.g:56:9: 'mousedown'
            {
            match("mousedown"); 


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
            // InternalKVis.g:57:7: ( 'mouseup' )
            // InternalKVis.g:57:9: 'mouseup'
            {
            match("mouseup"); 


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
            // InternalKVis.g:58:7: ( 'mousemove' )
            // InternalKVis.g:58:9: 'mousemove'
            {
            match("mousemove"); 


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
            // InternalKVis.g:59:7: ( 'step' )
            // InternalKVis.g:59:9: 'step'
            {
            match("step"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKVis.g:60:7: ( 'stop' )
            // InternalKVis.g:60:9: 'stop'
            {
            match("stop"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKVis.g:61:7: ( 'pause' )
            // InternalKVis.g:61:9: 'pause'
            {
            match("pause"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__67"

    // $ANTLR start "T__68"
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKVis.g:62:7: ( 'play' )
            // InternalKVis.g:62:9: 'play'
            {
            match("play"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__68"

    // $ANTLR start "T__69"
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKVis.g:63:7: ( '==' )
            // InternalKVis.g:63:9: '=='
            {
            match("=="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__69"

    // $ANTLR start "T__70"
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKVis.g:64:7: ( '<=' )
            // InternalKVis.g:64:9: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__70"

    // $ANTLR start "T__71"
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKVis.g:65:7: ( '>=' )
            // InternalKVis.g:65:9: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__71"

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKVis.g:66:7: ( '!=' )
            // InternalKVis.g:66:9: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKVis.g:67:7: ( 'pre' )
            // InternalKVis.g:67:9: 'pre'
            {
            match("pre"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__73"

    // $ANTLR start "T__74"
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKVis.g:68:7: ( 'val' )
            // InternalKVis.g:68:9: 'val'
            {
            match("val"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__74"

    // $ANTLR start "RULE_BOOLEAN"
    public final void mRULE_BOOLEAN() throws RecognitionException {
        try {
            int _type = RULE_BOOLEAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalKVis.g:5757:14: ( ( 'true' | 'false' ) )
            // InternalKVis.g:5757:16: ( 'true' | 'false' )
            {
            // InternalKVis.g:5757:16: ( 'true' | 'false' )
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
                    // InternalKVis.g:5757:17: 'true'
                    {
                    match("true"); 


                    }
                    break;
                case 2 :
                    // InternalKVis.g:5757:24: 'false'
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
            // InternalKVis.g:5759:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '/' | '\\\\' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | '.' | '/' | '\\\\' | '0' .. '9' )* )
            // InternalKVis.g:5759:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '/' | '\\\\' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | '.' | '/' | '\\\\' | '0' .. '9' )*
            {
            // InternalKVis.g:5759:11: ( '^' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='^') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalKVis.g:5759:11: '^'
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

            // InternalKVis.g:5759:49: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | '.' | '/' | '\\\\' | '0' .. '9' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='-' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='Z')||LA3_0=='\\'||LA3_0=='_'||(LA3_0>='a' && LA3_0<='z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalKVis.g:
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
            // InternalKVis.g:5761:15: ( '`' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '`' ) ) )* '`' )
            // InternalKVis.g:5761:17: '`' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '`' ) ) )* '`'
            {
            match('`'); 
            // InternalKVis.g:5761:21: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '`' ) ) )*
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
            	    // InternalKVis.g:5761:22: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
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
            	    // InternalKVis.g:5761:63: ~ ( ( '\\\\' | '`' ) )
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
            // InternalKVis.g:5763:25: ( '/**' ( options {greedy=false; } : . )* '*/' )
            // InternalKVis.g:5763:27: '/**' ( options {greedy=false; } : . )* '*/'
            {
            match("/**"); 

            // InternalKVis.g:5763:33: ( options {greedy=false; } : . )*
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
            	    // InternalKVis.g:5763:61: .
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
            // InternalKVis.g:5765:17: ( '/*' ~ ( '*' ) ( options {greedy=false; } : . )* '*/' )
            // InternalKVis.g:5765:19: '/*' ~ ( '*' ) ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            if ( (input.LA(1)>='\u0000' && input.LA(1)<=')')||(input.LA(1)>='+' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalKVis.g:5765:31: ( options {greedy=false; } : . )*
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
            	    // InternalKVis.g:5765:59: .
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
            // InternalKVis.g:5767:28: ( '//*' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalKVis.g:5767:30: '//*' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//*"); 

            // InternalKVis.g:5767:36: (~ ( ( '\\n' | '\\r' ) ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='\u0000' && LA7_0<='\t')||(LA7_0>='\u000B' && LA7_0<='\f')||(LA7_0>='\u000E' && LA7_0<='\uFFFF')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalKVis.g:5767:36: ~ ( ( '\\n' | '\\r' ) )
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

            // InternalKVis.g:5767:52: ( ( '\\r' )? '\\n' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='\n'||LA9_0=='\r') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalKVis.g:5767:53: ( '\\r' )? '\\n'
                    {
                    // InternalKVis.g:5767:53: ( '\\r' )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='\r') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // InternalKVis.g:5767:53: '\\r'
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
            // InternalKVis.g:5769:17: ( '//' ~ ( '*' ) (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalKVis.g:5769:19: '//' ~ ( '*' ) (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            if ( (input.LA(1)>='\u0000' && input.LA(1)<=')')||(input.LA(1)>='+' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalKVis.g:5769:31: (~ ( ( '\\n' | '\\r' ) ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='\u0000' && LA10_0<='\t')||(LA10_0>='\u000B' && LA10_0<='\f')||(LA10_0>='\u000E' && LA10_0<='\uFFFF')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalKVis.g:5769:31: ~ ( ( '\\n' | '\\r' ) )
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

            // InternalKVis.g:5769:47: ( ( '\\r' )? '\\n' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='\n'||LA12_0=='\r') ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalKVis.g:5769:48: ( '\\r' )? '\\n'
                    {
                    // InternalKVis.g:5769:48: ( '\\r' )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0=='\r') ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // InternalKVis.g:5769:48: '\\r'
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
            // InternalKVis.g:5771:22: ( '0' .. '9' )
            // InternalKVis.g:5771:24: '0' .. '9'
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
            // InternalKVis.g:5773:10: ( ( RULE_NUMBER )+ )
            // InternalKVis.g:5773:12: ( RULE_NUMBER )+
            {
            // InternalKVis.g:5773:12: ( RULE_NUMBER )+
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
            	    // InternalKVis.g:5773:12: RULE_NUMBER
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
            // InternalKVis.g:5775:12: ( ( ( RULE_NUMBER )+ '.' ( RULE_NUMBER )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )? ( 'f' )? | ( RULE_NUMBER )+ 'f' ) )
            // InternalKVis.g:5775:14: ( ( RULE_NUMBER )+ '.' ( RULE_NUMBER )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )? ( 'f' )? | ( RULE_NUMBER )+ 'f' )
            {
            // InternalKVis.g:5775:14: ( ( RULE_NUMBER )+ '.' ( RULE_NUMBER )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )? ( 'f' )? | ( RULE_NUMBER )+ 'f' )
            int alt21=2;
            alt21 = dfa21.predict(input);
            switch (alt21) {
                case 1 :
                    // InternalKVis.g:5775:15: ( RULE_NUMBER )+ '.' ( RULE_NUMBER )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )? ( 'f' )?
                    {
                    // InternalKVis.g:5775:15: ( RULE_NUMBER )+
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
                    	    // InternalKVis.g:5775:15: RULE_NUMBER
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
                    // InternalKVis.g:5775:32: ( RULE_NUMBER )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( ((LA15_0>='0' && LA15_0<='9')) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // InternalKVis.g:5775:32: RULE_NUMBER
                    	    {
                    	    mRULE_NUMBER(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);

                    // InternalKVis.g:5775:45: ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0=='E'||LA18_0=='e') ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // InternalKVis.g:5775:46: ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+
                            {
                            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}

                            // InternalKVis.g:5775:56: ( '+' | '-' )?
                            int alt16=2;
                            int LA16_0 = input.LA(1);

                            if ( (LA16_0=='+'||LA16_0=='-') ) {
                                alt16=1;
                            }
                            switch (alt16) {
                                case 1 :
                                    // InternalKVis.g:
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

                            // InternalKVis.g:5775:67: ( RULE_NUMBER )+
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
                            	    // InternalKVis.g:5775:67: RULE_NUMBER
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

                    // InternalKVis.g:5775:82: ( 'f' )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0=='f') ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // InternalKVis.g:5775:82: 'f'
                            {
                            match('f'); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // InternalKVis.g:5775:87: ( RULE_NUMBER )+ 'f'
                    {
                    // InternalKVis.g:5775:87: ( RULE_NUMBER )+
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
                    	    // InternalKVis.g:5775:87: RULE_NUMBER
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
            // InternalKVis.g:5777:13: ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' )
            // InternalKVis.g:5777:15: '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
            {
            match('\"'); 
            // InternalKVis.g:5777:19: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )*
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
            	    // InternalKVis.g:5777:20: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
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
            	    // InternalKVis.g:5777:61: ~ ( ( '\\\\' | '\"' ) )
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
            // InternalKVis.g:5779:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalKVis.g:5779:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalKVis.g:5779:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
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
            	    // InternalKVis.g:
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
            // InternalKVis.g:5781:16: ( . )
            // InternalKVis.g:5781:18: .
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
        // InternalKVis.g:1:8: ( T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | RULE_BOOLEAN | RULE_ID | RULE_HOSTCODE | RULE_COMMENT_ANNOTATION | RULE_ML_COMMENT | RULE_SL_COMMENT_ANNOTATION | RULE_SL_COMMENT | RULE_INT | RULE_FLOAT | RULE_STRING | RULE_WS | RULE_ANY_OTHER )
        int alt24=70;
        alt24 = dfa24.predict(input);
        switch (alt24) {
            case 1 :
                // InternalKVis.g:1:10: T__17
                {
                mT__17(); 

                }
                break;
            case 2 :
                // InternalKVis.g:1:16: T__18
                {
                mT__18(); 

                }
                break;
            case 3 :
                // InternalKVis.g:1:22: T__19
                {
                mT__19(); 

                }
                break;
            case 4 :
                // InternalKVis.g:1:28: T__20
                {
                mT__20(); 

                }
                break;
            case 5 :
                // InternalKVis.g:1:34: T__21
                {
                mT__21(); 

                }
                break;
            case 6 :
                // InternalKVis.g:1:40: T__22
                {
                mT__22(); 

                }
                break;
            case 7 :
                // InternalKVis.g:1:46: T__23
                {
                mT__23(); 

                }
                break;
            case 8 :
                // InternalKVis.g:1:52: T__24
                {
                mT__24(); 

                }
                break;
            case 9 :
                // InternalKVis.g:1:58: T__25
                {
                mT__25(); 

                }
                break;
            case 10 :
                // InternalKVis.g:1:64: T__26
                {
                mT__26(); 

                }
                break;
            case 11 :
                // InternalKVis.g:1:70: T__27
                {
                mT__27(); 

                }
                break;
            case 12 :
                // InternalKVis.g:1:76: T__28
                {
                mT__28(); 

                }
                break;
            case 13 :
                // InternalKVis.g:1:82: T__29
                {
                mT__29(); 

                }
                break;
            case 14 :
                // InternalKVis.g:1:88: T__30
                {
                mT__30(); 

                }
                break;
            case 15 :
                // InternalKVis.g:1:94: T__31
                {
                mT__31(); 

                }
                break;
            case 16 :
                // InternalKVis.g:1:100: T__32
                {
                mT__32(); 

                }
                break;
            case 17 :
                // InternalKVis.g:1:106: T__33
                {
                mT__33(); 

                }
                break;
            case 18 :
                // InternalKVis.g:1:112: T__34
                {
                mT__34(); 

                }
                break;
            case 19 :
                // InternalKVis.g:1:118: T__35
                {
                mT__35(); 

                }
                break;
            case 20 :
                // InternalKVis.g:1:124: T__36
                {
                mT__36(); 

                }
                break;
            case 21 :
                // InternalKVis.g:1:130: T__37
                {
                mT__37(); 

                }
                break;
            case 22 :
                // InternalKVis.g:1:136: T__38
                {
                mT__38(); 

                }
                break;
            case 23 :
                // InternalKVis.g:1:142: T__39
                {
                mT__39(); 

                }
                break;
            case 24 :
                // InternalKVis.g:1:148: T__40
                {
                mT__40(); 

                }
                break;
            case 25 :
                // InternalKVis.g:1:154: T__41
                {
                mT__41(); 

                }
                break;
            case 26 :
                // InternalKVis.g:1:160: T__42
                {
                mT__42(); 

                }
                break;
            case 27 :
                // InternalKVis.g:1:166: T__43
                {
                mT__43(); 

                }
                break;
            case 28 :
                // InternalKVis.g:1:172: T__44
                {
                mT__44(); 

                }
                break;
            case 29 :
                // InternalKVis.g:1:178: T__45
                {
                mT__45(); 

                }
                break;
            case 30 :
                // InternalKVis.g:1:184: T__46
                {
                mT__46(); 

                }
                break;
            case 31 :
                // InternalKVis.g:1:190: T__47
                {
                mT__47(); 

                }
                break;
            case 32 :
                // InternalKVis.g:1:196: T__48
                {
                mT__48(); 

                }
                break;
            case 33 :
                // InternalKVis.g:1:202: T__49
                {
                mT__49(); 

                }
                break;
            case 34 :
                // InternalKVis.g:1:208: T__50
                {
                mT__50(); 

                }
                break;
            case 35 :
                // InternalKVis.g:1:214: T__51
                {
                mT__51(); 

                }
                break;
            case 36 :
                // InternalKVis.g:1:220: T__52
                {
                mT__52(); 

                }
                break;
            case 37 :
                // InternalKVis.g:1:226: T__53
                {
                mT__53(); 

                }
                break;
            case 38 :
                // InternalKVis.g:1:232: T__54
                {
                mT__54(); 

                }
                break;
            case 39 :
                // InternalKVis.g:1:238: T__55
                {
                mT__55(); 

                }
                break;
            case 40 :
                // InternalKVis.g:1:244: T__56
                {
                mT__56(); 

                }
                break;
            case 41 :
                // InternalKVis.g:1:250: T__57
                {
                mT__57(); 

                }
                break;
            case 42 :
                // InternalKVis.g:1:256: T__58
                {
                mT__58(); 

                }
                break;
            case 43 :
                // InternalKVis.g:1:262: T__59
                {
                mT__59(); 

                }
                break;
            case 44 :
                // InternalKVis.g:1:268: T__60
                {
                mT__60(); 

                }
                break;
            case 45 :
                // InternalKVis.g:1:274: T__61
                {
                mT__61(); 

                }
                break;
            case 46 :
                // InternalKVis.g:1:280: T__62
                {
                mT__62(); 

                }
                break;
            case 47 :
                // InternalKVis.g:1:286: T__63
                {
                mT__63(); 

                }
                break;
            case 48 :
                // InternalKVis.g:1:292: T__64
                {
                mT__64(); 

                }
                break;
            case 49 :
                // InternalKVis.g:1:298: T__65
                {
                mT__65(); 

                }
                break;
            case 50 :
                // InternalKVis.g:1:304: T__66
                {
                mT__66(); 

                }
                break;
            case 51 :
                // InternalKVis.g:1:310: T__67
                {
                mT__67(); 

                }
                break;
            case 52 :
                // InternalKVis.g:1:316: T__68
                {
                mT__68(); 

                }
                break;
            case 53 :
                // InternalKVis.g:1:322: T__69
                {
                mT__69(); 

                }
                break;
            case 54 :
                // InternalKVis.g:1:328: T__70
                {
                mT__70(); 

                }
                break;
            case 55 :
                // InternalKVis.g:1:334: T__71
                {
                mT__71(); 

                }
                break;
            case 56 :
                // InternalKVis.g:1:340: T__72
                {
                mT__72(); 

                }
                break;
            case 57 :
                // InternalKVis.g:1:346: T__73
                {
                mT__73(); 

                }
                break;
            case 58 :
                // InternalKVis.g:1:352: T__74
                {
                mT__74(); 

                }
                break;
            case 59 :
                // InternalKVis.g:1:358: RULE_BOOLEAN
                {
                mRULE_BOOLEAN(); 

                }
                break;
            case 60 :
                // InternalKVis.g:1:371: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 61 :
                // InternalKVis.g:1:379: RULE_HOSTCODE
                {
                mRULE_HOSTCODE(); 

                }
                break;
            case 62 :
                // InternalKVis.g:1:393: RULE_COMMENT_ANNOTATION
                {
                mRULE_COMMENT_ANNOTATION(); 

                }
                break;
            case 63 :
                // InternalKVis.g:1:417: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 64 :
                // InternalKVis.g:1:433: RULE_SL_COMMENT_ANNOTATION
                {
                mRULE_SL_COMMENT_ANNOTATION(); 

                }
                break;
            case 65 :
                // InternalKVis.g:1:460: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 66 :
                // InternalKVis.g:1:476: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 67 :
                // InternalKVis.g:1:485: RULE_FLOAT
                {
                mRULE_FLOAT(); 

                }
                break;
            case 68 :
                // InternalKVis.g:1:496: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 69 :
                // InternalKVis.g:1:508: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 70 :
                // InternalKVis.g:1:516: RULE_ANY_OTHER
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
            return "5775:14: ( ( RULE_NUMBER )+ '.' ( RULE_NUMBER )* ( ( 'e' | 'E' ) ( '+' | '-' )? ( RULE_NUMBER )+ )? ( 'f' )? | ( RULE_NUMBER )+ 'f' )";
        }
    }
    static final String DFA24_eotS =
        "\1\uffff\1\56\1\uffff\1\56\2\uffff\4\56\1\76\2\56\5\uffff\3\56\1\116\1\120\2\uffff\1\125\1\uffff\1\130\2\uffff\1\56\1\135\1\137\1\141\2\uffff\1\56\1\53\1\uffff\1\53\1\146\1\53\2\uffff\1\56\1\154\2\uffff\2\56\2\uffff\4\56\1\165\4\56\2\uffff\4\56\5\uffff\5\56\7\uffff\1\56\6\uffff\1\56\10\uffff\1\56\3\uffff\1\146\2\uffff\1\56\1\uffff\1\56\1\u008e\3\56\1\u0092\2\56\1\uffff\17\56\3\uffff\1\56\1\uffff\1\56\1\u00a6\2\56\1\uffff\3\56\1\uffff\1\56\1\u00ad\1\u00ae\1\56\1\u00b0\3\56\1\u00b4\1\u00b5\6\56\1\u00bc\2\56\1\uffff\1\u00be\1\56\1\u00c0\2\56\1\u00c3\2\uffff\1\56\1\uffff\1\u00bc\2\56\2\uffff\1\u00c7\1\u00c8\2\56\1\u00cd\1\56\1\uffff\1\56\1\uffff\1\56\1\uffff\2\56\1\uffff\3\56\2\uffff\4\56\1\uffff\1\56\1\u00db\1\u00dc\1\u00dd\1\u00de\1\u00df\3\56\1\u00e3\3\56\5\uffff\1\56\1\u00e8\1\56\1\uffff\1\56\1\u00eb\1\u00ec\1\56\1\uffff\1\u00ee\1\u00ef\2\uffff\1\u00f0\3\uffff";
    static final String DFA24_eofS =
        "\u00f1\uffff";
    static final String DFA24_minS =
        "\1\0\1\155\1\uffff\1\156\2\uffff\1\141\1\156\1\150\1\141\1\75\1\143\1\163\5\uffff\1\157\1\154\1\145\1\174\1\46\2\uffff\1\52\1\uffff\1\51\2\uffff\1\170\3\75\2\uffff\1\141\1\57\1\uffff\1\0\1\56\1\0\2\uffff\1\141\1\55\2\uffff\1\144\1\160\2\uffff\1\162\1\145\1\165\1\141\1\55\1\145\1\141\1\157\1\154\2\uffff\1\155\1\150\1\145\1\151\5\uffff\1\144\1\155\1\151\1\155\1\165\6\uffff\2\0\6\uffff\1\164\10\uffff\1\154\3\uffff\1\56\2\uffff\1\147\1\uffff\1\155\1\55\1\154\1\146\1\143\1\55\1\163\1\171\1\uffff\1\156\1\160\1\155\1\163\1\165\1\145\2\160\1\156\1\145\1\163\1\160\1\143\1\160\1\145\3\uffff\1\0\1\uffff\1\145\1\55\1\145\1\141\1\uffff\1\171\1\157\1\145\1\uffff\1\145\2\55\1\160\1\55\1\145\1\154\1\144\2\55\1\147\1\154\1\145\1\151\1\153\1\154\1\55\1\0\1\162\1\uffff\1\55\1\164\1\55\1\162\1\163\1\55\2\uffff\1\145\1\uffff\1\55\1\141\1\165\2\uffff\2\55\1\144\1\154\1\55\1\141\1\uffff\1\156\1\uffff\1\145\1\uffff\1\155\1\163\1\uffff\1\162\1\164\1\154\2\uffff\1\157\1\160\1\157\1\145\1\uffff\1\164\5\55\1\151\1\145\1\167\1\55\1\166\1\162\1\145\5\uffff\1\157\1\55\1\156\1\uffff\1\145\2\55\1\156\1\uffff\2\55\2\uffff\1\55\3\uffff";
    static final String DFA24_maxS =
        "\1\uffff\1\163\1\uffff\1\160\2\uffff\1\162\1\156\2\162\1\75\1\164\1\163\5\uffff\2\157\1\162\1\174\1\46\2\uffff\1\172\1\uffff\1\51\2\uffff\1\170\3\75\2\uffff\1\141\1\172\1\uffff\1\uffff\1\146\1\uffff\2\uffff\1\141\1\172\2\uffff\1\151\1\160\2\uffff\1\162\1\157\1\165\1\141\1\172\1\145\1\141\1\157\1\154\2\uffff\1\155\1\150\1\157\1\151\5\uffff\1\165\1\155\1\151\1\155\1\165\6\uffff\2\uffff\6\uffff\1\164\10\uffff\1\154\3\uffff\1\146\2\uffff\1\147\1\uffff\1\155\1\172\1\154\1\146\1\143\1\172\1\163\1\171\1\uffff\1\156\1\160\1\155\1\163\1\165\1\145\2\160\1\156\1\145\1\163\1\160\1\143\1\160\1\145\3\uffff\1\uffff\1\uffff\1\145\1\172\1\145\1\141\1\uffff\1\171\1\157\1\145\1\uffff\1\145\2\172\1\160\1\172\1\145\1\154\1\144\2\172\1\147\1\154\1\145\1\151\1\153\1\154\1\172\1\uffff\1\162\1\uffff\1\172\1\164\1\172\1\162\1\163\1\172\2\uffff\1\145\1\uffff\1\172\1\141\1\165\2\uffff\2\172\1\165\1\154\1\172\1\141\1\uffff\1\156\1\uffff\1\145\1\uffff\1\155\1\163\1\uffff\1\162\1\164\1\154\2\uffff\1\157\1\160\1\157\1\145\1\uffff\1\164\5\172\1\151\1\145\1\167\1\172\1\166\1\162\1\145\5\uffff\1\157\1\172\1\156\1\uffff\1\145\2\172\1\156\1\uffff\2\172\2\uffff\1\172\3\uffff";
    static final String DFA24_acceptS =
        "\2\uffff\1\2\1\uffff\1\4\1\5\7\uffff\1\16\1\21\1\22\1\23\1\24\5\uffff\1\37\1\40\1\uffff\1\42\1\uffff\1\44\1\45\4\uffff\1\53\1\54\2\uffff\1\74\3\uffff\1\105\1\106\2\uffff\1\74\1\2\2\uffff\1\4\1\5\11\uffff\1\65\1\12\4\uffff\1\16\1\21\1\22\1\23\1\24\5\uffff\1\33\1\35\1\34\1\36\1\37\1\40\2\uffff\1\41\1\42\1\46\1\43\1\44\1\45\1\uffff\1\66\1\50\1\67\1\51\1\70\1\52\1\53\1\54\1\uffff\1\75\1\102\1\103\1\uffff\1\104\1\105\1\uffff\1\17\10\uffff\1\7\17\uffff\1\76\1\77\1\100\1\uffff\1\101\4\uffff\1\20\3\uffff\1\71\23\uffff\1\72\6\uffff\1\64\1\10\1\uffff\1\11\3\uffff\1\61\1\62\6\uffff\1\73\1\uffff\1\1\1\uffff\1\14\2\uffff\1\63\3\uffff\1\15\1\25\4\uffff\1\55\15\uffff\1\47\1\3\1\6\1\27\1\31\3\uffff\1\57\4\uffff\1\32\2\uffff\1\26\1\30\1\uffff\1\56\1\60\1\13";
    static final String DFA24_specialS =
        "\1\1\46\uffff\1\3\1\uffff\1\6\51\uffff\1\0\1\4\63\uffff\1\5\33\uffff\1\2\114\uffff}>";
    static final String[] DFA24_transitionS = {
            "\11\53\2\52\2\53\1\52\22\53\1\52\1\41\1\51\1\43\1\53\1\32\1\26\1\35\1\33\1\34\1\30\1\27\1\15\1\21\1\20\1\31\12\50\1\2\1\53\1\37\1\12\1\40\1\53\1\42\32\46\1\16\1\46\1\17\1\45\1\46\1\47\1\3\1\46\1\23\1\46\1\36\1\11\2\46\1\1\3\46\1\22\1\46\1\7\1\6\2\46\1\13\1\24\1\14\1\44\1\10\3\46\1\4\1\25\1\5\uff82\53",
            "\1\54\5\uffff\1\55",
            "",
            "\1\60\1\uffff\1\61",
            "",
            "",
            "\1\66\3\uffff\1\64\6\uffff\1\67\5\uffff\1\65",
            "\1\70",
            "\1\71\11\uffff\1\72",
            "\1\74\20\uffff\1\73",
            "\1\75",
            "\1\100\5\uffff\1\77\12\uffff\1\101",
            "\1\102",
            "",
            "",
            "",
            "",
            "",
            "\1\110",
            "\1\112\2\uffff\1\111",
            "\1\113\14\uffff\1\114",
            "\1\115",
            "\1\117",
            "",
            "",
            "\1\123\2\uffff\2\56\1\124\12\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "",
            "\1\127",
            "",
            "",
            "\1\133",
            "\1\134",
            "\1\136",
            "\1\140",
            "",
            "",
            "\1\144",
            "\1\56\21\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "",
            "\0\145",
            "\1\147\1\uffff\12\150\54\uffff\1\147",
            "\0\151",
            "",
            "",
            "\1\153",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "",
            "",
            "\1\156\4\uffff\1\155",
            "\1\157",
            "",
            "",
            "\1\160",
            "\1\162\11\uffff\1\161",
            "\1\163",
            "\1\164",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\166",
            "\1\167",
            "\1\170",
            "\1\171",
            "",
            "",
            "\1\172",
            "\1\173",
            "\1\174\11\uffff\1\175",
            "\1\176",
            "",
            "",
            "",
            "",
            "",
            "\1\177\20\uffff\1\u0080",
            "\1\u0081",
            "\1\u0082",
            "\1\u0083",
            "\1\u0084",
            "",
            "",
            "",
            "",
            "",
            "",
            "\52\u0086\1\u0085\uffd5\u0086",
            "\52\u0089\1\u0087\2\u0089\15\u0088\7\u0089\32\u0088\1\u0089\1\u0088\2\u0089\1\u0088\1\u0089\32\u0088\uff85\u0089",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u008a",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u008b",
            "",
            "",
            "",
            "\1\147\1\uffff\12\150\54\uffff\1\147",
            "",
            "",
            "\1\u008c",
            "",
            "\1\u008d",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\u008f",
            "\1\u0090",
            "\1\u0091",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\u0093",
            "\1\u0094",
            "",
            "\1\u0095",
            "\1\u0096",
            "\1\u0097",
            "\1\u0098",
            "\1\u0099",
            "\1\u009a",
            "\1\u009b",
            "\1\u009c",
            "\1\u009d",
            "\1\u009e",
            "\1\u009f",
            "\1\u00a0",
            "\1\u00a1",
            "\1\u00a2",
            "\1\u00a3",
            "",
            "",
            "",
            "\55\u0089\15\u00a4\7\u0089\32\u00a4\1\u0089\1\u00a4\2\u0089\1\u00a4\1\u0089\32\u00a4\uff85\u0089",
            "",
            "\1\u00a5",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\u00a7",
            "\1\u00a8",
            "",
            "\1\u00a9",
            "\1\u00aa",
            "\1\u00ab",
            "",
            "\1\u00ac",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\u00af",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\u00b1",
            "\1\u00b2",
            "\1\u00b3",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\u00b6",
            "\1\u00b7",
            "\1\u00b8",
            "\1\u00b9",
            "\1\u00ba",
            "\1\u00bb",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\55\u0089\15\u00a4\7\u0089\32\u00a4\1\u0089\1\u00a4\2\u0089\1\u00a4\1\u0089\32\u00a4\uff85\u0089",
            "\1\u00bd",
            "",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\u00bf",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\u00c1",
            "\1\u00c2",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "",
            "",
            "\1\u00c4",
            "",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\u00c5",
            "\1\u00c6",
            "",
            "",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\u00c9\10\uffff\1\u00cb\7\uffff\1\u00ca",
            "\1\u00cc",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\u00ce",
            "",
            "\1\u00cf",
            "",
            "\1\u00d0",
            "",
            "\1\u00d1",
            "\1\u00d2",
            "",
            "\1\u00d3",
            "\1\u00d4",
            "\1\u00d5",
            "",
            "",
            "\1\u00d6",
            "\1\u00d7",
            "\1\u00d8",
            "\1\u00d9",
            "",
            "\1\u00da",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\u00e0",
            "\1\u00e1",
            "\1\u00e2",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\u00e4",
            "\1\u00e5",
            "\1\u00e6",
            "",
            "",
            "",
            "",
            "",
            "\1\u00e7",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\u00e9",
            "",
            "\1\u00ea",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\1\u00ed",
            "",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "",
            "",
            "\15\56\7\uffff\32\56\1\uffff\1\56\2\uffff\1\56\1\uffff\32\56",
            "",
            "",
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
            return "1:1: Tokens : ( T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | RULE_BOOLEAN | RULE_ID | RULE_HOSTCODE | RULE_COMMENT_ANNOTATION | RULE_ML_COMMENT | RULE_SL_COMMENT_ANNOTATION | RULE_SL_COMMENT | RULE_INT | RULE_FLOAT | RULE_STRING | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA24_83 = input.LA(1);

                        s = -1;
                        if ( (LA24_83=='*') ) {s = 133;}

                        else if ( ((LA24_83>='\u0000' && LA24_83<=')')||(LA24_83>='+' && LA24_83<='\uFFFF')) ) {s = 134;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA24_0 = input.LA(1);

                        s = -1;
                        if ( (LA24_0=='i') ) {s = 1;}

                        else if ( (LA24_0==':') ) {s = 2;}

                        else if ( (LA24_0=='a') ) {s = 3;}

                        else if ( (LA24_0=='{') ) {s = 4;}

                        else if ( (LA24_0=='}') ) {s = 5;}

                        else if ( (LA24_0=='p') ) {s = 6;}

                        else if ( (LA24_0=='o') ) {s = 7;}

                        else if ( (LA24_0=='w') ) {s = 8;}

                        else if ( (LA24_0=='f') ) {s = 9;}

                        else if ( (LA24_0=='=') ) {s = 10;}

                        else if ( (LA24_0=='s') ) {s = 11;}

                        else if ( (LA24_0=='u') ) {s = 12;}

                        else if ( (LA24_0==',') ) {s = 13;}

                        else if ( (LA24_0=='[') ) {s = 14;}

                        else if ( (LA24_0==']') ) {s = 15;}

                        else if ( (LA24_0=='.') ) {s = 16;}

                        else if ( (LA24_0=='-') ) {s = 17;}

                        else if ( (LA24_0=='m') ) {s = 18;}

                        else if ( (LA24_0=='c') ) {s = 19;}

                        else if ( (LA24_0=='t') ) {s = 20;}

                        else if ( (LA24_0=='|') ) {s = 21;}

                        else if ( (LA24_0=='&') ) {s = 22;}

                        else if ( (LA24_0=='+') ) {s = 23;}

                        else if ( (LA24_0=='*') ) {s = 24;}

                        else if ( (LA24_0=='/') ) {s = 25;}

                        else if ( (LA24_0=='%') ) {s = 26;}

                        else if ( (LA24_0=='(') ) {s = 27;}

                        else if ( (LA24_0==')') ) {s = 28;}

                        else if ( (LA24_0=='\'') ) {s = 29;}

                        else if ( (LA24_0=='e') ) {s = 30;}

                        else if ( (LA24_0=='<') ) {s = 31;}

                        else if ( (LA24_0=='>') ) {s = 32;}

                        else if ( (LA24_0=='!') ) {s = 33;}

                        else if ( (LA24_0=='@') ) {s = 34;}

                        else if ( (LA24_0=='#') ) {s = 35;}

                        else if ( (LA24_0=='v') ) {s = 36;}

                        else if ( (LA24_0=='^') ) {s = 37;}

                        else if ( ((LA24_0>='A' && LA24_0<='Z')||LA24_0=='\\'||LA24_0=='_'||LA24_0=='b'||LA24_0=='d'||(LA24_0>='g' && LA24_0<='h')||(LA24_0>='j' && LA24_0<='l')||LA24_0=='n'||(LA24_0>='q' && LA24_0<='r')||(LA24_0>='x' && LA24_0<='z')) ) {s = 38;}

                        else if ( (LA24_0=='`') ) {s = 39;}

                        else if ( ((LA24_0>='0' && LA24_0<='9')) ) {s = 40;}

                        else if ( (LA24_0=='\"') ) {s = 41;}

                        else if ( ((LA24_0>='\t' && LA24_0<='\n')||LA24_0=='\r'||LA24_0==' ') ) {s = 42;}

                        else if ( ((LA24_0>='\u0000' && LA24_0<='\b')||(LA24_0>='\u000B' && LA24_0<='\f')||(LA24_0>='\u000E' && LA24_0<='\u001F')||LA24_0=='$'||LA24_0==';'||LA24_0=='?'||(LA24_0>='~' && LA24_0<='\uFFFF')) ) {s = 43;}

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA24_164 = input.LA(1);

                        s = -1;
                        if ( ((LA24_164>='\u0000' && LA24_164<=',')||(LA24_164>=':' && LA24_164<='@')||LA24_164=='['||(LA24_164>=']' && LA24_164<='^')||LA24_164=='`'||(LA24_164>='{' && LA24_164<='\uFFFF')) ) {s = 137;}

                        else if ( ((LA24_164>='-' && LA24_164<='9')||(LA24_164>='A' && LA24_164<='Z')||LA24_164=='\\'||LA24_164=='_'||(LA24_164>='a' && LA24_164<='z')) ) {s = 164;}

                        else s = 46;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA24_39 = input.LA(1);

                        s = -1;
                        if ( ((LA24_39>='\u0000' && LA24_39<='\uFFFF')) ) {s = 101;}

                        else s = 43;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA24_84 = input.LA(1);

                        s = -1;
                        if ( (LA24_84=='*') ) {s = 135;}

                        else if ( ((LA24_84>='-' && LA24_84<='9')||(LA24_84>='A' && LA24_84<='Z')||LA24_84=='\\'||LA24_84=='_'||(LA24_84>='a' && LA24_84<='z')) ) {s = 136;}

                        else if ( ((LA24_84>='\u0000' && LA24_84<=')')||(LA24_84>='+' && LA24_84<=',')||(LA24_84>=':' && LA24_84<='@')||LA24_84=='['||(LA24_84>=']' && LA24_84<='^')||LA24_84=='`'||(LA24_84>='{' && LA24_84<='\uFFFF')) ) {s = 137;}

                        else s = 46;

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA24_136 = input.LA(1);

                        s = -1;
                        if ( ((LA24_136>='-' && LA24_136<='9')||(LA24_136>='A' && LA24_136<='Z')||LA24_136=='\\'||LA24_136=='_'||(LA24_136>='a' && LA24_136<='z')) ) {s = 164;}

                        else if ( ((LA24_136>='\u0000' && LA24_136<=',')||(LA24_136>=':' && LA24_136<='@')||LA24_136=='['||(LA24_136>=']' && LA24_136<='^')||LA24_136=='`'||(LA24_136>='{' && LA24_136<='\uFFFF')) ) {s = 137;}

                        else s = 46;

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA24_41 = input.LA(1);

                        s = -1;
                        if ( ((LA24_41>='\u0000' && LA24_41<='\uFFFF')) ) {s = 105;}

                        else s = 43;

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