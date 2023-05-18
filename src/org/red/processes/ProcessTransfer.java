package org.red.processes;

import org.compiere.model.MProcessPara;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Msg;

public class ProcessTransfer extends SvrProcess{

	private int p_A_Asset_ID = 0;
	private int p_M_Locator_ID = 0;
	private int p_M_LocatorTo_ID = 0;
	

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
		log.warning("-------- Here I'm in the prepare() method");
		
		ProcessInfoParameter[] paras = getParameter();
		for (ProcessInfoParameter para : paras) {
			String paraName = para.getParameterName();
			
			if (paraName.equalsIgnoreCase("A_Asset_ID")) {
				p_A_Asset_ID = para.getParameterAsInt();
			}
			else if (paraName.equalsIgnoreCase("M_Locator_ID")) {
				p_M_Locator_ID = para.getParameterAsInt();
			}
			else if (paraName.equalsIgnoreCase("M_LocatorTo_ID")) {
				p_M_LocatorTo_ID = para.getParameterAsInt();
			}else {
//				log.log(Level.SEVERE, "Unknown Parameter: " + paraName);
				MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para);
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		
		
//		log.warning("-------- Here I'm in the doIt() method");
		
		if (p_A_Asset_ID == 0)
			throw new AdempiereUserError (Msg.parseTranslation(getCtx(), "@FillMandatory@ @A_Asset_ID@"));
		
		if (p_M_Locator_ID == 0)
			throw new AdempiereUserError (Msg.parseTranslation(getCtx(), "@FillMandatory@ @M_Locator_ID@"));
		
		if (p_M_LocatorTo_ID == 0)
			throw new AdempiereUserError (Msg.parseTranslation(getCtx(), "@FillMandatory@ @M_LocatorTo_ID@"));
		
		if (p_M_Locator_ID == p_M_LocatorTo_ID)
			throw new AdempiereUserError (Msg.getMsg(getCtx(), "LocatorFromToMustDiffer"));
		
		try {
			M_RED_Asset_Transfer transfer = new M_RED_Asset_Transfer(getCtx(), 0, get_TrxName());
			transfer.setA_Asset_ID(p_A_Asset_ID);
			transfer.setM_Locator_ID(p_M_Locator_ID);
			transfer.setM_LocatorTo_ID(p_M_LocatorTo_ID);
			transfer.saveEx();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "Transfered";
	}

}
