package riccardogulin.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "animals")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// @DiscriminatorColumn(name = "animal_type") // OPZIONALE. Serve per personalizzare il nome della Discriminator Column (di default si chiama DTYPE)
/*
SINGLE TABLE: è la strategia più semplice perché genererà un'unica tabella contenente tutti gli animali (nel nostro caso Cat e Dog)
Ha la comodità di avere un unico posto dove trovare tutti gli animali, quindi è più facilmente gestibile rispetto altre strategie. E' anche
performante in quanto le informazioni sul singolo animale sono tutte nella stessa tabella quindi quando devo reperire i dati sugli animali, le
queries non dovranno fare dei join o operazioni aggiuntive per recuperare tutti i dati

Di contro però ci potremmo trovare ad avere delle tabelle parecchio disordinate in alcuni casi, in particolare quando le classi figlie sono tante e
differiscono molto tra di esse in quanto ad attributi, con risultato tante celle della tabella avranno valore null. Questo mi impedisce anche di poter
inserire dei vincoli NON NULL su tali colonne e quindi sarò costretto a gestire il tutto tramite controlli lato codice

JOINED: Strategia che mi porta ad avere una tabella "padre" in cui saranno presenti gli attributi in comune (quelli della classe Animal nel nostro caso)
e una tabella per ogni figlio con gli attributi specifici di ogni figlio (quindi una per Cat e una per Dog). Nel nostro caso quindi avremo 3 tabelle totali
Mi dà una struttura più "pulita" rispetto alla single table in quanto non abbiamo il problema del NULL (quindi posso mettere i vincoli che voglio sulle
colonne)

Di contro però le operazioni che coinvolgono tutti i dati degli animali avranno bisogno di JOIN (seppur hanno un costo limitato, sono sempre un'operazione
extra)

Questa strategia è da preferire quando i figli hanno tanti attributi diversi e pochi in comune

TABLE_PER_CLASS: La si potrebbe anche chiamare TABLE_PER_CONCRETE_CLASS nel senso che creerà tabelle solo ed esclusivamente per le classi concrete
(non per quelle astratte quindi). Il risultato è sicuramente uno schema ben pulito, in quanto tutto è ben separato, non ho problemi con i null, non
c'è bisogno di fare join e posso anche mettere i vincoli sulle colonne che voglio

Di contro ci sono però degli svantaggi abbastanza importanti in termini di prestazioni e di relazioni. Quando effettuo delle queries polimorfiche,
cioè queries che coinvolgono tutti gli animali (non uno specifico) ci sarà del lavoro extra dietro le quinte per unire i dati di entrambe le tabelle
Se invece ci sono queries che come target hanno o cani o gatti, allora nessun problema anzi si comporta molto bene in questo caso.
Un altro problema riguarda le relazioni, nel senso che se ci dovesse essere una terza tabella da mettere in relazione con gli animali, questo non
sarebbe possibile in quanto una relazione è sempre tra tabella A e tabella B, non può biforcarsi da tabella A a tabella B e tabella C in contemporanea


* */
@NamedQuery(name = "findAllNames", query = "SELECT a.name FROM Animal a")
// Le named queries oltre a poter essere riutilizzate quante volte vogliamo hanno anche il vantaggio di venir controllate all'avvio dell'applicazione
public abstract class Animal {
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue
	protected long id;
	protected String name;
	protected int age;


	@ManyToOne
	@JoinColumn(name = "owner_id")
	protected Owner owner;

	public Animal() {
	}

	public Animal(String name, int age) {
		this.age = age;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
