Il progetto è stato realizzato seguendo lo schema MVC

# MODEL:

### Responsabilità:

1) Applicare il comando di un Robot all’ambiente:

   É stata implementata dall'interfaccia funzionale Command, che preso un ambiente lo modifica.

  
2) Contenere i vari oggetti nello spazio conoscendo le loro posizioni:
É stata implementata dall’interfaccia Environment parametrizzata con i Programmabili(robot) e Figure che contiene. L’ambiente è concretizzato dalla classe Space che viene instantiate tramite due mappe, una di Programmable e Cordinate l’altra con Figure e Cordinate.


3) Rappresentare un’area nello spazio.
É stata rappresentata dall’interfaccia Figure, chi implementa questa interfaccia deve essere in grado di dire se un punto è contenuto in una figura.


4) Rappresentare una posizione nello spazio:
Si suppone il punto come bidimensionale, il punto nello spazio e’ rappresentato dalla classe
Cordinate.


5) Rappresentare una direzione nello spazio con coordinate relative e velocità:
La direzione e’ rappresentata come un vettore che può essere anche visto come con coordinate polari e la sua lunghezza è data dalla velocità.


6) Gestire i Robot con il loro programma:
Il flusso del programma dei robot viene gestito tramite l’interfaccia ProgramFlowController concretizzata dalla classe ProgrammableFlowController, che preso un programmable e un ambiente e una lista di coppie(comando, parametri) è in grado di decidere il comando da applicare all’ambiente, sotto forma dell’interfaccia funzionale Command descritta nel punto 1.

   
7) Costruire il programma dei robot:
Chi costruisce il programma dei robot implementa sia l’handler, sia il ProgramFlowControllerBuilder, poiché dati i comandi letti dal FollowMeParser deve essere in grado di costruire la lista definita nel punto 6.


8) Applicare le direzioni a punti nello spazio:
Questa responsabilità è data dall’interfaccia funzionale DirectionApplier, la quale preso un punto, una direzione, un'unità di tempo espressa in secondi ne restituisce una nuova direzione.


9) Costruire le figure a partire dal file di specifica:
Viene rappresentata prima dall’interfaccia MapFigureBuilder, quest’ultima costruisce una mappa di figure a cui associa la loro posizione nello spazio, che nella simulazione è stata implementata da RectangleCircleBuilder.


10) Preso un file con le specifiche dell’ambiente costruirne l’ambiente:
È stata rappresentata dall’interfaccia EnvironmentFileLoader che preso un file con le figure e presa una mappa di Programmable ne restituisce un ambiente, la quale poi è stata concretizzata da SimulationEnvironmentLoader.


11) Preso un file contenente i programmi dei robot costruirne i controllori del programma :
Questa responsabilità è stata implementata dall’ interfaccia ProgrammableProgramFileLoader, la quale è parametrizzata con il tipo di controllori che andrà a creare, i programmable e le figure dell’ambiente.



# CONTROLLER:
Il controller richiede dei FileLoader sopra definiti con i quali presi dei file costituirà l’ambiente i controllori dei robot, i quali verranno inizializzati da un'apposita classe.
È inoltrein grado di far avanzare il modello di uno step, quindi di far eseguire a tutti i robot il proprio comando, è in grado di fornire la mappa dei robot e delle figure, è anche in grado di modificare l’unità di tempo del sistema.


# VIEW:
La view supporta una schermata principale dove ci sono i robot e le figure. Per inizializzarla è necessario premere il pulsante in alto a sinistra con l’ingranaggio il quale chiederà il numero dei robot da visualizzare, i file di specifica dell’ambiente e del programma.
- É stata fornita la possibilità di spostarsi nell’ambiente o tramite i bottoni appositi nell’interfaccia grafica oppure tramite i tasti WASD è anche stata data la possibilità di fare uno zoom verso l’interno e verso l’esterno.
- Si noti che gli assi rimarranno sempre centrali, anche quando ci si sposterà potranno essere usati come punto riferimento.
- Le figure che non sono completamente all’interno della simulazione non verranno mostrate, ma il sistema ne terrà comunque traccia quindi o rimpicciolendo oppure spostandosi questa potrà essere visualizzata.
- Per dei programmi e ambienti di prova si consiglia di usare i file nella cartella risorse/file nel modulo applicazione.
- Per cambiare la time unit bisogna scrivere l’unità desiderata nella casella in basso a sinistra e poi premere sul tasto in alto a destra con la chiave inglese.
- Per eseguire la simulazione per un determinato tempo dt bisogna prima impostare nella casella in basso, successivamente premere sull’icona in alto a destra per avviare per il tempo descritto(numero di passi).

