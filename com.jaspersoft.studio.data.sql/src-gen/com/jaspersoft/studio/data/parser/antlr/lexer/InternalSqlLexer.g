
/*
* generated by Xtext
*/
lexer grammar InternalSqlLexer;


@header {
package com.jaspersoft.studio.data.parser.antlr.lexer;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}




KEYWORD_60 : ('R'|'r')('I'|'i')('G'|'g')('H'|'h')('T'|'t')' '('O'|'o')('U'|'u')('T'|'t')('E'|'e')('R'|'r')' '('J'|'j')('O'|'o')('I'|'i')('N'|'n');

KEYWORD_58 : ('F'|'f')('U'|'u')('L'|'l')('L'|'l')' '('O'|'o')('U'|'u')('T'|'t')('E'|'e')('R'|'r')' '('J'|'j')('O'|'o')('I'|'i')('N'|'n');

KEYWORD_59 : ('L'|'l')('E'|'e')('F'|'f')('T'|'t')' '('O'|'o')('U'|'u')('T'|'t')('E'|'e')('R'|'r')' '('J'|'j')('O'|'o')('I'|'i')('N'|'n');

KEYWORD_56 : ('I'|'i')('S'|'s')' '('N'|'n')('O'|'o')('T'|'t')' '('N'|'n')('U'|'u')('L'|'l')('L'|'l');

KEYWORD_57 : ('N'|'n')('O'|'o')('T'|'t')' '('B'|'b')('E'|'e')('T'|'t')('W'|'w')('E'|'e')('E'|'e')('N'|'n');

KEYWORD_54 : ('C'|'c')('R'|'r')('O'|'o')('S'|'s')('S'|'s')' '('J'|'j')('O'|'o')('I'|'i')('N'|'n');

KEYWORD_55 : ('I'|'i')('N'|'n')('N'|'n')('E'|'e')('R'|'r')' '('J'|'j')('O'|'o')('I'|'i')('N'|'n');

KEYWORD_52 : ('I'|'i')('N'|'n')('T'|'t')('E'|'e')('R'|'r')('S'|'s')('E'|'e')('C'|'c')('T'|'t');

KEYWORD_53 : '['('B'|'b')('E'|'e')('T'|'t')('W'|'w')('E'|'e')('E'|'e')('N'|'n')']';

KEYWORD_44 : ('B'|'b')('E'|'e')('T'|'t')('W'|'w')('E'|'e')('E'|'e')('N'|'n')']';

KEYWORD_45 : ('D'|'d')('I'|'i')('S'|'s')('T'|'t')('I'|'i')('N'|'n')('C'|'c')('T'|'t');

KEYWORD_46 : ('G'|'g')('R'|'r')('O'|'o')('U'|'u')('P'|'p')' '('B'|'b')('Y'|'y');

KEYWORD_47 : ('N'|'n')('O'|'o')('T'|'t')' '('L'|'l')('I'|'i')('K'|'k')('E'|'e');

KEYWORD_48 : ('N'|'n')('O'|'o')('T'|'t')('E'|'e')('Q'|'q')('U'|'u')('A'|'a')('L'|'l');

KEYWORD_49 : ('O'|'o')('R'|'r')('D'|'d')('E'|'e')('R'|'r')' '('B'|'b')('Y'|'y');

KEYWORD_50 : '['('B'|'b')('E'|'e')('T'|'t')('W'|'w')('E'|'e')('E'|'e')('N'|'n');

KEYWORD_51 : '['('G'|'g')('R'|'r')('E'|'e')('A'|'a')('T'|'t')('E'|'e')('R'|'r');

KEYWORD_41 : ('B'|'b')('E'|'e')('T'|'t')('W'|'w')('E'|'e')('E'|'e')('N'|'n');

KEYWORD_42 : ('G'|'g')('R'|'r')('E'|'e')('A'|'a')('T'|'t')('E'|'e')('R'|'r');

KEYWORD_43 : ('I'|'i')('S'|'s')' '('N'|'n')('U'|'u')('L'|'l')('L'|'l');

