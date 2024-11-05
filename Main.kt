import java.io.File // para selecciones 1 y 2.
import java.io.FileNotFoundException // para selección 2
import kotlin.system.exitProcess // para selección 3

fun main(args: Array<String>) {
    var seleccion: Int = 0
    while (true) {
        println("Selecciona una opción: 1. Jugar 2. Ver partida anterior 3. Salir")
        seleccion = readLine()!!.toInt()
        if (seleccion==1) {
            val fondoVerde = "\u001B[42m"
            val fondoAmarillo = "\u001B[43m"
            val reset = "\u001b[0m"
            val azul = "\u001B[34m"
            // longitud del número = 4 cifras, sin cifras repetidas. solo usamos digitos del 1 al 6.
            val numeroSecreto = (1..6).shuffled().take(4) // por ejemplo 2134 .
            println(numeroSecreto)
            val numeroSecretoString = numeroSecreto.joinToString().replace(", ","")
            File("PartidaAnterior.txt").writeText("Número secreto: $numeroSecretoString \n")
            // Cada vuelta del bucle for es un intento :
            val maxIntentos = 3
            for (x in 1..maxIntentos){ // Número máximo de intentos permitidos: 2.
                println("Introduce un número de 4 cifras sin que éstas se repitan:")
                val misCifras = readln().map { it.digitToInt() }
                // digitToInt() Returns the numeric value of the decimal digit that this Char (it) represents.
                var c: Int = 0
                var aciertos: Int = 0
                var coincidencias: Int = 0
                if (numeroSecreto==misCifras) {
                    println("Enhorabuena, has acertado el número secreto.")
                    break // este break me saca del bucle for y por ende se vuelve a pedir seleccionar opción.
                }
                while (c < 4) {
                    if (numeroSecreto.get(c)==misCifras.get(c)) {aciertos++}
                    else if (misCifras.any { it == numeroSecreto.get(c) }) {coincidencias++}
                    c++
                }
                val misCifrasString = misCifras.joinToString().replace(", ","")
                println("$misCifrasString: ${fondoVerde}$aciertos aciertos${reset}, ${fondoAmarillo}${azul}$coincidencias coincidencias${reset}")
                File("PartidaAnterior.txt").appendText("Intento $x: ${misCifrasString}, Aciertos: $aciertos, Coincidencias: $coincidencias \n")
                if (x==maxIntentos) {println("Se han agotado los $x intentos. El número secreto era $numeroSecretoString")}
            }
        }
        else if (seleccion==2) {
            try {
                val content = File("PartidaAnterior.txt").readLines()
                //content es una lista de Strings, cada elemento de la lista es una línea del fichero txt.
                for (linea: String in content) {println(linea)}
            }
            catch (fnfe: FileNotFoundException) {println(fnfe.message)}
        }
        else if (seleccion==3) {
            println("Ha escogido salir del juego. Esperamos que vuelva pronto.")
            exitProcess(0)
        } // fin/salir del programa AdivinaElNumero.
        else {println("Número no válido, debe introducir su selección de nuevo (entre 1 y 3)")}
    }
}