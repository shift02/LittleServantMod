package littleservantmod.client.gui.toptab;

import littleservantmod.entity.EntityLittleServant;

public abstract class AbstractServantTab extends AbstractTab {

	protected EntityLittleServant servant;

	public AbstractServantTab(EntityLittleServant servant) {

		this.servant = servant;

	}

}
