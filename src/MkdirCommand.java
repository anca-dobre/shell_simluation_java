
public class MkdirCommand extends Command {

	Component component;
	String name;
	
	public MkdirCommand( Component component, String name, Directory currentDir) {
		this.component = component;
		this.name = name;
		this.currentDir = currentDir;

	}

	public void execute() {
		this.component.add(new Directory(name, component));
	}
	
}
 