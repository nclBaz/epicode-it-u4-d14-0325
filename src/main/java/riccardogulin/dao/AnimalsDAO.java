package riccardogulin.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import riccardogulin.entities.Animal;

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
}