KEYWORD_37 : ('E'|'e')('X'|'x')('C'|'c')('E'|'e')('P'|'p')('T'|'t');

KEYWORD_38 : ('H'|'h')('A'|'a')('V'|'v')('I'|'i')('N'|'n')('G'|'g');

KEYWORD_39 : ('N'|'n')('O'|'o')('T'|'t')' '('I'|'i')('N'|'n');

KEYWORD_40 : ('S'|'s')('E'|'e')('L'|'l')('E'|'e')('C'|'c')('T'|'t');

KEYWORD_31 : ('E'|'e')('Q'|'q')('U'|'u')('A'|'a')('L'|'l');

KEYWORD_32 : ('L'|'l')('E'|'e')('S'|'s')('S'|'s')']';

KEYWORD_33 : ('M'|'m')('I'|'i')('N'|'n')('U'|'u')('S'|'s');

KEYWORD_34 : ('N'|'n')('O'|'o')('T'|'t')('I'|'i')('N'|'n');

KEYWORD_35 : ('U'|'u')('N'|'n')('I'|'i')('O'|'o')('N'|'n');

KEYWORD_36 : ('W'|'w')('H'|'h')('E'|'e')('R'|'r')('E'|'e');

KEYWORD_26 : ('D'|'d')('E'|'e')('S'|'s')('C'|'c');

KEYWORD_27 : ('F'|'f')('R'|'r')('O'|'o')('M'|'m');

KEYWORD_28 : ('L'|'l')('E'|'e')('S'|'s')('S'|'s');

KEYWORD_29 : ('L'|'l')('I'|'i')('K'|'k')('E'|'e');

KEYWORD_30 : ('S'|'s')('O'|'o')('M'|'m')('E'|'e');

KEYWORD_22 : ('A'|'a')('L'|'l')('L'|'l');

KEYWORD_23 : ('A'|'a')('N'|'n')('D'|'d');

KEYWORD_24 : ('A'|'a')('N'|'n')('Y'|'y');

KEYWORD_25 : ('A'|'a')('S'|'s')('C'|'c');

KEYWORD_13 : '$'('X'|'x');

KEYWORD_14 : '<''=';

KEYWORD_15 : '<''>';

KEYWORD_16 : '>''=';

KEYWORD_17 : ('A'|'a')('S'|'s');

KEYWORD_18 : ('I'|'i')('N'|'n');

KEYWORD_19 : ('O'|'o')('N'|'n');

KEYWORD_20 : ('O'|'o')('R'|'r');

KEYWORD_21 : '|''|';

KEYWORD_1 : '(';

KEYWORD_2 : ')';

KEYWORD_3 : '+';

KEYWORD_4 : ',';

KEYWORD_5 : '-';

KEYWORD_6 : '.';

KEYWORD_7 : '/';

KEYWORD_8 : '<';

KEYWORD_9 : '=';

KEYWORD_10 : '>';

KEYWORD_11 : '{';

KEYWORD_12 : '}';



RULE_JRPARAM : '$P{' ( options {greedy=false;} : . )*'}';

RULE_JRNPARAM : '$P!{' ( options {greedy=false;} : . )*'}';

RULE_STAR : '*';

RULE_INT : '-'? ('0'..'9')+;

RULE_TIMESTAMP : RULE_DATE ' ' RULE_TIME;

RULE_DATE : '\'' '0'..'9' '0'..'9' '0'..'9' '0'..'9' '-' '0'..'1' '0'..'9' '-' '0'..'3' '0'..'9' '\'';

RULE_TIME : '\'' '0'..'9' '0'..'9' ':' '0'..'9' '0'..'9' ':' '0'..'1' '0'..'9' '.' '0'..'9' '0'..'9' '0'..'9' '\'';

RULE_SIGNED_DOUBLE : '-'? ('0'..'9')+ ('.' ('0'..'9')+)?;

RULE_STRING : ('"' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'"')))* '"'|'\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\''|'`' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'"')))* '`'|'[' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'"')))* ']');

RULE_ID : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_SL_COMMENT : ('--'|'#'|'//') ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;



