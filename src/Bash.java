
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Bash {

	private static final Bash INSTANCE = new Bash();	
	static Component root = new Directory("/", null);
	
	public Directory currentDirectory;

	public static Bash getInstance(){
		return INSTANCE;
	}

	void main(String[] args) throws FileNotFoundException {
		int instructionNo = 1;
		BufferedReader br = null;
		FileReader fr = null;

		PrintStream fileStream = null;
		PrintStream fileStream2 = null;
		String nume = args[1];
		String nume2 = args[2];
		fileStream = new PrintStream (nume);
		fileStream2 = new PrintStream (nume2);
		System.setOut(fileStream);
		System.setErr(fileStream2);

		try {
			fr = new FileReader(args[0]);
			br = new BufferedReader(fr);
			currentDirectory = (Directory) root;
			String instruction;
			while((instruction = br.readLine()) != null) {
				System.out.println(instructionNo);
				System.err.println(instructionNo);
				instructionNo++;
				readInstruction (instruction, root);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getNewName(String[] array) {
		String[] cale = array[1].split("/");
		return cale[cale.length-1];
	}

	public String getParentPath(String[] cale) {
		String path = "";
		int i =0;
		if(cale.length == 0) return null;
		else if(cale.length == 1 && cale[0].equals(""))
			return "/";
		else if (cale[0].length() != 0) {
			path = cale[0];
			i++;}
		while( i < cale.length - 1)  {
			if(!cale[i].equals(""))
				path = path + "/";
			path = path + cale[i];
			i++;
		}
		return path;
	}

	public Component getStaticComponent (String[] array, Component root) {
		Component staticComponent = currentDirectory;
		String name = null;
		int ok = 1;
		Directory newDir = null;
		if (array.length>1) {
			String[] cale = array[1].split("/");
			name = cale[cale.length-1];
			if(cale.length>2 || (cale.length>1 && (!cale[0].equals(""))) ) {
				if(cale[0].equals("")) {
					//am cale absoluta
					newDir = root.getDir(cale, 1, (cale.length -2) );
				}
				else {
					//cale relativa
					newDir = currentDirectory.getDir(cale, 0, (cale.length -2) );
				}
				if(newDir == null) {
					if(array[0].equals("touch"))
						System.err.printf("touch: %s: No such directory\n", getParentPath(cale));
					else
						System.err.printf("mkdir: %s: No such directory\n", getParentPath(cale));
					ok =0;
				}
				else 
					staticComponent = newDir;
			} else if(cale[0].equals("")) staticComponent = root;
		} 
		if(ok==1) {
			Component duplicat = staticComponent.getSubComponent(name);
			if(duplicat!=null) {
				ok = 0;
				if(array[0].equals("touch"))
					if(staticComponent == root)
						System.err.printf("touch: cannot create file %s%s: Node exists\n", staticComponent.getPath(), name);
					else
						System.err.printf("touch: cannot create file %s/%s: Node exists\n", staticComponent.getPath(), name);
				else
					if(staticComponent == root)
						System.err.printf("mkdir: cannot create directory %s%s: Node exists\n", staticComponent.getPath(), name );
					else 
						System.err.printf("mkdir: cannot create directory %s/%s: Node exists\n", staticComponent.getPath(), name );

			}
			else return staticComponent;
		}
		return null;
	}

	public Command touchExecute (String[] array, Component root) {
		Component staticComponent = getStaticComponent(array, root);
		CommandFactory cmd = CommandFactory.getInstance();
		String name = getNewName(array);
		if (staticComponent!=null)
			return cmd.CreateCommand(null, staticComponent,  (Directory) currentDirectory, CommandFactory.CommandType.Touch, name);
		return null;
	}

	public Command mkdirExecute (String[] array, Component root) {
		Component staticComponent = getStaticComponent(array, root);

		CommandFactory cmd = CommandFactory.getInstance();
		String name = getNewName(array);
		if (staticComponent!=null)
			return cmd.CreateCommand(null, staticComponent, (Directory) currentDirectory, CommandFactory.CommandType.Mkdir, name);
		return null;
	}

	public void readInstruction(String instruction, Component root){
		String[] array = instruction.split(" ");
		CommandFactory cmd = CommandFactory.getInstance();
		Command command = null;
		if(array[0].equals("mkdir")) {
			command = mkdirExecute(array, root);

		} else if(array[0].equals("ls")) {
			command = cmd.CreateCommand(array, null, (Directory) currentDirectory, CommandFactory.CommandType.Ls, null);

		} else if(array[0].equals("touch")) {
			command = touchExecute(array, root);

		} else if(array[0].equals("pwd")) {
			command = cmd.CreateCommand(null, currentDirectory, (Directory) currentDirectory, CommandFactory.CommandType.Pwd, "");

		} else if(array[0].equals("rm")) { 
		command = cmd.CreateCommand(array, null, (Directory) currentDirectory, CommandFactory.CommandType.Rm, "");

		} else if(array[0].equals("cp")) {
			command = cmd.CreateCommand(array, currentDirectory, (Directory) currentDirectory, CommandFactory.CommandType.Cp, "");

		} else if(array[0].equals("mv")) {
			command = cmd.CreateCommand(array, null, (Directory) currentDirectory, CommandFactory.CommandType.Mv, "");

		} else if(array[0].equals("cd")) {
			command = cmd.CreateCommand(array, currentDirectory, (Directory) currentDirectory, CommandFactory.CommandType.Cd, "");

		}
		Invoker invoker = new Invoker(command);
			invoker.invoke();
			if( command!=null && command.currentDir!=currentDirectory)
				this.currentDirectory = command.currentDir;
		
	}
}