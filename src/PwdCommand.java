

public class PwdCommand extends Command {

	Component component;
	
	
	public PwdCommand (Component component, Directory currentDir) {
		this.component = component;
		this.currentDir = currentDir;

	}


	public void execute() {
		
		if(component.getParent()!=null){
			String path = component.getPath();
			System.out.printf("%s\n", path);
		}
		else {
			System.out.printf("%s\n", component.getName());
		}
	}
	
}
