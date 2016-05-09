package pt.tecnico.mydrive.presentation;
//imports missing

import java.math.BigInteger;
import java.util.Random;

import pt.tecnico.mydrive.service.WriteFileService;

public class WriteCommand extends Command {

	public WriteCommand(Shell sh) {
		super(sh, "update", "writes text to a file (given the path of that same file).");
	}

	public void execute(String[] args) {

	}
}
