# ReadCVS
Java programs used to clean and parse CSV for Bachelors Final Dissertation at UFPB

Hello,

As the Final Dissertation project for my Computer Science degree I did this analysis on some amount of open data relasead by the
Brazilian government regarding the PAC (Programa de Aceleração do Crescimento - Growth Acceleration Program).

The data released on http://dados.gov.br/dataset/obras-do-pac-programa-de-aceleracao-do-crescimento was separeted in many files, each corresponding to a period of about 3 months. These files weren't perfectly formated ( in the .cvs format) and therefore needed cleaning.

They were also not cumulative. Eg. if some construcion took 3 cycles to get ready, it would not show up in the 4th cycle file. So there was need for a program to get all itens into one file.

The cleanned data was feeded to Tableau and the result was displayed on: http://omarsr.wix.com/dadospac


Currently rewriting the parse code in Ruby and propably going to use XML file to skip char errors (which were wuite common).
 
