package com.packtpub.felix.bookshelf.inventory.impl.h2.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.packtpub.felix.bookshelf.inventory.api.BookInventory;
import com.packtpub.felix.bookshelf.inventory.impl.h2.BookInventoryH2Impl;

public class BookInventoryH2ImplActivator implements BundleActivator {

	private ServiceRegistration reg = null;

	public void start(BundleContext context) throws Exception {
		System.out.println("\nStarting Book Inventory H2 Impl");
		this.reg = context.registerService(BookInventory.class.getName(),
				new BookInventoryH2Impl(), null);

	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("\nStoping Book Inventory H2 Impl");
		if (this.reg != null) {
			context.ungetService(reg.getReference());
			this.reg = null;
		}

	}

}
