lexer grammar StateSpace;
@header {
package org.eclipse.emf.henshin.statespace.parser;
}

LINE : '--' ;
EQUAL : '=' ;
LPAREN : '(' ;
RPAREN : ')' ;
LBRACKET : '[' ;
RBRACKET : ']' ;
COMMA : ',' ;
SEMICOLON : ';' ;

// $ANTLR src "StateSpace.g" 70
ID  :	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

// $ANTLR src "StateSpace.g" 73
INT :	'0'..'9'+
    ;

// $ANTLR src "StateSpace.g" 76
WS  :   (' '|'\t'|'\n'|'\r')+ { skip(); }
    ;
