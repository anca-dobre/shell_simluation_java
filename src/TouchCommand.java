
public class TouchCommand extends Command {

	Component component;
	String name;
	
	public TouchCommand (Component component, String name, Directory currentDir) {
		this.component = component;
		this.name = name;
		this.currentDir = currentDir;

	}

	public void execute() {
		this.component.add(new File(name, component));
	}
	
}
