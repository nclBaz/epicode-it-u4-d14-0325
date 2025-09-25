package riccardogulin.entities;

import jakarta.persistence.Entity;

@Entity
// @DiscriminatorValue("Gatto")
// OPZIONALE. Serve per personalizzare il nome inserito all'interno della Discriminator Column ( di default è il nome della classe)
public class Cat extends Animal {
	private double maxJumpHeight;

	public Cat() {
	}

	public Cat(String name, int age, double maxJumpHeight) {
		super(name, age);
		this.maxJumpHeight = maxJumpHeight;
	}

	public double getMaxJumpHeight() {
		return maxJumpHeight;
	}

	public void setMaxJumpHeight(double maxJumpHeight) {
		this.maxJumpHeight = maxJumpHeight;
	}

	@Override
	public String toString() {
		return "Cat{" +
				"maxJumpHeight=" + maxJumpHeight +
				", id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				"} ";
	}
}
