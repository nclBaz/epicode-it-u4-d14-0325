package riccardogulin.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import riccardogulin.entities.Animal;
import riccardogulin.entities.Cat;
import riccardogulin.entities.Dog;
import riccardogulin.entities.Owner;
import riccardogulin.exceptions.NotFoundException;

import java.util.List;

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
		Animal found = entityManager.find(Animal.class, animalId); // Con la Single Table equivale a SELECT * FROM animals WHERE id = :animalId
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

	public List<Animal> findAllAnimals() {
		// La seguente query JPQL equivale a:
		// - caso single_table: SELECT * FROM animals
		// - caso joined: come sopra però con l'aggiunta di due JOIN per prelevare i dati anche da cats e dogs
		// - caso table_per_class: SELECT * FROM cats, SELECT * FROM dogs e unisce tutto con un operazione UNION ALL

		TypedQuery<Animal> query = entityManager.createQuery("SELECT a FROM Animal a", Animal.class);
		// entityManager.createNativeQuery("SELECT * FROM animals"); // <-- createNativeQuery permette di eseguire codice SQL e non JPQL
		return query.getResultList();
	}

	public List<Cat> findAllCats() {
		// La seguente query JPQL equivale a:
		// - caso single_table: SELECT * FROM animals WHERE dtype = 'Cat'
		// - caso joined: come sopra più un JOIN per prelevare i dati dei gatti
		// - caso table_per_class: SELECT * FROM cats
		TypedQuery<Cat> query = entityManager.createQuery("SELECT c FROM Cat c", Cat.class);
		return query.getResultList();
	}

	public List<Dog> findAllDogs() {
		TypedQuery<Dog> query = entityManager.createQuery("SELECT d FROM Dog d", Dog.class);
		return query.getResultList();
	}

	public List<String> findAllNames() {
		TypedQuery<String> query = entityManager.createNamedQuery("findAllNames", String.class);
		return query.getResultList();
	}

	public List<Animal> findAllNamesStartingWith(String partialName) {
		TypedQuery<Animal> query = entityManager.createQuery("SELECT a FROM Animal a WHERE LOWER(a.name) LIKE LOWER(:name)", Animal.class);
		query.setParameter("name", partialName + "%");
		return query.getResultList();
	}

	public void findAnimalsByNameAndUpdateName(String oldName, String newName) {
		EntityTransaction transaction = entityManager.getTransaction();
		// Siccome stiamo SCRIVENDO nel db, dobbiamo usare le transazioni
		transaction.begin();
		Query query = entityManager.createQuery("UPDATE Animal a SET a.name = :new WHERE a.name = :old");
		// UPDATE Animal a SET a.name = :new WHERE a.name = :old
		query.setParameter("new", newName);
		query.setParameter("old", oldName);

		int numModified = query.executeUpdate(); // Eseguiamo la query di update

		transaction.commit();

		System.out.println(numModified + " elementi sono stati aggiornati");

	}

	public void findAnimalsByNameAndDelete(String name) {
		EntityTransaction transaction = entityManager.getTransaction();
		// Siccome stiamo SCRIVENDO nel db, dobbiamo usare le transazioni
		transaction.begin();
		Query query = entityManager.createQuery("DELETE FROM Animal a WHERE a.name = :name");
		query.setParameter("name", name);

		int numDeleted = query.executeUpdate(); // Eseguiamo la query di delete

		transaction.commit();

		System.out.println(numDeleted + " elementi sono stati cancellati");
	}

	public List<Animal> findAnimalsByOwner(Owner owner) {
		TypedQuery<Animal> query = entityManager.createQuery("SELECT a FROM Animal a WHERE a.owner = :owner", Animal.class);
		query.setParameter("owner", owner);
		return query.getResultList();
	}

	public List<Animal> findAnimalsByOwnersName(String name) {
		TypedQuery<Animal> query = entityManager.createQuery("SELECT a FROM Animal a WHERE a.owner.name = :name", Animal.class);
		query.setParameter("name", name);
		return query.getResultList();
	}
}
