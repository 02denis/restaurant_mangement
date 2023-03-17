package management;

/**
 * @author denii
 * Aceasta clasa ne ajuta la definirea atributelor si metodelor pentru administratori
 * Contine numele, ID-ul si parola administratorilor
 */
public class Admin {
	
	String name;
	int ID;
	String pass;
	
	/**
	 * constructor
	 */
	public Admin() {
		
	}
	
	/**
	 * @param name = numele administratorului
	 * @param ID = id-ul administratorului 
	 * @param pass = parola adminiustratorului
	 * constructor pentru obiecte de tip Admin
	 */
	public Admin(String name, int ID, String pass) {
		this.name=name;
		this.ID=ID;
		this.pass=pass;
	}
	
	/**
	 * @return name numele
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name numele
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return ID
	 */
	public int getID() {
		return ID;
	}
	/**
	 * @param iD ID-ul
	 */
	public void setID(int iD) {
		ID = iD;
	}

}
