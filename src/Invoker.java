

public class Invoker {

	Command theCommand;
	
	public Invoker(Command theCommand) {
		this.theCommand = theCommand;
	}

	public void invoke() {
		if(theCommand!=null)
		theCommand.execute();
	}
	
}
