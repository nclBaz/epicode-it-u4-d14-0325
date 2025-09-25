package riccardogulin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import riccardogulin.dao.AnimalsDAO;
import riccardogulin.entities.Cat;
import riccardogulin.entities.Dog;
import riccardogulin.exceptions.NotFoundException;

public class Application {

	public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("u4d14pu");

	public static void main(String[] args) {
		EntityManager em = emf.createEntityManager();
		AnimalsDAO ad = new AnimalsDAO(em);

		Cat felix = new Cat("Felix", 40, 2.5);
		Dog ringhio = new Dog("Ringhio", 2, 1);
		Cat tom = new Cat("Tom", 4, 1);
		Dog rex = new Dog("Rex", 6, 10);

//		ad.save(felix);
//		ad.save(ringhio);
//		ad.save(tom);
//		ad.save(rex);

		try {

//			Animal animalFromDB = ad.findById(2);
//			System.out.println(animalFromDB);

			Cat catFromDB = ad.findCatById(2);
			System.out.println(catFromDB);
		} catch (NotFoundException ex) {
			System.out.println(ex.getMessage());
		}

		//ad.findAllAnimals().forEach(System.out::println);
		// ad.findAllCats().forEach(System.out::println);

		// ad.findAllNames().forEach(System.out::println);

//		ad.findAllNamesStartingWith("r").forEach(System.out::println);
//
//		ad.findAnimalsByNameAndUpdateName("Felix", "Ringhio2");
//
//		ad.findAnimalsByNameAndDelete("Rex");

		ad.findAnimalsByOwnersName("Giacomo").forEach(System.out::println);
		em.close();
		emf.close();

	}
}
