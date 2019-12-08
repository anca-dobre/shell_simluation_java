
public class RmCommand extends Command {

	String[] array;
	
	public RmCommand (String[] array, Directory currentDir) {
		this.array = array;
		this.currentDir = currentDir;
	}

	public void execute() {
		Component staticComponent = currentDir;
		String[] cale = array[1].split("/");
		int err=2;
		int ok =1;
		Directory newDir = null;
		if(cale.length == 1 && (!cale[0].equals("") ))  {
			err = currentDir.deleteSubComponent(cale[0], currentDir);
			if(err==1) System.err.printf("rm: cannot remove %s: No such file or directory\n", array[1]);
		} else {
			if(cale[0].equals("")) {
				//daca am cale absoluta
				newDir = Bash.root.getDir(cale, 1, (cale.length -2));
			}
			else if(cale.length>=2) 
				//cale relativa
				newDir = currentDir.getDir(cale, 0, (cale.length -2));
			if(newDir == null) {
				System.err.printf("rm: cannot remove %s: No such file or directory\n", array[1]);
				ok=0;
			}
			else 
				staticComponent = newDir;
		}
		String name = cale[cale.length -1];
		if(err == 2 && ok == 1) {
			err = staticComponent.deleteSubComponent(name, currentDir);
			if(err == 1) System.err.printf("rm: cannot remove %s: No such file or directory\n", array[1]);
		}	}
	
}
