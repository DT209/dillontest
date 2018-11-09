package com.hoopladigital.test;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.hoopladigital.derby.DerbyHelper;
import com.hoopladigital.guice.GuiceModule;
import com.hoopladigital.guice.SampleMyBatisModule;
import org.apache.derby.drda.NetworkServerControl;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMapperTest extends AbstractTest {

	private static final Logger log = LoggerFactory.getLogger(AbstractMapperTest.class);

	private static final Injector injector = Guice.createInjector(
			new GuiceModule(),
			new SampleMyBatisModule()
	);

	@Inject
	private DerbyHelper derbyHelper;
	@Inject
	private NetworkServerControl networkServerControl;

	@Inject
	private SqlSessionManager sqlSessionManager;

	@Before
	public void beforeAbstractMapperTest() throws Exception {
		injector.injectMembers(this);
		derbyHelper.init(true);
		sqlSessionManager.startManagedSession();
		DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
	}

	@After
	public void afterAbstractMapperTest() {
		sqlSessionManager.rollback(true);
		sqlSessionManager.close();
		try {
			derbyHelper.destroy(networkServerControl);
		} catch (final Exception e) {
			// this can fail sometimes...if it does, swallow it.
			log.warn(e.toString(), e);
		}
	}

}
