
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




KEYWORD_125 : ('U'|'u')('N'|'n')('B'|'b')('O'|'o')('U'|'u')('N'|'n')('D'|'d')('E'|'e')('D'|'d')' '('F'|'f')('O'|'o')('L'|'l')('L'|'l')('O'|'o')('W'|'w')('I'|'i')('N'|'n')('G'|'g');

KEYWORD_126 : ('U'|'u')('N'|'n')('B'|'b')('O'|'o')('U'|'u')('N'|'n')('D'|'d')('E'|'e')('D'|'d')' '('P'|'p')('R'|'r')('E'|'e')('C'|'c')('E'|'e')('D'|'d')('I'|'i')('N'|'n')('G'|'g');

KEYWORD_123 : ('M'|'m')('I'|'i')('N'|'n')('U'|'u')('T'|'t')('E'|'e')'_'('M'|'m')('I'|'i')('C'|'c')('R'|'r')('O'|'o')('S'|'s')('E'|'e')('C'|'c')('O'|'o')('N'|'n')('D'|'d');

KEYWORD_124 : ('S'|'s')('E'|'e')('C'|'c')('O'|'o')('N'|'n')('D'|'d')'_'('M'|'m')('I'|'i')('C'|'c')('R'|'r')('O'|'o')('S'|'s')('E'|'e')('C'|'c')('O'|'o')('N'|'n')('D'|'d');

KEYWORD_122 : ('O'|'o')('R'|'r')('D'|'d')('E'|'e')('R'|'r')' '('S'|'s')('I'|'i')('B'|'b')('L'|'l')('I'|'i')('N'|'n')('G'|'g')('S'|'s')' '('B'|'b')('Y'|'y');

KEYWORD_121 : ('H'|'h')('O'|'o')('U'|'u')('R'|'r')'_'('M'|'m')('I'|'i')('C'|'c')('R'|'r')('O'|'o')('S'|'s')('E'|'e')('C'|'c')('O'|'o')('N'|'n')('D'|'d');

KEYWORD_120 : ('D'|'d')('A'|'a')('Y'|'y')'_'('M'|'m')('I'|'i')('C'|'c')('R'|'r')('O'|'o')('S'|'s')('E'|'e')('C'|'c')('O'|'o')('N'|'n')('D'|'d');

KEYWORD_118 : ('M'|'m')('I'|'i')('N'|'n')('U'|'u')('T'|'t')('E'|'e')'_'('S'|'s')('E'|'e')('C'|'c')('O'|'o')('N'|'n')('D'|'d');

KEYWORD_119 : ('S'|'s')('T'|'t')('R'|'r')('A'|'a')('I'|'i')('G'|'g')('H'|'h')('T'|'t')'_'('J'|'j')('O'|'o')('I'|'i')('N'|'n');

KEYWORD_117 : ('P'|'p')('A'|'a')('R'|'r')('T'|'t')('I'|'i')('T'|'t')('I'|'i')('O'|'o')('N'|'n')' '('B'|'b')('Y'|'y');

KEYWORD_110 : ('C'|'c')('U'|'u')('R'|'r')('R'|'r')('E'|'e')('N'|'n')('T'|'t')' '('R'|'r')('O'|'o')('W'|'w');

KEYWORD_111 : ('F'|'f')('E'|'e')('T'|'t')('C'|'c')('H'|'h')' '('F'|'f')('I'|'i')('R'|'r')('S'|'s')('T'|'t');

KEYWORD_112 : ('H'|'h')('O'|'o')('U'|'u')('R'|'r')'_'('M'|'m')('I'|'i')('N'|'n')('U'|'u')('T'|'t')('E'|'e');

KEYWORD_113 : ('H'|'h')('O'|'o')('U'|'u')('R'|'r')'_'('S'|'s')('E'|'e')('C'|'c')('O'|'o')('N'|'n')('D'|'d');

KEYWORD_114 : ('I'|'i')('S'|'s')' '('N'|'n')('O'|'o')('T'|'t')' '('N'|'n')('U'|'u')('L'|'l')('L'|'l');

KEYWORD_115 : ('M'|'m')('I'|'i')('C'|'c')('R'|'r')('O'|'o')('S'|'s')('E'|'e')('C'|'c')('O'|'o')('N'|'n')('D'|'d');

KEYWORD_116 : ('N'|'n')('O'|'o')('T'|'t')' '('B'|'b')('E'|'e')('T'|'t')('W'|'w')('E'|'e')('E'|'e')('N'|'n');

