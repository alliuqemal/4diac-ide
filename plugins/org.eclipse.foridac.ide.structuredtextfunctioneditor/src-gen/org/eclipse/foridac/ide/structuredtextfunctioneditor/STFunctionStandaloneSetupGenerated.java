/*
 * generated by Xtext 2.25.0
 */
package org.eclipse.foridac.ide.structuredtextfunctioneditor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.structuredtextcore.STCoreStandaloneSetup;
import org.eclipse.foridac.ide.structuredtextfunctioneditor.sTFunction.STFunctionPackage;
import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.IResourceServiceProvider;

@SuppressWarnings("all")
public class STFunctionStandaloneSetupGenerated implements ISetup {

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		STCoreStandaloneSetup.doSetup();

		Injector injector = createInjector();
		register(injector);
		return injector;
	}
	
	public Injector createInjector() {
		return Guice.createInjector(new STFunctionRuntimeModule());
	}
	
	public void register(Injector injector) {
		if (!EPackage.Registry.INSTANCE.containsKey("http://www.eclipse.org/foridac/ide/structuredtextfunctioneditor/STFunction")) {
			EPackage.Registry.INSTANCE.put("http://www.eclipse.org/foridac/ide/structuredtextfunctioneditor/STFunction", STFunctionPackage.eINSTANCE);
		}
		IResourceFactory resourceFactory = injector.getInstance(IResourceFactory.class);
		IResourceServiceProvider serviceProvider = injector.getInstance(IResourceServiceProvider.class);
		
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("stfunc", resourceFactory);
		IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put("stfunc", serviceProvider);
	}
}
