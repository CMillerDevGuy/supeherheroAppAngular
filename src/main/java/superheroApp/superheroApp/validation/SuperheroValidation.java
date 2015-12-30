package superheroApp.superheroApp.validation;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import superheroApp.superheroApp.daos.OriginDao;
import superheroApp.superheroApp.daos.SuperheroDao;
import superheroApp.superheroApp.entities.Origin;
import superheroApp.superheroApp.entities.Power;
import superheroApp.superheroApp.entities.Superhero;

@Service
public class SuperheroValidation {

	@Autowired
	SuperheroDao superheroDao;
	@Autowired
	OriginDao originDao;

	public static class SuperheroException extends Exception {

		private static final long serialVersionUID = 1048412143358910691L;

		public SuperheroException(String message) {
			super(message);
		}

	}

	public boolean validateSuperhero(Superhero superhero) throws SuperheroException {
		return validatePowers(superhero) && validateWeight(superhero) && validateHeight(superhero)
				&& validateCostumeImage(superhero) && validateOrigin(superhero) && validateRealName(superhero)
				&& validateHeroName(superhero);
	}

	private boolean validatePowers(Superhero superhero) throws SuperheroException {
		boolean result = true;
		try {
			Set<Power> powerSet = superhero.getPowers();
			if (powerSet.size() == 0) {
				result = false;
				throw new SuperheroException("Power is empty");
			}
			for (Power p : powerSet) {
				String powerName = p.getPowerName();
				if (powerName == null || powerName.equals("")) {
					result = false;
					throw new SuperheroException("Power name is null or empty");
				}
			}
		} catch (NullPointerException e) {
			throw new SuperheroException("Hero powers are null");
		}
		return result;
	}

	private boolean validateWeight(Superhero superhero) throws SuperheroException {
		boolean result = false;
		try {
			int weight = superhero.getWeight();
			if (weight > 999) {
				throw new SuperheroException("No one weighs more than 999 pounds");
			} else {
				result = true;
			}
		} catch (NullPointerException e) {
			throw new SuperheroException("Hero weight is null");
		}
		return result;
	}

	private boolean validateHeight(Superhero superhero) throws SuperheroException {
		boolean result = false;
		try {
			String heightRegex = "^[3-9]{1}[']{1}([0]{1}[0-9]{1}|[1]{1}[0-1]{1})[\"]{1}$";
			String height = superhero.getHeight();
			if (!height.matches(heightRegex)) {
				throw new SuperheroException("Not a valid height");
			} else {
				result = true;
			}
		} catch (NullPointerException e) {
			throw new SuperheroException("Hero height is null");
		}
		return result;
	}

	private boolean validateCostumeImage(Superhero superhero) throws SuperheroException {
		boolean result = false;
		try {
			String costumeImage = superhero.getCostumeImage();
			if (costumeImage.equals("")) {
				throw new SuperheroException("Costume Image is empty");
			} else {
				result = true;
			}
		} catch (NullPointerException e) {
			throw new SuperheroException("Hero picture url is null");
		}
		return result;

	}

	private boolean validateOrigin(Superhero superhero) throws SuperheroException {
		boolean result = false;
		try {
			String origin = superhero.getOrigin().getOrigin();
			List<Origin> originList = originDao.getAllOrigin();
			if (origin.equals("")) {
				throw new SuperheroException("Origin is empty");
			} else {
				result = true;
			}
			for (Origin o : originList) {
				if (o.getOrigin().equals(origin)) {
					superhero.setOrigin(o);
				}
			}
		} catch (NullPointerException e) {
			throw new SuperheroException("Hero origin is null");
		}
		return result;
	}

	private boolean validateRealName(Superhero superhero) throws SuperheroException {
		boolean result = false;
		try {
			String realName = superhero.getSuperheroName();
			if (realName == null || realName.equals("")) {
				throw new SuperheroException("Real name is empty");
			} else {
				result = true;
			}
		} catch (NullPointerException e) {
			throw new SuperheroException("Real name is null");
		}
		return result;

	}

	private boolean validateHeroName(Superhero superhero) throws SuperheroException {
		boolean result = false;
		try {
			String heroName = superhero.getSuperheroName();
			if (heroName.equals("")) {
				throw new SuperheroException("Hero name is empty");
			} else {
				result = true;
			}
		} catch (NullPointerException e) {
			throw new SuperheroException("Hero name is null");
		}
		return result;
	}
}
