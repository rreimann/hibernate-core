/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2011, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.test.booleans;

import org.junit.Assert;
import org.junit.Test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.testing.TestForIssue;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;

/**
 * @author Robert Reimann
 */
public class BooleanTest extends BaseCoreFunctionalTestCase {
	public static final byte TEST_VALUE = 65;

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class[] {
				BooleanEntity.class
		};
	}

	/**
	 * Test persist and save of Booleans containing null values.
	 */
	@Test
	@TestForIssue( jiraKey = "HHH-6890" )
	public void testBooleanNullValuePersistenceAndRetriveal() {		
		
		// persist with Boolean == null
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		BooleanEntity entity = new BooleanEntity();
		entity.setId(1);
		entity.setBooleanData(null);
		session.persist(entity);
		transaction.commit();
		session.close();

		// verify null value and save after changing Boolean to TRUE
		session = openSession();
		transaction = session.beginTransaction();
		entity = (BooleanEntity) session.get(BooleanEntity.class, entity.getId());
		Assert.assertNotNull(entity);
		Assert.assertNull(entity.getBooleanData());
		entity.setBooleanData(Boolean.TRUE);
		session.update(entity);
		transaction.commit();
		session.close();

		// verify TRUE value and safe after changing back to null
		session = openSession();
		transaction = session.beginTransaction();
		entity = (BooleanEntity) session.get(BooleanEntity.class, entity.getId());
		Assert.assertNotNull(entity);
		Assert.assertEquals(Boolean.TRUE, entity.getBooleanData());
		entity.setBooleanData(null);
		session.update(entity);
		transaction.commit();
		session.close();
	}
	
	/**
	 * Test persist and save of Booleans containing not-null values to rule out side effects.
	 */
	@Test
	public void testBooleanNotNullValuePersistenceAndRetriveal() {		
		
		// persist with Boolean.TRUE
		Session session = openSession();
		Transaction transaction = session.beginTransaction();
		BooleanEntity entity = new BooleanEntity();
		entity.setId(2);
		entity.setBooleanData(Boolean.TRUE);
		session.persist(entity);
		transaction.commit();
		session.close();

		// verify TRUE value and save after changing Boolean to FALSE
		session = openSession();
		transaction = session.beginTransaction();
		entity = (BooleanEntity) session.get(BooleanEntity.class, entity.getId());
		Assert.assertNotNull(entity);
		Assert.assertEquals(Boolean.TRUE, entity.getBooleanData());
		entity.setBooleanData(Boolean.FALSE);
		session.update(entity);
		transaction.commit();
		session.close();

		// verify FALSE value
		session = openSession();
		transaction = session.beginTransaction();
		entity = (BooleanEntity) session.get(BooleanEntity.class, entity.getId());
		Assert.assertNotNull(entity);
		Assert.assertEquals(Boolean.FALSE, entity.getBooleanData());
		transaction.commit();
		session.close();
	}
}
