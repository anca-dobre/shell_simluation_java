
public class LsCommand extends Command {
	String[] array;

	public LsCommand (String[] array, Directory currentDir) {
		this.currentDir = currentDir;
		this.array = array;
	}

	public void execute() {
		boolean haveRoot = false;
		int lsr = 0;
		for(int i = 0; i < array.length; i++) {
			if(array[i].equals("-R")) lsr = 1;  			
		}
		//am verificat daca am -R
		Component staticComponent = currentDir;
		int calea = 2;
		int ok = 1;
		if (array.length > 2 || (lsr == 0 && array.length == 2) ) {
			//daca am vreun argument de cale
			String[] cale = null;
			//verific al catalea argument este calea (poate fi al array[1] sau array[2])
			if(lsr == 0 || (lsr == 1 && (!array[1].equals("-R")) ) )
			{cale= array[1].split("/");
			calea = 1;
			}
			else
				cale = array[2].split("/");
			Directory newDir = null;
			if((calea == 1 && array[1].equals("/")) || ( (calea == 2 && array[2].equals("/"))  ) ) {
				//am root
				staticComponent = Bash.root;
				haveRoot = true;
			}
			else if(!cale[0].equals("")) {
				//am cale relativa					
				newDir = currentDir.getDir(cale, 0, cale.length -1);
			}
			else {
				//am cale absoluta
				newDir = Bash.root.getDir(cale, 1, cale.length -1);
			}
			if(haveRoot==false)
				if(newDir == null) {
					System.err.printf("ls: %s: No such directory\n", array[1]);
					ok=0;
				}
				else 
					staticComponent = newDir;
		}
		if(ok==1) staticComponent.displayName(lsr);
	}

}
