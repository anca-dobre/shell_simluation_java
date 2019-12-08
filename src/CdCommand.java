
public class CdCommand extends Command {
	String[] array;
	
	public CdCommand (String[] array, Directory currentDir) {
		this.currentDir = currentDir;
		this.array = array;

	}

	public void execute() {
		String[] cale = array[1].split("/");
		Directory newDir = null;
		if(array[1].equals("/")) { 
			newDir = (Directory) Bash.root;
		}
		else if(cale[0].equals(""))
			//daca am cale absoluta
			newDir = Bash.root.getDir(cale, 1, (cale.length -1));
		else
			//cale relativa
			newDir = currentDir.getDir(cale, 0, (cale.length -1));
		if(newDir == null) {
			System.err.printf("cd: %s: No such directory\n", array[1]);
		}
		if(newDir!=null)
			this.currentDir = newDir;
	}
	
}
