import re

"""
Funcion rewrite
No recibe parametros
Esta funcion abre el archivo "formateado.txt" para sobreescribirlo
y si no existe el archivo, lo crea
"""
def rewrite():
    r = open("formateado.txt","w")
    r.close()
    return

"""
Funcion leerConfig
No recibe parametros
Esta funcion lee el archivo "config.txt" y retorna un string con 
las configuraciones para el formateo
"""
def leerConfig():
    f = open("config.txt","r")
    config = f.readline()
    f.close()
    return config

"""
Funcion leerCodigo
No recibe parametros
Esta funcion lee el codigo y retorna una lista 
con las lineas del archivo de texto
"""
def leerCodigo():
    c = open("codigo.txt","r")
    lineas =[]
    for linea in c:
        lineas.append(linea)
    c.close()
    return lineas

"""
Funcion EscribirLineaFormateada
Recibe las cantidades de saltos, espacios y tabs (todos ints) y un string linea
La funcion abre el archivo "formateado.txt" para agregar la linea 
al archivo de texto con las configuraciones pedidas
"""

def EscribirLineaFormateada(espacios, saltos, Tabs, linea):
    F = open("formateado.txt","a")
    i = 0
    if ";" in linea:
        lista_1 = linea.split(";")
        for str in lista_1:
            lista_1[i] = lista_1[i].strip()
            if lista_1[i] != "":
                F.write("\t"*Tabs + lista_1[i] + ";")
                F.write("\n"*saltos)
            i+=1
    elif "{" in linea:
        lista_2 = linea.split("{")
        for str in lista_2:
            lista_2[i] = lista_2[i].strip()
            if lista_2[i] != "":
                F.write(lista_2[i] + "{")
                F.write("\n"*saltos)
            i+=1
    F.close()
    return




"""
EBNF
Por si solas, todas estas expresiones funcionan, pero no pueden detectar ningun tipo de anidacion
"""
digito = r'[1-9]'
digito_o_zero = r"("+digito+"|0)"
variable = r"([a-z]|[A-Z])([a-z]|[A-Z]|"+digito_o_zero+")*"
entero = r''+digito+'('+digito_o_zero+')*'
booleano = r'true|false'
string = r"(#)([a-z]|[A-Z]|"+digito_o_zero+")*(#)"
espacio = r'( )*|\t*( )*|\n*( )*'
operadores = r'\+|\-|\/|\*|\<|\=\='
bin = r'('+entero+'|'+booleano+'|'+string+'|'+variable+')('+espacio+')('+operadores+')('+espacio+')('+entero+'|'+booleano+'|'+string+'|'+variable+')'
oper_bin = r'('+entero+'|'+booleano+'|'+string+'|'+variable+'|'+bin+')(('+espacio+')('+operadores+')('+espacio+')('+entero+'|'+booleano+'|'+string+'|'+variable+'|'+bin+'))*'
condicion_oper = r'\<|\=\='
condicion = r'('+entero+'|'+booleano+'|'+string+'|'+variable+')('+espacio+')('+condicion_oper+')('+espacio+')('+entero+'|'+booleano+'|'+string+'|'+variable+')'
tipo = r'int|bool|str'
declaracion = r'('+tipo+')('+espacio+')('+variable+')('+espacio+');'
igual = r'('+variable+')('+espacio+')=('+espacio+')('+oper_bin+')('+espacio+');'
linea_simple = r'('+declaracion+')|('+igual+')'
bloque =r'\{(('+espacio+')('+linea_simple+'))*('+espacio+')\}'
condicional = r'if('+espacio+')\(('+espacio+')('+condicion+')('+espacio+')\)('+espacio+')('+bloque+')('+espacio+')else('+espacio+')('+bloque+')'
ciclo = r'while('+espacio+')\(('+espacio+')('+condicion+')('+espacio+')\)('+espacio+')('+bloque+')'
linea_compleja = r'('+linea_simple+')|('+condicional+')|('+ciclo+')'
main = r'int('+espacio+')main\(\)('+espacio+')\{(('+espacio+')('+linea_compleja+'))*('+espacio+')return('+espacio+')0('+espacio+');('+espacio+')\}'


#codigo principal

rewrite() #Se llama a rewite para limpiar el archivo de formateo

#Se obtiene la informacion de los archivos de texto 
lineas_list = leerCodigo()
str_config = leerConfig()

#En estas lineas se consiguen los valores de la configuracion
config_list = str_config.split()
cantEspacios = int(config_list[0])
cantSaltosLinea = int(config_list[1])
cantTab = int(config_list[2])

#Se crea el string codigo, el cual sera de utilidad
codigo = ""
for linea in lineas_list:
    codigo += linea

#Se verifica que el codigo este bien escrito
#Si lo esta, este se escribe con la funcion EscribirLineaFormateada
#Si no lo esta, se escribe en formateado que el codico es incorrecto
if (re.match(main,codigo)):
    for linea in lineas_list:
        EscribirLineaFormateada(cantEspacios,cantSaltosLinea,cantTab,linea)
else:
    Wrong = open("formateado.txt","w")
    Wrong.write("Codigo incorrecto")
    Wrong.close()


#Esta seccion surgio como un "parche"
#El codigo funcionaba bien pero cambie algo 
#y debido a eso estan estas lineas
if (re.match(main,codigo)):
    Fin = open("formateado.txt","a")
    Fin.write("}")
    Fin.close()