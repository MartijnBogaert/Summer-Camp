package domain;

import java.util.ArrayList;
import java.util.List;

public class Camp {
	private Integer id;
	private Person manager;
	private Integer postalCode;
	private Integer maxChildren;

	private List<Person> children;

	public Camp(Integer id, String name, int postalCode, int maxChildren) {
		this.id = id;
		this.manager = new Person(name);
		this.postalCode = postalCode;
		this.maxChildren = maxChildren;
		children = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public Person getManager() {
		return manager;
	}

	public Integer getMaxChildren() {
		return maxChildren;
	}

	public boolean maxChildrenExceeded() {
		return (maxChildren == 0);
	}

	public void signUpChild(Person child) {
		if (!maxChildrenExceeded())
        {
			maxChildren--;
			children.add(child);
        }
	}

	public Integer getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}


}
