package riccardogulin.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "animals")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "animal_type") // OPZIONALE. Serve per personalizzare il nome della Discriminator Column (di default si chiama DTYPE)
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


* */
public abstract class Animal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;
	protected String name;
	protected int age;

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
