# Sobre la practica
## Versión 1
En la v1 <ins>**se hizo toda la lógica**</ins> que debe pasar unos test proporcionados por el profesor.
La practica consiste en hacer un simulador donde unos coches con un itinerario de cruces van de carretera a carrtera de un cruce al ultimo de sus cruces en su itinerario. Además las carreteras tendrán un tiempo que pordrá afectar la velocidad máxima a la que los coches puedan ir. A esto se le incluye un contador de CO2 a las carreteras donde los coches emiten una cantidad de CO2 dependiendo de su clase de CO2, si la carretera supera la cantidad limite de CO2 que puede tener reducirá la velocidad de los coches.
</br>
<ins>**El simulador usará JSONs para manejar información**</ins>, es decir, para cargar los coches, carretera... cogerá un JSON con un formato, la aplicación transforma el JSON en los objetos correspondientes para hacer la simulación. Por último, <ins>**la simulación se hará mediante ticks**</ins>, cada tick es un ciclo update de todos los objetos de la simulación cargada.
## Versión 2
En la v2 <ins>**se hizo la GUI**</ins> de la aplicación con **Java Swing**.
La GUI consiste de una ventana con una toolbar con botones:
- Archivo: escoge un fichero JSON para cargar la simulación con los eventos correspondientes
- CO2 change: cambia la clase de CO2 de un coche en un tick especifico (Abre un dialogo)
- Weather change: cambia el tiempo de una carretera en un tick especifico (Abre un dialogo)
- Run: ejecuta x ticks de la simulación (x es el número que ponga en el spinner)
- Stop: para la simulación
- Spinner ticks: para cambiar la cantidad de ticks que se ejecutan
- Cerrar
En la ventana se ven otras ventanas, a la izquierda estan todas la tablas de de eventos, vehiculos, carretera y crucs de la siulación, a la derecha están los 2 mapas:
- Map: el mapa de los cruces y carreteras puestos es sus posiciones
- Map by road: el mapa del estado de las carreteras
Por último abajo del todo tenemos una barra de estado que muestra el tick en el cual la simulación actualmente está y el útmimo evento añadido. </br>
![mainwindow](https://github.com/user-attachments/assets/f1fdfee0-630d-4c56-8c4b-13061e027579)

## Versión 3
> Por definir
# Uso del proyecto
Se puede consulatar de forma libre, no hay copyright
> [!CAUTION]
> Por favor no copiar nada del reopsitorio, es un trabajo de la universidad sin ningún valor para personas fuera de esta. Para aquellos que sean universitarios tened encuanta que copiar de aquí no tiene ningún valor, es mejor intentar sacar soluciones propias a estos trabajos.
