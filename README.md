# ProgettoObjectOriented
 Mi è stato chiesto di creare una versione videoludica del gioco da tavolo WhiteHall Mistery un gioco da tavolo (di cui on avevo mai sentito parlare)
 
# Whitehall Mistery

Progettare e implementare un programma Java che consenta all'utente di giocare al gioco da tavolo "Whitehall Mistery". In questo gioco i giocatori si dividono tra investigatori (tutti i giocatori tranne uno) e un assassino. Gli investigatori hanno l'obiettivo di trovare l'assassino (l'assassino conosce la posizione degli investigatori, mentre gli investigatori non conoscono la posizione dell'assassino). L'assassino, invece, ha l'obiettivo di raggiungere 4 obiettivi senza farsi catturare.

# Tabellone

Il tabellone di gioco è composto da una serie di luoghi collegati tra di loro. Esistono due tipologie di luoghi:

    Celle dell'assassino (indicate attraverso cerchi numerati, es. 24), su cui può sostare solo l'assassino;
    Celle degli investigatori (indicate attraverso quadrati con coppie di lettere, es "AB"), su cui possono sostare solo gli investigatori. Si noti che, nel tabellone allegato, è riportato il nome solo di un sottoinsieme delle celle degli investigatori (solo quelle più in alto), ma sono comunque tutte identificate da un nome di due lettere.

Alcune delle celle dell'assassino (quelle con sfondo nero) non possono essere scelte come obiettivi. La posizione iniziale degli investigatori può essere una delle celle degli investigatori in giallo.

Il tabellone è diviso in quattro quadranti (Nord-Ovest, Nord-Est, Sud-Ovest, Sud-Est).

# Setup

    Inserimento dei giocatori: Il programma chiede all'utente di inserire i nomi dei giocatori (da 2 a 4). Il programma assegna a ciascuno di essi un ruolo: ci sarà un assassino solo, gli altri saranno investigatori.
    Posizionamento dell'assassino: Il programma chiede all'assassino di inserire 4 luoghi dove intende commettere i crimini nel tabellone (luoghi di ritrovamento). I luoghi devono essere in quadranti distinti del tabellone (per evitare che siano troppo vicini). Inoltre, chiede all'assassino di indicare la sua posizione iniziale tra i quattro luoghi di ritrovamento. Dopo il posizionamento degli investigatori, questo verrà rivelato e all'assassino resteranno tre obiettivi da raggiungere.
    Posizionamento degli investigatori: Il programma chiede a ogni investigatore, a turno, di scegliere e inserire la propria posizione iniziale tra quelle possibili.

# Fase di gioco

    Fuga nella notte: L'assassino sceglie di spostarsi in una determinata cella adiacente a quella in cui si trova. Se un investigatore è presente tra il luogo di partenza e il luogo di destinazione dell'assassino, la mossa non è ammessa. L'assassino può spostarsi solo su una cella numerata. L'assassino può spostarsi per un massimo di 15 volte tra un omicidio e l'altro. Se si eccedono le 15 mosse, l'assassino ha perso il gioco e gli investigatori hanno vinto. Se l'assassino raggiunge uno dei suoi obiettivi, si azzera il suo contatore di mosse e la posizione del ritrovamento (e, quindi, dell'assassino) viene resa nota agli investigatori. Se l'assassino raggiunge l'ultimo obiettivo (non dev'essere fatto in un particolare ordine), ha vinto il gioco.
    Caccia al mostro: A turno, gli investigatori possono scegliere o meno di spostarsi in una delle celle adiacenti a loro dedicate. Gli investigatori possono spostarsi solo su una cella indicata dalle lettere. Gli investigatori possono scavalcare l'assassino (non conoscono la sua posizione!).
    Indizi e sospetti: A turno, gli investigatori possono scegliere se chiedere indizi o effettuare un arresto. Se si chiedono indizi, si può chiedere al programma se l'assassino è stato in una delle celle dell'assassino adiacenti alla cella in cui è posizionato l'investigatore in questione. Se l'assassino è passato nella cella scelta durante la partita, il programma lo segnala agli investigatori, l'indizio viene registrato dal programma e il turno dell'investigatore termina (non può più chiedere indizi). Se l'investigatore decide di effettuare l'arresto, sceglie una singola cella dell'assassino a lui adiacente. Se l'assassino si trova in quel momento in quella cella, gli investigatori hanno vinto il gioco e la partita termina.

Le tre fasi di gioco si ripetono finché l'assassino o gli investigatori vincono.

# Funzioni di utilità

In qualsiasi fase della partita, dev'essere possibile usare le seguenti funzionalità:

    Elenco degli indizi: Il programma elenca gli indizi raccolti dagli investigatori, nell'ordine in cui sono stati raccolti (dal meno recente al più recente).
    Posizione degli investigatori: Il programma mostra la posizione degli investigatori.
    Numero di mosse fatte dall'assassino: Il programma mostra il numero di mosse effettuate dall'assassino dall'ultimo obiettivo raggiunto.
    Posizione dell'assassino: Il programma mostra la posizione attuale dell'assassino (solo l'assassino dovrebbe usare questa funzionalità!).
    Fine partita: La partita termina.

Interfaccia
Implementare una prima versione del gioco con un'interfaccia da linea di comando. Progettare autonomamente l'interfaccia e le interazioni con l'utente. Si assuma che i giocatori abbiano un tabellone fisico per guardare le varie celle e le loro relazioni (il programma non dovrà mai mostrare a schermo il tabellone!)

# Note

    Uno sviluppatore dovrebbe poter personalizzare il tabellone a proprio piacimento.
    Per la prima consegna non è necessario simulare l'intero tabellone; un sotto-tabellone con 20 celle dell'assassino (e un numero analogo di celle degli investigatori) è sufficiente. In ogni caso, va simulata la divisione in quadranti relativa alle varie celle appartenenti al tabellone.
