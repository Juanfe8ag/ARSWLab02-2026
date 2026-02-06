# ARSW Lab 02 2026
## Autor: Juan Felipe Ochoa
## Parte I: PrimeFinder
La solución planteada es usar un objeto Lock compartido por los hilos. Después de los 5 segundos el estado del lock
cambia a true por lo que los hilos se detendrán en el orden que agarren el Lock, posterior a ello se calcula la cuenta 
de números primos y se reanudan con un notifyAll().

## Parte II: SnakeRace
Primero se hizo el análisis de concurrencia donde se encontró lo siguiente:
- Cuando GameClock se detiene, no detiene a los hilos de las serpientes.