KEYWORD_106 : ('D'|'d')('A'|'a')('Y'|'y')'_'('M'|'m')('I'|'i')('N'|'n')('U'|'u')('T'|'t')('E'|'e');

KEYWORD_107 : ('D'|'d')('A'|'a')('Y'|'y')'_'('S'|'s')('E'|'e')('C'|'c')('O'|'o')('N'|'n')('D'|'d');

KEYWORD_108 : ('N'|'n')('O'|'o')('T'|'t')' '('E'|'e')('X'|'x')('I'|'i')('S'|'s')('T'|'t')('S'|'s');

KEYWORD_109 : ('Y'|'y')('E'|'e')('A'|'a')('R'|'r')'_'('M'|'m')('O'|'o')('N'|'n')('T'|'t')('H'|'h');

KEYWORD_101 : ('F'|'f')('O'|'o')('L'|'l')('L'|'l')('O'|'o')('W'|'w')('I'|'i')('N'|'n')('G'|'g');

KEYWORD_102 : ('I'|'i')('N'|'n')('T'|'t')('E'|'e')('R'|'r')('S'|'s')('E'|'e')('C'|'c')('T'|'t');

KEYWORD_103 : ('P'|'p')('R'|'r')('E'|'e')('C'|'c')('E'|'e')('D'|'d')('I'|'i')('N'|'n')('G'|'g');

KEYWORD_104 : ('W'|'w')('I'|'i')('T'|'t')('H'|'h')' '('T'|'t')('I'|'i')('E'|'e')('S'|'s');

KEYWORD_105 : '['('B'|'b')('E'|'e')('T'|'t')('W'|'w')('E'|'e')('E'|'e')('N'|'n')']';

KEYWORD_92 : ('B'|'b')('E'|'e')('T'|'t')('W'|'w')('E'|'e')('E'|'e')('N'|'n')']';

KEYWORD_93 : ('D'|'d')('A'|'a')('Y'|'y')'_'('H'|'h')('O'|'o')('U'|'u')('R'|'r');

KEYWORD_94 : ('D'|'d')('I'|'i')('S'|'s')('T'|'t')('I'|'i')('N'|'n')('C'|'c')('T'|'t');

KEYWORD_95 : ('G'|'g')('R'|'r')('O'|'o')('U'|'u')('P'|'p')' '('B'|'b')('Y'|'y');

KEYWORD_96 : ('N'|'n')('O'|'o')('T'|'t')' '('L'|'l')('I'|'i')('K'|'k')('E'|'e');

KEYWORD_97 : ('N'|'n')('O'|'o')('T'|'t')('E'|'e')('Q'|'q')('U'|'u')('A'|'a')('L'|'l');

KEYWORD_98 : ('O'|'o')('R'|'r')('D'|'d')('E'|'e')('R'|'r')' '('B'|'b')('Y'|'y');

KEYWORD_99 : '['('B'|'b')('E'|'e')('T'|'t')('W'|'w')('E'|'e')('E'|'e')('N'|'n');

KEYWORD_100 : '['('G'|'g')('R'|'r')('E'|'e')('A'|'a')('T'|'t')('E'|'e')('R'|'r');

KEYWORD_82 : ('B'|'b')('E'|'e')('T'|'t')('W'|'w')('E'|'e')('E'|'e')('N'|'n');

KEYWORD_83 : ('E'|'e')('X'|'x')('C'|'c')('L'|'l')('U'|'u')('D'|'d')('E'|'e');

KEYWORD_84 : ('E'|'e')('X'|'x')('T'|'t')('R'|'r')('A'|'a')('C'|'c')('T'|'t');

KEYWORD_85 : ('G'|'g')('R'|'r')('E'|'e')('A'|'a')('T'|'t')('E'|'e')('R'|'r');

KEYWORD_86 : ('I'|'i')('N'|'n')('C'|'c')('L'|'l')('U'|'u')('D'|'d')('E'|'e');

KEYWORD_87 : ('I'|'i')('S'|'s')' '('N'|'n')('U'|'u')('L'|'l')('L'|'l');

KEYWORD_88 : ('N'|'n')('A'|'a')('T'|'t')('U'|'u')('R'|'r')('A'|'a')('L'|'l');

KEYWORD_89 : ('P'|'p')('E'|'e')('R'|'r')('C'|'c')('E'|'e')('N'|'n')('T'|'t');

KEYWORD_90 : ('Q'|'q')('U'|'u')('A'|'a')('R'|'r')('T'|'t')('E'|'e')('R'|'r');

KEYWORD_91 : ('U'|'u')('N'|'n')('P'|'p')('I'|'i')('V'|'v')('O'|'o')('T'|'t');

