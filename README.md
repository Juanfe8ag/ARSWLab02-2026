# ARSW Lab 02 2026 - I
## Autor: Juan Felipe Ochoa
## Parte I: PrimeFinder
La solución planteada es usar un objeto Lock compartido por los hilos. Después de los 5 segundos el estado del lock
cambia a true por lo que los hilos se detendrán en el orden que agarren el Lock, posterior a ello se calcula la cuenta 
de números primos y se reanudan con un notifyAll().

## Parte II: SnakeRace
El código define cada serpiente con su respectivo hilo, los cuales son creados en conjunto con un tablero y un reloj. La autonomía
que se ve en este ejercicio es que cada objeto se puede recoger y asociar a la serpiente que lo obtuvo, sin necesidad de parar el juego, solo
actualizando el estado de la serpiente.

Primero se hizo el análisis de concurrencia donde se encontró lo siguiente:
- Cuando GameClock se detiene, no detiene a los hilos de las serpientes.
- Snake.body (colección) se accede varias veces desde varios puntos, lo que puede generar lecturas inconsistentes.
- Las estadísticas deberían calcularse, solo si el juego está pausado, es decir, cuando todos los SnakeRunner estén detenidos.

Las soluciones para cada una de estas observaciones fueron:
1. Así como en la parte I de este laboratorio crear un lock compartido entre todos los SnakeRunner del juego.
2. Al GameClock se le añadió el mismo objeto de control de las serpientes para que los dos hilos paren al tiempo.
3. Los métodos de Snake que accedían a Snake.body ahora tienen un candado para que las lecturas sean consistentes, ya sea para la interfaz o para el hilo.
4. Se creó un método de estadísticas para que cada vez que se pare el juego se vea la serpiente más larga y más corta.