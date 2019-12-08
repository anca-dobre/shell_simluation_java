


public class CommandFactory {

	private static final CommandFactory INSTANCE = new CommandFactory();

	private CommandFactory(){
	}

	public static CommandFactory getInstance(){
		return INSTANCE;
	}

	public enum CommandType
	{
		Mkdir, Ls, Touch, Pwd, Cp, Cd, Mv, Rm;
	}

	public Command CreateCommand (String[] array, Component cmd, Directory currentDir, CommandType ct, String name){
		switch(ct){
			case Mkdir: return new MkdirCommand(cmd, name ,currentDir);
			case Ls: return new LsCommand(array, currentDir);
			case Touch: return new TouchCommand(cmd, name, currentDir);
			case Pwd: return new PwdCommand(cmd, currentDir);
			case Cp: return new CpCommand(array, currentDir);
			case Cd: return new CdCommand(array, currentDir);
			case Mv: return new MvCommand(array, currentDir);
			case Rm: return new RmCommand(array, currentDir);
		}
        throw new IllegalArgumentException("Command type not found");
	}

}
