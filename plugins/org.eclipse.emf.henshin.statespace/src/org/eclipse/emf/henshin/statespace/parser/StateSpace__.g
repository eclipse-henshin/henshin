lexer grammar StateSpace;
@header {
package org.eclipse.emf.henshin.statespace.parser;
}

LINE : '--' ;
ARROW : '->' ;
EQUAL : '=' ;
LPAREN : '(' ;
RPAREN : ')' ;
LBRACKET : '[' ;
RBRACKET : ']' ;
COMMA : ',' ;
SEMICOLON : ';' ;
RULE_CMD : '#rule' ;

// $ANTLR src "StateSpace.g" 157
ID  :	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*;
// $ANTLR src "StateSpace.g" 158
INT :	'0'..'9'+;
// $ANTLR src "StateSpace.g" 159
WS  :   (' '|'\t'|'\n'|'\r')+ { skip(); } ;
// $ANTLR src "StateSpace.g" 160
STRING :
		'"'
		(   EscapeSequence
        |   ~( '\\' | '"' | '\r' | '\n' )        
        )* 
        '"' {
        	// Automatically remove the surrounding quotes: 
        	setText( getText().substring(1,getText().length()-1));
        	}
;
// $ANTLR src "StateSpace.g" 170
fragment
EscapeSequence:
		'\\' (
                 'b' 
             |   't' 
             |   'n' 
             |   'f' 
             |   'r' 
             |   '\"' 
             |   '\'' 
             |   '\\' 
             |   ('0'..'3') ('0'..'7') ('0'..'7')
             |   ('0'..'7') ('0'..'7') 
             |   ('0'..'7')
             )          
;
