

import java.util.Collections;

public class MvCommand extends Command {
	String[] array;

	public MvCommand(String[] array, Directory currentDir) {
		super();
		this.array = array;	
		this.currentDir = currentDir;

	}

	public void execute() {

		Component staticComponent = currentDir;
		Component toCopy = null;
		String[] cale = array[1].split("/");
		boolean haveRoot = false;
		Directory newDir = null;
		if(array[1].equals("/")) { 
			toCopy = Bash.root;
			haveRoot = true;
		}
		else if(cale[0].equals(""))
			//daca am cale absoluta
			newDir  = Bash.root.getDir(cale, 1, (cale.length -2));
		else
			//cale relativa
			newDir = currentDir.getDir(cale, 0, (cale.length -2));

		int ok = 1;
		if(haveRoot == false)
			if(newDir == null) {
				System.err.printf("mv: cannot move %s: No such file or directory\n", array[1]);
				ok =0;
			}
			else 
				toCopy = newDir;
		String name = null;
		//in toCopy am directorul in care se afla elementul de mutat
		if(ok == 1 && haveRoot == false) {
			toCopy = toCopy.getSubComponent(cale[cale.length -1]);
			if(toCopy == null) {
				ok = 0;
				System.err.printf("mv: cannot move %s: No such file or directory\n", array[1]);
			}
			if(ok ==1) {
				String[] aux1 = toCopy.getPath().split("/");
				if(array[1].equals(".")) name = currentDir.getName();
				else
					name  = aux1[aux1.length -1];
			}
			//acum am in toCopy elementul ce trebuie mutat
		}
		//aflu calea catre destiatie
		if(ok==1) {
			String[] cale2 = array[2].split("/");
			boolean haveRoot2 = false;
			if(array[2].equals("/")) { 
				staticComponent = Bash.root;
				haveRoot2 = true;
			}
			else if(cale2[0].equals(""))
				//daca am cale absoluta
				newDir = Bash.root.getDir(cale2, 1, (cale2.length -1));
			else
				//cale relativa
				newDir = currentDir.getDir(cale2, 0, (cale2.length -1));

			if(haveRoot2 == false)
				if(newDir == null) {
					System.err.printf("mv: cannot move into %s: No such directory\n", array[2]);
					ok=0;
				}
				else 
					staticComponent = newDir;

			Directory whereToCopy = (Directory) staticComponent;
			//verificam daca la destinatie exista deja sau nu
			if(ok ==1) {
				Component duplicat = whereToCopy.getSubComponent(name);
				if(duplicat!=null) {ok = 0;
				System.err.printf("mv: cannot move %s: Node exists at destination\n", array[1]);
				}
			}

			if(ok ==1) {
				Component newComponent = null;
				String actual = currentDir.getPath();
				String pathToDelete = toCopy.getPath();
				boolean changeActualComponent = false;
				if(actual.contains(pathToDelete))
					changeActualComponent = true;

				if(toCopy instanceof Directory) {
					newComponent = new Directory(toCopy.getName(), null);
					toCopy.copyComponent((Directory) newComponent); 
				}
				else {
					newComponent = new File (toCopy.getName(), null);
				}
				newComponent.setParent(whereToCopy);
				whereToCopy.components.add(newComponent);
				Collections.sort(whereToCopy.components);
				((Directory) toCopy.getParent()).components.remove(toCopy);

				if(changeActualComponent == true)
					this.currentDir = (Directory) newComponent;
			}
		}

	}
}
