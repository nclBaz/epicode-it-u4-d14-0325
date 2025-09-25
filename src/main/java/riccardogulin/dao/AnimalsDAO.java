package riccardogulin.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import riccardogulin.entities.Animal;
import riccardogulin.entities.Cat;
import riccardogulin.entities.Dog;
import riccardogulin.exceptions.NotFoundException;

public class AnimalsDAO {
	private final EntityManager entityManager;

	public AnimalsDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void save(Animal newAnimal) {
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		entityManager.persist(newAnimal);

		transaction.commit();

		System.out.println("L'animale " + newAnimal.getName() + " è stato salvato!");
	}

	public Animal findById(long animalId) {
		Animal found = entityManager.find(Animal.class, animalId); // Con la Single Table equivale aSELECT * FROM animals WHERE id = :animalId
		if (found == null) throw new NotFoundException(animalId);
		return found;
	}

	public Dog findDogById(long dogId) {
		Dog found = entityManager.find(Dog.class, dogId);
		// Con la Single Table equivale a SELECT * FROM animals WHERE id = :dogId AND animal_type = "Cane"
		if (found == null) throw new NotFoundException(dogId);
		return found;
	}

	public Cat findCatById(long catId) {
		Cat found = entityManager.find(Cat.class, catId); // Con la Single Table equivale a SELECT * FROM animals WHERE id = :catId AND animal_type = "Gatto"
		if (found == null) throw new NotFoundException(catId);
		return found;
	}
}