KEYWORD_74 : ('E'|'e')('X'|'x')('C'|'c')('E'|'e')('P'|'p')('T'|'t');

KEYWORD_75 : ('E'|'e')('X'|'x')('I'|'i')('S'|'s')('T'|'t')('S'|'s');

KEYWORD_76 : ('H'|'h')('A'|'a')('V'|'v')('I'|'i')('N'|'n')('G'|'g');

KEYWORD_77 : ('M'|'m')('I'|'i')('N'|'n')('U'|'u')('T'|'t')('E'|'e');

KEYWORD_78 : ('N'|'n')('O'|'o')('T'|'t')' '('I'|'i')('N'|'n');

KEYWORD_79 : ('O'|'o')('F'|'f')('F'|'f')('S'|'s')('E'|'e')('T'|'t');

KEYWORD_80 : ('S'|'s')('E'|'e')('C'|'c')('O'|'o')('N'|'n')('D'|'d');

KEYWORD_81 : ('S'|'s')('E'|'e')('L'|'l')('E'|'e')('C'|'c')('T'|'t');

KEYWORD_57 : ('C'|'c')('A'|'a')('S'|'s')('T'|'t')'(';

KEYWORD_58 : ('C'|'c')('R'|'r')('O'|'o')('S'|'s')('S'|'s');

KEYWORD_59 : ('E'|'e')('Q'|'q')('U'|'u')('A'|'a')('L'|'l');

KEYWORD_60 : ('F'|'f')('I'|'i')('R'|'r')('S'|'s')('T'|'t');

KEYWORD_61 : ('I'|'i')('N'|'n')('N'|'n')('E'|'e')('R'|'r');

KEYWORD_62 : ('L'|'l')('E'|'e')('S'|'s')('S'|'s')']';

KEYWORD_63 : ('L'|'l')('I'|'i')('M'|'m')('I'|'i')('T'|'t');

KEYWORD_64 : ('M'|'m')('I'|'i')('N'|'n')('U'|'u')('S'|'s');

KEYWORD_65 : ('M'|'m')('O'|'o')('N'|'n')('T'|'t')('H'|'h');

KEYWORD_66 : ('N'|'n')('O'|'o')('T'|'t')('I'|'i')('N'|'n');

KEYWORD_67 : ('N'|'n')('U'|'u')('L'|'l')('L'|'l')('S'|'s');

KEYWORD_68 : ('O'|'o')('U'|'u')('T'|'t')('E'|'e')('R'|'r');

KEYWORD_69 : ('P'|'p')('I'|'i')('V'|'v')('O'|'o')('T'|'t');

KEYWORD_70 : ('R'|'r')('A'|'a')('N'|'n')('G'|'g')('E'|'e');

KEYWORD_71 : ('R'|'r')('I'|'i')('G'|'g')('H'|'h')('T'|'t');

KEYWORD_72 : ('U'|'u')('N'|'n')('I'|'i')('O'|'o')('N'|'n');

KEYWORD_73 : ('W'|'w')('H'|'h')('E'|'e')('R'|'r')('E'|'e');

KEYWORD_36 : ('C'|'c')('A'|'a')('S'|'s')('E'|'e');

KEYWORD_37 : ('D'|'d')('E'|'e')('S'|'s')('C'|'c');

KEYWORD_38 : ('E'|'e')('L'|'l')('S'|'s')('E'|'e');

KEYWORD_39 : ('F'|'f')('R'|'r')('O'|'o')('M'|'m');

KEYWORD_40 : ('F'|'f')('U'|'u')('L'|'l')('L'|'l');

KEYWORD_41 : ('H'|'h')('O'|'o')('U'|'u')('R'|'r');

KEYWORD_42 : ('J'|'j')('O'|'o')('I'|'i')('N'|'n');

KEYWORD_43 : ('L'|'l')('A'|'a')('S'|'s')('T'|'t');

KEYWORD_44 : ('L'|'l')('E'|'e')('F'|'f')('T'|'t');

KEYWORD_45 : ('L'|'l')('E'|'e')('S'|'s')('S'|'s');

KEYWORD_46 : ('L'|'l')('I'|'i')('K'|'k')('E'|'e');

KEYWORD_47 : ('N'|'n')('O'|'o')('T'|'t')'\n';

KEYWORD_48 : ('N'|'n')('O'|'o')('T'|'t')' ';

KEYWORD_49 : ('O'|'o')('N'|'n')('L'|'l')('Y'|'y');

KEYWORD_50 : ('O'|'o')('V'|'v')('E'|'e')('R'|'r');

