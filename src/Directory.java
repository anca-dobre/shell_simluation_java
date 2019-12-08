

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class Directory extends Component{

	private String name;
	private Component parent;

	public ArrayList<Component> components = new ArrayList<Component>();

	public Directory(String name, Component parent) {
		this.name = name;
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public Component getParent() {
		return parent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(Directory parent) {
		this.parent = parent;
	}

	public String getPath() {
		if (parent == null ) {
			return "/";
		} else {
			if(this.parent.getParent() == null) return parent.getPath() + name;
			else
				return parent.getPath() + "/" + name;	
		}
	}

	public void add(Component newComponent) {

		components.add(newComponent);
		Collections.sort(components);
	}

	public void displayName(int lsr) {
		if(parent!=null){
			String path = getPath();
			System.out.printf("%s:\n", path);
		}
		else {
			System.out.printf("%s:\n", getName());
		}
		Iterator<Component> iterator = (Iterator<Component>) components.iterator();

		while(iterator.hasNext()) {
			Component component = iterator.next();
			String path = component.getPath();
			if(iterator.hasNext())
				System.out.printf("%s ", path);
			else
				System.out.printf("%s", path);

		}
		System.out.println("\n");

		if(lsr == 1) {
			iterator = (Iterator<Component>) components.iterator();
			while(iterator.hasNext()) {
				Component component = iterator.next();
				if(component instanceof Directory)
					component.displayName(lsr);
			}
		}
	}

	public Directory getDir(String[] cale, int start, int stop) {
		while( (cale[start].equals(".") || cale[start].equals("..") ) ) {
			if(cale[start].equals(".")){	
				start++;
				if(start > stop) return this;
			}
			else if(cale[start].equals("..")){	
				if(this.parent!=null) { 
					if(start==stop) {
						if(this.parent!=null) 
							return (Directory) this.parent;
						else 
							return null;
					}
					else {
						Directory found = this.parent.getDir(cale, ++start, stop);
						if(found!=null) return found;
						else return null;
					}
				}
				else {
					return null;
				}
			}
		}
		if(start>stop) return this;
		Iterator<Component> iterator = (Iterator<Component>) components.iterator();
		while(iterator.hasNext()) {
			Component component = iterator.next();
			if(component instanceof Directory) {
				if(component.getName().equals(cale[start]) && start == stop){
					return (Directory) component;
				} else if (component.getName().equals(cale[start]) && start<stop) {
					Directory found = component.getDir(cale, ++start, stop);
					if(found!=null) return found;
				}
			}
		}
		return null;
	}

	public Component getSubComponent (String name) {
		if(name.equals(".")){	
			return this;
		}
		else if(name.equals("..")){	
			if(this.parent!=null) { 
				return this.parent;
			}
			else {
				return null;
			}
		}
		Iterator<Component> iterator = (Iterator<Component>) components.iterator();
		while(iterator.hasNext()) {
			Component component = iterator.next();
			if(component.getName().equals(name)){
				return component;
			}

		}
		return null; 
	}


	public int deleteSubComponent (String name, Component whereToDelete) {
		int err =0;
		Component toDelete = null;
		Directory from = null;
		if(name.equals(".")){
			Directory parent = (Directory) this.parent;
			from = parent;
			toDelete = this;
		}
		else if(name.equals("..")){	
			Directory grandParent = (Directory) this.parent.getParent();
			from = grandParent;
			toDelete = this.parent;
		}
		else {
			Iterator<Component> iterator = (Iterator<Component>) components.iterator();
			while(iterator.hasNext()) {
				Component component = iterator.next();
				if(component.getName().equals(name)){
					from = this;
					toDelete = component;
					break;
				}
			}			
		}
		if(from == null) err =1;
		else {
			String actual = whereToDelete.getPath();
			String pathToDelete = toDelete.getPath();

			//stergem dupa ce ne asiguram ca nu contine si current directory
			if(!actual.contains(pathToDelete))
				from.components.remove(toDelete);
		}
		return err;
	}

	@Override
	public void copyComponent(Directory newComponent) {
		for(int i =0; i< this.components.size(); i++) {
			Component anotherNew;
			if(this.components.get(i)instanceof Directory) 
				anotherNew = new Directory (this.components.get(i).getName(), newComponent);
			else 
				anotherNew = new File (this.components.get(i).getName(), newComponent);
			newComponent.components.add(anotherNew);
			if(anotherNew instanceof Directory)
				this.components.get(i).copyComponent((Directory) anotherNew);
		}
	}

}