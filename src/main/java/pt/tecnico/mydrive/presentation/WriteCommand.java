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
		//long token = sh.getToken();  token bem feito
//		long token = new BigInteger(64, new Random()).longValue(); // token temporario
//
//		if (args.length == 2) {
//			WriteFileService wfs = new WriteFileService(token, args[0], args[1]);
//			wfs.execute();
//		} else {
//			throw new RuntimeException("USAGE: <update> <path> <text>");
//		}
	}
}