KEYWORD_51 : ('R'|'r')('O'|'o')('W'|'w')('S'|'s');

KEYWORD_52 : ('S'|'s')('O'|'o')('M'|'m')('E'|'e');

KEYWORD_53 : ('T'|'t')('H'|'h')('E'|'e')('N'|'n');

KEYWORD_54 : ('W'|'w')('E'|'e')('E'|'e')('K'|'k');

KEYWORD_55 : ('W'|'w')('H'|'h')('E'|'e')('N'|'n');

KEYWORD_56 : ('Y'|'y')('E'|'e')('A'|'a')('R'|'r');

KEYWORD_25 : '(''+'')';

KEYWORD_26 : ('A'|'a')('L'|'l')('L'|'l');

KEYWORD_27 : ('A'|'a')('N'|'n')('D'|'d');

KEYWORD_28 : ('A'|'a')('N'|'n')('Y'|'y');

KEYWORD_29 : ('A'|'a')('S'|'s')('C'|'c');

KEYWORD_30 : ('D'|'d')('A'|'a')('Y'|'y');

KEYWORD_31 : ('E'|'e')('N'|'n')('D'|'d');

KEYWORD_32 : ('F'|'f')('O'|'o')('R'|'r');

KEYWORD_33 : ('R'|'r')('O'|'o')('W'|'w');

KEYWORD_34 : ('T'|'t')('O'|'o')('P'|'p');

KEYWORD_35 : ('X'|'x')('M'|'m')('L'|'l');

KEYWORD_14 : '!''=';

KEYWORD_15 : '$'('X'|'x');

KEYWORD_16 : '<''=';

KEYWORD_17 : '<''>';

KEYWORD_18 : '>''=';

KEYWORD_19 : ('A'|'a')('S'|'s');

KEYWORD_20 : ('I'|'i')('N'|'n');

KEYWORD_21 : ('O'|'o')('N'|'n');

KEYWORD_22 : ('O'|'o')('R'|'r');

KEYWORD_23 : '^''=';

KEYWORD_24 : '|''|';

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

KEYWORD_12 : '|';

KEYWORD_13 : '}';



RULE_JRPARAM : '$P{' ( options {greedy=false;} : . )*'}';

RULE_JRNPARAM : '$P!{' ( options {greedy=false;} : . )*'}';

RULE_STAR : '*';

RULE_UNSIGNED : ('0'..'9')+;

RULE_INT : '-'? RULE_UNSIGNED;

RULE_SIGNED_DOUBLE : '-'? RULE_UNSIGNED ('.' RULE_UNSIGNED)?;

RULE_TIMESTAMP : RULE_DATE ' ' RULE_TIME;

RULE_DATE : '\'' '0'..'9' '0'..'9' '0'..'9' '0'..'9' '-' '0'..'1' '0'..'9' '-' '0'..'3' '0'..'9' '\'';

RULE_TIME : '\'' '0'..'9' '0'..'9' ':' '0'..'9' '0'..'9' ':' '0'..'9' '0'..'9' '.' '0'..'9' '0'..'9' '0'..'9' '\'';

RULE_STRING_ : '\'' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'\'')))* '\'';

RULE_STRING : '"' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'"')))* '"';

RULE_DBNAME : ('`' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|'`')))* '`'|'[' ('\\' ('b'|'t'|'n'|'f'|'r'|'u'|'"'|'\''|'\\')|~(('\\'|']')))* ']');

RULE_ID : ('a'..'z'|'A'..'Z'|'\u00C0'..'\u00FF'|'\u0100'..'\u017F'|'\u0180'..'\u024F'|'\u0410'..'\u044F'|'_'|'$'|'\u3041'..'\u309F'|'\u30A0'..'\u30FF'|'\u31F0'..'\u31FF'|'\u4E00'..'\u9FFF'|'\u6B74'..'\u3059'|'\u30A2'..'\u30F3'|'\uF900'..'\uFAFF'|'\u3400'..'\u4DBF'|'\uFF3F') ('a'..'z'|'A'..'Z'|'\u00C0'..'\u00FF'|'\u0100'..'\u017F'|'\u0180'..'\u024F'|'\u0410'..'\u044F'|'_'|'-'|'$'|'\u3041'..'\u309F'|'\u30A0'..'\u30FF'|'\u31F0'..'\u31FF'|'\u4E00'..'\u9FFF'|'\u6B74'..'\u3059'|'\u30A2'..'\u30F3'|'\uF900'..'\uFAFF'|'\u3400'..'\u4DBF'|'\uFF3F'|'0'..'9')*;

RULE_SL_COMMENT : ('--'|'#'|'//') ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;



