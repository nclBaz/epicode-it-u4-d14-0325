package riccardogulin.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "animals")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "animal_type") // OPZIONALE. Serve per personalizzare il nome della Discriminator Column (di default si chiama DTYPE)
/*
SINGLE TABLE: è la strategia più semplice perché genererà un'unica tabella contenente tutti gli animali (nel nostro caso Cat e Dog)
Ha la comodità di avere un unico posto dove trovare tutti gli animali, quindi è più facilmente gestibile rispetto altre strategie. E' anche
performante in quanto le informazioni sul singolo animale sono tutte nella stessa tabella quindi quando devo reperire i dati sugli animali, le
queries non dovranno fare dei join o operazioni aggiuntive per recuperare tutti i dati

Di contro però ci potremmo trovare ad avere delle tabelle parecchio disordinate in alcuni casi, in particolare quando le classi figlie sono tante e
differiscono molto tra di esse in quanto ad attributi, con risultato tante celle della tabella avranno valore null. Questo mi impedisce anche di poter
inserire dei vincoli NON NULL su tali colonne e quindi sarò costretto a gestire il tutto tramite controlli lato codice

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
