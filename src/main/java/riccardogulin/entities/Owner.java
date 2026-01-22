package riccardogulin.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "owners")
public class Owner {
	@Id
	@GeneratedValue
	private long id;
	private String name;

	@OneToMany(mappedBy = "owner")
	private List<Animal> animal;

	public Owner() {
	}

	public Owner(String name) {
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

	@Override
	public String toString() {
		return "Owner{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
