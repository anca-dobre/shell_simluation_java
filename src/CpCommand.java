
import java.util.Collections;

public class CpCommand extends Command {
	//	Component component;
	String[] array;

	public CpCommand (String[] array, Directory currentDir) {
		//		this.component = component;
		this.currentDir = currentDir;
		this.array = array;
	}

	public void execute( ) {
		Component staticComponent = currentDir;
		Component toCopy = null;
		String[] cale = array[1].split("/");
		//aflu calea catre ce trebuie copiat
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
				System.err.printf("cp: cannot copy %s: No such file or directory\n", array[1]);
				ok =0;
			}
			else 
				toCopy = newDir;
		String name = null;
		//in toCopy am directorul in care se afla elementul de copiat
		if(ok == 1 && haveRoot == false) {
			toCopy = toCopy.getSubComponent(cale[cale.length -1]);
			if(toCopy == null) {
				System.err.printf("cp: cannot copy %s: No such file or directory\n", array[1]);
				ok = 0;
			}
			if(ok ==1)
			{String[] aux1 = toCopy.getPath().split("/");  
			name  = aux1[aux1.length -1];
			//acum am in toCopy elementul ce trebuie copiat
			}
		}
		//aflu calea catre destiatie
		if(ok ==1) {
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
					System.err.printf("cp: cannot copy into %s: No such directory\n", array[2]);
					ok=0;
				}
				else 
					staticComponent = newDir;
			Directory whereToCopy = (Directory) staticComponent;
			//verificam daca la destinatie exista deja sau nu
			if(ok == 1) {
				Component duplicat = whereToCopy.getSubComponent(name);
				if(duplicat!=null) {
					ok = 0;
					System.err.printf("cp: cannot copy %s: Node exists at destination\n", array[1]);
				}
			}
			//		creez o copie a lui toCopy;
			if(ok == 1) {
				Component newComponent = null;
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
			}
		}

	}
}
