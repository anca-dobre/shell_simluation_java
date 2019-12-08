

public abstract class Component implements Comparable<Object>{
	
	public String getName() {
		return null;
	}

	public void setName(String name) {
	}

	public abstract Component getParent();

	public abstract void copyComponent(Directory newComponent);
	
	public abstract void displayName(int lsr);

	public abstract void add(Component component);

	public Directory getDir(String[] cale, int start, int stop) {
		return null;
	};

	public abstract  void setParent (Directory newParent);
	
	
	public abstract Component getSubComponent (String name);	
	
	public abstract String getPath();

	public abstract int deleteSubComponent (String name, Component actualComponent);
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		String compare=((Component)o).getName();
		return this.getName().compareTo(compare);
	}

}
