package org.red.factories;

import org.adempiere.base.IProcessFactory;
import org.compiere.process.ProcessCall;
import org.red.processes.ProcessTransfer;

public class ProcessTransferFactory implements IProcessFactory{

	@Override
	public ProcessCall newProcessInstance(String className) {
		// TODO Auto-generated method stub
		if(className.equals("org.red.processes.processassettransfer"))
			return new ProcessTransfer();
		return null;
	}

}
