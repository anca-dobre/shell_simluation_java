

public class File extends Component {

	private String name;
	private Component parent;

	public File(String name, Component parent) {
		this.name = name;
		this.parent = parent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(Component parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public Component getParent() {
		return parent;
	}

	@Override
	public void displayName(int lsr) {
		if(parent!=null){
			String path = getPath();
			System.out.printf("%s:\n", path);
		}
		else {
			System.out.printf("%s:\n", getName());
		}
	}

	@Override
	public void add(Component ex2) {
		// TODO Auto-generated method stub		
	}


	@Override
	public String getPath() {
		if (parent == null) {
			return "/";
		} else {
			if(this.parent.getParent() == null) return parent.getPath() + name;
			else
				return parent.getPath() + "/" + name;	
		}

		// TODO Auto-generated method stub

	}

	@Override
	public Component getSubComponent(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteSubComponent(String name, Component actualComponent) {
		return 0;
		// TODO Auto-generated method stub

	}

	@Override
	public void copyComponent(Directory newComponent) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setParent(Directory newParent) {
		this.parent = newParent;
		// TODO Auto-generated method stub

	}


}

